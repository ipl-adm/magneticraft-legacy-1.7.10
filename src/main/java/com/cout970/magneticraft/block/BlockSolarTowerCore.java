package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.tileentity.TileSolarTowerCore;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockSolarTowerCore extends BlockMg {

    public BlockSolarTowerCore() {
        super(Material.iron);
        setCreativeTab(CreativeTabsMg.SteamAgeTab);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileSolarTowerCore();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"solar_tower_core"};
    }

    @Override
    public String getName() {
        return "solar_tower_core";
    }

}
