package com.cout970.magneticraft.items;

import java.util.Locale;

public class ItemResource extends ItemBasic {

    public ItemResource(String unlocalizedname) {
        super(unlocalizedname.toLowerCase(Locale.US));
        setTextureName(base + "ores/" + unlocalizedname);
    }
}
