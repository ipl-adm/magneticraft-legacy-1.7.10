package com.cout970.magneticraft;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.items.ItemBasic;
import com.cout970.magneticraft.items.ItemBattery;
import com.cout970.magneticraft.items.ItemChainSaw;
import com.cout970.magneticraft.items.ItemElectricSword;
import com.cout970.magneticraft.items.ItemGravelOre;
import com.cout970.magneticraft.items.ItemHeatCoilCopper;
import com.cout970.magneticraft.items.ItemHeatCoilIron;
import com.cout970.magneticraft.items.ItemHeatCoilTungsten;
import com.cout970.magneticraft.items.ItemMapPositioner;
import com.cout970.magneticraft.items.ItemOilProspector;
import com.cout970.magneticraft.items.ItemPartCableHigh;
import com.cout970.magneticraft.items.ItemPartCableLow;
import com.cout970.magneticraft.items.ItemPartCableMedium;
import com.cout970.magneticraft.items.ItemPartCopperWire;
import com.cout970.magneticraft.items.ItemPartPipe;
import com.cout970.magneticraft.items.ItemResource;
import com.cout970.magneticraft.items.ItemSandOre;
import com.cout970.magneticraft.items.ItemThermometer;
import com.cout970.magneticraft.items.ItemThoriumRod;
import com.cout970.magneticraft.items.ItemTurbine;
import com.cout970.magneticraft.items.ItemUraniumRod;
import com.cout970.magneticraft.items.ItemVoltmeter;
import com.cout970.magneticraft.items.ItemWrench;
import com.cout970.magneticraft.util.NamedItem;

import cpw.mods.fml.common.registry.GameRegistry;

public class ManagerItems {

	
	public static List<Item> items = new ArrayList<Item>();
	public static List<NamedItem> named = new ArrayList<NamedItem>();
	public static Map<String, Item> dusts = new HashMap<String, Item>();
	public static Map<String, Item> ingots = new HashMap<String, Item>();
	public static List<ItemGravelOre> gravelOre = new ArrayList<ItemGravelOre>();
	public static List<ItemSandOre> sandOre = new ArrayList<ItemSandOre>();
	
	public static Item ingotCopper;
	public static Item ingotTungsten;
	public static Item dustSulfur;
	public static Item dustUranium;
	public static Item dustQuartz;
	public static Item dustObsidian;
	public static Item dustCopper;
	public static Item dustTungsten;
	public static Item dustSalt;
	public static Item dustThorium;
	
	public static Item cablelow;
	public static Item cablemedium;
	public static Item cablehigh;
	public static Item wire_copper;
	public static Item partcopperpipe;
	
	public static Item heatCoilCopper;
	public static Item heatCoilIron;
	public static Item heatCoilTungsten;
	public static Item volt;
	public static Item therm;
	public static Item battery_item;
	public static Item map;
	public static Item wrench;
	public static Item turbine_0;
	public static Item turbine_1;
	public static Item turbine_2;
	public static Item uranium_rod;
	public static Item thorium_rod;
	public static Item motor;
	public static Item copper_coil;
	public static Item drill;
	public static Item ingotCarbide;
	public static Item oil_prospector;
	public static Item photoelectricDust;
	public static Item string_fabric;
	public static Item turbine_wing;
	public static Item plastic;
	public static Item dustDiamond;
	public static Item sword;
	public static Item chainsaw;
	
	public static void initItems(){
		//ores
		registerGravel();
		registerSand();
		ingotCopper = new ItemBasic("ingotCopper");
		ingotTungsten = new ItemBasic("ingotTungsten");
		dustSulfur = new ItemBasic("dustSulfur");
		dustUranium = new ItemBasic("dustUranium");
		dustQuartz = new ItemBasic("dustQuartz");
		dustObsidian = new ItemBasic("dustObsidian");
		dustCopper = new ItemBasic("dustCopper");
		dustTungsten = new ItemBasic("dustTungsten");
		dustThorium = new ItemBasic("dustThorium");
		dustSalt = new ItemBasic("dustSalt");
		
		heatCoilCopper = new ItemHeatCoilCopper("heatcoil_copper");
		heatCoilIron = new ItemHeatCoilIron("heatcoil_iron");
		heatCoilTungsten = new ItemHeatCoilTungsten("heatcoil_tungsten");
		cablelow = new ItemPartCableLow("cable_low");
		cablemedium = new ItemPartCableMedium("cable_medium");
		cablehigh = new ItemPartCableHigh("cable_high");
		volt = new ItemVoltmeter("voltmeter");
		therm = new ItemThermometer("thermometer");
		battery_item = new ItemBattery("battery");
		map = new ItemMapPositioner("map_pos");
		partcopperpipe = new ItemPartPipe("copper_pipe");
		wrench = new ItemWrench("wrench");
		turbine_0 = new ItemTurbine("turbine_0",0,3,3,0.33d,0.70f);
		turbine_1 = new ItemTurbine("turbine_1",1,5,5,1d,1.25f);
		turbine_2 = new ItemTurbine("turbine_2",2,7,7,2d,1.85f);
		uranium_rod = new ItemUraniumRod("uranium_rod");
		thorium_rod = new ItemThoriumRod("thorium_rod");
		motor = new ItemBasic("motor");
		copper_coil = new ItemBasic("copper_coil");
		drill = new ItemBasic("drill");
		wire_copper = new ItemPartCopperWire("copper_wire");
		ingotCarbide = new ItemBasic("ingotCarbide");
		oil_prospector = new ItemOilProspector("oil_prospector");
		photoelectricDust= new ItemBasic("photoelectric_dust");
		string_fabric = new ItemBasic("string_fabric");
		turbine_wing = new ItemBasic("turbine_wing");
		plastic = new ItemBasic("plastic");
		dustDiamond = new ItemBasic("dustdiamond");
		sword = new ItemElectricSword("electric_sword");
		chainsaw = new ItemChainSaw("chainsaw");
	}
	
	public static void registerItems(){
		//dusts
		dusts.put("Sulfur", dustSulfur);
		dusts.put("Uranium", dustUranium);
		dusts.put("Quartz", dustQuartz);
		dusts.put("Obsidian", dustObsidian);
		dusts.put("Copper", dustCopper);
		dusts.put("Tungsten", dustTungsten);
		dusts.put("Thorium", dustThorium);
		dusts.put("Salt", dustSalt);
		//ingots
		ingots.put("Copper", ingotCopper);
		ingots.put("Tungsten", ingotTungsten);
		ingots.put("TungstenCarbide", ingotCarbide);
		
		addItem(ingotCopper, "Copper Ingot");
		addItem(ingotTungsten, "Tungsten Ingot");
		addItem(dustSulfur, "Sulfur Dust");
		addItem(dustUranium, "Uranium Dust");
		addItem(dustQuartz, "Quartz Dust");
		addItem(dustObsidian, "Obsidian Dust");
		addItem(dustCopper, "Copper Dust");
		addItem(dustTungsten, "Tungsten Dust");
		addItem(dustThorium, "Thorium Dust");
		addItem(dustSalt, "Salt Dust");
		
		addItem(heatCoilCopper,"Copper Heat Coil");
		addItem(heatCoilIron,"Iron Heat Coil");
		addItem(heatCoilTungsten,"Tungsten Heat Coil");
		addItem(cablelow,"Low Voltage Cable");
		addItem(cablemedium,"Medium Voltage Cable");
		addItem(cablehigh,"High Voltage Cable");
		addItem(volt,"Voltmeter");
		addItem(therm,"Thermometer");
		addItem(battery_item,"Sulfuric Acid Battery");
		addItem(map,"Map Position");
		addItem(partcopperpipe,"Copper Pipe");
		addItem(wrench,"Wrench");
		addItem(turbine_0,"Wind Turbine lv 0");
		addItem(turbine_1,"Wind Turbine lv 1");
		addItem(turbine_2,"Wind Turbine lv 2");
		addItem(uranium_rod,"Uranium Rod");
		addItem(thorium_rod,"Thorium Rod");
		addItem(motor, "Electric Motor");
		addItem(copper_coil,"Copper Coil");
		addItem(drill,"Diamond Drill");
		addItem(wire_copper,"Copper Wire");
		addItem(ingotCarbide,"Carbide Ingot");
		addItem(oil_prospector,"Oil Prospector");
		
		addItem(photoelectricDust,"Photovoltaic Dust");
		addItem(string_fabric,"Fabric");
		addItem(turbine_wing,"Wind Turbine Part");
		addItem(plastic,"Plastic Sheet");
		addItem(dustDiamond,"Diamond Dust");
		addItem(sword, "Electric Sword");
		addItem(chainsaw, "Electric Chainsaw");
		
		for(ItemGravelOre z : gravelOre){
			addItem(z,z.locName+" Gravel Ore");
		}
		
		for(ItemSandOre z : sandOre){
			addItem(z,z.locName+" Sand Ore");
		}
		
		for(Item i : items){
			GameRegistry.registerItem(i, i.getUnlocalizedName());
		}
	}
	
	private static void registerGravel() {
		gravelOre.add(new ItemGravelOre("coppergravel","Copper"));
		gravelOre.add(new ItemGravelOre("irongravel","Iron"));
		gravelOre.add(new ItemGravelOre("goldgravel","Gold"));
		gravelOre.add(new ItemGravelOre("tungstengravel","Tungsten"));
		gravelOre.add(new ItemGravelOre("uraniumgravel","Uranium"));
		gravelOre.add(new ItemGravelOre("thoriumgravel","Thorium"));
		gravelOre.add(new ItemGravelOre("aluminiumgravel","Aluminium"));
		gravelOre.add(new ItemGravelOre("leadgravel","Lead"));
		gravelOre.add(new ItemGravelOre("nickelgravel","Nickel"));
		gravelOre.add(new ItemGravelOre("silvergravel","Silver"));
		gravelOre.add(new ItemGravelOre("tingravel","Tin"));
		gravelOre.add(new ItemGravelOre("titaniumgravel","Titanium"));
		gravelOre.add(new ItemGravelOre("zincgravel","Zinc"));
	}
	
	private static void registerSand() {
		sandOre.add(new ItemSandOre("coppersand","Copper"));
		sandOre.add(new ItemSandOre("ironsand","Iron"));
		sandOre.add(new ItemSandOre("goldsand","Gold"));
		sandOre.add(new ItemSandOre("tungstensand","Tungsten"));
		sandOre.add(new ItemSandOre("uraniumsand","Uranium"));
		sandOre.add(new ItemSandOre("thoriumsand","Thorium"));
		sandOre.add(new ItemSandOre("aluminiumsand","Aluminium"));
		sandOre.add(new ItemSandOre("leadsand","Lead"));
		sandOre.add(new ItemSandOre("nickelsand","Nickel"));
		sandOre.add(new ItemSandOre("silversand","Silver"));
		sandOre.add(new ItemSandOre("tinsand","Tin"));
		sandOre.add(new ItemSandOre("titaniumsand","Titanium"));
		sandOre.add(new ItemSandOre("zincsand","Zinc"));
	}
	
	public static void addItem(Item i,String name){
		items.add(i);
		named.add(new NamedItem(i, name));
	}
	
	public static ItemStack getDust(String g,int amount){
		if(dusts.containsKey(g))return new ItemStack(dusts.get(g),amount);
		ItemStack a = ManagerOreDict.getOre("dust"+g);
		if(a == null)return null;
		a = a.copy();
		a.stackSize = amount;
		return a;
	}
	
	public static ItemStack getSand(String g,int amount){
		for(ItemSandOre s : sandOre){
			if(s.locName.equalsIgnoreCase(g))
				return new ItemStack(s,amount);
		}
		ItemStack a = ManagerOreDict.getOre("sand"+g);
		if(a == null)return null;
		a = a.copy();
		a.stackSize = amount;
		return a;
	}
	
	public static ItemStack getIngot(String g,int amount){
		if(ingots.containsKey(g))return new ItemStack(ingots.get(g),amount);
		ItemStack a = ManagerOreDict.getOre("ingot"+g);
		if(a == null)return null;
		a = a.copy();
		a.stackSize = amount;
		return a;
	}
}
