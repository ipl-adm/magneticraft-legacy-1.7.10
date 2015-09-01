package com.cout970.magneticraft.items.block;

import com.cout970.magneticraft.block.slabs.BlockMgSlab;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

public class ItemBlockMgSlab extends ItemSlab {

    public ItemBlockMgSlab(Block par1) {
        super(par1, ((BlockMgSlab) par1).getSingleBlock(), ((BlockMgSlab) par1).getFullBlock(), false);
    }

    @Override
    public String getUnlocalizedName(ItemStack par1ItemStack) {
        return field_150939_a.getUnlocalizedName();
    }

}

