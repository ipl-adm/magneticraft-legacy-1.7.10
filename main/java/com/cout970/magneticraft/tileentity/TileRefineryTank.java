package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.fluid.TankMg;


public class TileRefineryTank extends TileMB_Base implements IFluidHandler1_8{

	private TankMg tank = new TankMg(this, 4000);
	
	public TankMg getTank(){
		return tank;
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		if(getTank().getFluidAmount() > 0){
			for(MgDirection dir : MgDirection.values()){
				TileEntity t = MgUtils.getTileEntity(this, dir);
				if(t instanceof IFluidHandler){
					IFluidHandler f = (IFluidHandler) t;
					if(f.canFill(dir.opposite().getForgeDir(), getTank().getFluid().getFluid()) && getTank().getFluidAmount() > 0){
						int accepted = f.fill(dir.opposite().getForgeDir(), getTank().getFluid(), false);
						if(accepted > 0){
							accepted = f.fill(dir.opposite().getForgeDir(), getTank().getFluid(), true);
							getTank().drain(accepted, true);
						}
					}
				}
			}
		}
	}
	
	@Override
	public int fill(MgDirection from, FluidStack resource, boolean doFill) {
		return getTank().fill(resource, doFill);
	}

	@Override
	public FluidStack drain(MgDirection from, FluidStack resource, boolean doDrain) {
		return getTank().drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(MgDirection from, int maxDrain, boolean doDrain) {
		return getTank().drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(MgDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(MgDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(MgDirection from) {
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
}
