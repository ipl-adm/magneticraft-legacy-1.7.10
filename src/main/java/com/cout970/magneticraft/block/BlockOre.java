package com.cout970.magneticraft.block;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockOre extends Block {

    public String name;

    public BlockOre(String n, String tool, int harvestLevel) {
        super(Material.rock);
        name = n;
        setHardness(1.5f);
        setBlockTextureName("magneticraft:" + name);
        setCreativeTab(CreativeTabsMg.MainTab);
        setStepSound(Block.soundTypeStone);
        setHarvestLevel(tool, harvestLevel);
    }

    public String getUnlocalizedName() {
        return name;
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return this == ManagerBlocks.oreSulfur ? ManagerItems.dustSulfur : this == ManagerBlocks.oreSalt ? ManagerItems.dustSalt : Item.getItemFromBlock(this);
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random p_149745_1_) {
        return this == ManagerBlocks.oreSulfur ? 4 + p_149745_1_.nextInt(5) : 1;
    }

    /**
     * Returns the usual quantity dropped by the block plus a bonus of 1 to 'i' (inclusive).
     */
    @Override
    public int quantityDroppedWithBonus(int fortune, Random ran) {
        if (fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped(0, ran, fortune)) {
            int j = ran.nextInt(fortune + 2) - 1;

            if (j < 0) {
                j = 0;
            }

            return this.quantityDropped(ran) * (j + 1);
        } else {
            return this.quantityDropped(ran);
        }
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, p_149690_7_);
    }

    private Random rand = new Random();

    @Override
    public int getExpDrop(IBlockAccess p_149690_1_, int p_149690_5_, int p_149690_7_) {
        if (this.getItemDropped(p_149690_5_, rand, p_149690_7_) != Item.getItemFromBlock(this)) {
            int j1 = 0;

            if (this == ManagerBlocks.oreSulfur || this == ManagerBlocks.oreSalt) {
                j1 = MathHelper.getRandomIntegerInRange(rand, 3, 7);
            }

            return j1;
        }
        return 0;
    }

}
