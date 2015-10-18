package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileInfiniteWater;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockInfiniteWater extends BlockMg {

    public BlockInfiniteWater() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.SteamAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileInfiniteWater();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"infinite_water"};
    }

    @Override
    public String getName() {
        return "InfiniteWater";
    }
}
