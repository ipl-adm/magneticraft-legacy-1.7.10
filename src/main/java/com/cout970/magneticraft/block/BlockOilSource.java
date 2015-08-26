package com.cout970.magneticraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOilSource extends BlockMg {

    public BlockOilSource() {
        super(Material.rock);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return null;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"oilsource"};
    }

    @Override
    public String getName() {
        return "oil_source";
    }

}
