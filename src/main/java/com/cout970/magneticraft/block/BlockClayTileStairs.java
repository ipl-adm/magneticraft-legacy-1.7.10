package com.cout970.magneticraft.block;

import com.cout970.magneticraft.ManagerBlocks;
import net.minecraft.block.BlockStairs;

public class BlockClayTileStairs extends BlockMgStairs {
    public BlockClayTileStairs() {
        super(ManagerBlocks.roofTile, ManagerBlocks.roofTile.getUnlocalizedName() + "Stairs");
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setBlockTextureName("magneticraft:roofTile");
    }
}
