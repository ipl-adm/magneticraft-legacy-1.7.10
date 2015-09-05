package com.cout970.magneticraft.block.stairs;

import com.cout970.magneticraft.ManagerBlocks;

public class BlockBurntLimeStairs extends BlockMgStairs {
    public BlockBurntLimeStairs() {
        super(ManagerBlocks.burntLime, ManagerBlocks.burntLime.getUnlocalizedName() + "Stairs");
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setBlockTextureName("magneticraft:burnt_limestone");
    }
}
