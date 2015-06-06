package com.cout970.magneticraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileHandCrankGenerator;

public class BlockHandCrankGenerator extends BlockMg{

	public BlockHandCrankGenerator() {
		super(Material.wood);
		setCreativeTab(CreativeTabsMg.MedievalAgeTab);
	}

	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
		TileEntity t = w.getTileEntity(x, y, z);
		if(t instanceof TileHandCrankGenerator){
			((TileHandCrankGenerator) t).tickCounter = 20;
			return true;
		}
        return false;
    }
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileHandCrankGenerator();
	}

	@Override
	public String[] getTextures() {
		return new String[]{"hand_crank_generator"};
	}

	@Override
	public String getName() {
		return "hand_crank_generator";
	}

}
