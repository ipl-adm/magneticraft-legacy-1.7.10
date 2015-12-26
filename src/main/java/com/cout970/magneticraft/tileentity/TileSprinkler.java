package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import java.util.HashMap;
import java.util.Map;

public class TileSprinkler extends TileBase implements IFluidHandler1_8 {
    public static Map<Fluid, Double> fertilizers = new HashMap<>();
    private int toSpread = 0;

    private MgDirection getDir() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fertilizers.containsKey(fluid) && from.equals(getDir().toForgeDir());
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[0];
    }
}
