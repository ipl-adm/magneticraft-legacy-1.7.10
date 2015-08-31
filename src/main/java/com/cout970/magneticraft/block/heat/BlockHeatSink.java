package com.cout970.magneticraft.block.heat;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileHeatSink;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockHeatSink extends BlockMg {

    public BlockHeatSink() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
    }

    public String[] getTextures() {
        return new String[]{"void"};
    }

    public int onBlockPlaced(World w, int x, int y, int z, int side, float p_149660_6_, float p_149660_7_, float p_149660_8_, int meta) {
        return ForgeDirection.getOrientation(side).getOpposite().ordinal();
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileHeatSink();
    }

    @Override
    public String getName() {
        return "heat_sink";
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

    public void setBlockBoundsBasedOnState(IBlockAccess w, int x, int y, int z) {
        float desp = 0.0625f * 2f;
        float height = 0.0625f * 4f;
        MgDirection o = MgDirection.getDirection(w.getBlockMetadata(x, y, z));
        switch (o) {
            case DOWN:
                setBlockBounds(desp, 0, desp, 1 - desp, height, 1 - desp);
                break;
            case UP:
                setBlockBounds(desp, 1 - height, desp, 1 - desp, 1, 1 - desp);
                break;
            case NORTH:
                setBlockBounds(desp, desp, 0, 1 - desp, 1 - desp, height);
                break;
            case SOUTH:
                setBlockBounds(desp, desp, 1 - height, 1 - desp, 1 - desp, 1);
                break;
            case WEST:
                setBlockBounds(0, desp, desp, height, 1 - desp, 1 - desp);
                break;
            case EAST:
                setBlockBounds(1 - height, desp, desp, 1, 1 - desp, 1 - desp);
                break;
        }
    }

    public AxisAlignedBB getSelectedBoundingBoxFromPool(World w, int x, int y, int z) {

        double desp = 0.0625 * 2;
        double height = 0.0625 * 4;
        MgDirection o = MgDirection.getDirection(w.getBlockMetadata(x, y, z));
        switch (o) {
            case DOWN:
                return AxisAlignedBB.getBoundingBox(x + desp, y, z + desp, x + 1 - desp, y + height, z + 1 - desp);
            case UP:
                return AxisAlignedBB.getBoundingBox(x + desp, y + 1 - height, z + desp, x + 1 - desp, y + 1, z + 1 - desp);
            case NORTH:
                return AxisAlignedBB.getBoundingBox(x + desp, y + desp, z, x + 1 - desp, y + 1 - desp, z + height);
            case SOUTH:
                return AxisAlignedBB.getBoundingBox(x + desp, y + desp, z + 1 - height, x + 1 - desp, y + 1 - desp, z + 1);
            case WEST:
                return AxisAlignedBB.getBoundingBox(x, y + desp, z + desp, x + height, y + 1 - desp, z + 1 - desp);
            case EAST:
                return AxisAlignedBB.getBoundingBox(x + 1 - height, y + desp, z + desp, x + 1, y + 1 - desp, z + 1 - desp);
        }
        return AxisAlignedBB.getBoundingBox((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World w, int x, int y, int z) {

        double desp = 0.0625 * 2;
        double height = 0.0625 * 4;
        MgDirection o = MgDirection.getDirection(w.getBlockMetadata(x, y, z));
        switch (o) {
            case DOWN:
                return AxisAlignedBB.getBoundingBox(x + desp, y, z + desp, x + 1 - desp, y + height, z + 1 - desp);
            case UP:
                return AxisAlignedBB.getBoundingBox(x + desp, y + 1 - height, z + desp, x + 1 - desp, y + 1, z + 1 - desp);
            case NORTH:
                return AxisAlignedBB.getBoundingBox(x + desp, y + desp, z, x + 1 - desp, y + 1 - desp, z + height);
            case SOUTH:
                return AxisAlignedBB.getBoundingBox(x + desp, y + desp, z + 1 - height, x + 1 - desp, y + 1 - desp, z + 1);
            case WEST:
                return AxisAlignedBB.getBoundingBox(x, y + desp, z + desp, x + height, y + 1 - desp, z + 1 - desp);
            case EAST:
                return AxisAlignedBB.getBoundingBox(x + 1 - height, y + desp, z + desp, x + 1, y + 1 - desp, z + 1 - desp);
        }
        return AxisAlignedBB.getBoundingBox((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }

    public ForgeDirection[] getValidRotations(World worldObj, int x, int y, int z) {
        return ForgeDirection.VALID_DIRECTIONS;
    }
}
