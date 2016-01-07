package com.cout970.magneticraft;

import com.cout970.magneticraft.block.*;
import com.cout970.magneticraft.block.compat.BlockEUAlternator;
import com.cout970.magneticraft.block.compat.BlockKineticGenerator;
import com.cout970.magneticraft.block.compat.BlockRCAlternator;
import com.cout970.magneticraft.block.compat.BlockRFAlternator;
import com.cout970.magneticraft.block.computer.BlockComputer;
import com.cout970.magneticraft.block.computer.BlockDroidRED;
import com.cout970.magneticraft.block.computer.BlockMonitor;
import com.cout970.magneticraft.block.energy.*;
import com.cout970.magneticraft.block.heat.BlockCooler;
import com.cout970.magneticraft.block.heat.BlockHeatResistance;
import com.cout970.magneticraft.block.heat.BlockHeatSink;
import com.cout970.magneticraft.block.multiblock.*;
import com.cout970.magneticraft.block.multiblock.controllers.*;
import com.cout970.magneticraft.block.slabs.*;
import com.cout970.magneticraft.block.stairs.*;
import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.items.block.ItemBlockMg;
import com.cout970.magneticraft.items.block.ItemBlockMgSlab;
import com.cout970.magneticraft.items.block.ItemBlockPumpjack;
import com.cout970.magneticraft.items.block.ItemBlockShelvingUnit;
import com.cout970.magneticraft.tileentity.*;
import com.cout970.magneticraft.tileentity.multiblock.*;
import com.cout970.magneticraft.tileentity.multiblock.controllers.*;
import com.cout970.magneticraft.tileentity.pole.*;
import com.cout970.magneticraft.tileentity.shelf.TileShelfFiller;
import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import com.cout970.magneticraft.util.NamedBlock;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import com.cout970.magneticraft.util.tile.TileHeatConductor;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.LinkedList;
import java.util.List;

public class ManagerBlocks {

    public static List<Class<? extends TileEntity>> tileEntities = new LinkedList<>();
    public static List<Block> blocks = new LinkedList<>();
    public static List<Block> microblocks = new LinkedList<>();
    public static List<NamedBlock> named = new LinkedList<>();

    public static Block oreCopper;
    public static Block oreTungsten;
    public static Block oreUranium;
    public static Block oreSulfur;
    public static Block oreThorium;
    public static Block oreZinc;
    public static Block oreSalt;
    public static Block oreLime;
    public static Block oilSource;
    public static Block oilSourceDrained;
    public static Block ingot_block_copper;
    public static Block ingot_block_tungsten;
    public static Block ingot_block_carbide;
    public static Block ingot_block_brass;
    public static Block ingot_block_zinc;
    public static Block dust_block_salt;
    public static Block dust_block_sulfur;
    public static Block solarpanel;
    public static Block furnace;
    public static Block battery;
    public static Block transformer_lm;
    public static Block transformer_mh;
    public static Block firebox;
    public static Block boiler;
    public static Block cooler;
    public static Block heat_resist;
    public static Block fluidhopper;
    public static Block basic_gen;
    public static Block pumpJack;
    public static Block steam_engine;
    public static Block crusher;
    public static Block chassis;
    public static Block multi_io;
    public static Block conveyor_l;
    public static Block mirror;
    public static Block breaker;
    public static Block kinetic;
    public static Block windturbine;
    public static Block geothermal;
    public static Block permagnet;
    public static Block concreted_pipe;
    public static Block reactor_vessel;
    public static Block thermopile;
    public static Block multi_energy_low;
    public static Block reactor_control_rods;
    public static Block reactor_wall;
    public static Block reactor_activator;
    public static Block inserter;
    public static Block housing;
    public static Block grinder;
    public static Block miner;
    public static Block tesla_coil;
    public static Block airlock;
    public static Block biomass_burner;
    public static Block stirling;
    public static Block infinite_water;
    public static Block refinery;
    public static Block refinery_gap;
    public static Block refinery_tank;
    public static Block copper_tank;
    public static Block heater;
    public static Block air_bubble;
    public static Block solar_tower_core;
    public static Block polimerizer;
    public static Block reactor_controller;
    public static Block multi_energy_medium;
    public static Block steam_turbine;
    public static Block combustion_engine;
    public static Block heat_sink;
    public static Block brickFurnace;
    public static Block crafter;
    public static Block monitor;
    public static Block cpu;
    public static Block multi_heat;
    public static Block pole_tier1;
    public static Block pole_connector;
    public static Block droid_red;
    public static Block oil_distillery;
    public static Block grinding_mill;
    public static Block multi_kinetic;
    public static Block grinding_mill_gap;
    public static Block wooden_shaft;
    public static Block hand_crank_gen;
    public static Block infinite_steam;
    public static Block void_inv;
    public static Block electric_switch;
    public static Block diode;
    public static Block resistance;
    public static Block rf_alternator;
    public static Block rc_alternator;
    public static Block eu_alternator;
    public static Block pole_cable_wire;
    public static Block infinite_energy;
    public static Block sifter;
    public static Block mb_controls;
    public static Block pressure_tank;
    public static Block crushing_table;
    public static Block shelving_unit;
    public static Block sprinkler;

    public static BlockSlab slabOreLimeSingle;
    public static BlockSlab slabOreLimeDouble;
    public static Block stairsOreLime;

    public static Block burntLime;
    public static BlockSlab slabBurntLimeSingle;
    public static BlockSlab slabBurntLimeDouble;
    public static Block stairsBurntLime;

    public static Block brickLime;
    public static BlockSlab slabBrickLimeSingle;
    public static BlockSlab slabBrickLimeDouble;
    public static Block stairsBrickLime;

    public static Block roofTile;
    public static BlockSlab slabRoofTileSingle;
    public static BlockSlab slabRoofTileDouble;
    public static Block stairsRoofTile;

    public static Block tileLime;
    public static BlockSlab slabTileLimeSingle;
    public static BlockSlab slabTileLimeDouble;
    public static Block stairsTileLime;

    public static Block burntBrickLime;
    public static BlockSlab slabBurntBrickLimeSingle;
    public static BlockSlab slabBurntBrickLimeDouble;
    public static Block stairsBurntBrickLime;

    public static Block cobbleLime;
    public static BlockSlab slabCobbleLimeSingle;
    public static BlockSlab slabCobbleLimeDouble;
    public static Block stairsCobbleLime;

    public static Block burntCobbleLime;
    public static BlockSlab slabBurntCobbleLimeSingle;
    public static BlockSlab slabBurntCobbleLimeDouble;
    public static Block stairsBurntCobbleLime;

    public static Block blockTar;

    public static void initBlocks() {
        //ores & resources
        oilSource = new BlockOilSource();
        oreCopper = new BlockOre("copper_ore", "pickaxe", 1);
        oreTungsten = new BlockOre("tungsten_ore", "pickaxe", 2);
        oreUranium = new BlockOre("uranium_ore", "pickaxe", 2);
        oreSulfur = new BlockOre("sulfur_ore", "pickaxe", 1);
        oreThorium = new BlockOre("thorium_ore", "pickaxe", 2);
        oreSalt = new BlockOre("salt_ore", "pickaxe", 1);
        oreZinc = new BlockOre("zinc_ore", "pickaxe", 1);

        oreLime = new BlockOre("limestone", "pickaxe", 0);
        slabOreLimeSingle = new BlockOreLimeSlab(false);
        slabOreLimeDouble = new BlockOreLimeSlab(true);
        stairsOreLime = new BlockOreLimeStairs();

        burntLime = new BlockSimple("burnt_limestone");
        burntLime.setHarvestLevel(oreLime.getHarvestTool(0), oreLime.getHarvestLevel(0));
        slabBurntLimeSingle = new BlockBurntLimeSlab(false);
        slabBurntLimeDouble = new BlockBurntLimeSlab(true);
        stairsBurntLime = new BlockBurntLimeStairs();

        brickLime = new BlockSimple("brick_limestone");
        brickLime.setHarvestLevel(oreLime.getHarvestTool(0), oreLime.getHarvestLevel(0));
        slabBrickLimeSingle = new BlockBrickLimeSlab(false);
        slabBrickLimeDouble = new BlockBrickLimeSlab(true);
        stairsBrickLime = new BlockBrickLimeStairs();

        roofTile = new BlockClayTile();
        roofTile.setHarvestLevel(Blocks.hardened_clay.getHarvestTool(0), Blocks.hardened_clay.getHarvestLevel(0));
        slabRoofTileSingle = new BlockClayTileSlab(false);
        slabRoofTileDouble = new BlockClayTileSlab(true);
        stairsRoofTile = new BlockClayTileStairs();

        tileLime = new BlockSimple("tile_limestone");
        tileLime.setHarvestLevel(oreLime.getHarvestTool(0), oreLime.getHarvestLevel(0));
        slabTileLimeSingle = new BlockTileLimeSlab(false);
        slabTileLimeDouble = new BlockTileLimeSlab(true);
        stairsTileLime = new BlockTileLimeStairs();

        cobbleLime = new BlockSimple("cobble_limestone");
        cobbleLime.setHarvestLevel(oreLime.getHarvestTool(0), oreLime.getHarvestLevel(0));
        slabCobbleLimeSingle = new BlockCobbleLimeSlab(false);
        slabCobbleLimeDouble = new BlockCobbleLimeSlab(true);
        stairsCobbleLime = new BlockCobbleLimeStairs();

        burntBrickLime = new BlockSimple("burnt_brick_limestone");
        burntBrickLime.setHarvestLevel(oreLime.getHarvestTool(0), oreLime.getHarvestLevel(0));
        slabBurntBrickLimeSingle = new BlockBurntBrickLimeSlab(false);
        slabBurntBrickLimeDouble = new BlockBurntBrickLimeSlab(true);
        stairsBurntBrickLime = new BlockBurntBrickLimeStairs();

        burntCobbleLime = new BlockSimple("burnt_cobble_limestone");
        burntCobbleLime.setHarvestLevel(oreLime.getHarvestTool(0), oreLime.getHarvestLevel(0));
        slabBurntCobbleLimeSingle = new BlockBurntCobbleLimeSlab(false);
        slabBurntCobbleLimeDouble = new BlockBurntCobbleLimeSlab(true);
        stairsBurntCobbleLime = new BlockBurntCobbleLimeStairs();

        oilSourceDrained = new BlockOilSourceDrained();
        solarpanel = new BlockSolarPanel();
        furnace = new BlockElectricFurnace();
        battery = new BlockBattery();
        transformer_lm = new BlockTransformerLow_Medium();
        transformer_mh = new BlockTransformerMedium_High();
        basic_gen = new BlockBasicGenerator();
        firebox = new BlockFireBox();
        boiler = new BlockBoiler();
        conveyor_l = new BlockConveyorLow();
        pumpJack = new BlockPumpJack();
        cooler = new BlockCooler();
        crusher = new BlockCrusher();
        chassis = new BlockMB_Chassis();
        multi_io = new BlockMB_Inv();
        mirror = new BlockMirror();
        windturbine = new BlockWindTurbine();
        permagnet = new BlockPermanentMagnet();
        fluidhopper = new BlockFluidHopper();
        heat_resist = new BlockHeatResistance();
        breaker = new BlockBreaker();
        steam_engine = new BlockSteamEngine();
        geothermal = new BlockGeothermalPump();
        concreted_pipe = new BlockConcretedPipe();
        reactor_vessel = new BlockReactorVessel();
        thermopile = new BlockThermopile();
        multi_energy_low = new BlockMB_Energy_Low();
        reactor_control_rods = new BlockReactorControlRods();
        reactor_wall = new BlockReactorWall();
        inserter = new BlockInserter();
        reactor_activator = new BlockReactorActivator();
        housing = new BlockMachineHousing();
        grinder = new BlockGrinder();
        miner = new BlockMiner();
        tesla_coil = new BlockTeslaCoil();
        airlock = new BlockAirlock();
        biomass_burner = new BlockBiomassBurner();
        stirling = new BlockStirlingGenerator();
        infinite_water = new BlockInfiniteWater();
        refinery = new BlockRefinery();
        refinery_gap = new BlockRefineryGap();
        refinery_tank = new BlockRefineryTank();
        copper_tank = new BlockCopperTank();
        heater = new BlockHeater();
        air_bubble = new BlockAirBubble();
        solar_tower_core = new BlockSolarTowerCore();
        polimerizer = new BlockPolymerizer();
        reactor_controller = new BlockReactorController();
        multi_energy_medium = new BlockMB_Energy_Medium();
        steam_turbine = new BlockSteamTurbine();
        combustion_engine = new BlockCombustionEngine();
        heat_sink = new BlockHeatSink();
        brickFurnace = new BlockBrickFurnace();
        crafter = new BlockCrafter();
        monitor = new BlockMonitor();
        cpu = new BlockComputer();
        multi_heat = new BlockMB_Heat();
        pole_tier1 = new BlockElectricPoleTier1();
        pole_connector = new BlockElectricConnector();
        droid_red = new BlockDroidRED();
        oil_distillery = new BlockOilDistillery();
        grinding_mill = new BlockGrindingMill();
        grinding_mill_gap = new BlockGrindingMillGap();
        wooden_shaft = new BlockWoodenShaft();
        hand_crank_gen = new BlockHandCrankGenerator();
        multi_kinetic = new BlockMB_Kinetic();
        infinite_steam = new BlockInfiniteSteam();
        void_inv = new BlockVoidInventory();
        electric_switch = new BlockElectricSwitch();
        diode = new BlockDiode();
        resistance = new BlockResistance();
        if (ManagerIntegration.COFH_ENERGY) {
            rf_alternator = new BlockRFAlternator();
            kinetic = new BlockKineticGenerator();
        }
        if (ManagerIntegration.RAILCRAFT) {
            rc_alternator = new BlockRCAlternator();
        }
        if (ManagerIntegration.IC2) {
            eu_alternator = new BlockEUAlternator();
        }
        if (Magneticraft.DEBUG) {
            sprinkler = new BlockSprinkler();
        }
        pole_cable_wire = new BlockElectricPoleCableWire();
        infinite_energy = new BlockInfiniteEnergy();
        sifter = new BlockSifter();
        ingot_block_copper = new BlockOfIngots("block_copper");
        ingot_block_copper.setHarvestLevel(oreCopper.getHarvestTool(0), oreCopper.getHarvestLevel(0));
        ingot_block_tungsten = new BlockOfIngots("block_tungsten");
        ingot_block_tungsten.setHarvestLevel(oreTungsten.getHarvestTool(0), oreTungsten.getHarvestLevel(0));
        ingot_block_carbide = new BlockOfIngots("block_carbide");
        ingot_block_carbide.setHarvestLevel(oreTungsten.getHarvestTool(0), oreTungsten.getHarvestLevel(0));
        ingot_block_brass = new BlockOfIngots("block_brass");
        ingot_block_brass.setHarvestLevel(oreCopper.getHarvestTool(0), oreCopper.getHarvestLevel(0));
        ingot_block_zinc = new BlockOfIngots("block_zinc");
        ingot_block_zinc.setHarvestLevel(oreZinc.getHarvestTool(0), oreZinc.getHarvestLevel(0));
        dust_block_salt = new BlockOfIngots("block_salt");
        dust_block_salt.setHarvestLevel(oreSalt.getHarvestTool(0), oreSalt.getHarvestLevel(0));
        dust_block_sulfur = new BlockOfIngots("block_sulfur");
        dust_block_sulfur.setHarvestLevel(oreSulfur.getHarvestTool(0), oreSulfur.getHarvestLevel(0));
        mb_controls = new BlockMB_Controls();
        pressure_tank = new BlockPressureTank();
        crushing_table = new BlockCrushingTable();
        shelving_unit = new BlockShelvingUnit();
        blockTar = new BlockTar();
    }

    public static void registerBlocks() {

        addBlock(oilSource, "Oil Source");
        addBlock(oilSourceDrained, "Drained Oil Source");
        addBlock(oreCopper, "Copper Ore", true);
        addBlock(oreSalt, "Salt Ore", true);
        addBlock(oreSulfur, "Sulfur Ore", true);
        addBlock(oreThorium, "Thorium Ore", true);
        addBlock(oreTungsten, "Tungsten Ore", true);
        addBlock(oreUranium, "Uranium Ore", true);

        addBlock(oreLime, "Limestone", true);
        addAltItemBlock(slabOreLimeSingle, ItemBlockMgSlab.class, "Limestone Slab");
        addAltItemBlock(slabOreLimeDouble, ItemBlockMgSlab.class, "Limestone Slab");
        addBlock(stairsOreLime, "Limestone Stairs");

        addBlock(burntLime, "Burnt Limestone", true);
        addAltItemBlock(slabBurntLimeSingle, ItemBlockMgSlab.class, "Burnt Limestone Slab");
        addAltItemBlock(slabBurntLimeDouble, ItemBlockMgSlab.class, "Burnt Limestone Slab");
        addBlock(stairsBurntLime, "Burnt Limestone Stairs");

        addBlock(brickLime, "Limestone Bricks", true);
        addAltItemBlock(slabBrickLimeSingle, ItemBlockMgSlab.class, "Limestone Brick Slab");
        addAltItemBlock(slabBrickLimeDouble, ItemBlockMgSlab.class, "Limestone Brick Slab");
        addBlock(stairsBrickLime, "Limestone Brick Stairs");

        addBlock(roofTile, "Clay Roof Tiles", true);
        addAltItemBlock(slabRoofTileSingle, ItemBlockMgSlab.class, "Clay Roof Tile Slab");
        addAltItemBlock(slabRoofTileDouble, ItemBlockMgSlab.class, "Clay Roof Tile Slab");
        addBlock(stairsRoofTile, "Clay Roof Tile Stairs");

        addBlock(tileLime, "Limestone Tiles", true);
        addAltItemBlock(slabTileLimeSingle, ItemBlockMgSlab.class, "Limestone Tile Slab");
        addAltItemBlock(slabTileLimeDouble, ItemBlockMgSlab.class, "Limestone Tile Slab");
        addBlock(stairsTileLime, "Limestone Tile Stairs");

        addBlock(burntBrickLime, "Burnt Limestone Bricks", true);
        addAltItemBlock(slabBurntBrickLimeSingle, ItemBlockMgSlab.class, "Burnt Limestone Brick Slab");
        addAltItemBlock(slabBurntBrickLimeDouble, ItemBlockMgSlab.class, "Burnt Limestone Brick Slab");
        addBlock(stairsBurntBrickLime, "Burnt Limestone Brick Stairs");

        addBlock(cobbleLime, "Limestone Cobble", true);
        addAltItemBlock(slabCobbleLimeSingle, ItemBlockMgSlab.class, "Limestone Cobble Slab");
        addAltItemBlock(slabCobbleLimeDouble, ItemBlockMgSlab.class, "Limestone Cobble Slab");
        addBlock(stairsCobbleLime, "Limestone Cobble Stairs");

        addBlock(burntCobbleLime, "Burnt Limestone Cobble", true);
        addAltItemBlock(slabBurntCobbleLimeSingle, ItemBlockMgSlab.class, "Burnt Limestone Cobble Slab");
        addAltItemBlock(slabBurntCobbleLimeDouble, ItemBlockMgSlab.class, "Burnt Limestone Cobble Slab");
        addBlock(stairsBurntCobbleLime, "Burnt Limestone Cobble Stairs");

        addBlock(ingot_block_copper, "Copper Block", true);
        addBlock(ingot_block_tungsten, "Tungsten Block", true);
        addBlock(ingot_block_carbide, "Carbide Block", true);
        addBlock(ingot_block_brass, "Brass Block", true);
        addBlock(ingot_block_zinc, "Zinc Block", true);
        addBlock(solarpanel, "Solar Panel");
        addBlock(furnace, "Electric Furnace");
        addBlock(battery, "Battery");
        addBlock(transformer_lm, "Low/Medium Voltage Transformer");
        addBlock(transformer_mh, "Medium/High Voltage Transformer");
        addBlock(firebox, "Firebox");
        addBlock(boiler, "Boiler");
        addBlock(cooler, "Cooler");
        addBlock(heat_resist, "Heat Resistance");
        addBlock(fluidhopper, "Fluid Hopper");
        addBlock(basic_gen, "Basic Generator");
        addAltItemBlock(pumpJack, ItemBlockPumpjack.class, "Pumpjack");
        addBlock(steam_engine, "Steam Engine");
        addBlock(crusher, "Crusher Control");
        addBlock(chassis, "Multiblock Chassis");
        addBlock(multi_io, "Multiblock I/O");
        addBlock(conveyor_l, "Conveyor Belt");
        addBlock(mirror, "Mirror");
        addBlock(breaker, "Block Breaker");
        addBlock(windturbine, "Wind Turbine");
        addBlock(geothermal, "Geothermal Pump");
        addBlock(permagnet, "Permanent Magnet");
        addBlock(concreted_pipe, "Concrete");
        addBlock(thermopile, "Thermopile");
        addBlock(multi_energy_low, "Multiblock Energy I/O (Low Voltage)");
        addBlock(inserter, "Inserter");
        addBlock(housing, "Machine Housing");
        addBlock(grinder, "Grinder Control");
        addBlock(miner, "Miner");
        addBlock(tesla_coil, "Tesla Coil");
        addBlock(airlock, "Airlock");
        addBlock(biomass_burner, "Biomass Burner");
        addBlock(stirling, "Stirling Generator Control");
        addBlock(infinite_water, "Infinite Water");
        addBlock(refinery, "Refinery Control");
        addBlock(refinery_gap, "Multiblock Component");
        addBlock(refinery_tank, "Refinery Tank");
        addBlock(copper_tank, "Fluid Tank");
        addBlock(heater, "Heater");
        addBlock(air_bubble, "Air Bubble");
        addBlock(solar_tower_core, "Solar Tower Core");
        addBlock(polimerizer, "Polymerizer Control");
        addBlock(multi_energy_medium, "Multiblock Energy I/O (Medium Voltage)");
        addBlock(steam_turbine, "Turbine Control");
        addBlock(combustion_engine, "Combustion Engine");
        addBlock(heat_sink, "Heat Sink");
        addBlock(brickFurnace, "Brick Furnace");
        addBlock(crafter, "Crafter");
        addBlock(multi_heat, "Multiblock Heat I/O");
        addBlock(oil_distillery, "Oil Distillery Control");
        addBlock(pole_tier1, "Electrical Wooden Pole");
        addBlock(multi_kinetic, "Multiblock Kinetic I/O");
        addBlock(infinite_steam, "Creative Infinite Steam");
        addBlock(void_inv, "Void Inventory");
        addBlock(electric_switch, "Electric Switch");
        addBlock(diode, "Diode");
        addBlock(resistance, "Resistance");
        if (ManagerIntegration.COFH_ENERGY) {
            addBlock(rf_alternator, "RF Alternator");
            addBlock(kinetic, "Kinetic Generator");
        }
        if (ManagerIntegration.RAILCRAFT) {
            addBlock(rc_alternator, "RailCraft Charge Alternator");
        }
        if (ManagerIntegration.IC2) {
            addBlock(eu_alternator, "EU Alternator");
        }
        if (Magneticraft.DEBUG) {
            addBlock(pole_connector, "Electrical Pole Connector");
            addBlock(reactor_vessel, "Reactor Vessel");
            addBlock(reactor_control_rods, "Reactor Control");
            addBlock(reactor_wall, "Reactor Wall");
            addBlock(reactor_activator, "Reactor Accelerator");
            addBlock(grinding_mill, "Grinding Mill");
            addBlock(grinding_mill_gap, "Grinding Mill");
            addBlock(wooden_shaft, "Wooden Shaft");
            addBlock(hand_crank_gen, "Hand Crank");
            addBlock(reactor_controller, "Reactor Controller");
            addBlock(monitor, "Text Monitor");
            addBlock(cpu, "CPU");
            addBlock(droid_red, "R.E.D.");
            addBlock(pressure_tank, "Pressure Tank");
            addBlock(sprinkler, "Sprinkler");
        }
        addBlock(pole_cable_wire, "Electrical Pole With Transformer");
        addBlock(infinite_energy, "Creative Infinite Energy");
        addBlock(sifter, "Sifter Control");
        addBlock(mb_controls, "Multiblock Controls");
        addBlock(dust_block_salt, "Salt Block", true);
        addBlock(dust_block_sulfur, "Sulfur Block", true);
        addBlock(oreZinc, "Zinc Ore", true);
        addBlock(crushing_table, "Crushing Table");
        addAltItemBlock(shelving_unit, ItemBlockShelvingUnit.class, "Shelving Unit");
        addBlock(blockTar, "Tar");

        for (Block b : blocks)
            GameRegistry.registerBlock(b, ItemBlockMg.class, b.getUnlocalizedName());

        for (Block b : microblocks) {
            FMLInterModComms.sendMessage("ForgeMicroblock", "microMaterial", new ItemStack(b));
        }
    }


    public static void registerTileEntities() {

        tileEntities.add(TileBase.class);
        tileEntities.add(TileConductorLow.class);
        tileEntities.add(TileSolarPanel.class);
        tileEntities.add(TileElectricFurnace.class);
        tileEntities.add(TileBattery.class);
        tileEntities.add(TileTransformerLow_Medium.class);
        tileEntities.add(TileTransformerMedium_High.class);
        tileEntities.add(TileBasicGenerator.class);
        tileEntities.add(TileHeatConductor.class);
        tileEntities.add(TileFireBox.class);
        tileEntities.add(TileBoiler.class);
        tileEntities.add(TileCrusher.class);
        tileEntities.add(TileMB_Inv.class);
        tileEntities.add(TilePumpJack.class);
        tileEntities.add(TileCooler.class);
        tileEntities.add(TileMirror.class);
        tileEntities.add(TileWindTurbine.class);
        tileEntities.add(TilePermanentMagnet.class);
        tileEntities.add(TileFluidHopper.class);
        tileEntities.add(TileHeatResistance.class);
        tileEntities.add(TileBreaker.class);
        tileEntities.add(TileSteamEngine.class);
        tileEntities.add(TileGeothermalPump.class);
        tileEntities.add(TileMB_Base.class);
        tileEntities.add(TileConveyorBelt.class);
        tileEntities.add(TileReactorVessel.class);
        tileEntities.add(TileThermopile.class);
        tileEntities.add(TileMB_Energy_Low.class);
        tileEntities.add(TileReactorControlRods.class);
        tileEntities.add(TileReactorWall.class);
        tileEntities.add(TileInserter.class);
        tileEntities.add(TileReactorActivator.class);
        tileEntities.add(TileGrinder.class);
        tileEntities.add(TileMiner.class);
        tileEntities.add(TileWindTurbineGap.class);
        tileEntities.add(TileTeslaCoil.class);
        tileEntities.add(TileAirlock.class);
        tileEntities.add(TileBiomassBurner.class);
        tileEntities.add(TileStirlingGenerator.class);
        tileEntities.add(TileInfiniteWater.class);
        tileEntities.add(TileRefinery.class);
        tileEntities.add(TileRefineryTank.class);
        tileEntities.add(TileCopperTank.class);
        tileEntities.add(TileHeater.class);
        tileEntities.add(TileSolarTowerCore.class);
        tileEntities.add(TilePolymerizer.class);
        tileEntities.add(TileReactorController.class);
        tileEntities.add(TileSteamTurbineControl.class);
        tileEntities.add(TileMB_Energy_Medium.class);
        tileEntities.add(TileCombustionEngine.class);
        tileEntities.add(TileHeatSink.class);
        tileEntities.add(TileBrickFurnace.class);
        tileEntities.add(TileCrafter.class);
        tileEntities.add(TileTextMonitor.class);
        tileEntities.add(TileComputer.class);
        tileEntities.add(TileMB_Heat.class);
        tileEntities.add(TileElectricPoleTier1.class);
        tileEntities.add(TileDroidRED.class);
        tileEntities.add(TileOilDistillery.class);
        tileEntities.add(TileGrindingMill.class);
        tileEntities.add(TileMB_Replaced.class);
        tileEntities.add(TileWoodenShaft.class);
        tileEntities.add(TileHandCrankGenerator.class);
        tileEntities.add(TileMB_Kinetic.class);
        tileEntities.add(TileInfiniteSteam.class);
        tileEntities.add(TileVoidInventory.class);
        tileEntities.add(TileElectricSwitch.class);
        tileEntities.add(TileDiode.class);
        tileEntities.add(TileResistance.class);
        
        if (ManagerIntegration.COFH_ENERGY) {
            tileEntities.add(TileRFAlternator.class);
            tileEntities.add(TileKineticGenerator.class);
        }
        if (ManagerIntegration.RAILCRAFT) {
            tileEntities.add(TileRCAlternator.class);
        }
        if (ManagerIntegration.IC2) {
            tileEntities.add(TileEUAlternator.class);
        }
        if (Magneticraft.DEBUG) {
        	tileEntities.add(TileElectricConnector.class);
        	tileEntities.add(TileElectricConnectorDown.class);
            tileEntities.add(TileSprinkler.class);
        }
        tileEntities.add(TileElectricPoleCableWire.class);
        tileEntities.add(TileElectricPoleCableWireDown.class);
        tileEntities.add(TileInfiniteEnergy.class);
        tileEntities.add(TileSifter.class);
        tileEntities.add(TileElectricPoleGap.class);
        tileEntities.add(TileMB_Controls.class);
        tileEntities.add(TilePressureTank.class);
        tileEntities.add(TileCrushingTable.class);
        tileEntities.add(TileShelvingUnit.class);
        tileEntities.add(TileShelfFiller.class);
        tileEntities.add(TilePumpjackEnergyLink.class);

        for (Class<? extends TileEntity> c : tileEntities) {
            GameRegistry.registerTileEntity(c, c.getName() + "_Mg");
        }
    }

    private static void addBlock(Block b, String name, boolean micro) {
        blocks.add(b);
        if (micro) {
            microblocks.add(b);
        }
        named.add(new NamedBlock(b, name));
    }

    private static void addBlock(Block b, String name) {
        addBlock(b, name, false);
    }

    private static void addAltItemBlock(Block b, Class<? extends ItemBlock> ic, String name) {
        named.add(new NamedBlock(b, name));
        GameRegistry.registerBlock(b, ic, b.getUnlocalizedName());
    }
}
