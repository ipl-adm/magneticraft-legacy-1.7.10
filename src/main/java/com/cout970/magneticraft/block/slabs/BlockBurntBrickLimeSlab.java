package com.cout970.magneticraft.block.slabs;

import com.cout970.magneticraft.ManagerBlocks;
import net.minecraft.block.BlockSlab;

public class BlockBurntBrickLimeSlab extends BlockMgSlab {

    public BlockBurntBrickLimeSlab(boolean full) {
        super(full, ManagerBlocks.burntBrickLime.getMaterial(), ManagerBlocks.burntBrickLime.getUnlocalizedName() + "Slab" + (full ? "Full" : ""));
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setBlockTextureName("magneticraft:burnt_brick_limestone");
        setHarvestLevel(ManagerBlocks.burntBrickLime.getHarvestTool(0), ManagerBlocks.burntBrickLime.getHarvestLevel(0));
    }

    @Override
    public BlockSlab getFullBlock() {
        return ManagerBlocks.slabBurntBrickLimeDouble;
    }

    @Override
    public BlockSlab getSingleBlock() {
        return ManagerBlocks.slabBurntBrickLimeSingle;
    }
}