package com.cout970.magneticraft.tileentity.multiblock.controllers;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.*;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.tileentity.TileCopperTank;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Base;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.tile.AverageBar;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileSteamTurbineControl extends TileMB_Base implements IGuiSync {

    private static final int MAX_STEAM = 1200;
    public TankMg[] in = new TankMg[4];
    public IElectricConductor out;
    public AverageBar energy = new AverageBar(20);
    public AverageBar steam = new AverageBar(20);
    public int drawCounter;
    public float animation;
    public float speed;
    private long time;
    private double[] flow = new double[1];
    public IElectricConductor cond = new ElectricConductor(this, 1, ElectricConstants.RESISTANCE_COPPER_MED) {
        @Override
        public double getVoltageCapacity() {
            return ElectricConstants.MACHINE_CAPACITY*10;
        }

    };

    private void updateConductor() {
        if (out == null) return;
        ElectricConductor.valance(cond, out, flow, 0);
    }

    public boolean isActive() {
        return getBlockMetadata() > 6;
    }

    public void updateEntity() {
        super.updateEntity();
        if (drawCounter > 0) drawCounter--;
        if (!isActive())
            return;
        if (in[0] == null || out == null || worldObj.getTotalWorldTime() % 200 == 0) {
            search();
            return;
        }
        float activity = 0.5f * (getFluidAmount() / 64000f);
        if (activity < 0.01f) activity = 0;
        if (speed < activity) {
            speed += 1 / 32f;
        } else if (speed > activity) {
            speed -= 1 / 32f;
        }
        if (worldObj.isRemote) return;
        energy.tick();
        steam.tick();
        updateConductor();
        balanceTanks();
        double miss = (ElectricConstants.MAX_VOLTAGE - cond.getVoltage() / cond.getVoltageMultiplier()) * 200;
        int steam = (int) Math.min(Math.min(getFluidAmount() > 1000 ? getFluidAmount() + 1000 : getFluidAmount(), miss), MAX_STEAM);
        steam = (int) Math.min(steam, ((getFluidAmount() + 1000) / 64000f) * MAX_STEAM);
        if (steam > 0 && cond.getVoltage() < ElectricConstants.MAX_VOLTAGE * 100) {
            drain(steam, true);
            double power = EnergyConverter.STEAMtoW(steam);
            cond.applyPower(power);
            energy.addValue((float) power);
            this.steam.addValue(steam);
        }
    }

    private void balanceTanks() {
        int sum = getFluidAmount();
        int rest = sum % 4;
        for (TankMg t : in) {
            if (t == null) {
                continue;
            }
            t.setFluid(null);
            t.fill(FluidRegistry.getFluidStack("steam", sum / 4), true);
            if (rest > 0)
                rest -= t.fill(FluidRegistry.getFluidStack("steam", rest), true);
        }
    }

    public void drain(int steam, boolean b) {
        for (int i = 0; i < 4; i++) {
            if (in[i].getFluid() != null && in[i].getFluid().getFluid() == FluidRegistry.getFluid("steam")) {
                int extract = Math.min(in[i].getFluidAmount(), steam);
                in[i].drain(extract, b);
                steam -= extract;
            }
        }
    }

    public int getFluidAmount() {
        int steam = 0;
        for (int i = 0; i < 4; i++) {
            if (in[i] != null && in[i].getFluid() != null && in[i].getFluid().getFluid() == FluidRegistry.getFluid("steam"))
                steam += in[i].getFluidAmount();
        }
        return steam;
    }

    private void search() {
        VecInt vec = getDirection().toVecInt().getOpposite();
        TileEntity t = MgUtils.getTileEntity(this, vec.copy().add(getDirection().opposite().step(MgDirection.UP).toVecInt()));
        if (t instanceof TileCopperTank) {
            in[0] = ((TileCopperTank) t).getTank();
        }
        t = MgUtils.getTileEntity(this, vec.copy().add(getDirection().opposite().step(MgDirection.DOWN).toVecInt()));
        if (t instanceof TileCopperTank) {
            in[1] = ((TileCopperTank) t).getTank();
        }
        t = MgUtils.getTileEntity(this, vec.copy().multiply(2).add(getDirection().opposite().step(MgDirection.UP).toVecInt()));
        if (t instanceof TileCopperTank) {
            in[2] = ((TileCopperTank) t).getTank();
        }
        t = MgUtils.getTileEntity(this, vec.copy().multiply(2).add(getDirection().opposite().step(MgDirection.DOWN).toVecInt()));
        if (t instanceof TileCopperTank) {
            in[3] = ((TileCopperTank) t).getTank();
        }
        t = MgUtils.getTileEntity(this, vec.copy().add(new VecInt(0, 1, 0)));

        if (t instanceof IElectricTile) {
            out = ((IElectricTile) t).getConds(vec.getOpposite(), 1)[0];
        }
    }

    @Override
    public void onDestroy(World w, VecInt p, Multiblock c, MgDirection e) {
        setActive(false);
        in = new TankMg[4];
        out = null;
    }

    @Override
    public void onActivate(World w, VecInt p, Multiblock c, MgDirection e) {
        setActive(true);
    }

    private void setActive(boolean b) {
        if (b)
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() % 6 + 6, 2);
        else
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() % 6, 2);
    }

    public int getCapacity() {
        return 64000;
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        if (out != null)
            craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, (int) energy.getAverage()*16);
        for (int i = 0; i < 4; i++) {
            if (in[i] != null) {
                if (in[i].getFluidAmount() > 0) {
                    craft.sendProgressBarUpdate(cont, i * 2 + 2, in[i].getFluid().getFluidID());
                    craft.sendProgressBarUpdate(cont, i * 2 + 3, in[i].getFluidAmount());
                } else {
                    craft.sendProgressBarUpdate(cont, i * 2 + 2, -1);
                }
            }
        }
        craft.sendProgressBarUpdate(cont, 10, (int) steam.getAverage()*16);
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        switch (id) {
            case 0:
                cond.setVoltage(value);
                break;
            case 1:
                energy.setStorage(value / 16f);
                break;
            case 10:
                steam.setStorage(value / 16f);
                break;
            default:
                if (id >= 2 && id <= 9) {
                    int i = (id - 2) / 2;
                    if (in[i] != null) {
                        if (id - i * 2 + 2 != 0) {
                            if (in[i].getFluid() != null)
                                in[i].setFluid(new FluidStack(in[i].getFluid(), value));
                        } else {
                            if (value == -1)
                                in[i].setFluid(null);
                            else
                                in[i].setFluid(new FluidStack(FluidRegistry.getFluid(value), 1));
                        }
                    }
                }
                break;
        }

    }

    @Override
    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata() % 6);
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        VecInt v1 = VecIntUtil.getRotatedOffset(getDirection().opposite(), -1, -1, 0);
        VecInt v2 = VecIntUtil.getRotatedOffset(getDirection().opposite(), 1, 1, 4);
        VecInt block = new VecInt(xCoord, yCoord, zCoord);

        return VecIntUtil.getAABBFromVectors(v1.add(block), v2.add(block));
    }

    public float getDelta() {
        long aux = time;
        time = System.nanoTime();
        return time - aux;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        NBTTagList conduit = nbt.getTagList("Capacity_cond", 10);
        NBTTagCompound conduit_nbt = conduit.getCompoundTagAt(0);
        cond.load(conduit_nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        NBTTagList conduit = new NBTTagList();
        NBTTagCompound conduit_nbt = new NBTTagCompound();
        cond.save(conduit_nbt);
        conduit.appendTag(conduit_nbt);
        nbt.setTag("Capacity_cond", conduit);
    }

    public IBarProvider getProductionBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return String.format("Generating: %.3f kW", energy.getStorage() / 1000d);
            }

            @Override
            public float getMaxLevel() {
                return (float) EnergyConverter.STEAMtoW(MAX_STEAM);
            }

            @Override
            public float getLevel() {
                return Math.min(energy.getStorage(), getMaxLevel());
            }
        };
    }

    public IBarProvider getSteamConsumptionBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return String.format("Consuming %.1f mB/t", steam.getStorage());
            }

            @Override
            public float getMaxLevel() {
                return (float) MAX_STEAM;
            }

            @Override
            public float getLevel() {
                return Math.min(steam.getStorage(), getMaxLevel());
            }
        };
    }
}
