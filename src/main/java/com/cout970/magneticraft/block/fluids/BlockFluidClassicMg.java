package com.cout970.magneticraft.block.fluids;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import net.minecraft.util.BlockPos;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public abstract class BlockFluidClassicMg extends BlockFluidClassic {

    public static Material fluidMaterial = Material.water;

    public BlockFluidClassicMg(Fluid fluid, Material material) {
        super(fluid, material);
        setCreativeTab(CreativeTabsMg.MainTab);
        setUnlocalizedName("mg_" + getName() + "_block");
    }

    @Override
    public boolean displaceIfPossible(World world, BlockPos pos) {
        return !world.getBlockState(pos).getBlock().getMaterial().isLiquid()
                && super.displaceIfPossible(world, pos);
    }

    public abstract String getName();

}
