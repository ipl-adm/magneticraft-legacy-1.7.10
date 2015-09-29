package com.cout970.magneticraft.util.fluid;

import com.cout970.magneticraft.api.util.MgDirection;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TankConnection {

    public IFluidHandler tank;
    public MgDirection side;

    public TankConnection(IFluidHandler t, MgDirection enumFacing) {
        tank = t;
        side = enumFacing;
    }

    public FluidTankInfo[] getTankInfo(MgDirection enumF) {
        return tank.getTankInfo(ForgeDirection.getOrientation(enumF.ordinal()));
    }

    public FluidStack drain(MgDirection down, int maxDrain, boolean b) {
        return tank.drain(ForgeDirection.getOrientation(down.ordinal()), maxDrain, b);
    }

    public int fill(MgDirection f, FluidStack resource, boolean doFill) {
        return tank.fill(ForgeDirection.getOrientation(f.ordinal()), resource, doFill);
    }
}
