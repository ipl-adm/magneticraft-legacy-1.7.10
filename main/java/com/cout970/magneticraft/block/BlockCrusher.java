package com.cout970.magneticraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.tileentity.TileCrusher;
import com.cout970.magneticraft.util.multiblock.MB_ControlBlock;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.MB_Tile;
import com.cout970.magneticraft.util.multiblock.MB_Watcher;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrusher extends BlockMg implements MB_ControlBlock{

	public BlockCrusher() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileCrusher();
	}

	@Override
	public String[] getTextures() {
		return new String[]{"crusher","chasis"};
	}

	@Override
	public String getName() {
		return "crusher_control";
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if(meta == 0){
			return side == 3 ? icons[0] : icons[1];
		}
        return side == meta ? icons[0] : icons[1];
    }
	
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_){
		if(p.isSneaking())return false;
		if(!w.isRemote){
			TileEntity t = w.getTileEntity(x, y, z);
			if(t instanceof TileCrusher){
				if(!((TileCrusher) t).active){
					MB_Watcher.watchStructure(w, new BlockPosition(x,y,z),MB_Register.Crusher, getDirection(w, new BlockPosition(x,y,z)));
				}else{
					p.openGui(Magneticraft.Instance, 0, w, x, y, z);
				}
			}
		}
		return true;
	}
	
	public void breakBlock(World w,int x,int y,int z,Block b,int side){
		if(!w.isRemote){
			TileEntity t = w.getTileEntity(x, y, z);
			if(t instanceof MB_Tile){
				if(((MB_Tile) t).getControlPos() != null && ((MB_Tile) t).getMultiblock() != null)
				MB_Watcher.destroyStructure(w, ((MB_Tile) t).getControlPos(), ((MB_Tile) t).getMultiblock(),((MB_Tile) t).getDirection());
			}
		}
		super.breakBlock(w, x, y, z, b, side);
	}

	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i)
	{
		int l = MathHelper.floor_double((double)(p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

		if (l == 0){
			w.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (l == 1){
			w.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (l == 2){
			w.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (l == 3){
			w.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}

	@Override
	public MgDirection getDirection(World w, BlockPosition p) {
		return MgDirection.getDirection(w.getBlockMetadata(p.getX(), p.getY(), p.getZ()));
	}

}
