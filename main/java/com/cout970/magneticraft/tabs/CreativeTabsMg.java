package com.cout970.magneticraft.tabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.cout970.magneticraft.ManagerItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class CreativeTabsMg {

	public static CreativeTabs MainTab = new CreativeTabMain("Magneticraft|Main");
	public static CreativeTabs MedievalAgeTab = new CreativeTabMedieval("Magneticraft|Medieval Age");
	public static CreativeTabs SteamAgeTab = new CreativeTabSteam("Magneticraft|Steam Age");
	public static CreativeTabs ElectricalAgeTab = new CreativeTabElectrical("Magneticraft|Electrical Age");
	public static CreativeTabs IndustrialAgeTab = new CreativeTabIndustrial("Magneticraft|Industrial Age");
	public static CreativeTabs InformationAgeTab = new CreativeTabInformation("Magneticraft|Information Age");

}
