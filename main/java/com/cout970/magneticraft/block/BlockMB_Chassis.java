package com.cout970.magneticraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileMB_Base;
import com.cout970.magneticraft.util.multiblock.MB_Block;
import com.cout970.magneticraft.util.multiblock.MB_Tile;
import com.cout970.magneticraft.util.multiblock.MB_Watcher;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import com.cout970.magneticraft.util.multiblock.types.MultiblockCrusher;
import com.cout970.magneticraft.util.multiblock.types.MultiblockOilDistillery;
import com.cout970.magneticraft.util.multiblock.types.MultiblockPolymerizer;
import com.cout970.magneticraft.util.multiblock.types.MultiblockRefinery;
import com.cout970.magneticraft.util.multiblock.types.MultiblockStirlig;
import com.cout970.magneticraft.util.multiblock.types.MultiblockTurbine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockMB_Chassis extends BlockMg implements MB_Block{

	public BlockMB_Chassis() {
		super(Material.iron);
		setLightOpacity(0);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileMB_Base();
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
	
	public boolean renderAsNormalBlock(){
		return false;
	}

	@Override
	public String[] getTextures() {
		return new String[]{"chasis","multi"};
	}
	
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public String getName() {
		return "chasis";
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
		if(meta == 1)return icons[1];
        return icons[0];
    }

	@Override
	public void mutates(World w, VecInt p, Multiblock c, MgDirection e) {
		w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 2, 2);
	}
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side)
    {
		MgDirection d = MgDirection.getDirection(side);
		if(w.getBlockMetadata(x-d.getOffsetX(), y-d.getOffsetY(), z-d.getOffsetZ()) == 2)return false;
        return super.shouldSideBeRendered(w, x, y, z, side);
    }

	@Override
	public void destroy(World w, VecInt p, Multiblock c, MgDirection e) {
		w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 0, 2);
	}

}
