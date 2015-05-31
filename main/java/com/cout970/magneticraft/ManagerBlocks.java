package com.cout970.magneticraft;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.block.BlockAirBubble;
import com.cout970.magneticraft.block.BlockAirlock;
import com.cout970.magneticraft.block.BlockBasicGenerator;
import com.cout970.magneticraft.block.BlockBattery;
import com.cout970.magneticraft.block.BlockBiomassBurner;
import com.cout970.magneticraft.block.BlockBoiler;
import com.cout970.magneticraft.block.BlockBreaker;
import com.cout970.magneticraft.block.BlockBrickFurnace;
import com.cout970.magneticraft.block.BlockChasis;
import com.cout970.magneticraft.block.BlockCombustionEngine;
import com.cout970.magneticraft.block.BlockComputer;
import com.cout970.magneticraft.block.BlockConcretedPipe;
import com.cout970.magneticraft.block.BlockConveyorLow;
import com.cout970.magneticraft.block.BlockCooler;
import com.cout970.magneticraft.block.BlockCrafter;
import com.cout970.magneticraft.block.BlockCrusher;
import com.cout970.magneticraft.block.BlockOilDistillery;
import com.cout970.magneticraft.block.BlockDroidRED;
import com.cout970.magneticraft.block.BlockElectricFurnace;
import com.cout970.magneticraft.block.BlockElectricPoleTier1;
import com.cout970.magneticraft.block.BlockFireBox;
import com.cout970.magneticraft.block.BlockFluidHopper;
import com.cout970.magneticraft.block.BlockGeothermalPump;
import com.cout970.magneticraft.block.BlockGrinder;
import com.cout970.magneticraft.block.BlockHeatCable;
import com.cout970.magneticraft.block.BlockHeatResistence;
import com.cout970.magneticraft.block.BlockHeatSink;
import com.cout970.magneticraft.block.BlockHeater;
import com.cout970.magneticraft.block.BlockInfiniteWater;
import com.cout970.magneticraft.block.BlockInserter;
import com.cout970.magneticraft.block.BlockKineticGenerator;
import com.cout970.magneticraft.block.BlockMB_Energy_Low;
import com.cout970.magneticraft.block.BlockMB_Energy_Medium;
import com.cout970.magneticraft.block.BlockMB_Heat;
import com.cout970.magneticraft.block.BlockMB_Inv;
import com.cout970.magneticraft.block.BlockMachineHousing;
import com.cout970.magneticraft.block.BlockMgTank;
import com.cout970.magneticraft.block.BlockMiner;
import com.cout970.magneticraft.block.BlockMirror;
import com.cout970.magneticraft.block.BlockMonitor;
import com.cout970.magneticraft.block.BlockOilSource;
import com.cout970.magneticraft.block.BlockOilSourceDrained;
import com.cout970.magneticraft.block.BlockOre;
import com.cout970.magneticraft.block.BlockPermanentMagnet;
import com.cout970.magneticraft.block.BlockPolymerizer;
import com.cout970.magneticraft.block.BlockPumpJack;
import com.cout970.magneticraft.block.BlockReactorActivator;
import com.cout970.magneticraft.block.BlockReactorControlRods;
import com.cout970.magneticraft.block.BlockReactorController;
import com.cout970.magneticraft.block.BlockReactorVessel;
import com.cout970.magneticraft.block.BlockReactorWall;
import com.cout970.magneticraft.block.BlockRefinery;
import com.cout970.magneticraft.block.BlockRefineryGap;
import com.cout970.magneticraft.block.BlockRefineryTank;
import com.cout970.magneticraft.block.BlockSolarPanel;
import com.cout970.magneticraft.block.BlockSolarTowerCore;
import com.cout970.magneticraft.block.BlockSteamEngine;
import com.cout970.magneticraft.block.BlockStirlingGenerator;
import com.cout970.magneticraft.block.BlockTeslaCoil;
import com.cout970.magneticraft.block.BlockThermopile;
import com.cout970.magneticraft.block.BlockTransformerLow_Medium;
import com.cout970.magneticraft.block.BlockTransformerMedium_High;
import com.cout970.magneticraft.block.BlockTurbine;
import com.cout970.magneticraft.block.BlockWindTurbine;
import com.cout970.magneticraft.tileentity.TileAirlock;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.tileentity.TileBasicGenerator;
import com.cout970.magneticraft.tileentity.TileBattery;
import com.cout970.magneticraft.tileentity.TileBiomassBurner;
import com.cout970.magneticraft.tileentity.TileBoiler;
import com.cout970.magneticraft.tileentity.TileBreaker;
import com.cout970.magneticraft.tileentity.TileBrickFurnace;
import com.cout970.magneticraft.tileentity.TileCombustionEngine;
import com.cout970.magneticraft.tileentity.TileComputer;
import com.cout970.magneticraft.tileentity.TileConveyorBelt;
import com.cout970.magneticraft.tileentity.TileCooler;
import com.cout970.magneticraft.tileentity.TileCrafter;
import com.cout970.magneticraft.tileentity.TileCrusher;
import com.cout970.magneticraft.tileentity.TileDroidRED;
import com.cout970.magneticraft.tileentity.TileElectricFurnace;
import com.cout970.magneticraft.tileentity.TileElectricPoleTier1;
import com.cout970.magneticraft.tileentity.TileFireBox;
import com.cout970.magneticraft.tileentity.TileFluidHopper;
import com.cout970.magneticraft.tileentity.TileGeothermalPump;
import com.cout970.magneticraft.tileentity.TileGrinder;
import com.cout970.magneticraft.tileentity.TileHeatCable;
import com.cout970.magneticraft.tileentity.TileHeatResistance;
import com.cout970.magneticraft.tileentity.TileHeatSink;
import com.cout970.magneticraft.tileentity.TileHeater;
import com.cout970.magneticraft.tileentity.TileInfiniteWater;
import com.cout970.magneticraft.tileentity.TileInserter;
import com.cout970.magneticraft.tileentity.TileKineticGenerator;
import com.cout970.magneticraft.tileentity.TileMB_Base;
import com.cout970.magneticraft.tileentity.TileMB_Energy_Low;
import com.cout970.magneticraft.tileentity.TileMB_Energy_Medium;
import com.cout970.magneticraft.tileentity.TileMB_Heat;
import com.cout970.magneticraft.tileentity.TileMB_Inv;
import com.cout970.magneticraft.tileentity.TileMgTank;
import com.cout970.magneticraft.tileentity.TileMiner;
import com.cout970.magneticraft.tileentity.TileMirror;
import com.cout970.magneticraft.tileentity.TileOilDistillery;
import com.cout970.magneticraft.tileentity.TilePermanentMagnet;
import com.cout970.magneticraft.tileentity.TilePolymerizer;
import com.cout970.magneticraft.tileentity.TilePumpJack;
import com.cout970.magneticraft.tileentity.TileReactorActivator;
import com.cout970.magneticraft.tileentity.TileReactorControlRods;
import com.cout970.magneticraft.tileentity.TileReactorController;
import com.cout970.magneticraft.tileentity.TileReactorVessel;
import com.cout970.magneticraft.tileentity.TileReactorWall;
import com.cout970.magneticraft.tileentity.TileRefinery;
import com.cout970.magneticraft.tileentity.TileRefineryTank;
import com.cout970.magneticraft.tileentity.TileSolarPanel;
import com.cout970.magneticraft.tileentity.TileSolarTowerCore;
import com.cout970.magneticraft.tileentity.TileSteamEngine;
import com.cout970.magneticraft.tileentity.TileStirlingGenerator;
import com.cout970.magneticraft.tileentity.TileTeslaCoil;
import com.cout970.magneticraft.tileentity.TileTextMonitor;
import com.cout970.magneticraft.tileentity.TileThermopile;
import com.cout970.magneticraft.tileentity.TileTransformerLow_Medium;
import com.cout970.magneticraft.tileentity.TileTransformerMedium_High;
import com.cout970.magneticraft.tileentity.TileTurbineControl;
import com.cout970.magneticraft.tileentity.TileWindTurbine;
import com.cout970.magneticraft.tileentity.TileWindTurbineGap;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

import cpw.mods.fml.common.registry.GameRegistry;

public class ManagerBlocks {

	public static List<Class> tileEntities = new LinkedList<Class>();
	public static List<Block> blocks = new LinkedList<Block>();
	public static Map<String, Block> ores = new HashMap<String, Block>();

	public static Block oreCopper;
	public static Block oreTungsten;
	public static Block oreUranium;
	public static Block oreSulfur;
	public static Block oreThorium;
	public static Block oreSalt;
	public static Block oilSource;
	public static Block oilSourceDrained;
	public static Block solarpanel;
	public static Block furnace;
	public static Block battery;
	public static Block transformer_lm;
	public static Block transformer_mh;
	public static Block firebox;
	public static Block boiler;
	public static Block heat_cable;
	public static Block cooler;
	public static Block heat_resist;
	public static Block fluidhopper;
	public static Block basic_gen;
	public static Block pumpJack;
	public static Block steamengine;
	public static Block crusher;
	public static Block chasis;
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
	public static Block tank_mg;
	public static Block heater;
	public static Block air_bubble;
	public static Block solar_tower_core;
	public static Block polimerizer;
	public static Block reactor_controller;
	public static Block multi_energy_medium;
	public static Block turbine;
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
	
	
	public static void initBlocks(){
		//ores & resources
		oilSource = new BlockOilSource();
		oreCopper = new BlockOre("copper_ore");
		oreTungsten = new BlockOre("tungsten_ore");
		oreUranium = new BlockOre("uranium_ore");
		oreSulfur = new BlockOre("sulfur_ore");
		oreThorium = new BlockOre("thorium_ore");
		oreSalt = new BlockOre("salt_ore");
		oilSourceDrained = new BlockOilSourceDrained();
		//machines
		solarpanel = new BlockSolarPanel();
		furnace = new BlockElectricFurnace();
		battery = new BlockBattery();
		transformer_lm = new BlockTransformerLow_Medium();
		transformer_mh = new BlockTransformerMedium_High();
		kinetic = new BlockKineticGenerator();
		basic_gen = new BlockBasicGenerator();
		heat_cable = new BlockHeatCable();
		firebox = new BlockFireBox();
		boiler = new BlockBoiler();
		conveyor_l = new BlockConveyorLow();
		pumpJack = new BlockPumpJack();
		cooler = new BlockCooler();
		crusher = new BlockCrusher();
		chasis = new BlockChasis();
		multi_io = new BlockMB_Inv();
		mirror = new BlockMirror();
		windturbine = new BlockWindTurbine();
		permagnet = new BlockPermanentMagnet();
		fluidhopper = new BlockFluidHopper();
		heat_resist = new BlockHeatResistence();
		breaker = new BlockBreaker();
		steamengine = new BlockSteamEngine();
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
		tank_mg = new BlockMgTank();
		heater = new BlockHeater();
		air_bubble = new BlockAirBubble();
		solar_tower_core = new BlockSolarTowerCore();
		polimerizer = new BlockPolymerizer();
		reactor_controller = new BlockReactorController();
		multi_energy_medium = new BlockMB_Energy_Medium();
		turbine = new BlockTurbine();
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
	}
	
	public static void registerBlocks(){
		//ores 
		ores.put("Copper",oreCopper);
		ores.put("Tungsten",oreTungsten);
		ores.put("Sulfur",oreSulfur);
		ores.put("Uranium",oreUranium);
		ores.put("Thorium",oreThorium);
		ores.put("Salt",oreSalt);
		
		blocks.add(oilSource);
		blocks.add(oilSourceDrained);
		blocks.add(oreCopper);
		blocks.add(oreTungsten);
		blocks.add(oreSulfur);
		blocks.add(oreUranium);
		blocks.add(oreThorium);
		blocks.add(oreSalt);
		blocks.add(battery);
		blocks.add(solarpanel);
		blocks.add(furnace);
		blocks.add(transformer_lm);
		blocks.add(transformer_mh);
		blocks.add(kinetic);
		blocks.add(basic_gen);
		blocks.add(heat_cable);
		blocks.add(firebox);
		blocks.add(boiler);
		blocks.add(cooler);
		blocks.add(housing);
		blocks.add(crusher);
		blocks.add(chasis);
		blocks.add(multi_io);
		blocks.add(conveyor_l);
		blocks.add(pumpJack);
		blocks.add(mirror);
		blocks.add(windturbine);
		blocks.add(permagnet);
		blocks.add(fluidhopper);
		blocks.add(heat_resist);
		blocks.add(breaker);
		blocks.add(steamengine);
		blocks.add(geothermal);
		blocks.add(concreted_pipe);
		blocks.add(reactor_vessel);
		blocks.add(thermopile);
		blocks.add(multi_energy_low);
		blocks.add(reactor_control_rods);
		blocks.add(reactor_wall);
		blocks.add(inserter);
		blocks.add(reactor_activator);
		blocks.add(grinder);
		blocks.add(miner);
		blocks.add(tesla_coil);
		blocks.add(airlock);
		blocks.add(biomass_burner);
		blocks.add(stirling);
		blocks.add(infinite_water);
		blocks.add(refinery);
		blocks.add(refinery_gap);
		blocks.add(refinery_tank);
		blocks.add(tank_mg);
		blocks.add(heater);
		blocks.add(air_bubble);
		blocks.add(solar_tower_core);
		blocks.add(polimerizer);
		blocks.add(reactor_controller);
		blocks.add(multi_energy_medium);
		blocks.add(turbine);
		blocks.add(combustion_engine);
		blocks.add(heat_sink);
		blocks.add(brickFurnace);
		blocks.add(crafter);
		blocks.add(monitor);
		blocks.add(cpu);
		blocks.add(multi_heat);
		blocks.add(pole_tier1);
		blocks.add(droid_red);
		blocks.add(oil_distillery);
		
		for(Block b : blocks)
			GameRegistry.registerBlock(b, b.getUnlocalizedName());
	}
	
	public static void registerTileEntities(){
		
		tileEntities.add(TileBase.class);
		tileEntities.add(TileConductorLow.class);
		tileEntities.add(TileSolarPanel.class);
		tileEntities.add(TileElectricFurnace.class);
		tileEntities.add(TileBattery.class);
		tileEntities.add(TileTransformerLow_Medium.class);
		tileEntities.add(TileTransformerMedium_High.class);
		tileEntities.add(TileKineticGenerator.class);
		tileEntities.add(TileBasicGenerator.class);
		tileEntities.add(TileHeatConductor.class);
		tileEntities.add(TileFireBox.class);
		tileEntities.add(TileHeatCable.class);
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
		tileEntities.add(TileMgTank.class);
		tileEntities.add(TileHeater.class);
		tileEntities.add(TileSolarTowerCore.class);
		tileEntities.add(TilePolymerizer.class);
		tileEntities.add(TileReactorController.class);
		tileEntities.add(TileTurbineControl.class);
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
		
		for(Class c : tileEntities){
			GameRegistry.registerTileEntity(c, c.getName()+"_Mg");
		}
	}
	
	public static ItemStack getOre(String g,int amount){
		if(ores.containsKey(g))return new ItemStack(ores.get(g),amount);
		ItemStack a = ManagerOreDict.getOre("ore"+g);
		if(a == null)return null;
		a = a.copy();
		a.stackSize = amount;
		return a;
	}
}
