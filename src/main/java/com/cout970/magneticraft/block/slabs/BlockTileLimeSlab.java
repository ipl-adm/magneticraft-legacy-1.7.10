package com.cout970.magneticraft.block.slabs;

import com.cout970.magneticraft.ManagerBlocks;
import net.minecraft.block.BlockSlab;

public class BlockTileLimeSlab extends BlockMgSlab {

    public BlockTileLimeSlab(boolean full) {
        super(full, ManagerBlocks.tileLime.getMaterial(), ManagerBlocks.tileLime.getUnlocalizedName() + "Slab" + (full ? "Full" : ""));
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setBlockTextureName("magneticraft:tile_limestone");
        setHarvestLevel(ManagerBlocks.tileLime.getHarvestTool(0), ManagerBlocks.tileLime.getHarvestLevel(0));
    }

    @Override
    public BlockSlab getFullBlock() {
        return ManagerBlocks.slabTileLimeDouble;
    }

    @Override
    public BlockSlab getSingleBlock() {
        return ManagerBlocks.slabTileLimeSingle;
    }
}