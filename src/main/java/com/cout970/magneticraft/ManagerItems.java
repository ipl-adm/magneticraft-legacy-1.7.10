package com.cout970.magneticraft;

import com.cout970.magneticraft.items.*;
import com.cout970.magneticraft.util.NamedItem;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.List;

public class ManagerItems {


    public static List<Item> items = new ArrayList<>();
    public static List<NamedItem> named = new ArrayList<>();

    public static String[] oreNames = new String[]{"Iron", "Gold", "Copper", "Tin", "Tungsten", "Lead", "Silver", "Uranium", "Thorium", "Nickel", "Ardite", "Cobalt", "Zinc", "Aluminium", "Platinum", "Titanium", "Osmium", "Bismuth", "Chromium", "Mithril", "Lithium", "Iridium", "Manganese", "Galena"};
    public static String[][] extraNames = new String[][]{{"Nickel", "Aluminium"}, {"Copper", "Silver"}, {"Gold", "Iron"}, {"Iron", "Silver"}, {"Iron", "Aluminium"}, {"Silver", "Thorium"}, {"Lead", "Copper"}, {"Thorium", "Plutonium"}, {"Uranium", "Plutonium"}, {"Iron", "Zinc"}, {"Cobalt", null},
            {"Ardite", null}, {"Iron", "Nickel"}, {"Iron", "Titanium"}, {"Nickel", "Silver"}, {"Iron", "Nickel"}, {null, "Iron"}, {null, "Zinc"}, {null, "Nickel"}, {null, "Zinc"}, {null, "Iron"}, {null, "Iron"}, {"Iron", null}, {"Silver", null}};
    public static int[] altNames = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 5};
    public static Item ingotCopper;
    public static Item ingotTungsten;
    public static Item ingotZinc;
    public static Item ingotBrass;
    public static Item ingotCarbide;
    public static Item dustSulfur;
    public static Item dustQuartz;
    public static Item dustObsidian;
    public static Item dustSalt;
    public static Item part_copper_cable_low;
    public static Item part_copper_cable_medium;
    public static Item part_copper_cable_high;
    public static Item part_copper_wire;
    public static Item part_copper_pipe;
    public static Item heatCoilCopper;
    public static Item heatCoilIron;
    public static Item heatCoilTungsten;
    public static Item voltmeter;
    public static Item thermometer;
    public static Item battery_item;
    public static Item map_positioner;
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
    public static Item floppy_disk;
    public static Item hard_drive;
    public static Item part_iron_pipe;
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
    public static Item manometer;
    public static Item guide_book;
    
    private static int oreAmount = oreNames.length;
    public static ItemMeta chunks = new ItemMeta("chunks", oreAmount)
            .setUnlocByPattern("chunk_!lower_name!", oreNames)
            .setTextureByPattern("ores/chunk_!lower_name!", oreNames)
            .setOreDictByPattern("chunk!name!", oreNames)
            .setNameByPattern("!name! Chunk", oreNames);
    public static ItemMeta rubble = new ItemMeta("rubble", oreAmount)
            .setUnlocByPattern("rubble_!lower_name!", oreNames)
            .setTextureByPattern("ores/rubble_!lower_name!", oreNames)
            .setOreDictByPattern("rubble!name!", oreNames)
            .setNameByPattern("!name! Rubble", oreNames);
    public static ItemMeta pebbles = new ItemMeta("pebbles", oreAmount)
            .setUnlocByPattern("pebbles_!lower_name!", oreNames)
            .setTextureByPattern("ores/pebbles_!lower_name!", oreNames)
            .setOreDictByPattern("pebbles!name!", oreNames)
            .setNameByPattern("!name! Pebbles", oreNames);
    public static ItemMeta dust = new ItemMeta("dust", oreAmount)
            .setUnlocByPattern("dust_!lower_name!", oreNames)
            .setTextureByPattern("ores/dust_!lower_name!", oreNames)
            .setOreDictByPattern("dust!name!", oreNames)
            .setNameByPattern("!name! Dust", oreNames);

    public static void initItems() {

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
        part_copper_cable_low = new ItemPartCableLow("cable_low");
        part_copper_cable_medium = new ItemPartCableMedium("cable_medium");
        part_copper_cable_high = new ItemPartCableHigh("cable_high");
        voltmeter = new ItemVoltmeter("voltmeter");
        thermometer = new ItemThermometer("thermometer");
        battery_item = new ItemBattery("battery");
        map_positioner = new ItemMapPositioner("map_pos");
        part_copper_pipe = new ItemPartCopperPipe("copper_pipe");
        part_iron_pipe = new ItemPartIronPipe("iron_pipe");
        wrench = new ItemWrench("wrench");
        turbine_0 = new ItemTurbine("turbine_0", 0, 3, 3, 0.33d, 0.70f);
        turbine_1 = new ItemTurbine("turbine_1", 1, 5, 5, 1d, 1.25f);
        turbine_2 = new ItemTurbine("turbine_2", 2, 7, 7, 2d, 1.85f);
        uranium_rod = new ItemUraniumRod("uranium_rod");
        thorium_rod = new ItemThoriumRod("thorium_rod");
        motor = new ItemBasic("motor");
        copper_coil = new ItemBasic("copper_coil");
        drill = new ItemBasic("drill");
        part_copper_wire = new ItemPartCopperWire("copper_wire");
        oil_prospector = new ItemOilProspector("oil_prospector");
        photoelectricDust = new ItemBasic("photoelectric_dust");
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
        floppy_disk = new ItemFloppyDisk("floppydisk");
        hard_drive = new ItemHardDrive("harddrive");
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
        manometer = new ItemManometer("manometer");
        guide_book = new ItemGuideBook("guide_book");
    }

    public static void registerItems() {

        addItem(ingotCopper, "Copper Ingot");
        addItem(ingotTungsten, "Tungsten Ingot");
        addItem(ingotBrass, "Brass Ingot");
        addItem(ingotZinc, "Zinc Ingot");
        addItem(dustSulfur, "Sulfur Dust");
        addItem(dustQuartz, "Quartz Dust");
        addItem(dustObsidian, "Obsidian Dust");
        addItem(dustSalt, "Salt");

        addItem(heatCoilCopper, "Copper Heat Coil");
        addItem(heatCoilIron, "Iron Heat Coil");
        addItem(heatCoilTungsten, "Tungsten Heat Coil");
        addItem(part_copper_cable_low, "Low Voltage Cable");
        addItem(part_copper_cable_medium, "Medium Voltage Cable");
        addItem(part_copper_cable_high, "High Voltage Cable");
        addItem(voltmeter, "Voltmeter");
        addItem(thermometer, "Thermometer");
        addItem(battery_item, "Sulfuric Acid Battery");
        addItem(map_positioner, "Position Map");
        addItem(part_copper_pipe, "Copper Pipe");
        addItem(wrench, "Wrench");
        addItem(turbine_0, "Small Wind Turbine");
        addItem(turbine_1, "Medium Wind Turbine");
        addItem(turbine_2, "Large Wind Turbine");
        addItem(uranium_rod, "Uranium Rod");
        addItem(thorium_rod, "Thorium Rod");
        addItem(motor, "Electric Motor");
        addItem(copper_coil, "Copper Coil");
        addItem(drill, "Diamond Drill");
        addItem(part_copper_wire, "Copper Wire");
        addItem(ingotCarbide, "Carbide Ingot");
        addItem(oil_prospector, "Oil Prospector");

        addItem(photoelectricDust, "Photovoltaic Dust");
        addItem(string_fabric, "Fabric");
        addItem(turbine_wing, "Wind Turbine Part");
        addItem(plastic, "Plastic Sheet");
        addItem(dustDiamond, "Diamond Dust");
        addItem(tool_sword, "Electric Sword");
        addItem(tool_chainsaw, "Electric Chainsaw");

        addItem(bucket_oil, "Oil Bucket");
        addItem(bucket_light_oil, "Light Oil Bucket");
        addItem(bucket_heavy_oil, "Heavy Oil Bucket");
        addItem(bucket_hot_crude, "Hot Crude Bucket");

        addItem(part_iron_pipe, "Iron Pipe");
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
        addItem(upgrade_speed, "Inserter Upgrade: Increase Speed");
        addItem(upgrade_slow, "Inserter Upgrade: Decrease Speed");
        addItem(rubber, "Rubber Sheet");
        addItem(part_brass_pipe, "Brass Pipe");
        addItem(hammer_stone, "Stone Hammer");
        addItem(hammer_iron, "Iron Hammer");
        if (Magneticraft.DEBUG) {
            addItem(manometer, "Manometer");
            addItem(chip_cpu_mips, "Mips CPU");
            addItem(chip_ram, "RAM Module");
            addItem(chip_rom, "ROM Module");
            addItem(floppy_disk, "Floppy Disk");
            addItem(hard_drive, "Hard Drive");
            addItem(guide_book, "Guide Book");
        }

        for (Item i : items) {
            GameRegistry.registerItem(i, i.getUnlocalizedName());
        }
    }

    private static void registerProducts() {
        addItem(chunks, "chunk");
        addItem(rubble, "rubble");
        addItem(pebbles, "pebbles");
        addItem(dust, "dust");

    }

    public static void addItem(Item i, String name) {
        items.add(i);
        named.add(new NamedItem(i, name));
    }
}
