package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tileentity.TileElectricCableHigh;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by cout970 on 23/12/2015.
 */
public class BlockElectricCableT3 extends BlockMg {

    public BlockElectricCableT3() {
        super(Material.iron);
    }

    @Override
    public String[] getTextures() {
        return new String[]{"electric_cable_3"};
    }

    @Override
    public String getName() {
        return "electric_cable_3";
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileElectricCableHigh();
    }
}
