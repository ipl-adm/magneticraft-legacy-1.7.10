package com.cout970.magneticraft.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;

public class TileInfiniteWater extends TileBase implements IFluidHandler1_8{

	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		for(MgDirection d : MgDirection.values()){
			TileEntity t = MgUtils.getTileEntity(this, d);
			if(t instanceof IFluidHandler){
				IFluidHandler f = (IFluidHandler) t;
				if(f.canFill(d.getForgeDir(), FluidRegistry.WATER)){
					f.fill(d.getForgeDir().getOpposite(), FluidRegistry.getFluidStack("water", 50) ,true);
				}
			}
		}
	}

	@Override
	public int fill(MgDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(MgDirection from, FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(MgDirection from, int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public boolean canFill(MgDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(MgDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public FluidTankInfo[] getTankInfo(MgDirection from) {
		return new FluidTankInfo[0];
	}
	
}
