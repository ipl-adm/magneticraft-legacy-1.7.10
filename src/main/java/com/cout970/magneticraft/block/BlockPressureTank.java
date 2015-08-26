package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tileentity.TilePressureTank;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPressureTank extends BlockMg {

    public BlockPressureTank() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TilePressureTank();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"pressure_tank"};
    }

    @Override
    public String getName() {
        return "pressure_tank";
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }
}
