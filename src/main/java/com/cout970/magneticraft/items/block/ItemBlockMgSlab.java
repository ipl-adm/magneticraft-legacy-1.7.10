package com.cout970.magneticraft.items.block;

import com.cout970.magneticraft.block.slabs.BlockMgSlab;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockMgSlab extends ItemSlab {
    BlockSlab singleBlock;
    BlockSlab doubleBlock;

    public ItemBlockMgSlab(Block par1) {
        super(par1, ((BlockMgSlab) par1).getSingleBlock(), ((BlockMgSlab) par1).getFullBlock(), false);
        singleBlock = ((BlockMgSlab) par1).getSingleBlock();
        doubleBlock = ((BlockMgSlab) par1).getFullBlock();
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return field_150939_a.getUnlocalizedName();
    }
    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float clickX, float clickY, float clickZ) {
        if (world.getBlock(x, y, z) == singleBlock) {
            int meta = world.getBlockMetadata(x, y, z) & 7;
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
            boolean res = super.onItemUse(stack, player, world, x, y, z, side, clickX, clickY, clickZ);
            if (world.getBlock(x, y, z) != singleBlock) {
                world.setBlockMetadataWithNotify(x, y, z, meta, 2);
            }
            return res;
        } else {
            return super.onItemUse(stack, player, world, x, y, z, side, clickX, clickY, clickZ);
        }

    }
}

