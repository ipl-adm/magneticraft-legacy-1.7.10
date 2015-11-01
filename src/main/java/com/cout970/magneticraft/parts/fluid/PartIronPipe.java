package com.cout970.magneticraft.parts.fluid;

import buildcraft.api.tools.IToolWrench;
import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.ISidedHollowConnect;
import codechicken.multipart.NormallyOccludedPart;
import codechicken.multipart.TMultiPart;
import cofh.api.item.IToolHammer;
import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.tool.IWrench;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.client.tilerender.TileRenderPipeBronce;
import com.cout970.magneticraft.util.fluid.FluidUtils;
import com.cout970.magneticraft.util.fluid.IFluidTransport;
import com.cout970.magneticraft.util.fluid.TankConnection;
import com.cout970.magneticraft.util.fluid.TankMg;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.fluids.*;

import java.util.*;


public class PartIronPipe extends PartFluidPipe implements ISidedHollowConnect {

    public boolean[] connections = new boolean[7];
    public static List<Cuboid6> boxes = new ArrayList<>();

    public PartIronPipe() {
        super(ManagerItems.part_iron_pipe);
    }

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

    @Override
    public Iterable<IndexedCuboid6> getSubParts() {
        Iterable<Cuboid6> boxList = getCollisionBoxes();
        LinkedList<IndexedCuboid6> partList = new LinkedList<>();
        for (Cuboid6 c : boxList)
            partList.add(new IndexedCuboid6(0, c));
        ((ArrayList<Cuboid6>) boxList).clear();
        return partList;
    }

    @Override
    public List<Cuboid6> getOcclusionCubes() {
        return Collections.singletonList(boxes.get(6));
    }

    @Override
    public List<Cuboid6> getCollisionCubes() {
        ArrayList<Cuboid6> t2 = new ArrayList<>();
        t2.add(boxes.get(6));
        for (int i = 0; i < 6; i++) {
            if (connections[i]) {// && side[i] != ConnectionMode.NOTHING){
                t2.add(boxes.get(i));
            }
        }
        return t2;
    }

    private TileRenderPipeBronce render;

    @Override
    public void renderPart(Vector3 pos) {
        if (render == null) render = new TileRenderPipeBronce();
        render.render(this, pos);
    }

    @Override
    public int getHollowSize(int arg0) {
        return 6;
    }

    public static final int MAX_ACCEPT = 360;
    public static final int MAX_EXTRACT = 360;
    public Map<MgDirection, TankConnection> tanks = new HashMap<>();
    public ConnectionMode[] side = {ConnectionMode.OUTPUT, ConnectionMode.OUTPUT, ConnectionMode.OUTPUT, ConnectionMode.OUTPUT, ConnectionMode.OUTPUT, ConnectionMode.OUTPUT};//sides input and output
    public boolean[] locked = new boolean[6];
    public boolean toUpdate = true;


    public void updateConnections() {
        if (tile() == null) return;
        tanks.clear();
        connections = new boolean[6];//connection cable
        for (MgDirection d : MgDirection.values()) {

            TileEntity t = MgUtils.getTileEntity(tile(), d);
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
        if (buffer == null) buffer = new TankMg(tile(), MAX_EXTRACT);
        return buffer;
    }

    @Override
    public void update() {
        super.update();
        if (toUpdate) {
            updateConnections();
            boolean redstone = world().isBlockIndirectlyGettingPowered(x(), y(), z());
            for (MgDirection d : MgDirection.values()) {
                if (!locked[d.ordinal()] && connections[d.ordinal()]) {
                    if (redstone) side[d.ordinal()] = ConnectionMode.INPUT;
                    else side[d.ordinal()] = ConnectionMode.OUTPUT;
                }
            }
            toUpdate = false;
        }
        if (world().isRemote) return;
        if (world().getTotalWorldTime() % 20 == 3) sendDescUpdate();
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

    public void save(NBTTagCompound nbt) {
        super.save(nbt);
        for (int d = 0; d < 6; d++) {
            nbt.setInteger("s" + d, side[d].ordinal());
        }
        for (int d = 0; d < 6; d++) {
            nbt.setBoolean("l" + d, locked[d]);
        }
    }

    public void load(NBTTagCompound nbt) {
        super.load(nbt);
        for (int d = 0; d < 6; d++) {
            side[d] = ConnectionMode.values()[nbt.getInteger("s" + d)];
        }
        for (int d = 0; d < 6; d++) {
            locked[d] = nbt.getBoolean("l" + d);
        }
    }

    public void writeDesc(MCDataOutput p) {
        super.writeDesc(p);
        for (int d = 0; d < 6; d++) {
            p.writeInt(side[d].ordinal());
        }
        for (int d = 0; d < 6; d++) {
            p.writeBoolean(locked[d]);
        }
    }

    public void readDesc(MCDataInput p) {
        super.readDesc(p);
        for (int d = 0; d < 6; d++) {
            side[d] = ConnectionMode.values()[p.readInt()];
        }
        for (int d = 0; d < 6; d++) {
            locked[d] = p.readBoolean();
        }
    }

    @Override
    public void onNeighborChanged() {
        toUpdate = true;
        if (getNetwork() != null) {
            getNetwork().refresh();
        }
    }

    @Override
    public void onPartChanged(TMultiPart part) {
        onNeighborChanged();
    }

    @Override
    public void onAdded() {
        onNeighborChanged();
    }

    @Override
    public void onRemoved() {
        if (getNetwork() != null)
            getNetwork().excludeAndRecalculate(this);
    }

    public boolean activate(EntityPlayer player, MovingObjectPosition hit, ItemStack item) {
        if ((item != null) && MgUtils.isWrench(item.getItem())) {
            Vector3 v = new Vector3(hit.hitVec.xCoord - hit.blockX, hit.hitVec.yCoord - hit.blockY, hit.hitVec.zCoord - hit.blockZ);
            for (MgDirection d : MgDirection.values()) {
                if (connections[d.ordinal()]) {
                    if (isHit(boxes.get(d.ordinal()), v)) {
                        side[d.ordinal()] = ConnectionMode.getNext(side[d.ordinal()]);
                        locked[d.ordinal()] = true;
                        if (!world().isRemote) this.sendDescUpdate();
                    }
                }
            }
        }
        return false;
    }

    public static boolean isHit(Cuboid6 c, Vector3 v) {
        if (c == null || v == null) return false;
        if ((float) c.max.y == (float) v.y || (float) c.min.y == (float) v.y) {
            if ((c.min.x <= v.x) && (c.max.x >= v.x)) {
                if ((c.min.z <= v.z) && (c.max.z >= v.z)) {
                    return true;
                }
            }
        }
        if ((float) c.max.x == (float) v.x || (float) c.min.x == (float) v.x) {
            if ((c.min.y <= v.y) && (c.max.y >= v.y)) {
                if ((c.min.z <= v.z) && (c.max.z >= v.z)) {
                    return true;
                }
            }
        }
        if ((float) c.max.z == (float) v.z || (float) c.min.z == (float) v.z) {
            if ((c.min.x <= v.x) && (c.max.x >= v.x)) {
                if ((c.min.y <= v.y) && (c.max.y >= v.y)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean canConnectOnSide(MgDirection d) {
        return tile().canAddPart(new NormallyOccludedPart(boxes.get(d.ordinal())));
    }

    @Override
    public boolean isCompatible(IFluidTransport a) {
        return a instanceof PartIronPipe;
    }
}