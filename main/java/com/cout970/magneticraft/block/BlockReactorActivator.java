package com.cout970.magneticraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.cout970.magneticraft.tileentity.TileReactorActivator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockReactorActivator extends BlockMg{

	public BlockReactorActivator() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileReactorActivator();
	}

	@Override
	public String[] getTextures() {
		return new String[]{"void"};
	}

	@Override
	public String getName() {
		return "reactor_activator";
	}
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_){
        return false;
    }
	
	public boolean renderAsNormalBlock(){
		return false;
	}

	public boolean isOpaqueCube(){
		return false;
	}
}
