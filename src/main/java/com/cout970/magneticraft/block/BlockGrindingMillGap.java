package com.cout970.magneticraft.block;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Replaced;
import com.cout970.magneticraft.util.multiblock.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockGrindingMillGap extends BlockMg implements MB_Block {

    public BlockGrindingMillGap() {
        super(Material.iron);
        setCreativeTab(null);
        setLightOpacity(0);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileMB_Replaced();
    }

    public void breakBlock(World w, int x, int y, int z, Block b, int side) {
        if (!w.isRemote) {
            TileEntity t = w.getTileEntity(x, y, z);
            if (t instanceof MB_Tile) {
                if (((MB_Tile) t).getControlPos() != null && ((MB_Tile) t).getMultiblock() != null)
                    MB_Watcher.destroyStructure(w, ((MB_Tile) t).getControlPos(), ((MB_Tile) t).getMultiblock(), ((MB_Tile) t).getDirection());
            }
        }
        super.breakBlock(w, x, y, z, b, side);
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {

        ArrayList<ItemStack> ret = new ArrayList<>();
        TileEntity tile = world.getTileEntity(x, y, z);
        if (tile instanceof MB_Tile_Replaced) {
            List<ItemStack> t = ((MB_Tile_Replaced) tile).getDrops();
            if (t != null) {
                ret.addAll(t);
            }
        }
        return ret;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public String getName() {
        return "grinding_mill_gap";
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return icons[0];
    }

    @Override
    public void mutates(World w, VecInt p, Multiblock c, MgDirection e) {
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        return false;
    }

    @Override
    public void destroy(World w, VecInt p, Multiblock c, MgDirection e) {
    }
}
