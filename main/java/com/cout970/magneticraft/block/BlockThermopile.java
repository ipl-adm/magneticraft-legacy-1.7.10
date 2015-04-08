package com.cout970.magneticraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.cout970.magneticraft.tileentity.TileThermopile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockThermopile extends BlockMg{

	public BlockThermopile() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileThermopile();
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return side == 1 || side == 0 ? icons[1] : icons[0];
	}

	@Override
	public String[] getTextures() {
		return new String[]{"thermopile","thermopile_top"};
	}

	@Override
	public String getName() {
		return "thermopile";
	}

}
