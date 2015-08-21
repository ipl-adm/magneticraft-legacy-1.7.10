package com.cout970.magneticraft.items;


import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;
import java.util.Locale;

public class ItemMeta extends Item {
    public static final String name_base = "magneticraft:";
    private String[] unloc_names;
    private String[] texture_names;
    private String[] oreDict_names;
    private int meta_amount;

    public IIcon[] icons;

    public ItemMeta(String unlocNamingPattern, String texturePattern, String oreDictPattern, String[] names) {
        super();
        meta_amount = names.length;
        this.setHasSubtypes(true);
        this.setUnlocalizedName(unlocNamingPattern);
        unloc_names = new String[names.length];
        texture_names = new String[names.length];
        oreDict_names = new String[names.length];
        icons = new IIcon[names.length];
        for (int i = 0; i < names.length; i++) {
            unloc_names[i] = unlocNamingPattern.replaceAll("!id!", Integer.toString(i)).replaceAll("!name!", names[i]).replaceAll("!lower_name!", names[i].toLowerCase(Locale.US));
            texture_names[i] = texturePattern.replaceAll("!id!", Integer.toString(i)).replaceAll("!name!", names[i]).replaceAll("!lower_name!", names[i].toLowerCase(Locale.US));
            oreDict_names[i] = oreDictPattern.replaceAll("!id!", Integer.toString(i)).replaceAll("!name!", names[i]).replaceAll("!lower_name!", names[i].toLowerCase(Locale.US));

        }
    }

    public String getUnlocalizedName(ItemStack is) {
        return unloc_names[is.getItemDamage()];
    }

    @Override
    public void registerIcons(IIconRegister ir) {
        for (int i = 0; i < meta_amount; i++) {
            icons[i] = ir.registerIcon(name_base + texture_names[i]);
        }
    }

    @Override
    public IIcon getIconFromDamage(int meta) {
        if (meta >= meta_amount) {
            meta = 0;
        }
        return icons[meta];
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < meta_amount; i ++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    public String getOreDictName(int meta) {
        return oreDict_names[meta];
    }

    public int getMetaAmount() {
        return meta_amount;
    }
}
