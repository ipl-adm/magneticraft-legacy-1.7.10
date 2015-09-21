package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.pressure.IExplodable;
import com.cout970.magneticraft.api.pressure.IPressureConductor;
import com.cout970.magneticraft.api.pressure.PressureUtils;
import com.cout970.magneticraft.api.pressure.prefab.PressureConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.CubeRenderer_Util;
import com.cout970.magneticraft.util.tile.TilePressure;

import net.minecraft.init.Blocks;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TilePressureTank extends TilePressure implements IFluidHandler, IExplodable {

    public CubeRenderer_Util cubeRenderer;

	@Override
    public IPressureConductor initConductor() {
        return new PressureConductor(this, 16000);
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        if (pressure.getPressure() > pressure.getMaxPressure()) {
            this.explode(this.worldObj, this.xCoord, this.yCoord, this.zCoord, true);
        }
        if(worldObj.getTotalWorldTime() % 20 == 0){
        	sendUpdateToClient();
        }
    }

    @Override
    public void explode(World world, int x, int y, int z, boolean explodeNeighbors) {
        if (!world.isRemote) {
            pressure.onBlockExplode();
            world.setBlock(x, y, z, Blocks.air);

            if (explodeNeighbors) {
                for (MgDirection dir : MgDirection.values()) {
                    VecInt pos = new VecInt(this).add(dir);
                    IExplodable exp = PressureUtils.getExplodable(world, pos);
                    if (exp != null) {
                        exp.explode(world, pos.getX(), pos.getY(), pos.getZ(), true);
                    }
                }
            }
            Explosion e = world.createExplosion(null, x, y, z, 1f, true);
            e.doExplosionA();
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack gas, boolean doFill) {
        return pressure.applyGas(gas, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return drain(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return pressure.drainGas(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return (pressure.getFluid() == null || pressure.getFluid().equals(fluid));
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
