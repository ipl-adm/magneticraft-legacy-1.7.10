package com.cout970.magneticraft.block.fluids;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

public class BlockFluidHotCrude extends BlockFluidClassicMg {

    public BlockFluidHotCrude(Fluid fluid, Material material) {
        super(fluid, material);
    }

    @Override
    public String getName() {
        return "hot_crude";
    }
}
