package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.ManagerConfig;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileInfiniteWater extends TileBase implements IFluidHandler1_8 {

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        if(ManagerConfig.INFINITE_WATER_GENERATION > 0){
        	for (MgDirection d : MgDirection.values()) {
        		TileEntity t = MgUtils.getTileEntity(this, d);
        		if (t instanceof IFluidHandler) {
        			IFluidHandler f = (IFluidHandler) t;
        			if (f.canFill(d.toForgeDir(), FluidRegistry.WATER)) {
        				f.fill(d.toForgeDir().getOpposite(), FluidRegistry.getFluidStack("water", ManagerConfig.INFINITE_WATER_GENERATION), true);
        			}
        		}
        	}
        }
    }

    @Override
    public int fillMg(MgDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drainMg_F(MgDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drainMg(MgDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFillMg(MgDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrainMg(MgDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfoMg(MgDirection from) {
        return new FluidTankInfo[0];
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
