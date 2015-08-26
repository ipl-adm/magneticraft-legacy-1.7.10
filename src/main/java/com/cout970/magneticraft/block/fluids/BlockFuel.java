package com.cout970.magneticraft.block.fluids;

import net.minecraftforge.fluids.Fluid;

public class BlockFuel {

    private Fluid fluid;
    private int burnTime;
    private int energy;

    public BlockFuel(Fluid f, int e, int b) {
        fluid = f;
        energy = e;
        burnTime = b;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public int getTotalBurningTime() {
        return burnTime;
    }

    public int getPowerPerCycle() {
        return energy;
    }

}