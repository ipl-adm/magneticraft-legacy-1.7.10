package com.cout970.magneticraft.block;

import com.cout970.magneticraft.ManagerBlocks;
import net.minecraft.block.BlockSlab;

public class BlockClayTileSlab extends BlockMgSlab {

    public BlockClayTileSlab(boolean full) {
        super(full, ManagerBlocks.roofTile.getMaterial(), ManagerBlocks.roofTile.getUnlocalizedName() + "Slab" + (full ? "Full" : ""));
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setBlockTextureName("magneticraft:roofTile");
    }

    @Override
    public BlockSlab getFullBlock() {
        return ManagerBlocks.slabRoofTileDouble;
    }

    @Override
    public BlockSlab getSingleBlock() {
        return ManagerBlocks.slabRoofTileSingle;
    }
}