package com.cout970.magneticraft.block;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockConcretedPipe extends BlockMg{

	public BlockConcretedPipe() {
		super(Material.rock);
	}
	
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        return ret;
    }

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	@Override
	public String[] getTextures() {
		return new String[]{"concreted_pipe"};
	}

	@Override
	public String getName() {
		return "concreted_pipe";
	}

}
