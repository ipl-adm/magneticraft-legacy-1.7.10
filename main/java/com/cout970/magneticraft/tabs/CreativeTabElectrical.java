package com.cout970.magneticraft.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.cout970.magneticraft.ManagerItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabElectrical extends CreativeTabs{

	public CreativeTabElectrical(String s) {
		super(s);
	}

	@Override
	public Item getTabIconItem() {
		return ManagerItems.volt;
	}

	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel()
	{
		return "Magneticraft Electrical Age";
	}
}