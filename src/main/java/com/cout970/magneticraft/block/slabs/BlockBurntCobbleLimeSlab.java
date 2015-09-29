package com.cout970.magneticraft.block.slabs;

import com.cout970.magneticraft.ManagerBlocks;
import net.minecraft.block.BlockSlab;

public class BlockBurntCobbleLimeSlab extends BlockMgSlab {

    public BlockBurntCobbleLimeSlab(boolean full) {
        super(full, ManagerBlocks.burntCobbleLime.getMaterial(), ManagerBlocks.burntCobbleLime.getUnlocalizedName() + "Slab" + (full ? "Full" : ""));
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setBlockTextureName("magneticraft:burnt_cobble_limestone");
        setHarvestLevel(ManagerBlocks.burntCobbleLime.getHarvestTool(0), ManagerBlocks.burntCobbleLime.getHarvestLevel(0));
    }

    @Override
    public BlockSlab getFullBlock() {
        return ManagerBlocks.slabBurntCobbleLimeDouble;
    }

    @Override
    public BlockSlab getSingleBlock() {
        return ManagerBlocks.slabBurntCobbleLimeSingle;
    }
}