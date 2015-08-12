package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.pressure.IPressureConductor;
import com.cout970.magneticraft.api.pressure.prefab.PressureConductor;
import com.cout970.magneticraft.util.tile.TilePressure;

import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TilePressureTank extends TilePressure implements IFluidHandler{

	@Override
	public IPressureConductor initConductor() {
		return new PressureConductor(this, 16000);
	}
	
	public void updateEntity(){
		super.updateEntity();		
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(pressure.getPressure() < pressure.getMaxPressure())
			return pressure.applyGas(resource);
		return 0;
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return pressure.drainGas(resource.amount);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return pressure.drainGas(maxDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return (pressure.getFluid() == null || pressure.getFluid().equals(fluid)) && pressure.getPressure() < pressure.getMaxPressure();
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return pressure.getFluid() != null && pressure.getFluid() == fluid;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[]{};
	}
}
