package com.cout970.magneticraft.block.energy;

import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileDiode;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDiode extends BlockMg {

    public BlockDiode() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileDiode();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "diode";
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        if (p.getCurrentEquippedItem() != null && p.getCurrentEquippedItem().getItem() == ManagerItems.wrench) {
            int i = w.getBlockMetadata(x, y, z) - 2;
            MgDirection or = MgDirection.getDirection(((i + 1) % 4) + 2);
            w.setBlockMetadataWithNotify(x, y, z, or.ordinal(), 2);
            return true;
        }
        return false;
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            w.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if (l == 1) {
            w.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
        if (l == 2) {
            w.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
        if (l == 3) {
            w.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z) {
        double desp = 0.0625 * 4;
        MgDirection o = MgDirection.getDirection(w.getBlockMetadata(x, y, z));
        switch (o) {
            case NORTH:
            case SOUTH:
                return AxisAlignedBB.getBoundingBox(x + desp, y + desp, z, x + 1 - desp, y + 1 - +desp, z + 1);
            case WEST:
            case EAST:
                return AxisAlignedBB.getBoundingBox(x, y + desp, z + desp, x + 1, y + 1 - +desp, z + 1 - desp);
            default:
                break;
        }
        return AxisAlignedBB.getBoundingBox((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {
        double desp = 0.0625 * 4;
        MgDirection o = MgDirection.getDirection(w.getBlockMetadata(x, y, z));
        switch (o) {
            case NORTH:
            case SOUTH:
                return AxisAlignedBB.getBoundingBox(x + desp, y + desp, z, x + 1 - desp, y + 1 - +desp, z + 1);
            case WEST:
            case EAST:
                return AxisAlignedBB.getBoundingBox(x, y + desp, z + desp, x + 1, y + 1 - +desp, z + 1 - desp);
            default:
                break;
        }
        return AxisAlignedBB.getBoundingBox((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }
}
