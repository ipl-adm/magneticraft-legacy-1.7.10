package com.cout970.magneticraft.block;

import com.cout970.magneticraft.tileentity.TilePermanentMagnet;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockPermanentMagnet extends BlockMg {

    public BlockPermanentMagnet() {
        super(Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TilePermanentMagnet();
    }

    @Override
    public String[] getTextures() {
        return new String[]{"permanent_magnet"};
    }

    @Override
    public String getName() {
        return "permanent_magnet";
    }

}
