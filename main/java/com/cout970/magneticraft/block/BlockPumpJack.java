package com.cout970.magneticraft.block;

import java.util.Random;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TilePumpJack;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPumpJack extends BlockMg{

	public BlockPumpJack() {
		super(Material.iron);
		setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
	}
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        return false;
    }
	
	public boolean canPlaceBlockOnSide(World w, int x, int y, int z,int side)
    {
		boolean clear = true;
		if(!w.getBlock(x, y, z).isReplaceable(w, x, y, z))clear = false;
		if(!w.getBlock(x, y+1, z).isReplaceable(w, x, y, z))clear = false;
		
		if(side == 2 || side == 3 || side == 0 || side == 1){
			if(!w.getBlock(x, y+1, z-1).isReplaceable(w, x, y, z))clear = false;
			if(!w.getBlock(x, y+1, z+1).isReplaceable(w, x, y, z))clear = false;
			if(!w.getBlock(x, y, z-1).isReplaceable(w, x, y, z))clear = false;
			if(!w.getBlock(x, y, z+1).isReplaceable(w, x, y, z))clear = false;
		}
		if(side == 4 || side == 5 || side == 0 || side == 1){
			if(!w.getBlock(x-1, y+1, z).isReplaceable(w, x, y, z))clear = false;
			if(!w.getBlock(x+1, y+1, z).isReplaceable(w, x, y, z))clear = false;
			if(!w.getBlock(x-1, y, z).isReplaceable(w, x, y, z))clear = false;
			if(!w.getBlock(x+1, y, z).isReplaceable(w, x, y, z))clear = false;
		}
		return clear;
    }

	public Item getItemDropped(int meta, Random rand, int fort)
    {
		if(meta >= 4)return null;
        return Item.getItemFromBlock(this);
    }

	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack item){

		int l = MathHelper.floor_double((double)(p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		w.setBlockMetadataWithNotify(x, y, z, l, 2);
		if(l == 3 || l == 1){
			w.setBlock(x, y+1, z, this, 4, 2);
			w.setBlock(x, y, z-1, this, 5, 2);
			w.setBlock(x, y+1, z-1, this, 6, 2);
			w.setBlock(x, y, z+1, this, 7, 2);
			w.setBlock(x, y+1, z+1, this, 8, 2);
		}else{
			w.setBlock(x, y+1, z, this, 4, 2);
			w.setBlock(x-1, y, z, this, 9, 2);
			w.setBlock(x-1, y+1, z, this, 10, 2);
			w.setBlock(x+1, y, z, this, 11, 2);
			w.setBlock(x+1, y+1, z, this, 12, 2);
		}
	}
	
	public void breakBlock(World w, int x, int y, int z, Block b, int meta)
    {
        if (hasTileEntity(meta) && !(this instanceof BlockContainer))
        {
        	w.removeTileEntity(x, y, z);
        }

        if(meta == 0 || meta == 2){
        	w.setBlockToAir(x+1, y+1, z);
        	w.setBlockToAir(x, y+1, z);
        	w.setBlockToAir(x-1, y+1, z);
        	w.setBlockToAir(x+1, y, z);
        	w.setBlockToAir(x-1, y, z);
        	
        }else if(meta == 1 || meta == 3){
        	w.setBlockToAir(x, y+1, z+1);
        	w.setBlockToAir(x, y+1, z);
        	w.setBlockToAir(x, y+1, z-1);
        	w.setBlockToAir(x, y, z+1);
        	w.setBlockToAir(x, y, z-1);
        }else if (meta == 4){
        	w.func_147480_a(x, y-1, z,true);
        }else if(meta == 5){
        	w.func_147480_a(x, y, z+1,true);
        }else if(meta == 6){
        	w.func_147480_a(x, y-1, z+1,true);
        }else if(meta == 7){
        	w.func_147480_a(x, y, z-1,true);
        }else if(meta == 8){
        	w.func_147480_a(x, y-1, z-1,true);
        	
        }else if(meta == 9){
        	w.func_147480_a(x+1, y, z,true);
        }else if(meta == 10){
        	w.func_147480_a(x+1, y-1, z,true);
        }else if(meta == 11){
        	w.func_147480_a(x-1, y, z,true);
        }else if(meta == 12){
        	w.func_147480_a(x-1, y-1, z,true);
        }
    }

	@Override
	public TileEntity createNewTileEntity(World w, int m) {
		if(m <= 3)
			return new TilePumpJack();
		return null;
	}

	@Override
	public String[] getTextures() {
		return new String[]{"void"};
	}

	@Override
	public String getName() {
		return "pumpjack";
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public boolean isOpaqueCube()
	{
		return false;
	}
}
