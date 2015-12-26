package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tileentity.TileHeatCable;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by cout970 on 23/12/2015.
 */
public class BlockHeatCable extends BlockMg {

    public BlockHeatCable() {
        super(Material.iron);
    }

    @Override
    public String[] getTextures() {
        return new String[]{"heat_cable"};
    }

    @Override
    public String getName() {
        return "heat_cable";
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileHeatCable();
    }
}
