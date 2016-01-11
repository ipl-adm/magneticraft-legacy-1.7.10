package com.cout970.magneticraft.block.fluids;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public abstract class BlockFluidFiniteMg extends BlockFluidFinite {
    public Fluid toIcon;

    public BlockFluidFiniteMg(Fluid fluid, Material material) {
        super(fluid, material);
        setCreativeTab(CreativeTabsMg.MainTab);
        setUnlocalizedName("mg_" + getName() + "_block");
        toIcon = fluid;
    }

    public abstract String getName();
}
