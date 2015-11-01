package com.cout970.magneticraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.ArrayList;

public class BlockConcretedPipe extends BlockMg {

    public BlockConcretedPipe() {
        super(Material.rock);
        float w = 0.0625F * 2;
        setBlockBounds(0.5F - w, 0, 0.5F - w, 0.5F + w, 1, 0.5F + w);
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        return new ArrayList<>();
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return null;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"concreted_pipe"};
    }

    @Override
    public String getName() {
        return "concreted_pipe";
    }


    public boolean renderAsNormalBlock() {
        return false;
    }

    public boolean isOpaqueCube() {
        return false;
    }
}
