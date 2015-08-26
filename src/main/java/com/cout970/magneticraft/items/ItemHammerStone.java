package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.tool.IHammer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHammerStone extends ItemBasic implements IHammer {

    public ItemHammerStone(String unlocalizedname) {
        super(unlocalizedname);
        setMaxDamage(131);
    }

    @Override
    public ItemStack tick(ItemStack hammer, World w, int x, int y, int z) {
        if (hammer.getItemDamage() < hammer.getMaxDamage()) {
            hammer.setItemDamage(hammer.getItemDamage() + 1);
            return hammer;
        } else {
            return null;
        }
    }

    @Override
    public boolean canHammer(ItemStack hammer, World w, int x, int y, int z) {
        return true;
    }

    @Override
    public int getMaxHits(ItemStack hammer, World w, int x, int y, int z) {
        return 10;
    }
}
