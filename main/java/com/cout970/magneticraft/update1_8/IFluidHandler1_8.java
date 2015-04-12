package com.cout970.magneticraft.update1_8;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import com.cout970.magneticraft.api.util.MgDirection;

public interface IFluidHandler1_8 extends net.minecraftforge.fluids.IFluidHandler{

	//anticipation the 1.8 update
	
	int fillMg(MgDirection from, FluidStack resource, boolean doFill);
	FluidStack drainMg_F(MgDirection from, FluidStack resource, boolean doDrain);
	FluidStack drainMg(MgDirection from, int maxDrain, boolean doDrain);
	boolean canFillMg(MgDirection from, Fluid fluid);
	boolean canDrainMg(MgDirection from, Fluid fluid);
	FluidTankInfo[] getTankInfoMg(MgDirection from);
	
//	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
//		if(this instanceof IFluidHandler1_8)return((IFluidHandler1_8)this).fill(EnumFacing.values()[from.ordinal()], resource, doFill);
//		return 0;
//	}
//
//	public FluidStack drain(ForgeDirection from, FluidStack resource,
//			boolean doDrain) {
//		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).drain(EnumFacing.values()[from.ordinal()], resource,doDrain);
//		return null;
//	}
//
//	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
//		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).drain(EnumFacing.values()[from.ordinal()],maxDrain,doDrain);
//		return null;
//	}
//
//	public boolean canFill(ForgeDirection from, Fluid fluid) {
//		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).canFill(EnumFacing.values()[from.ordinal()],fluid);
//		return false;
//	}
//
//	public boolean canDrain(ForgeDirection from, Fluid fluid) {
//		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).canDrain(EnumFacing.values()[from.ordinal()],fluid);
//		return false;
//	}
//
//	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
//		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).getTankInfo(EnumFacing.values()[from.ordinal()]);
//		return null;
//	}
}
