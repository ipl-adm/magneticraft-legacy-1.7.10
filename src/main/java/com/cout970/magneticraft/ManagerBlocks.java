package com.cout970.magneticraft;

import java.util.LinkedList;
import java.util.List;

import com.cout970.magneticraft.block.*;
import com.cout970.magneticraft.block.compat.*;
import com.cout970.magneticraft.block.computer.*;
import com.cout970.magneticraft.block.energy.*;
import com.cout970.magneticraft.block.heat.*;
import com.cout970.magneticraft.block.multiblock.*;
import com.cout970.magneticraft.block.multiblock.controllers.*;
import com.cout970.magneticraft.block.slabs.BlockClayTileSlab;
import com.cout970.magneticraft.block.stairs.BlockClayTileStairs;
import com.cout970.magneticraft.items.block.ItemBlockMg;
import com.cout970.magneticraft.items.block.ItemBlockMgSlab;
import com.cout970.magneticraft.tileentity.*;
import com.cout970.magneticraft.util.NamedBlock;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;

public class ManagerBlocks {

    public static List<Class<? extends TileEntity>> tileEntities = new LinkedList<Class<? extends TileEntity>>();
    public static List<Block> blocks = new LinkedList<Block>();
    public static List<NamedBlock> named = new LinkedList<NamedBlock>();

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
    public static Block hammer_table;
    public static Block burntLime;
    public static Block brickLime;
    public static Block roofTile;
    public static BlockSlab slabRoofTileSingle;
    public static BlockSlab slabRoofTileDouble;
    public static Block stairsRoofTile;

    public static void initBlocks() {
        //ores & resources
        oilSource = new BlockOilSource();
        oreCopper = new BlockOre("copper_ore");
        oreTungsten = new BlockOre("tungsten_ore");
        oreUranium = new BlockOre("uranium_ore");
        oreSulfur = new BlockOre("sulfur_ore");
        oreThorium = new BlockOre("thorium_ore");
        oreSalt = new BlockOre("salt_ore");
        oreZinc = new BlockOre("zinc_ore");
        oreLime = new BlockOre("limestone");
        burntLime = new BlockSimple("burnt_limestone");
        brickLime = new BlockSimple("brick_limestone");
        roofTile = new BlockSimple("roofTile");
        slabRoofTileSingle = new BlockClayTileSlab(false);
        slabRoofTileDouble = new BlockClayTileSlab(true);
        stairsRoofTile = new BlockClayTileStairs();
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
        if (Magneticraft.COFH) {
            rf_alternator = new BlockRFAlternator();
            kinetic = new BlockKineticGenerator();
        }
        if (Magneticraft.RAILCRAFT) {
            rc_alternator = new BlockRCAlternator();
        }
        if (Magneticraft.IC2) {
            eu_alternator = new BlockEUAlternator();
        }
        pole_cable_wire = new BlockElectricPoleCableWire();
        infinite_energy = new BlockInfiniteEnergy();
        sifter = new BlockSifter();
        ingot_block_copper = new BlockOfIngots("block_copper");
        ingot_block_tungsten = new BlockOfIngots("block_tungsten");
        ingot_block_carbide = new BlockOfIngots("block_carbide");
        ingot_block_brass = new BlockOfIngots("block_brass");
        ingot_block_zinc = new BlockOfIngots("block_zinc");
        dust_block_salt = new BlockOfIngots("block_salt");
        dust_block_sulfur = new BlockOfIngots("block_sulfur");
        mb_controls = new BlockMB_Controls();
        pressure_tank = new BlockPressureTank();
        hammer_table = new BlockHammerTable();
    }

    public static void registerBlocks() {

        addBlock(oilSource, "Oil Source");
        addBlock(oilSourceDrained, "Drained Oil Source");
        addBlock(oreCopper, "Copper Ore");
        addBlock(oreSalt, "Salt Ore");
        addBlock(oreSulfur, "Sulfur Ore");
        addBlock(oreThorium, "Thorium Ore");
        addBlock(oreTungsten, "Tungsten Ore");
        addBlock(oreUranium, "Uranium Ore");
        addBlock(oreLime, "Limestone");
        addBlock(burntLime, "Burnt Limestone");
        addBlock(brickLime, "Limestone Brick");
        addBlock(roofTile, "Clay Roof Tiles");
        addAltItemBlock(slabRoofTileSingle, ItemBlockMgSlab.class, "Clay Roof Tile Slab");
        addAltItemBlock(slabRoofTileDouble, ItemBlockMgSlab.class, "Clay Roof Tile Slab");
        addBlock(stairsRoofTile, "Clay Roof Tile Stairs");
        addBlock(ingot_block_copper, "Copper Block");
        addBlock(ingot_block_tungsten, "Tungsten Block");
        addBlock(ingot_block_carbide, "Carbide Block");
        addBlock(ingot_block_brass, "Brass Block");
        addBlock(ingot_block_zinc, "Zinc Block");
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
        addBlock(pumpJack, "Pumpjack");
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
        addBlock(reactor_vessel, "Reactor Vessel");
        addBlock(thermopile, "Thermopile");
        addBlock(multi_energy_low, "Multiblock Energy I/O (Low Voltage)");
        addBlock(reactor_control_rods, "Reactor Control");
        addBlock(reactor_wall, "Reactor Wall");
        addBlock(reactor_activator, "Reactor Accelerator");
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
        addBlock(reactor_controller, "Reactor Controller");
        addBlock(multi_energy_medium, "Multiblock Energy I/O (Medium Voltage)");
        addBlock(steam_turbine, "Turbine Control");
        addBlock(combustion_engine, "Combustion Engine");
        addBlock(heat_sink, "Heat Sink");
        addBlock(brickFurnace, "Brick Furnace");
        addBlock(crafter, "Crafter");
        addBlock(monitor, "Text Monitor");
        addBlock(cpu, "CPU");
        addBlock(multi_heat, "Multiblock Heat I/O");
        addBlock(droid_red, "R.E.D.");
        addBlock(oil_distillery, "Oil Distillery Control");
        addBlock(pole_tier1, "Electrical Wooden Pole");
        addBlock(grinding_mill, "Grinding Mill");
        addBlock(grinding_mill_gap, "Grinding Mill");
        addBlock(wooden_shaft, "Wooden Shaft");
        addBlock(hand_crank_gen, "Hand Crank");
        addBlock(multi_kinetic, "Multiblock Kinetic I/O");
        addBlock(infinite_steam, "Creative Infinite Steam");
        addBlock(void_inv, "Void Inventory");
        addBlock(electric_switch, "Electric Switch");
        addBlock(diode, "Diode");
        addBlock(resistance, "Resistance");
        if (Magneticraft.COFH) {
            addBlock(rf_alternator, "RF Alternator");
            addBlock(kinetic, "Kinetic Generator");
        }
        if (Magneticraft.RAILCRAFT) {
            addBlock(rc_alternator, "RailCraft Charge Alternator");
        }
        if (Magneticraft.IC2) {
            addBlock(eu_alternator, "EU Alternator");
        }
        addBlock(pole_cable_wire, "Electrical Pole With Transformer");
        addBlock(infinite_energy, "Creative Infinite Energy");
        addBlock(sifter, "Sifter Control");
        addBlock(mb_controls, "Multiblock Controls");
        addBlock(dust_block_salt, "Salt Block");
        addBlock(dust_block_sulfur, "Sulfur Block");
        addBlock(oreZinc, "Zinc Ore");
        addBlock(pressure_tank, "Pressure Tank");
        addBlock(hammer_table, "Crushing Table");

        for (Block b : blocks)
            GameRegistry.registerBlock(b, ItemBlockMg.class, b.getUnlocalizedName());
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
        if (Magneticraft.COFH) {
            tileEntities.add(TileRFAlternator.class);
            tileEntities.add(TileKineticGenerator.class);
        }
        if (Magneticraft.RAILCRAFT) {
            tileEntities.add(TileRCAlternator.class);
        }
        if (Magneticraft.IC2) {
            tileEntities.add(TileEUAlternator.class);
        }
        tileEntities.add(TileElectricPoleCableWire.class);
        tileEntities.add(TileElectricPoleCableWireDown.class);
        tileEntities.add(TileInfiniteEnergy.class);
        tileEntities.add(TileSifter.class);
        tileEntities.add(TileElectricPoleGap.class);
        tileEntities.add(TileMB_Controls.class);
        tileEntities.add(TilePressureTank.class);
        tileEntities.add(TileHammerTable.class);

        for (Class<? extends TileEntity> c : tileEntities) {
            GameRegistry.registerTileEntity(c, c.getName() + "_Mg");
        }
    }

    private static void addBlock(Block b, String name) {
        blocks.add(b);
        named.add(new NamedBlock(b, name));
    }


    private static void addAltItemBlock(Block b, Class<? extends ItemBlock> ic, String name) {
        named.add(new NamedBlock(b, name));
        GameRegistry.registerBlock(b, ic, b.getUnlocalizedName());
    }
}
