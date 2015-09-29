package com.cout970.magneticraft.block.slabs;

import com.cout970.magneticraft.ManagerBlocks;
import net.minecraft.block.BlockSlab;

public class BlockOreLimeSlab extends BlockMgSlab {

    public BlockOreLimeSlab(boolean full) {
        super(full, ManagerBlocks.oreLime.getMaterial(), ManagerBlocks.oreLime.getUnlocalizedName() + "Slab" + (full ? "Full" : ""));
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setHarvestLevel(ManagerBlocks.oreLime.getHarvestTool(0), ManagerBlocks.oreLime.getHarvestLevel(0));
        setBlockTextureName("magneticraft:limestone");
    }

    @Override
    public BlockSlab getFullBlock() {
        return ManagerBlocks.slabOreLimeDouble;
    }

    @Override
    public BlockSlab getSingleBlock() {
        return ManagerBlocks.slabOreLimeSingle;
    }
}