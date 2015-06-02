package com.cout970.magneticraft.util;

import com.cout970.magneticraft.ManagerItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabMg extends CreativeTabs{
	
	public static final CreativeTabMg MgTab = new CreativeTabMg("Magneticraft");

	public CreativeTabMg(String s) {
		super(s);
	}

	@Override
	public Item getTabIconItem() {
		return ManagerItems.volt;
	}

	@SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel()
    {
        return "Magneticraft";
    }
}
