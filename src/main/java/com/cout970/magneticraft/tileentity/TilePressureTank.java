package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.pressure.IPressureConductor;
import com.cout970.magneticraft.api.pressure.prefab.PressureConductor;
import com.cout970.magneticraft.block.IExplodable;
import com.cout970.magneticraft.util.tile.TilePressure;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TilePressureTank extends TilePressure implements IFluidHandler, IExplodable {

    @Override
    public IPressureConductor initConductor() {
        return new PressureConductor(this, 16000);
    }

    public void updateEntity() {
        super.updateEntity();
        //System.out.println("Pressure Tank Stats: Current Pressure: "+pressure.getPressure()+"mL, Max Pressure: "+pressure.getMaxPressure()+"mL");
        if (pressure.getPressure() > pressure.getMaxPressure()) {
            this.explode(this.worldObj, this.xCoord, this.yCoord, this.zCoord, true);
        }
    }

    @Override
    public void explode(World world, int x, int y, int z, boolean explodeNeighbors) {
        if (!world.isRemote) {
            world.setBlock(x, y, z, Blocks.air);
            world.spawnParticle("hugeexplosion", x, y, z, 1.0F, 1.0F, 1.0F);
            world.playSound(x, y, z, "random.explode", 1.0F, 1.0F, true);
            if (explodeNeighbors) {
                if (world.getBlock(x, y + 1, z) instanceof IExplodable) { //up
                    ((IExplodable) world.getBlock(x, y + 1, z)).explode(world, x, y, z, true);
                }
                if (world.getBlock(x + 1, y, z) instanceof IExplodable) { //x+1
                    ((IExplodable) world.getBlock(x + 1, y, z)).explode(world, x, y, z, true);
                }
                if (world.getBlock(x - 1, y, z) instanceof IExplodable) { //x-1
                    ((IExplodable) world.getBlock(x - 1, y, z)).explode(world, x, y, z, true);
                }
                if (world.getBlock(x, y, z + 1) instanceof IExplodable) { //z+1
                    ((IExplodable) world.getBlock(x, y, z + 1)).explode(world, x, y, z, true);
                }
                if (world.getBlock(x, y, z - 1) instanceof IExplodable) { //z-1
                    ((IExplodable) world.getBlock(x, y, z - 1)).explode(world, x, y, z, true);
                }
                if (world.getBlock(x, y - 1, z) instanceof IExplodable) { //down
                    ((IExplodable) world.getBlock(x, y - 1, z)).explode(world, x, y, z, true);
                }
            }
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return pressure.applyGas(resource);
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
