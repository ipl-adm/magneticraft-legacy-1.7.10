package com.cout970.magneticraft.block.slabs;

        import com.cout970.magneticraft.ManagerBlocks;
        import net.minecraft.block.BlockSlab;

public class BlockCobbleLimeSlab extends BlockMgSlab {

    public BlockCobbleLimeSlab(boolean full) {
        super(full, ManagerBlocks.cobbleLime.getMaterial(), ManagerBlocks.cobbleLime.getUnlocalizedName() + "Slab" + (full ? "Full" : ""));
        setHardness(1.5F);
        setStepSound(soundTypeStone);
        setBlockTextureName("magneticraft:cobble_limestone");
    }

    @Override
    public BlockSlab getFullBlock() {
        return ManagerBlocks.slabCobbleLimeDouble;
    }

    @Override
    public BlockSlab getSingleBlock() {
        return ManagerBlocks.slabCobbleLimeSingle;
    }
}