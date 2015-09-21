package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Base;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.CubeRenderer_Util;
import com.cout970.magneticraft.util.fluid.TankMg;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;


public class TileRefineryTank extends TileMB_Base implements IFluidHandler1_8 {

    private TankMg tank = new TankMg(this, 4000);
    public CubeRenderer_Util CubeRenderer;
    private int oldAmount;

    public TankMg getTank() {
        return tank;
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.getTotalWorldTime() % 20 == 0 && (oldAmount != getTank().getFluidAmount() || worldObj.getTotalWorldTime() % 2000 == 0)) {
            oldAmount = getTank().getFluidAmount();
            sendUpdateToClient();
        }
    }

    @Override
    public int fillMg(MgDirection from, FluidStack resource, boolean doFill) {
        return getTank().fill(resource, doFill);
    }

    @Override
    public FluidStack drainMg_F(MgDirection from, FluidStack resource, boolean doDrain) {
        return getTank().drain(resource.amount, doDrain);
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
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        tank.readFromNBT(nbtTagCompound, "fluid");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        tank.writeToNBT(nbtTagCompound, "fluid");
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
}
