package com.cout970.magneticraft.block.stairs;

import com.cout970.magneticraft.ManagerBlocks;

public class BlockCobbleLimeStairs extends BlockMgStairs {
    public BlockCobbleLimeStairs() {
        super(ManagerBlocks.cobbleLime, ManagerBlocks.cobbleLime.getUnlocalizedName() + "Stairs");
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setBlockTextureName("magneticraft:cobble_limestone");
        setHarvestLevel(ManagerBlocks.cobbleLime.getHarvestTool(0), ManagerBlocks.cobbleLime.getHarvestLevel(0));
    }
}
