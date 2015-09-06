package com.cout970.magneticraft.items.block;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.slabs.BlockMgSlab;

import com.cout970.magneticraft.util.Log;
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
            VecInt vec = MgDirection.getDirection(side).toVecInt().add(x, y, z);
            Log.info(vec.getX() + " " + vec.getY() + " " + vec.getZ());
            if (vec.getBlock(world) == singleBlock) {
                int meta = world.getBlockMetadata(x, y, z) & 7;
                vec.setBlockMetadata(world, 0, 2);

                boolean res = super.onItemUse(stack, player, world, vec.getX(), vec.getY(), vec.getZ(), 1, clickX, clickY, clickZ);

                if (vec.getBlock(world) != singleBlock) {
                    vec.setBlockMetadata(world, meta, 2);
                }

                return res;
            }

            return super.onItemUse(stack, player, world, x, y, z, side, clickX, clickY, clickZ);

        }


    }
}

