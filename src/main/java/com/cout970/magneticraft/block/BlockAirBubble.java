package com.cout970.magneticraft.block;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockAirBubble extends Block {

    public BlockAirBubble() {
        super(Material.glass);
        setHardness(0.1f);
        setUnlocalizedName("air_bubble");
        setLightOpacity(0);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return null;
    }

    @Override
    public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing facing) {
        return false;
    }

    @Override
    public boolean isBlockSolid(IBlockAccess p_149747_1_, BlockPos pos, EnumFacing facing) {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, BlockPos pos, EnumFacing facing) {
        Block block = p_149646_1_.getBlockState(pos.offset(facing)).getBlock();

        return block != this && super.shouldSideBeRendered(p_149646_1_, pos, facing);
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return new ArrayList<>();
    }

    @Override
    public boolean canPlaceBlockAt(World p_149742_1_, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canCreatureSpawn(IBlockAccess world, BlockPos pos, EntityLiving.SpawnPlacementType type) {
        return false;
    }
}
