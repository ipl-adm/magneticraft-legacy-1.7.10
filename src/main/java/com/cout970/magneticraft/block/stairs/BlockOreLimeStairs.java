package com.cout970.magneticraft.block.stairs;

import com.cout970.magneticraft.ManagerBlocks;

public class BlockOreLimeStairs extends BlockMgStairs {
    public BlockOreLimeStairs() {
        super(ManagerBlocks.oreLime, ManagerBlocks.oreLime.getUnlocalizedName() + "Stairs");
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setHarvestLevel(ManagerBlocks.oreLime.getHarvestTool(0), ManagerBlocks.oreLime.getHarvestLevel(0));
        setBlockTextureName("magneticraft:limestone");
    }
}
