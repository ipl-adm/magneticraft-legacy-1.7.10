package com.cout970.magneticraft.tileentity;

import codechicken.lib.vec.Cuboid6;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.fluid.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Cypher121 on 12/28/2015.
 */
public class TileIronPipe extends TileBase implements IFluidTransport, IFluidHandler1_8 {
    public static final int MAX_ACCEPT = 360;
    public static final int MAX_EXTRACT = 360;
    public static List<Cuboid6> boxes = new ArrayList<>();

    static {
        double w = 0.2;
        boxes.clear();
        boxes.add(new Cuboid6(0.5 - w, 0, 0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w));//up
        boxes.add(new Cuboid6(0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w, 1, 0.5 + w));//down
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0, 0.5 + w, 0.5 + w, 0.5 - w));//north
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w, 1));//south
        boxes.add(new Cuboid6(0, 0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w));//west
        boxes.add(new Cuboid6(0.5 + w, 0.5 - w, 0.5 - w, 1, 0.5 + w, 0.5 + w));//east
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w));//base
    }

    public TankMg buffer;
    public Map<MgDirection, TankConnection> tanks = new HashMap<>();
    public ConnectionMode[] side = {ConnectionMode.OUTPUT, ConnectionMode.OUTPUT, ConnectionMode.OUTPUT, ConnectionMode.OUTPUT, ConnectionMode.OUTPUT, ConnectionMode.OUTPUT};//sides input and output
    public boolean[] locked = new boolean[6];
    public boolean toUpdate = true;
    public boolean[] connections = new boolean[7];
    private FluidNetwork net;

    @Override
    public void updateEntity() {
        if (worldObj.isRemote) return;
        if (net == null) {
            boolean hasNetwork = false;
            for (MgDirection dir : MgDirection.values()) {
                TileEntity e = MgUtils.getTileEntity(this, dir);
                if (FluidUtils.isPipe(e)) {
                    IFluidTransport it = FluidUtils.getFluidTransport(e);
                    if (it != null && isCompatible(it)) {
                        if (this.canConnectOnSide(dir) && it.canConnectOnSide(dir.opposite())) {
                            if (it.getNetwork() != null) {
                                if (!hasNetwork) {
                                    setNetwork(it.getNetwork());
                                    getNetwork().addPipe(this);
                                    hasNetwork = true;
                                } else {
                                    getNetwork().mergeWith(it.getNetwork());
                                }
                            }
                        }
                    }
                }
            }
            if (!hasNetwork) {
                setNetwork(FluidNetwork.create(this, this));
            }
            if (net != null && !net.getPipes().contains(this)) net.addPipe(this);
            getNetwork().refresh();
        }
        if (toUpdate) {
            updateConnections();
            boolean redstone = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
            for (MgDirection d : MgDirection.values()) {
                if (!locked[d.ordinal()] && connections[d.ordinal()]) {
                    if (redstone) side[d.ordinal()] = ConnectionMode.INPUT;
                    else side[d.ordinal()] = ConnectionMode.OUTPUT;
                }
            }
            toUpdate = false;
        }
        if (worldObj.isRemote) return;
        if (worldObj.getTotalWorldTime() % 20 == 3) sendUpdateToClient();
        if (getNetwork() == null) return;
        for (MgDirection d : MgDirection.values()) {
            if (connections[d.ordinal()]) {
                if (side[d.ordinal()] == ConnectionMode.OUTPUT) {
                    int total = getNetwork().getFluidAmount();//amount in total
                    int toD = Math.min(MAX_ACCEPT, total);//amount allowed to transfer
                    if (toD <= 0) continue;
                    if (getNetwork().getFluid() == null || !FluidRegistry.isFluidRegistered(getNetwork().fluid.getName()))
                        continue;
                    TankConnection t = tanks.get(d);//get the tank
                    if (t != null) {
                        toD = Math.min(toD, t.fill(t.side, new FluidStack(getNetwork().fluid, toD), false));//min (this can transfer and tank can transfer)
                        if (toD > 0) {
                            FluidStack df = drainMg(t.side.opposite(), toD, true);//drain from the network
                            if (df != null) {
                                t.fill(t.side, df, true);//fill the tank
                            }
                        }
                    }
                } else if (side[d.ordinal()] == ConnectionMode.INPUT) {
                    TankConnection t = tanks.get(d);//get tank
                    if (t != null) {
                        FluidStack f = t.drain(t.side, MAX_EXTRACT, false);//simulated extraction
                        if (f != null && f.getFluid() != null && (getNetwork().getFluid() == null || getNetwork().getFluid().getName().equals(f.getFluid().getName()))) {
                            int space = getNetwork().getCapacity() - getNetwork().getFluidAmount();//space for fluid in the network
                            int toD = Math.min(f.amount, space);//min space, fluid
                            if (toD > 0) {
                                FluidStack c = t.drain(t.side, toD, false);
                                int filled = fillMg(MgDirection.UP, c, true);
                                t.drain(t.side, filled, true);
                            }
                        }
                    }
                }
            }
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        ((TankMg) getTank()).writeToNBT(nbt, "net");
        for (int d = 0; d < 6; d++) {
            nbt.setInteger("s" + d, side[d].ordinal());
        }
        for (int d = 0; d < 6; d++) {
            nbt.setBoolean("l" + d, locked[d]);
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        ((TankMg) getTank()).readFromNBT(nbt, "net");
        if (net != null && net.fluid == null) {
            if (getTank().getFluid() != null) net.fluid = getTank().getFluid().getFluid();
        }
    }

    @Override
    public TileEntity getTileEntity() {
        return this;
    }

    @Override
    public void onNetworkUpdate() {
    }

    @Override
    public FluidNetwork getNetwork() {
        return net;
    }

    @Override
    public void setNetwork(FluidNetwork fluidNetwork) {
        net = fluidNetwork;
    }

    public void updateConnections() {
        tanks.clear();
        connections = new boolean[6];//connection cable
        for (MgDirection d : MgDirection.values()) {

            TileEntity t = MgUtils.getTileEntity(this, d);
            if (FluidUtils.isPipe(t)) {
                IFluidTransport trans = FluidUtils.getFluidTransport(t);
                if (isCompatible(trans) && this.canConnectOnSide(d) && FluidUtils.getFluidTransport(t).canConnectOnSide(d.opposite()))
                    connections[d.ordinal()] = true;
            } else if (t instanceof IFluidHandler) {
                if (this.canConnectOnSide(d)) {
                    connections[d.ordinal()] = true;
                    tanks.put(d, new TankConnection((IFluidHandler) t, d.opposite()));
                }
            }
        }
    }

    @Override
    public IFluidTank getTank() {
        if (buffer == null) buffer = new TankMg(this, MAX_EXTRACT);
        return buffer;
    }

    @Override
    public boolean canConnectOnSide(MgDirection dir) {
        return true;
    }

    @Override
    public boolean isCompatible(IFluidTransport a) {
        return true;
    }

    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return this.fillMg(MgDirection.getDirection(from.ordinal()), resource, doFill);
    }

    public FluidStack drain(ForgeDirection from, FluidStack resource,
                            boolean doDrain) {
        return this.drainMg_F(MgDirection.getDirection(from.ordinal()), resource, doDrain);
    }

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return this.drainMg(MgDirection.getDirection(from.ordinal()), maxDrain, doDrain);
    }

    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return this.canFillMg(MgDirection.getDirection(from.ordinal()), fluid);
    }

    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return this.canDrainMg(MgDirection.getDirection(from.ordinal()), fluid);
    }

    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return this.getTankInfoMg(MgDirection.getDirection(from.ordinal()));
    }

    @Override
    public int fillMg(MgDirection from, FluidStack resource, boolean doFill) {
        if (getNetwork() == null) return 0;
        return getNetwork().manager.fillMg(from, resource, doFill);
    }

    @Override
    public FluidStack drainMg_F(MgDirection from, FluidStack resource,
                                boolean doDrain) {
        if (resource == null) return null;
        if (getNetwork() == null) return null;
        return getNetwork().manager.drainMg_F(from, resource, doDrain);
    }

    @Override
    public FluidStack drainMg(MgDirection from, int maxDrain, boolean doDrain) {
        if (getNetwork() == null) return null;
        return getNetwork().manager.drainMg(from, maxDrain, doDrain);
    }

    @Override
    public boolean canFillMg(MgDirection from, Fluid fluid) {
        return getNetwork() != null && getNetwork().manager.canFillMg(from, fluid);
    }

    @Override
    public boolean canDrainMg(MgDirection from, Fluid fluid) {
        return getNetwork() != null && getNetwork().manager.canDrainMg(from, fluid);
    }

    @Override
    public FluidTankInfo[] getTankInfoMg(MgDirection from) {
        return new FluidTankInfo[]{new FluidTankInfo(getTank())};
    }

    public enum ConnectionMode {
        OUTPUT, INPUT, NOTHING;

        public static ConnectionMode getNext(ConnectionMode c) {
            if (c == null) return OUTPUT;
            if (c == OUTPUT) return INPUT;
            if (c == INPUT) return NOTHING;
            return OUTPUT;
        }
    }
}