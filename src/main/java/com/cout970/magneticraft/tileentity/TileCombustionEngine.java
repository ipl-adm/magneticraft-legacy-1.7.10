package com.cout970.magneticraft.tileentity;

import buildcraft.api.fuels.BuildcraftFuelRegistry;
import buildcraft.api.fuels.IFuel;
import com.cout970.magneticraft.ManagerFluids;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.BufferedConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.fluids.BlockFuel;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileCombustionEngine extends TileConductorLow implements IFluidHandler1_8, IHeatTile, IGuiSync {

    private TankMg tank = new TankMg(this, 4000);
    public IHeatConductor heat = new HeatConductor(this, 600, 800);
    private float buffer;
    private float prod, counter;
    private int maxProd;
    private int oldHeat;

    BlockFuel fuel;
    IFuel fuelBC;

    public TankMg getTank() {
        return tank;
    }

    public void updateEntity() {
        if (ManagerIntegration.BUILDCRAFT) {
            updateEntityBC();
            return;
        }


        super.updateEntity();

        if (worldObj.isRemote) return;
        heat.iterate();
        if (((int) heat.getTemperature()) != oldHeat && worldObj.getTotalWorldTime() % 10 == 0) {
            sendUpdateToClient();
            oldHeat = (int) heat.getTemperature();
        }
        if (buffer > 0 && cond.getVoltage() < ElectricConstants.MAX_VOLTAGE && heat.getTemperature() < 500 && isControlled() && fuel != null) {
            float speed = getSpeed();
            double p = EnergyConverter.RFtoW(fuel.getPowerPerCycle()) * speed;
            buffer -= speed;
            counter += p;
            cond.applyPower(p);
            heat.applyCalories(EnergyConverter.RFtoCALORIES(0.2));
        }

        if (buffer <= 0 || (fuel == null && getTank().getFluidAmount() >= 10)) {
            FluidStack fluid = getTank().getFluid();
            if (fluid != null) {
                BlockFuel f = ManagerFluids.fuels.get(fluid.getFluid());
                if (f != null) {
                    buffer += f.getTotalBurningTime() / 100;
                    fuel = f;
                    maxProd = (int) EnergyConverter.RFtoW(f.getPowerPerCycle());
                    getTank().drain(10, true);
                }
            }
        }
    }

    public void updateEntityBC() {
        super.updateEntity();

        if (worldObj.isRemote) return;
        heat.iterate();
        if (((int) heat.getTemperature()) != oldHeat && worldObj.provider.getWorldTime() % 10 == 0) {
            sendUpdateToClient();
            oldHeat = (int) heat.getTemperature();
        }
        if (buffer > 0 && cond.getVoltage() < ElectricConstants.MAX_VOLTAGE && heat.getTemperature() < 500 && isControlled() && fuelBC != null) {
            float speed = getSpeed();
            double p = EnergyConverter.RFtoW(fuelBC.getPowerPerCycle()) * speed;
            buffer -= speed;
            counter += p;
            cond.applyPower(p);
            heat.applyCalories(EnergyConverter.RFtoCALORIES(0.2));
        }

        if (buffer <= 0 || (fuelBC == null && getTank().getFluidAmount() >= 10)) {
            FluidStack fluid = getTank().getFluid();
            if (fluid != null) {
                IFuel f = BuildcraftFuelRegistry.fuel.getFuel(fluid.getFluid());
                if (f != null) {
                    buffer += f.getTotalBurningTime() / 100;
                    fuelBC = f;
                    maxProd = (int) EnergyConverter.RFtoW(f.getPowerPerCycle());
                    getTank().drain(10, true);
                }
            }
        }

        if (worldObj.getTotalWorldTime() % 20 == 0) {
            prod = counter / 20;
            counter = 0;
        }
    }

    private float getSpeed() {
        return 1f - ((int) (((heat.getTemperature() - 25) * 0.128f)) / 64f);
    }

    @Override
    public int fillMg(MgDirection from, FluidStack resource, boolean doFill) {
        return getTank().fill(resource, doFill);
    }

    @Override
    public FluidStack drainMg_F(MgDirection from, FluidStack resource, boolean doDrain) {
        return drainMg(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drainMg(MgDirection from, int maxDrain, boolean doDrain) {
        return getTank().drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFillMg(MgDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrainMg(MgDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfoMg(MgDirection from) {
        return new FluidTankInfo[]{getTank().getInfo()};
    }

    @Override
    public IElectricConductor initConductor() {
        return new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW, 80000, ElectricConstants.GENERATOR_DISCHARGE, ElectricConstants.GENERATOR_CHARGE);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        buffer = nbt.getFloat("Buffer");
        getTank().readFromNBT(nbt);
        heat.load(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setFloat("Buffer", buffer);
        getTank().writeToNBT(nbt);
        heat.save(nbt);
    }

    @Override
    public IHeatConductor[] getHeatCond(VecInt c) {
        return new IHeatConductor[]{heat};
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, (cond.getStorage() & 0xFFFF));
        craft.sendProgressBarUpdate(cont, 2, ((cond.getStorage() & 0xFFFF0000) >>> 16));
        craft.sendProgressBarUpdate(cont, 3, (int) heat.getTemperature());
        if (getTank().getFluidAmount() > 0) {
            craft.sendProgressBarUpdate(cont, 4, getTank().getFluid().getFluidID());
            craft.sendProgressBarUpdate(cont, 5, getTank().getFluidAmount());
        } else {
            craft.sendProgressBarUpdate(cont, 4, -1);
        }
        craft.sendProgressBarUpdate(cont, 6, (int) prod * 100);
        craft.sendProgressBarUpdate(cont, 7, maxProd);
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
            heat.setTemperature(value);
        if (id == 4) {
            if (value == -1) {
                getTank().setFluid(null);
            } else {
                getTank().setFluid(new FluidStack(FluidRegistry.getFluid(value), 1));
            }
        }
        if (id == 5) {
            getTank().setFluid(new FluidStack(getTank().getFluid(), value));
        }
        if (id == 6)
            prod = value / 100f;
        if (id == 7)
            maxProd = value;
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

    public IBarProvider getProductionBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return "Generating: " + prod + "W";
            }

            @Override
            public float getMaxLevel() {
                return Math.max(maxProd, 1);
            }

            @Override
            public float getLevel() {
                return prod;
            }
        };
    }
}
