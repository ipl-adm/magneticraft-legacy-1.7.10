package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.BufferedConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.gui.component.IEnergyTracker;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileSteamEngine extends TileConductorLow implements IFluidHandler1_8, IGuiSync {

    public static final int STEAM_LIMIT = 40;
    public float animation;
    private long time;
    public TankMg tank = new TankMg(this, 6000);
    private boolean working;
    //info
    public int steamConsumition;
    public int electricProduction;
    public float steamConsumitionM;
    public float electricProductionM;

    public float getDelta() {
        long aux = time;
        time = System.nanoTime();
        return (float) ((time - aux) * 1E-6);
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        if (worldObj.getTotalWorldTime() % 20 == 0) {
            steamConsumitionM = steamConsumition;
            electricProductionM = electricProduction;
            steamConsumition = 0;
            electricProduction = 0;
            if (working && !isActive()) {
                setActive(true);
            } else if (!working && isActive()) {
                setActive(false);
            }
        }
        if (tank.getFluidAmount() > 0 && cond.getVoltage() <= ElectricConstants.MAX_VOLTAGE && isControlled()) {
            int steam = Math.min(tank.getFluidAmount(), STEAM_LIMIT);
            if (steam > 0) {
                tank.drain(steam, true);
                cond.applyPower(EnergyConverter.STEAMtoW(steam));
                electricProduction += EnergyConverter.STEAMtoW(steam);
                steamConsumition += steam;
                working = true;
            } else {
                working = false;
            }
        } else {
            working = false;
        }
    }

    private void setActive(boolean b) {
        if (b)
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() + 6, 2);
        else
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() - 6, 2);
    }

    public boolean isActive() {
        return getBlockMetadata() > 5;
    }

    @Override
    public int fillMg(MgDirection from, FluidStack resource, boolean doFill) {
        if (resource != null && FluidRegistry.getFluid("steam") == resource.getFluid()) {
            return tank.fill(resource, doFill);
        }
        return 0;
    }

    @Override
    public FluidStack drainMg_F(MgDirection from, FluidStack resource,
                                boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drainMg(MgDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFillMg(MgDirection from, Fluid fluid) {
        return fluid != null && FluidRegistry.getFluid("steam") == fluid;
    }

    @Override
    public boolean canDrainMg(MgDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfoMg(MgDirection from) {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    @Override
    public IElectricConductor initConductor() {
        return new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW, 80000, ElectricConstants.GENERATOR_DISCHARGE, ElectricConstants.GENERATOR_CHARGE);
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, (cond.getStorage() & 0xFFFF));
        craft.sendProgressBarUpdate(cont, 2, ((cond.getStorage() & 0xFFFF0000) >>> 16));
        craft.sendProgressBarUpdate(cont, 3, tank.getFluidAmount());
        craft.sendProgressBarUpdate(cont, 4, (int) steamConsumitionM);
        craft.sendProgressBarUpdate(cont, 5, (int) (electricProductionM));
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 0)
            cond.setVoltage(value);
        if (id == 1)
            cond.setStorage(value & 0xFFFF);
        if (id == 2)
            cond.setStorage(cond.getStorage() | (value << 16));
        if (id == 3)
            tank.setFluid(FluidRegistry.getFluidStack("steam", value));
        if (id == 4)
            steamConsumitionM = value;
        if (id == 5)
            electricProductionM = value;
    }

    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
            return this.fillMg(MgDirection.getDirection(from.ordinal()), resource, doFill);
    }

    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
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

    public IEnergyTracker getEnergyTracker() {
        return new IEnergyTracker() {

            @Override
            public boolean isConsume() {
                return false;
            }

            @Override
            public float getMaxChange() {
                return (float) (EnergyConverter.STEAMtoW(STEAM_LIMIT));
            }

            @Override
            public float getChangeInTheLastTick() {
                return 0;
            }

            @Override
            public float getChangeInTheLastSecond() {
                return electricProductionM;
            }
        };
    }

}
