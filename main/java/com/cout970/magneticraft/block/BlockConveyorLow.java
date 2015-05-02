package com.cout970.magneticraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.conveyor.IConveyor;
import com.cout970.magneticraft.api.conveyor.ItemBox;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.tileentity.TileConveyorBelt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConveyorLow extends BlockMg{

	public BlockConveyorLow() {
		super(Material.iron);
		setBlockBounds(0, 0, 0, 1, 5/16f, 1);
	}

	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_){
		if(p.isSneaking())return false;
		if(p.getCurrentEquippedItem() != null){
			TileEntity t  = w.getTileEntity(x, y, z);
			if(t instanceof IConveyor){
				if(((IConveyor) t).addItem(MgDirection.SOUTH, 0, new ItemBox(p.getCurrentEquippedItem()), false)){
					p.setCurrentItemOrArmor(0, null);
				}
			}
		}
		return true;
	}
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileConveyorBelt();
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if(side == 1)return meta == 2 ? icons[3] : meta == 3 ? icons[1] : meta == 4 ? icons[2] : icons[4];
		return icons[0];
	}

	@Override
	public String[] getTextures() {
		return new String[]{"conveyor_low","belts/conveyor_low_north","belts/conveyor_low_east","belts/conveyor_low_south","belts/conveyor_low_west"};
	}
	
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i){
		int l = MathHelper.floor_double((double)(p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		if (l == 0){
			w.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}if (l == 1){
			w.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}if (l == 2){
			w.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}if (l == 3){
			w.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}

	@Override
	public String getName() {
		return "conveyor_low";
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
