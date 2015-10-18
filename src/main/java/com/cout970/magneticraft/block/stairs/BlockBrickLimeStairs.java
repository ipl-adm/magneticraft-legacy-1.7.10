package com.cout970.magneticraft.block.stairs;

import com.cout970.magneticraft.ManagerBlocks;

public class BlockBrickLimeStairs extends BlockMgStairs {
    public BlockBrickLimeStairs() {
        super(ManagerBlocks.brickLime, ManagerBlocks.brickLime.getUnlocalizedName() + "Stairs");
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setHarvestLevel(ManagerBlocks.brickLime.getHarvestTool(0), ManagerBlocks.brickLime.getHarvestLevel(0));
        setBlockTextureName("magneticraft:brick_limestone");
    }
}
