package com.cout970.magneticraft;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import com.cout970.magneticraft.block.fluids.BlockFluidHeavyOil;
import com.cout970.magneticraft.block.fluids.BlockFluidLightOil;
import com.cout970.magneticraft.block.fluids.BlockFluidNaturalGas;
import com.cout970.magneticraft.block.fluids.BlockFluidOil;
import com.cout970.magneticraft.block.fluids.BlockFluidSteam;

import cpw.mods.fml.common.registry.GameRegistry;

public class ManagerFluids {
	
	public static Fluid steam;
	public static Fluid oil;
	public static Fluid heavyOil;
	public static Fluid lightOil;
	public static Fluid naturalGas;
	
	public static Block steamBlock;
	public static Block oilBlock;
	public static Block lightOilBlock;
	public static Block heavyOilBlock;
	public static Block naturalGasBlock;
	
	//names
	public static final String STEAM_NAME = "steam";
	public static final String OIL_NAME = "oil";
	public static final String HEAVY_OIL = "heavyoil";
	public static final String LIGHT_OIL = "lightoil";
	public static final String NATURAL_GAS = "naturalgas";

	public static void initFluids(){

		steam = new Fluid(STEAM_NAME).setDensity(-5000).setViscosity(1000).setTemperature(373).setGaseous(true);
		FluidRegistry.registerFluid(steam);
		steam = FluidRegistry.getFluid(STEAM_NAME);

		oil = new Fluid(OIL_NAME).setDensity(800).setViscosity(1500);
		FluidRegistry.registerFluid(oil);
		oil = FluidRegistry.getFluid(OIL_NAME);

		heavyOil = new Fluid(HEAVY_OIL).setDensity(800).setViscosity(1500);
		FluidRegistry.registerFluid(heavyOil);
		heavyOil = FluidRegistry.getFluid(HEAVY_OIL);
		
		lightOil = new Fluid(LIGHT_OIL).setDensity(500).setViscosity(1200);
		FluidRegistry.registerFluid(lightOil);
		lightOil = FluidRegistry.getFluid(LIGHT_OIL);
		
		naturalGas = new Fluid(NATURAL_GAS).setDensity(-1000).setViscosity(1000).setGaseous(true);
		FluidRegistry.registerFluid(naturalGas);
		naturalGas = FluidRegistry.getFluid(NATURAL_GAS);
	}

	public static void registerFluidsBlocks(){
		//steam
		if(FluidRegistry.getFluid(STEAM_NAME).getBlock() == null){
			steamBlock = new BlockFluidSteam(steam, Material.water);
			GameRegistry.registerBlock(steamBlock, "block"+STEAM_NAME);
			FluidRegistry.getFluid(STEAM_NAME).setBlock(steamBlock);
		}
		steamBlock = FluidRegistry.getFluid(STEAM_NAME).getBlock();

		//oil
		if(FluidRegistry.getFluid(OIL_NAME).getBlock() == null){
			oilBlock = new BlockFluidOil(oil,Material.water);
			GameRegistry.registerBlock(oilBlock, "block"+OIL_NAME);
			FluidRegistry.getFluid(OIL_NAME).setBlock(oilBlock);
		}
		oilBlock = FluidRegistry.getFluid(OIL_NAME).getBlock();
		
		//light oil
		if(FluidRegistry.getFluid(LIGHT_OIL).getBlock() == null){
			lightOilBlock = new BlockFluidLightOil(lightOil,Material.water);
			GameRegistry.registerBlock(lightOilBlock, "block"+LIGHT_OIL);
			FluidRegistry.getFluid(LIGHT_OIL).setBlock(lightOilBlock);
		}
		lightOilBlock = FluidRegistry.getFluid(LIGHT_OIL).getBlock();
		
		//heavy oil
		if(FluidRegistry.getFluid(HEAVY_OIL).getBlock() == null){
			heavyOilBlock = new BlockFluidHeavyOil(heavyOil,Material.water);
			GameRegistry.registerBlock(heavyOilBlock, "block"+HEAVY_OIL);
			FluidRegistry.getFluid(HEAVY_OIL).setBlock(heavyOilBlock);
		}
		heavyOilBlock = FluidRegistry.getFluid(HEAVY_OIL).getBlock();
		
		//natural gas
		if(FluidRegistry.getFluid(NATURAL_GAS).getBlock() == null){
			naturalGasBlock = new BlockFluidNaturalGas(naturalGas,Material.water);
			GameRegistry.registerBlock(naturalGasBlock, "block"+NATURAL_GAS);
			FluidRegistry.getFluid(NATURAL_GAS).setBlock(naturalGasBlock);
		}
		naturalGasBlock = FluidRegistry.getFluid(NATURAL_GAS).getBlock();
	}
}
