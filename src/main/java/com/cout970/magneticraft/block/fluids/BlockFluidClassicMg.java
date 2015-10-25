package com.cout970.magneticraft.block.fluids;

import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

public abstract class BlockFluidClassicMg extends BlockFluidClassic {

    public static Material fluidMaterial = Material.water;

    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public Fluid toIcon;

    public BlockFluidClassicMg(Fluid fluid, Material material) {
        super(fluid, material);
        setCreativeTab(CreativeTabsMg.MainTab);
        setBlockName("mg_" + getName() + "_block");
        toIcon = fluid;
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? stillIcon : flowingIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister register) {
        stillIcon = register.registerIcon(BlockMg.base + "fluids/" + getName() + "_still");
        flowingIcon = register.registerIcon(BlockMg.base + "fluids/" + getName() + "_flow");
        toIcon.setIcons(stillIcon, flowingIcon);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
        return !world.getBlock(x, y, z).getMaterial().isLiquid()
                && super.displaceIfPossible(world, x, y, z);
    }

    public abstract String getName();

}
