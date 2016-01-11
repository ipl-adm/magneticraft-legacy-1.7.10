package com.cout970.magneticraft.block.slabs;

import com.cout970.magneticraft.ManagerBlocks;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;

public class BlockBrickLimeSlab extends BlockMgSlab {

    public BlockBrickLimeSlab(boolean full) {
        super(full, ManagerBlocks.brickLime.getMaterial(), ManagerBlocks.brickLime.getUnlocalizedName() + "Slab" + (full ? "Full" : ""));
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setHarvestLevel(ManagerBlocks.brickLime.getHarvestTool(ManagerBlocks.brickLime.getDefaultState()),
                ManagerBlocks.brickLime.getHarvestLevel(ManagerBlocks.brickLime.getDefaultState()));
    }

    @Override
    public BlockSlab getFullBlock() {
        return ManagerBlocks.slabBrickLimeDouble;
    }

    @Override
    public BlockSlab getSingleBlock() {
        return ManagerBlocks.slabBrickLimeSingle;
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return null;
    }
}