package com.cout970.magneticraft.block.heat;

import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileCooler;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCooler extends BlockMg {

    public BlockCooler() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.SteamAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileCooler();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"cooler"};
    }

    @Override
    public String getName() {
        return "cooler";
    }

}
