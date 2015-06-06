package com.cout970.magneticraft.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import com.cout970.magneticraft.ManagerItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabMedieval extends CreativeTabs{

	public CreativeTabMedieval(String s) {
		super(s);
	}

	@Override
	public Item getTabIconItem() {
		return Items.stick;
	}

	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
	{
		return "Magneticraft Medieval Age";
	}
}