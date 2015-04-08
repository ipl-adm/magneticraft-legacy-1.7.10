package com.cout970.magneticraft.block.fluids;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.util.CreativeTabMg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockFluidClasicMg extends BlockFluidClassic{

	@SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;
	
	public BlockFluidClasicMg(Fluid fluid, Material material) {
		super(fluid, material);
		setCreativeTab(CreativeTabMg.MgTab);
		setBlockName("mg_"+getName()+"_block");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
		return (side == 0 || side == 1)? stillIcon : flowingIcon;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register) {
		stillIcon = register.registerIcon(BlockMg.base+"fluids/"+getName()+"_still");
		flowingIcon = register.registerIcon(BlockMg.base+"fluids/"+getName()+"_flow");
		FluidRegistry.getFluid(fluidName).setIcons(stillIcon, flowingIcon);
	}

	public abstract String getName();

}
