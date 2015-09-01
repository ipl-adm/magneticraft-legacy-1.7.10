package com.cout970.magneticraft.block.slabs;

import java.util.Random;

import com.cout970.magneticraft.tabs.CreativeTabsMg;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class BlockMgSlab extends BlockSlab {

    String name;

    public BlockMgSlab(boolean full, Material mat, String name) {
        super(full, mat);
        this.name = name;
        setBlockName(name);
        if (!full) {
            setCreativeTab(CreativeTabsMg.MainTab);
            setLightOpacity(0);
        }
    }

    public abstract BlockSlab getFullBlock();

    public abstract BlockSlab getSingleBlock();

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
        return new ItemStack(getSingleBlock());
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(getSingleBlock());
    }

    @Override
    public ItemStack createStackedBlock(int par1) {
        return new ItemStack(getSingleBlock());
    }

    @Override
    public String func_150002_b(int i) {
        return name;
    }
}