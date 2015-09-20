package com.cout970.magneticraft.block.computer;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.Orientation;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileDroidRED;
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

public class BlockDroidRED extends BlockMg {

    public BlockDroidRED() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.InformationAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int m) {
        if (m == 15) return null;
        return new TileDroidRED();
    }

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        p.openGui(Magneticraft.INSTANCE, 0, w, x, y, z);
        return true;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "droid_red";
    }

    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase p, ItemStack i) {
        int l = MathHelper.floor_double((double) (p.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (l == 0) {
            w.setBlockMetadataWithNotify(x, y, z, Orientation.NORTH.toMeta(), 2);
        }
        if (l == 1) {
            w.setBlockMetadataWithNotify(x, y, z, Orientation.EAST.toMeta(), 2);
        }
        if (l == 2) {
            w.setBlockMetadataWithNotify(x, y, z, Orientation.SOUTH.toMeta(), 2);
        }
        if (l == 3) {
            w.setBlockMetadataWithNotify(x, y, z, Orientation.WEST.toMeta(), 2);
        }
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z) {
        double desp = 0.0625 * 4;
        Orientation o = Orientation.fromMeta(w.getBlockMetadata(x, y, z));
        if (o.getLevel() == 1 || o.getLevel() == -1)
            return AxisAlignedBB.getBoundingBox(x + desp, y, z + desp, x + 1 - desp, y + 1, z + 1 - desp);
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
        Orientation o = Orientation.fromMeta(w.getBlockMetadata(x, y, z));
        if (o.getLevel() == 1 || o.getLevel() == -1)
            return AxisAlignedBB.getBoundingBox(x + desp, y, z + desp, x + 1 - desp, y + 1, z + 1 - desp);
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

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }
}
