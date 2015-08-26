package com.cout970.magneticraft.guide;

import com.google.gson.annotations.Expose;
import com.google.gson.internal.LinkedTreeMap;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.GameRegistry.UniqueIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Stack {

    public ItemStack stack;
    @Expose
    public String name;
    @Expose
    public int metadata;
    @Expose
    public int amount;

    public Stack() {
    }

    public Stack(ItemStack item) {
        stack = item;
        metadata = item.getItemDamage();
        amount = item.stackSize;
        UniqueIdentifier uid = GameRegistry.findUniqueIdentifierFor(item.getItem());
        name = uid.modId + ":" + uid.name;
    }

    public Stack(LinkedTreeMap<String, Object> map) {
        name = (String) map.get("name");
        metadata = toInt(map, "metadata");
        amount = toInt(map, "amount");
    }

    public ItemStack getStack() {
        if (stack == null) {
            String modID = name.substring(0, name.indexOf(':'));
            String id = name.substring(modID.length() + 1);
            Item item = GameRegistry.findItem(modID, id);
            stack = new ItemStack(item, amount, metadata);
        }
        return stack;
    }

    private int toInt(LinkedTreeMap<String, Object> map, String string) {
        Double d = (Double) map.get(string);
        return d.intValue();
    }
}
