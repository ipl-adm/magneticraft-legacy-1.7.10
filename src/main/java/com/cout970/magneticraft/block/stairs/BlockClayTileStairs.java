package com.cout970.magneticraft.block.stairs;

import com.cout970.magneticraft.ManagerBlocks;

public class BlockClayTileStairs extends BlockMgStairs {
    public BlockClayTileStairs() {
        super(ManagerBlocks.roofTile, ManagerBlocks.roofTile.getUnlocalizedName() + "Stairs");
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setBlockTextureName("magneticraft:roofTile");
    }
}
