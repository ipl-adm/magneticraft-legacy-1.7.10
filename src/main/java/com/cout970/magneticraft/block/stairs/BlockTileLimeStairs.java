package com.cout970.magneticraft.block.stairs;

import com.cout970.magneticraft.ManagerBlocks;

public class BlockTileLimeStairs extends BlockMgStairs {
    public BlockTileLimeStairs() {
        super(ManagerBlocks.tileLime, ManagerBlocks.tileLime.getUnlocalizedName() + "Stairs");
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setBlockTextureName("magneticraft:tile_limestone");
        setHarvestLevel(ManagerBlocks.tileLime.getHarvestTool(0), ManagerBlocks.tileLime.getHarvestLevel(0));
    }
}
