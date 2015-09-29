package com.cout970.magneticraft.block.stairs;

import com.cout970.magneticraft.ManagerBlocks;

public class BlockBurntCobbleLimeStairs extends BlockMgStairs {
    public BlockBurntCobbleLimeStairs() {
        super(ManagerBlocks.burntCobbleLime, ManagerBlocks.burntCobbleLime.getUnlocalizedName() + "Stairs");
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setBlockTextureName("magneticraft:burnt_cobble_limestone");
        setHarvestLevel(ManagerBlocks.burntCobbleLime.getHarvestTool(0), ManagerBlocks.burntCobbleLime.getHarvestLevel(0));
    }
}
