package com.cout970.magneticraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.cout970.magneticraft.tileentity.TileHeater;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHeater extends BlockMg{

	public BlockHeater() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileHeater();
	}
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if(meta == 0)return icons[0];
		return icons[1];
	}

	@Override
	public String[] getTextures() {
		return new String[]{"heater_off","heater_on"};
	}

	@Override
	public String getName() {
		return "heater";
	}
}
