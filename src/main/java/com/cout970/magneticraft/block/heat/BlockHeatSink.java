package com.cout970.magneticraft.block.heat;

import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileHeatSink;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHeatSink extends BlockMg {

    public BlockHeatSink() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public IBlockState onBlockPlaced(World w, BlockPos pos, EnumFacing facing, float hitx, float hity, float hitz, int meta, EntityLivingBase placer) {
        return getBlockState().getBaseState().withProperty(FACING, facing.getOpposite());
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
    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, BlockPos pos, EnumFacing facing) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess w, BlockPos pos) {
        float desp = 0.0625f * 2f;
        float height = 0.0625f * 4f;
        EnumFacing facing = w.getBlockState(pos).getValue(FACING);
        switch (facing) {
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

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(World w, BlockPos pos) {
        double desp = 0.0625 * 2;
        double height = 0.0625 * 4;
        EnumFacing facing = w.getBlockState(pos).getValue(FACING);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        
        switch (facing) {
            case DOWN:
                return AxisAlignedBB.fromBounds(x + desp, y, z + desp, x + 1 - desp, y + height, z + 1 - desp);
            case UP:
                return AxisAlignedBB.fromBounds(x + desp, y + 1 - height, z + desp, x + 1 - desp, y + 1, z + 1 - desp);
            case NORTH:
                return AxisAlignedBB.fromBounds(x + desp, y + desp, z, x + 1 - desp, y + 1 - desp, z + height);
            case SOUTH:
                return AxisAlignedBB.fromBounds(x + desp, y + desp, z + 1 - height, x + 1 - desp, y + 1 - desp, z + 1);
            case WEST:
                return AxisAlignedBB.fromBounds(x, y + desp, z + desp, x + height, y + 1 - desp, z + 1 - desp);
            case EAST:
                return AxisAlignedBB.fromBounds(x + 1 - height, y + desp, z + desp, x + 1, y + 1 - desp, z + 1 - desp);
        }
        return AxisAlignedBB.fromBounds((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World w, BlockPos pos, IBlockState state) {
        double desp = 0.0625 * 2;
        double height = 0.0625 * 4;
        EnumFacing facing = w.getBlockState(pos).getValue(FACING);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        switch (facing) {
            case DOWN:
                return AxisAlignedBB.fromBounds(x + desp, y, z + desp, x + 1 - desp, y + height, z + 1 - desp);
            case UP:
                return AxisAlignedBB.fromBounds(x + desp, y + 1 - height, z + desp, x + 1 - desp, y + 1, z + 1 - desp);
            case NORTH:
                return AxisAlignedBB.fromBounds(x + desp, y + desp, z, x + 1 - desp, y + 1 - desp, z + height);
            case SOUTH:
                return AxisAlignedBB.fromBounds(x + desp, y + desp, z + 1 - height, x + 1 - desp, y + 1 - desp, z + 1);
            case WEST:
                return AxisAlignedBB.fromBounds(x, y + desp, z + desp, x + height, y + 1 - desp, z + 1 - desp);
            case EAST:
                return AxisAlignedBB.fromBounds(x + 1 - height, y + desp, z + desp, x + 1, y + 1 - desp, z + 1 - desp);
        }
        return AxisAlignedBB.fromBounds((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }
}
