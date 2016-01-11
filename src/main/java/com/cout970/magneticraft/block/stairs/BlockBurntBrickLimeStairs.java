package com.cout970.magneticraft.block.stairs;

import com.cout970.magneticraft.ManagerBlocks;

public class BlockBurntBrickLimeStairs extends BlockMgStairs {
    public BlockBurntBrickLimeStairs() {
        super(ManagerBlocks.burntBrickLime, ManagerBlocks.burntBrickLime.getUnlocalizedName() + "Stairs");
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setHarvestLevel(ManagerBlocks.burntBrickLime.getHarvestTool(ManagerBlocks.burntBrickLime.getDefaultState()),
                ManagerBlocks.burntBrickLime.getHarvestLevel(ManagerBlocks.burntBrickLime.getDefaultState()));
    }
}
