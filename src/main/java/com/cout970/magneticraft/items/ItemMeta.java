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
    private String[] en_US_names;
    private int meta_amount;

    public IIcon[] icons;

    public ItemMeta(String masterUnlocName, int nlength) {
        super();
        meta_amount = nlength;
        this.setHasSubtypes(true);
        this.setUnlocalizedName(masterUnlocName);
        unloc_names = new String[nlength];
        texture_names = new String[nlength];
        oreDict_names = new String[nlength];
        en_US_names = new String[nlength];
        icons = new IIcon[nlength];
    }


    public ItemMeta setUnlocByPattern(String pattern, String[] names) {
        assert (names.length == meta_amount);
        for (int i = 0; i < names.length; i++) {
            unloc_names[i] = pattern.replaceAll("!id!", Integer.toString(i)).replaceAll("!name!", names[i]).replaceAll("!lower_name!", names[i].toLowerCase(Locale.US));
        }
        return this;
    }

    public ItemMeta setTextureByPattern(String pattern, String[] names) {
        assert (names.length == meta_amount);
        for (int i = 0; i < names.length; i++) {
            texture_names[i] = pattern.replaceAll("!id!", Integer.toString(i)).replaceAll("!name!", names[i]).replaceAll("!lower_name!", names[i].toLowerCase(Locale.US));
        }
        return this;
    }

    public ItemMeta setOreDictByPattern(String pattern, String[] names) {
        assert (names.length == meta_amount);
        for (int i = 0; i < names.length; i++) {
            oreDict_names[i] = pattern.replaceAll("!id!", Integer.toString(i)).replaceAll("!name!", names[i]).replaceAll("!lower_name!", names[i].toLowerCase(Locale.US));
        }
        return this;
    }

    public ItemMeta setNameByPattern(String pattern, String[] names) {
        assert (names.length == meta_amount);
        for (int i = 0; i < names.length; i++) {
            en_US_names[i] = pattern.replaceAll("!id!", Integer.toString(i)).replaceAll("!name!", names[i]).replaceAll("!lower_name!", names[i].toLowerCase(Locale.US));
        }
        return this;
    }

    public String getUnlocalizedName(ItemStack is) {
        return unloc_names[is.getItemDamage()];
    }

    public String getName(ItemStack is, Locale locale) {
        if (locale == Locale.US) {
            return en_US_names[is.getItemDamage()];
        }
        return unloc_names[is.getItemDamage()];
    }

    public String getName(int meta, Locale locale) {
        assert (meta >= 0);
        assert (meta < meta_amount);
        if (locale == Locale.US) {
            return en_US_names[meta];
        }
        return unloc_names[meta];
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

    @SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (int i = 0; i < meta_amount; i++) {
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
