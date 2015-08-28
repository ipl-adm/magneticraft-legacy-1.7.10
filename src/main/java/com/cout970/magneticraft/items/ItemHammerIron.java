package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.tool.IHammer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemHammerIron extends ItemBasic implements IHammer {

    public ItemHammerIron(String unlocalizedname) {
        super(unlocalizedname);
        setMaxDamage(250);
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
        return 8;
    }

    @Override
    public boolean hasContainerItem(ItemStack is) {
        return ((getMaxDamage() - is.getItemDamage()) >= 1);
    }

    @Override
    public ItemStack getContainerItem(ItemStack is) {
        ItemStack t = is.copy();
        t.setItemDamage(is.getItemDamage() + 1);
        return ((getMaxDamage() - is.getItemDamage()) >= 1) ? t : null;
    }

    @Override
    public boolean doesContainerItemLeaveCraftingGrid(ItemStack itemStack) {
        return false;
    }
}
