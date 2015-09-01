package com.cout970.magneticraft.util;

import net.minecraft.item.Item;

public class NamedItem {

    public Item item;
    public String name;

    public NamedItem(Item i, String n) {
        name = n;
        item = i;
    }
}
