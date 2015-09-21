package com.cout970.magneticraft.block.multiblock;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Kinetic;
import com.cout970.magneticraft.util.multiblock.MB_Block;
import com.cout970.magneticraft.util.multiblock.MB_Tile;
import com.cout970.magneticraft.util.multiblock.MB_Watcher;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
    public void mutates(World w, VecInt p, Multiblock c, MgDirection e) {
        w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 2, 2);
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess w, int x, int y, int z, int side) {
        MgDirection d = MgDirection.getDirection(side);
        return (w.getBlockMetadata(x - d.getOffsetX(), y - d.getOffsetY(), z - d.getOffsetZ()) != 2)
                && super.shouldSideBeRendered(w, x, y, z, side);
    }

    @Override
    public void destroy(World w, VecInt p, Multiblock c, MgDirection e) {
        w.setBlockMetadataWithNotify(p.getX(), p.getY(), p.getZ(), 0, 2);
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

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        w.setBlockMetadataWithNotify(x, y, z, Facing.oppositeSide[determineOrientation(w, x, y, z, p)], 2);
    }

    public static int determineOrientation(World w, int x, int y, int z, EntityLivingBase p) {
        if (MathHelper.abs((float) p.posX - (float) x) < 2.0F && MathHelper.abs((float) p.posZ - (float) z) < 2.0F) {
            double d0 = p.posY + 1.82D - (double) p.yOffset;

            if (d0 - (double) y > 2.0D) {
                return 1;
            }

            if ((double) y - d0 > 0.0D) {
                return 0;
            }
        }
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }
}
