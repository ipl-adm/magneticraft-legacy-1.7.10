package com.cout970.magneticraft;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import com.cout970.magneticraft.util.NamedItem;

public class LangHelper {

	public static List<String> unloc = new ArrayList<String>();
	public static List<String> name = new ArrayList<String>();
	
	public static void addName(Object obj,String name){
		if(obj == null)return;
		if(obj instanceof ItemStack){
			LangHelper.put(((ItemStack) obj).getUnlocalizedName(), name);
		}else if(obj instanceof Block){
			LangHelper.put(((Block) obj).getUnlocalizedName(), name);
		}else if(obj instanceof Item){
			LangHelper.put(((Item) obj).getUnlocalizedName(), name);
		}else if(obj instanceof Fluid){
			LangHelper.put(((Fluid) obj).getUnlocalizedName(), name);
		}else if(obj instanceof String){
			LangHelper.put((String) obj, name);
		}
	}
	
	public static void put(String a, String b){
		unloc.add(a);
		name.add(b);
	}

	public static void setupLangFile(){
		File f = new File("I:/Development/Minecraft Mod 1.7.10/Magneticraft/src/main/resources/assets/magneticraft/lang/en_US.lang");
		Writer w;
		try {
			w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
			for(String s : unloc){
				w.write(s+".name="+name.get(unloc.indexOf(s))+"\n");
			}
			w.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void registerNames(){
		
		for(NamedItem i : ManagerItems.named)
			addName(i.item, i.name);
		//creative tab
		addName("itemGroup","Magneticraft");
		
		//fluids
		addName(ManagerFluids.steam,"Steam");
		addName(ManagerFluids.oil,"Crude Oil");
		addName(ManagerFluids.heavyOil,"Heavy Oil");
		addName(ManagerFluids.lightOil,"Light Oil");
		addName(ManagerFluids.naturalGas,"Natural Gas");
		//fluidblocks
		addName("tile.mg_steam_block","Steam");
		addName("tile.mg_oil_block","Crude Oil");
		addName("tile.mg_heavy_oil_block","Heavy Oil");
		addName("tile.mg_light_oil_block","Light Oil");
		addName("tile.mg_natural_gas_block","Natural Gas");
		
		//blocks
		addName(ManagerBlocks.oreCopper, "Copper Ore");
		addName(ManagerBlocks.oreSalt, "Salt Ore");
		addName(ManagerBlocks.oreSulfur, "Sulfur Ore");
		addName(ManagerBlocks.oreThorium, "Thorium Ore");
		addName(ManagerBlocks.oreTungsten, "Tungsten Ore");
		addName(ManagerBlocks.oreUranium, "Uranium Ore");
		addName(ManagerBlocks.oilSource, "Oil Source");
		addName(ManagerBlocks.oilSourceDrained, "Oil Source Drained");
		addName(ManagerBlocks.solarpanel, "Solar Panel");
		addName(ManagerBlocks.furnace, "Electric Furnace");
		addName(ManagerBlocks.battery, "Battery");
		addName(ManagerBlocks.transformer_lm, "Transformer Low to Medium");
		addName(ManagerBlocks.transformer_mh, "Transformer Medium to High");
		addName(ManagerBlocks.firebox, "Firebox");
		addName(ManagerBlocks.boiler, "Boiler");
		addName(ManagerBlocks.heat_cable, "Heat Conductor");
		addName(ManagerBlocks.cooler, "Cooler");
		addName(ManagerBlocks.heat_resist, "Heat Resistance");
		addName(ManagerBlocks.fluidhopper, "Fluid Hopper");
		addName(ManagerBlocks.basic_gen, "Basic Generator");
		addName(ManagerBlocks.pumpJack, "Pumpjack");
		addName(ManagerBlocks.steamengine, "Steam Engine");
		addName(ManagerBlocks.crusher, "Crusher Control");
		addName(ManagerBlocks.chasis, "Multiblock Chasis");
		addName(ManagerBlocks.multi_io, "Multiblock IO");
		addName(ManagerBlocks.conveyor_l, "Conveyor Belt");
		addName(ManagerBlocks.mirror, "Mirror");
		addName(ManagerBlocks.breaker, "Block Breaker");
		addName(ManagerBlocks.kinetic, "Kinetic Generator");
		addName(ManagerBlocks.windturbine, "Wind Turbine");
		addName(ManagerBlocks.geothermal, "Geothermal Pump");
		addName(ManagerBlocks.permagnet, "Permanent Magnet");
		addName(ManagerBlocks.concreted_pipe, "Concrete");
		addName(ManagerBlocks.reactor_vessel, "Reactor Vessel");
		addName(ManagerBlocks.thermopile, "Thermopile");
		addName(ManagerBlocks.multi_energy_low, "Multiblock Energy IO Low Voltage");
		addName(ManagerBlocks.reactor_control_rods, "Reactor Control");
		addName(ManagerBlocks.reactor_wall, "Reactor Wall");
		addName(ManagerBlocks.reactor_activator, "Reactor Accelerator");
		addName(ManagerBlocks.inserter, "Inserter");
		addName(ManagerBlocks.housing, "Machine Housing");
		addName(ManagerBlocks.grinder, "Grinder Control");
		addName(ManagerBlocks.miner, "Miner");
		addName(ManagerBlocks.tesla_coil, "Tesla Coil");
		addName(ManagerBlocks.airlock, "Airlock");
		addName(ManagerBlocks.biomass_burner, "Biomass Burner");
		addName(ManagerBlocks.stirling, "Stirling Generator");
		addName(ManagerBlocks.infinite_water, "Infinite Water");
		addName(ManagerBlocks.refinery, "Refinery Control");
		addName(ManagerBlocks.refinery_gap, "Refinery Block");
		addName(ManagerBlocks.refinery_tank, "Refinery Tank");
		addName(ManagerBlocks.tank_mg, "Fluid Tank");
		addName(ManagerBlocks.heater, "Heater");
		addName(ManagerBlocks.air_bubble, "Air Bouble");
		addName(ManagerBlocks.solar_tower_core, "Solar Tower Core");
		addName(ManagerBlocks.polimerizer, "Polimerizer Control");
		addName(ManagerBlocks.reactor_controller, "Reactor Controller");
		addName(ManagerBlocks.multi_energy_medium, "Multiblock Energy IO Madium voltage");
		addName(ManagerBlocks.turbine, "Turbine Control");
		addName(ManagerBlocks.combustion_engine, "Combustion Engine");
		addName(ManagerBlocks.heat_sink, "Heat Sink");
		addName(ManagerBlocks.brickFurnace, "Brick Furnace");
		addName(ManagerBlocks.crafter, "Crafter");
		addName(ManagerBlocks.monitor, "Text Monitor");
		addName(ManagerBlocks.cpu, "CPU");
		addName(ManagerBlocks.multi_heat, "Multiblock Heat IO");
	}
}
