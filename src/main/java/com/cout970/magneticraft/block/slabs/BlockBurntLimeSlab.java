package com.cout970.magneticraft.block.slabs;

import com.cout970.magneticraft.ManagerBlocks;
import net.minecraft.block.BlockSlab;

public class BlockBurntLimeSlab extends BlockMgSlab {

    public BlockBurntLimeSlab(boolean full) {
        super(full, ManagerBlocks.burntLime.getMaterial(), ManagerBlocks.burntLime.getUnlocalizedName() + "Slab" + (full ? "Full" : ""));
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setHarvestLevel(ManagerBlocks.burntLime.getHarvestTool(0), ManagerBlocks.burntLime.getHarvestLevel(0));
        setBlockTextureName("magneticraft:burnt_limestone");
    }

    @Override
    public BlockSlab getFullBlock() {
        return ManagerBlocks.slabBurntLimeDouble;
    }

    @Override
    public BlockSlab getSingleBlock() {
        return ManagerBlocks.slabBurntLimeSingle;
    }
}