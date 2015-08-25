package com.cout970.magneticraft.block;

import com.cout970.magneticraft.api.tool.IHammer;
import com.cout970.magneticraft.tileentity.TileHammerTable;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHammerTable extends BlockMg{

	public BlockHammerTable() {
		super(Material.rock);
		setBlockBounds(0, 0, 0, 1, 0.875f, 1);
	}
	
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if(p != null){
			TileEntity tile = w.getTileEntity(x, y, z);
			if(tile instanceof TileHammerTable){
				ItemStack i = p.getCurrentEquippedItem();
				if(i != null) {
					if(i.getItem() instanceof IHammer){
						if(((TileHammerTable) tile).canWork() && ((IHammer) i.getItem()).canHammer(i,w,x,y,z)){
							((TileHammerTable) tile).tick(((IHammer) i.getItem()).getMaxHits(i,w,x,y,z));
							ItemStack stack = ((IHammer) i.getItem()).tick(i,w,x,y,z);
							p.setCurrentItemOrArmor(0, stack);
							return true;
						}
						
					}else if(((TileHammerTable) tile).getInput() == null){
						ItemStack split = i.splitStack(1);
						if(i.stackSize <= 0){
							p.setCurrentItemOrArmor(0, null);
						}
						((TileHammerTable) tile).setInput(split);
						return true;
					}
				}
				if(((TileHammerTable) tile).getInput() != null){
					if(p.inventory.addItemStackToInventory(((TileHammerTable) tile).getInput())){
						((TileHammerTable) tile).setInput(null);
					}
				}
			}
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileHammerTable();
	}

	@Override
	public String[] getTextures() {
		return new String[]{"void"};
	}

	@Override
	public String getName() {
		return "hammer_table";
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
