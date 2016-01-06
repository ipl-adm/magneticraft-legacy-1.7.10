package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.tile.TileHeatConductor;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileBoiler extends TileHeatConductor implements IFluidHandler1_8, IGuiSync {

    public static final int MAX_STEAM = 80;
    public TankMg water = new TankMg(this, 2000);
    public TankMg steam = new TankMg(this, 8000);
    public int produce;

    @Override
    public IHeatConductor initHeatCond() {
        return new HeatConductor(this, 1400.0D, 1000.0D);
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        if (heat.getTemperature() > 100) {
            int cs = Math.min(water.getFluidAmount(), EnergyConverter.STEAMtoWATER(steam.getSpace()));//calcs in water mount
            int boil = Math.min(Math.min(cs, EnergyConverter.STEAMtoWATER(MAX_STEAM)), ((int) heat.getTemperature() - 100));
            produce = EnergyConverter.WATERtoSTEAM(boil);
            if (boil > 0) {
                water.drain(boil, true);
                steam.fill(FluidRegistry.getFluidStack("steam", EnergyConverter.WATERtoSTEAM(boil)), true);
                heat.drainCalories(EnergyConverter.WATERtoSTEAM_HEAT(boil));
            }
        }

        //TODO change this to only use tileEntities from 3 directions instead the 6 sides, "MgC boilers produces about 1.4% of the lag of the server" asie
        MgUtils.getNeig(this).stream().filter(t -> t instanceof TileBoiler).forEach(t -> {
            TileBoiler b = (TileBoiler) t;
            int dif = water.getFluidAmount() - b.water.getFluidAmount();
            if (dif > 0) {
                int pass = dif / 2;
                if (pass > 0) {
                    FluidStack d = water.drain(pass, false);
                    int a = b.water.fill(d, true);
                    water.drain(a, true);
                }
            }
            int dif2 = steam.getFluidAmount() - b.steam.getFluidAmount();
            if (dif2 > 0) {
                int pass = dif2 / 2;
                if (pass > 0) {
                    FluidStack d = steam.drain(pass, false);
                    int a = b.steam.fill(d, true);
                    steam.drain(a, true);
                }
            }
        });
    }

    @Override
    public int fillMg(MgDirection from, FluidStack resource, boolean doFill) {
        if (resource != null && resource.getFluid() == FluidRegistry.getFluid("water"))
            return water.fill(resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drainMg_F(MgDirection from, FluidStack resource,
                                boolean doDrain) {
        if (resource != null && resource.getFluid() == FluidRegistry.getFluid("steam"))
            return steam.drain(resource.amount, doDrain);
        return null;
    }

    @Override
    public FluidStack drainMg(MgDirection from, int maxDrain, boolean doDrain) {
        return steam.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFillMg(MgDirection from, Fluid fluid) {
        return fluid == FluidRegistry.WATER;
    }

    @Override
    public boolean canDrainMg(MgDirection from, Fluid fluid) {
        return fluid == FluidRegistry.getFluid("steam");
    }

    @Override
    public FluidTankInfo[] getTankInfoMg(MgDirection from) {
        return new FluidTankInfo[]{water.getInfo(), steam.getInfo()};
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        water.readFromNBT((NBTTagCompound) nbt.getTag("water"));
        steam.readFromNBT((NBTTagCompound) nbt.getTag("steam"));
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);

        NBTTagCompound nbt2 = new NBTTagCompound();
        water.writeToNBT(nbt2);
        nbt.setTag("water", nbt2);


        NBTTagCompound nbt3 = new NBTTagCompound();
        steam.writeToNBT(nbt3);
        nbt.setTag("steam", nbt3);
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting c) {
        c.sendProgressBarUpdate(cont, 0, (int) Math.ceil(heat.getTemperature()));
        c.sendProgressBarUpdate(cont, 1, steam.getFluidAmount());
        c.sendProgressBarUpdate(cont, 2, water.getFluidAmount());
        c.sendProgressBarUpdate(cont, 3, produce);
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 0) heat.setTemperature(value);
        if (id == 1) steam.setFluid(FluidRegistry.getFluidStack("steam", value));
        if (id == 2) water.setFluid(FluidRegistry.getFluidStack("water", value));
        if (id == 3) produce = value;
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

    public IBarProvider getProductionBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return "Steam produced: " + produce + "mB/t";
            }

            @Override
            public float getMaxLevel() {
                return MAX_STEAM;
            }

            @Override
            public float getLevel() {
                return produce;
            }
        };
    }
}
