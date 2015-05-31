package com.cout970.magneticraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tileentity.TileStirlingGenerator;
import com.cout970.magneticraft.util.multiblock.MB_ControlBlock;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.MB_Tile;
import com.cout970.magneticraft.util.multiblock.MB_Watcher;
import com.cout970.magneticraft.util.multiblock.Multiblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStirlingGenerator extends BlockMg implements MB_ControlBlock{

	public BlockStirlingGenerator() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileStirlingGenerator();
	}

	@Override
	public String[] getTextures() {
		return new String[]{"stirling_generator","stirling_generator_head","stirling_generator_on"};
	}

	@Override
	public String getName() {
		return "stirling_generator";
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if(meta == 0){
			return side == 3 ? icons[1] : icons[0];
		}else if(meta > 5){
			return side == 1 ? this.icons[0] : (side == 0 ? this.icons[0] : (side != meta%6 ? this.icons[0] : this.icons[2]));
		}
        return side == 1 ? this.icons[0] : (side == 0 ? this.icons[0] : (side != meta ? this.icons[0] : this.icons[1]));
    }
	
	public boolean isOpaqueCube(){
		return false;
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

	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_){
		if(p.isSneaking())return false;
		TileEntity t = w.getTileEntity(x, y, z);
		if(t instanceof TileStirlingGenerator){
			if(!((TileStirlingGenerator) t).activate){
				if(!w.isRemote)MB_Watcher.watchStructure(w, new VecInt(x,y,z),MB_Register.getMBbyID(MB_Register.ID_STIRLING), getDirection(w, new VecInt(x,y,z)),p);
				else ((TileStirlingGenerator) t).drawCounter = 200;
			}else{
				p.openGui(Magneticraft.Instance, 0, w, x, y, z);
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
	
	@Override
	public MgDirection getDirection(World w, VecInt p) {
		return MgDirection.getDirection(w.getBlockMetadata(p.getX(), p.getY(), p.getZ()) % 6);
	}
	
	@Override
	public Multiblock getStructure() {
		return MB_Register.getMBbyID(MB_Register.ID_STIRLING);
	}

	@Override
	public void mutates(World w, VecInt p, Multiblock c, MgDirection e) {
		int meta = w.getBlockMetadata(p.getX(), p.getY(), p.getZ());
		w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), meta%6+6, 2);
	}

	@Override
	public void destroy(World w, VecInt p, Multiblock c, MgDirection e) {
		int meta = w.getBlockMetadata(p.getX(), p.getY(), p.getZ());
		w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), meta%6, 2);
	}
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side)
    {
		MgDirection d = MgDirection.getDirection(side);
		if(w.getBlockMetadata(x-d.getOffsetX(), y-d.getOffsetY(), z-d.getOffsetZ()) >= 6)return false;
        return super.shouldSideBeRendered(w, x, y, z, side);
    }
}
