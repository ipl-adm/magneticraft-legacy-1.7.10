package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.CubeRenderer_Util;
import com.cout970.magneticraft.util.fluid.TankMg;


public class TileRefineryTank extends TileMB_Base implements IFluidHandler1_8{

	private TankMg tank = new TankMg(this, 4000);
	public CubeRenderer_Util CubeRenderer;
	private int oldAmount;
	
	public TankMg getTank(){
		return tank;
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.getTotalWorldTime()%20 == 0 && (oldAmount != getTank().getFluidAmount() || worldObj.getTotalWorldTime()%2000 == 0)){
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
		if(this instanceof IFluidHandler1_8)return((IFluidHandler1_8)this).fillMg(MgDirection.getDirection(from.ordinal()), resource, doFill);
		return 0;
	}

	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).drainMg_F(MgDirection.getDirection(from.ordinal()), resource,doDrain);
		return null;
	}

	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).drainMg(MgDirection.getDirection(from.ordinal()),maxDrain,doDrain);
		return null;
	}

	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).canFillMg(MgDirection.getDirection(from.ordinal()),fluid);
		return false;
	}

	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).canDrainMg(MgDirection.getDirection(from.ordinal()),fluid);
		return false;
	}

	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).getTankInfoMg(MgDirection.getDirection(from.ordinal()));
		return null;
	}
}
