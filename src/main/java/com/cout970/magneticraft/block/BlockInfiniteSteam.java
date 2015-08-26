package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tileentity.TileInfiniteSteam;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockInfiniteSteam extends BlockMg {

    public BlockInfiniteSteam() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileInfiniteSteam();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"infinite_steam"};
    }

    @Override
    public String getName() {
        return "infinite_steam";
    }
}
