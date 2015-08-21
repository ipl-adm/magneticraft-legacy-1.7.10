package com.cout970.magneticraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.cout970.magneticraft.items.ItemBasic;
import com.cout970.magneticraft.items.ItemBattery;
import com.cout970.magneticraft.items.ItemBucket;
import com.cout970.magneticraft.items.ItemChainSaw;
import com.cout970.magneticraft.items.ItemElectricSword;
import com.cout970.magneticraft.items.ItemFloppyDisk;
import com.cout970.magneticraft.items.ItemHammerIron;
import com.cout970.magneticraft.items.ItemHammerStone;
import com.cout970.magneticraft.items.ItemHardDrive;
import com.cout970.magneticraft.items.ItemHeatCoilCopper;
import com.cout970.magneticraft.items.ItemHeatCoilIron;
import com.cout970.magneticraft.items.ItemHeatCoilTungsten;
import com.cout970.magneticraft.items.ItemHeavyCopperCoil;
import com.cout970.magneticraft.items.ItemJackHammer;
import com.cout970.magneticraft.items.ItemMapPositioner;
import com.cout970.magneticraft.items.ItemModuleCPU_MIPS;
import com.cout970.magneticraft.items.ItemModuleROM;
import com.cout970.magneticraft.items.ItemModuleRam64K;
import com.cout970.magneticraft.items.ItemOilProspector;
import com.cout970.magneticraft.items.ItemPartBrassPipe;
import com.cout970.magneticraft.items.ItemPartCableHigh;
import com.cout970.magneticraft.items.ItemPartCableLow;
import com.cout970.magneticraft.items.ItemPartCableMedium;
import com.cout970.magneticraft.items.ItemPartCopperPipe;
import com.cout970.magneticraft.items.ItemPartCopperWire;
import com.cout970.magneticraft.items.ItemPartHeatCable;
import com.cout970.magneticraft.items.ItemPartIronPipe;
import com.cout970.magneticraft.items.ItemPartOpticFiber;
import com.cout970.magneticraft.items.ItemProduct;
import com.cout970.magneticraft.items.ItemSmallBattery;
import com.cout970.magneticraft.items.ItemThermometer;
import com.cout970.magneticraft.items.ItemThoriumRod;
import com.cout970.magneticraft.items.ItemToolCharger;
import com.cout970.magneticraft.items.ItemTurbine;
import com.cout970.magneticraft.items.ItemUraniumRod;
import com.cout970.magneticraft.items.ItemVoltmeter;
import com.cout970.magneticraft.items.ItemWrench;
import com.cout970.magneticraft.util.NamedItem;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ManagerItems {

	
	public static List<Item> items = new ArrayList<Item>();
	public static List<NamedItem> named = new ArrayList<NamedItem>();
	
	public static String[] oreNames = new String[]{"Iron", "Gold", "Copper", "Tin", "Tungsten", "Lead", "Silver", "Uranium", "Thorium", "Nickel", "Ardite", "Cobalt", "Zinc", "Aluminium", "Platinum", "Titanium", "Osmium", "Bismuth", "Chromium", "Mithril", "Lithium", "Iridium", "Manganese"};
	public static String[][] extraNames = new String[][]{{"Nickel", "Aluminium"}, {"Copper", "Silver"}, {"Gold", "Iron"}, {"Iron", "Silver"}, {"Iron", "Aluminium"}, {"Silver", "Thorium"}, {"Lead", "Copper"}, {"Thorium", "Plutonium"}, {"Uranium", "Plutonium"}, {"Iron", "Zinc"}, {"Cobalt", null},
		{"Ardite", null}, {"Iron", "Nickel"}, {"Iron", "Titanium"}, {"Nickel", "Silver"}, {"Iron", "Nickel"}, {null, "Iron"}, {null, "Zinc"}, {null, "Nickel"}, {null, "Zinc"}, {null, "Iron"}, {null, "Iron"}, {"Iron", null}};
	public static List<ItemProduct> chunks = new ArrayList<ItemProduct>();
//	public static List<ItemProduct> chunks_clean = new ArrayList<ItemProduct>();
	public static List<ItemProduct> rubble = new ArrayList<ItemProduct>();
//	public static List<ItemProduct> rubble_clean = new ArrayList<ItemProduct>();
	public static List<ItemProduct> pebbles = new ArrayList<ItemProduct>();
//	public static List<ItemProduct> pebbles_clean = new ArrayList<ItemProduct>();
	public static List<ItemProduct> dust = new ArrayList<ItemProduct>();
	public static List<ItemProduct> sinterClump = new ArrayList<ItemProduct>();
	public static List<ItemProduct> sinter = new ArrayList<ItemProduct>();
	
	
	public static Item ingotCopper;
	public static Item ingotTungsten;
	public static Item ingotZinc;
	public static Item ingotBrass;
	public static Item ingotCarbide;
	
	public static Item dustSulfur;
	public static Item dustQuartz;
	public static Item dustObsidian;
	public static Item dustSalt;
	
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
	public static Item oil_prospector;
	public static Item photoelectricDust;
	public static Item string_fabric;
	public static Item turbine_wing;
	public static Item plastic;
	public static Item dustDiamond;
	public static Item tool_sword;
	public static Item tool_chainsaw;
	public static Item bucket_oil;
	public static Item bucket_light_oil;
	public static Item bucket_heavy_oil;
	public static Item bucket_hot_crude;
	public static Item chip_cpu_mips;
	public static Item chip_ram;
	public static Item chip_rom;
	public static Item drive_floppy;
	public static Item drive_hard;
	public static Item partironpipe;
	public static Item partheatcable;
	public static Item tool_jackhammer;
	public static Item heavy_copper_coil;
	public static Item tool_charger;
	public static Item part_optic_fiber;
	public static Item dustBrass;
	public static Item stick_iron;
	public static Item small_battery;
	public static Item alternator;
	public static Item magnet;
	public static Item upgrade_drop;
	public static Item upgrade_speed;
	public static Item upgrade_suck;
	public static Item upgrade_slow;
	public static Item rubber;
	public static Item part_brass_pipe;
	public static Item hammer_stone;
	public static Item hammer_iron;
	
	public static void initItems(){
		
		registerProducts();
		
		ingotCopper = new ItemBasic("ingotCopper", "ingot_copper");
		ingotTungsten = new ItemBasic("ingotTungsten", "ingot_tungsten");
		ingotBrass = new ItemBasic("ingotBrass", "ingot_brass");
		ingotZinc = new ItemBasic("ingotZinc", "ingot_zinc");
		ingotCarbide = new ItemBasic("ingotCarbide", "ingot_carbide");
		
		dustSulfur = new ItemBasic("dustSulfur");
		dustQuartz = new ItemBasic("dustQuartz");
		dustObsidian = new ItemBasic("dustObsidian");
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
		partcopperpipe = new ItemPartCopperPipe("copper_pipe");
		partironpipe = new ItemPartIronPipe("iron_pipe");
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
		oil_prospector = new ItemOilProspector("oil_prospector");
		photoelectricDust= new ItemBasic("photoelectric_dust");
		string_fabric = new ItemBasic("string_fabric");
		turbine_wing = new ItemBasic("turbine_wing");
		plastic = new ItemBasic("plastic");
		dustDiamond = new ItemBasic("dustdiamond");
		tool_sword = new ItemElectricSword("electric_sword");
		tool_chainsaw = new ItemChainSaw("chainsaw");
		bucket_oil = new ItemBucket("bucket_oil", ManagerFluids.OIL_NAME);
		bucket_light_oil = new ItemBucket("bucket_light_oil", ManagerFluids.LIGHT_OIL);
		bucket_heavy_oil = new ItemBucket("bucket_heavy_oil", ManagerFluids.HEAVY_OIL);
		bucket_hot_crude = new ItemBucket("bucket_hot_crude", ManagerFluids.HOT_CRUDE);
		chip_cpu_mips = new ItemModuleCPU_MIPS("mips_cpu");
		chip_ram = new ItemModuleRam64K("ram");
		chip_rom = new ItemModuleROM("rom");
		drive_floppy = new ItemFloppyDisk("floppydisk");
		drive_hard = new ItemHardDrive("harddrive");
		partheatcable = new ItemPartHeatCable("heat_cable");
		tool_jackhammer = new ItemJackHammer("jack_hammer");
		heavy_copper_coil = new ItemHeavyCopperCoil("heavy_copper_coil");
		tool_charger = new ItemToolCharger("tool_charger");
		part_optic_fiber = new ItemPartOpticFiber("optic_fiber");
		dustBrass = new ItemBasic("dustBrass", "dust_brass");
		stick_iron = new ItemBasic("stick_iron");
		small_battery = new ItemSmallBattery("battery_small");
		alternator = new ItemBasic("alternator");
		magnet = new ItemBasic("magnet");
		upgrade_drop = new ItemBasic("inserter_item_drop_upgrade");
		upgrade_speed = new ItemBasic("inserter_item_speed_upgrade");
		upgrade_suck = new ItemBasic("inserter_item_suck_upgrade");
		upgrade_slow = new ItemBasic("inserter_item_slow_upgrade");
		rubber = new ItemBasic("rubber");
		part_brass_pipe = new ItemPartBrassPipe("brass_pipe");
		hammer_stone = new ItemHammerStone("stone_hammer");
		hammer_iron = new ItemHammerIron("iron_hammer");
	}
	
	public static void registerItems(){
		
		addItem(ingotCopper, "Copper Ingot");
		addItem(ingotTungsten, "Tungsten Ingot");
		addItem(ingotBrass, "Brass Ingot");
		addItem(ingotZinc, "Zinc Ingot");
		addItem(dustSulfur, "Sulfur Dust");
		addItem(dustQuartz, "Quartz Dust");
		addItem(dustObsidian, "Obsidian Dust");
		addItem(dustSalt, "Salt");
		
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
		addItem(tool_sword, "Electric Sword");
		addItem(tool_chainsaw, "Electric Chainsaw");
		
		addItem(bucket_oil, "Oil Bucket");
		addItem(bucket_light_oil, "Light Oil Bucket");
		addItem(bucket_heavy_oil, "Heavy Oil Bucket");
		addItem(bucket_hot_crude, "Hot Crude Bucket");
		
		addItem(chip_cpu_mips, "Mips CPU");
		addItem(chip_ram, "RAM Module");
		addItem(chip_rom, "ROM Module");
		addItem(drive_floppy, "Floppy Disk");
		addItem(drive_hard, "Hard Drive");
		addItem(partironpipe, "Iron Pipe");
		addItem(partheatcable, "Heat Cable");
		addItem(tool_jackhammer, "Jackhammer");
		addItem(heavy_copper_coil, "Heavy Copper Coil");
		addItem(tool_charger, "Tool Charger");
		addItem(part_optic_fiber, "Optic Fiber");
		addItem(dustBrass, "Brass Dust");
		addItem(stick_iron, "Iron Stick");
		addItem(small_battery, "Small Battery");
		addItem(alternator, "Alternator");
		addItem(magnet, "Magnet");
		addItem(upgrade_drop, "Inserter Upgrade: Drop Items To Ground");
		addItem(upgrade_suck, "Inserter Upgrade: Pick Items From Ground");
		addItem(upgrade_speed, "Inserter Upgrade: Hight Speed");
		addItem(upgrade_slow, "Inserter Upgrade: Slow Speed");
		addItem(rubber, "Rubber Sheet");
		addItem(part_brass_pipe, "Brass Pipe");
		addItem(hammer_stone, "Stone Hammer");
		addItem(hammer_iron, "Iron Hammer");
		
		for(Item i : items){
			GameRegistry.registerItem(i, i.getUnlocalizedName());
		}
	}
	
	private static void registerProducts(){
		for(String name : oreNames){
			chunks.add(new ItemProduct("chunk_"+name.toLowerCase(Locale.US), "chunk"+name, name));
//			chunks_clean.add(new ItemProduct("chunk_clean_"+name.toLowerCase(), "cleanChunk"+name, name));
			rubble.add(new ItemProduct("rubble_"+name.toLowerCase(Locale.US), "rubble"+name, name));
//			rubble_clean.add(new ItemProduct("rubble_clean_"+name.toLowerCase(), "cleanRubble"+name, name));
			pebbles.add(new ItemProduct("pebbles_"+name.toLowerCase(Locale.US), "pebbles"+name, name));
//			pebbles_clean.add(new ItemProduct("pebbles_clean_"+name.toLowerCase(), "cleanPebbles"+name, name));
			dust.add(new ItemProduct("dust_"+name.toLowerCase(Locale.US), "dust"+name, name));
			sinterClump.add(new ItemProduct("sinterClump_"+name.toLowerCase(Locale.US), "sinterClump"+name, name));
			sinter.add(new ItemProduct("sinter_"+name.toLowerCase(Locale.US), "sinter"+name, name));
		}
		for(ItemProduct i : chunks)addItem(i, i.getBaseName()+" Chunk");
//		for(ItemProduct i : chunks_clean)addItem(i, i.getBaseName()+" Clean Chunk");
		for(ItemProduct i : rubble)addItem(i, i.getBaseName()+" Rubble");
//		for(ItemProduct i : rubble_clean)addItem(i, i.getBaseName()+" Clean Rubble");
		for(ItemProduct i : pebbles)addItem(i, i.getBaseName()+" Pebbles");
//		for(ItemProduct i : pebbles_clean)addItem(i, i.getBaseName()+" Clean Pebbles");
		for(ItemProduct i : dust)addItem(i, i.getBaseName()+" Dust");
		for (ItemProduct i : sinterClump)addItem(i, i.getBaseName()+" Sinter Clump");
		for (ItemProduct i : sinter)addItem(i, i.getBaseName()+" Sinter");
	}
	
	public static void addItem(Item i,String name){
		items.add(i);
		named.add(new NamedItem(i, name));
	}
}
