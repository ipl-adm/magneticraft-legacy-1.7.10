package com.cout970.magneticraft.block.stairs;

import com.cout970.magneticraft.ManagerBlocks;

public class BlockBurntBrickLimeStairs extends BlockMgStairs {
    public BlockBurntBrickLimeStairs() {
        super(ManagerBlocks.burntBrickLime, ManagerBlocks.burntBrickLime.getUnlocalizedName() + "Stairs");
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setBlockTextureName("magneticraft:burnt_brick_limestone");
        setHarvestLevel(ManagerBlocks.burntBrickLime.getHarvestTool(0), ManagerBlocks.burntBrickLime.getHarvestLevel(0));
    }
}
