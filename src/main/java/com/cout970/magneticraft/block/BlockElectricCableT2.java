package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tileentity.TileElectricCableMedium;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by cout970 on 23/12/2015.
 */
public class BlockElectricCableT2 extends BlockMg {

    public BlockElectricCableT2() {
        super(Material.iron);
        setBlockBounds(0.3125f, 0.3125f, 0.3125f, 0.6875f, 0.6875f, 0.6875f);
    }

    @Override
    public String[] getTextures() {
        return new String[]{"void"};
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_) {
        return false;
    }

    @Override
    public boolean getUseNeighborBrightness() {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public String getName() {
        return "electric_cable_2";
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        setBlockBounds(0.3125f, 0.3125f, 0.3125f, 0.6875f, 0.6875f, 0.6875f);return new TileElectricCableMedium();
    }
}
