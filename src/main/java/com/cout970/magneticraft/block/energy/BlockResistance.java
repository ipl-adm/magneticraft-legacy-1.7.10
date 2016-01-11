package com.cout970.magneticraft.block.energy;

import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileResistance;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockResistance extends BlockMg {

    public BlockResistance() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileResistance();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public String getName() {
        return "resistance";
    }


    @Override
    public boolean onBlockActivated(World w, BlockPos pos, IBlockState state, EntityPlayer p, EnumFacing facing, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (p.isSneaking()) return false;
        if (MgUtils.isWrench(p.getCurrentEquippedItem())) {
            EnumFacing cur = (EnumFacing) state.getValue(FACING);
            w.setBlockState(pos, state.withProperty(FACING, cur.getOpposite()), 2);
            return true;
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World w, BlockPos pos, IBlockState state, EntityLivingBase p, ItemStack i) {
        rotate(w, pos, state, p);
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

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBox(World w, BlockPos pos) {
        double desp = 0.0625 * 4;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        EnumFacing facing = w.getBlockState(pos).getValue(FACING);
        switch (facing) {
            case NORTH:
            case SOUTH:
                return AxisAlignedBB.fromBounds(x + desp, y + desp, z, x + 1 - desp, y + 1 - +desp, z + 1);
            case WEST:
            case EAST:
                return AxisAlignedBB.fromBounds(x, y + desp, z + desp, x + 1, y + 1 - +desp, z + 1 - desp);
            default:
                break;
        }
        return AxisAlignedBB.fromBounds((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World w, BlockPos pos, IBlockState state) {
        double desp = 0.0625 * 4;
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();

        EnumFacing facing = state.getValue(FACING);
        switch (facing) {
            case NORTH:
            case SOUTH:
                return AxisAlignedBB.fromBounds(x + desp, y + desp, z, x + 1 - desp, y + 1 - +desp, z + 1);
            case WEST:
            case EAST:
                return AxisAlignedBB.fromBounds(x, y + desp, z + desp, x + 1, y + 1 - +desp, z + 1 - desp);
            default:
                break;
        }
        return AxisAlignedBB.fromBounds((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (double) y + this.maxY, (double) z + this.maxZ);
    }
}
