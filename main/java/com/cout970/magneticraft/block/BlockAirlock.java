package com.cout970.magneticraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cout970.magneticraft.tileentity.TileAirlock;

public class BlockAirlock extends BlockMg{

	public BlockAirlock() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileAirlock();
	}

	@Override
	public String[] getTextures() {
		return new String[]{"airlock"};
	}

	@Override
	public String getName() {
		return "airlock";
	}

}
