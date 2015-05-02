package com.cout970.magneticraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.tileentity.TileMB_Energy_Low;
import com.cout970.magneticraft.util.multiblock.MB_Block;
import com.cout970.magneticraft.util.multiblock.MB_Tile;
import com.cout970.magneticraft.util.multiblock.MB_Watcher;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.types.MultiblockCrusher;
import com.cout970.magneticraft.util.multiblock.types.MultiblockPolymerizer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMB_Energy_Low extends BlockMg implements MB_Block{

	public BlockMB_Energy_Low() {
		super(Material.iron);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileMB_Energy_Low();
	}

	@Override
	public String[] getTextures() {
		return new String[]{"mb_energy"};
	}

	@Override
	public String getName() {
		return "MB_EnergyIO";
	}
	
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public void mutates(World w, BlockPosition p, Multiblock c, MgDirection e) {
		if(c instanceof MultiblockCrusher || c instanceof MultiblockPolymerizer){
			w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 2, 2);
		}else
		w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 1, 2);
	}
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side)
    {
		MgDirection d = MgDirection.getDirection(side);
		if(w.getBlockMetadata(x-d.getOffsetX(), y-d.getOffsetY(), z-d.getOffsetZ()) == 2)return false;
        return super.shouldSideBeRendered(w, x, y, z, side);
    }

	@Override
	public void destroy(World w, BlockPosition p, Multiblock c, MgDirection e) {
		w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 0, 2);
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
}
