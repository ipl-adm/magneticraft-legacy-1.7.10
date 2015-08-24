package com.cout970.magneticraft.block.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidSteam extends BlockFluidFiniteMg {

    public BlockFluidSteam(Fluid fluid, Material material) {
        super(fluid, material);
    }

    @Override
    public String getName() {
        return "steam";
    }
}

