package com.cout970.magneticraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockMachineHousing extends BlockMg {

    public BlockMachineHousing() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return null;
    }

    @Override
    public String[] getTextures() {
        return new String[]{"housing"};
    }

    @Override
    public String getName() {
        return "machine_housing";
    }

}
