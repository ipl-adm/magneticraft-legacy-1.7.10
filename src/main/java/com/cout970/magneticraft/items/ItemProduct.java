package com.cout970.magneticraft.items;

public class ItemProduct extends ItemResource {

    private String oredict;
    private String base_ore;

    public ItemProduct(String unloc, String oredict, String base_ore) {
        super(unloc);
        this.oredict = oredict;
        this.base_ore = base_ore;
    }

    public String getOreDictName() {
        return oredict;
    }

    public String getBaseName() {
        return base_ore;
    }
}
