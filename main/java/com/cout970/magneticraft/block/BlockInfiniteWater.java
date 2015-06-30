package com.cout970.magneticraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileInfiniteWater;

public class BlockInfiniteWater extends BlockMg{

	public BlockInfiniteWater() {
		super(Material.water);
		setCreativeTab(CreativeTabsMg.SteamAgeTab);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileInfiniteWater();
	}

	@Override
	public String[] getTextures() {
		return new String[]{"infinite_water"};
	}

	@Override
	public String getName() {
		return "InfiniteWater";
	}
	
	public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }
}
