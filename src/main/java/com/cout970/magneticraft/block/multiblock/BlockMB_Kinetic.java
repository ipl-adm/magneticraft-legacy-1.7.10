package com.cout970.magneticraft.block.multiblock;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Kinetic;
import com.cout970.magneticraft.util.multiblock.MB_Block;
import com.cout970.magneticraft.util.multiblock.MB_Tile;
import com.cout970.magneticraft.util.multiblock.MB_Watcher;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMB_Kinetic extends BlockMg implements MB_Block {

    public BlockMB_Kinetic() {
        super(Material.wood);
        setLightOpacity(0);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileMB_Kinetic();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"mb_kinetic"};
    }

    @Override
    public String getName() {
        return "mb_kinetic";
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void mutates(World w, BlockPos p, Multiblock c, EnumFacing e) {
        w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 2, 2);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        MgDirection d = MgDirection.getDirection(side);
        return (w.getBlockMetadata(x - d.getOffsetX(), y - d.getOffsetY(), z - d.getOffsetZ()) != 2)
                && super.shouldSideBeRendered(w, x, y, z, side);
    }

    @Override
    public void destroy(World w, BlockPos p, Multiblock c, EnumFacing e) {
        w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 0, 2);
    }

    @Override
    public void breakBlock(World w, BlockPos pos, IBlockState state) {
        if (!w.isRemote) {
            TileEntity t = w.getTileEntity(pos);
            if (t instanceof MB_Tile) {
                if (((MB_Tile) t).getControlPos() != null && ((MB_Tile) t).getMultiblock() != null)
                    MB_Watcher.destroyStructure(w, ((MB_Tile) t).getControlPos(), ((MB_Tile) t).getMultiblock(), ((MB_Tile) t).getDirection());
            }
        }
        super.breakBlock(w, x, y, z, b, side);
    }

    @Override
    public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase p, ItemStack i) {
        rotate(w, pos, state, p);
    }
}
