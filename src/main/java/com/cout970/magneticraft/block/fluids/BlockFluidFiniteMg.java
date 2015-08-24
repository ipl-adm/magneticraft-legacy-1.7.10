package com.cout970.magneticraft.block.fluids;

import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

public abstract class BlockFluidFiniteMg extends BlockFluidFinite {

    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public Fluid toIcon;

    public BlockFluidFiniteMg(Fluid fluid, Material material) {
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

    public abstract String getName();

}
