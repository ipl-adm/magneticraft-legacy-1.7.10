package com.cout970.magneticraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cout970.magneticraft.tileentity.TileRCAlternator;

public class BlockRCAlternator extends BlockMg{

	public BlockRCAlternator() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileRCAlternator();
	}

	@Override
	public String[] getTextures() {
		return new String[]{"rc_alternator"};
	}

	@Override
	public String getName() {
		return "rc_alternator";
	}
}
