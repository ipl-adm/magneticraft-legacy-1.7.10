package com.cout970.magneticraft;

import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import buildcraft.api.fuels.BuildcraftFuelRegistry;

import com.cout970.magneticraft.block.fluids.BlockFluidClasicMg;
import com.cout970.magneticraft.block.fluids.BlockFluidHeavyOil;
import com.cout970.magneticraft.block.fluids.BlockFluidHotCrude;
import com.cout970.magneticraft.block.fluids.BlockFluidLightOil;
import com.cout970.magneticraft.block.fluids.BlockFluidNaturalGas;
import com.cout970.magneticraft.block.fluids.BlockFluidOil;
import com.cout970.magneticraft.block.fluids.BlockFluidSteam;
import com.cout970.magneticraft.handlers.FluidFuelHandler;
import com.cout970.magneticraft.util.Log;

import cpw.mods.fml.common.registry.GameRegistry;

public class ManagerFluids {
	
	public static Fluid steam;
	public static Fluid oil;
	public static Fluid heavyOil;
	public static Fluid lightOil;
	public static Fluid naturalGas;
	public static Fluid hotCrude;
	
	public static Block steamBlock;
	public static Block oilBlock;
	public static Block lightOilBlock;
	public static Block heavyOilBlock;
	public static Block naturalGasBlock;
	public static Block hotCrudeBlock;
	
	//names
	public static final String STEAM_NAME = "steam";
	public static final String OIL_NAME = "oil";
	public static final String HEAVY_OIL = "heavyoil";
	public static final String LIGHT_OIL = "lightoil";
	public static final String NATURAL_GAS = "naturalgas";
	public static final String HOT_CRUDE = "hotcrude";

	public static void initFluids(){
		//steam
		steam = new Fluid(STEAM_NAME).setDensity(-5000).setViscosity(1000).setTemperature(373).setGaseous(true);
		FluidRegistry.registerFluid(steam);
		steamBlock = new BlockFluidSteam(steam, BlockFluidClasicMg.fluidMaterial);
		GameRegistry.registerBlock(steamBlock, "block"+STEAM_NAME);
		steam.setBlock(steamBlock);
		

		//oil
		oil = new Fluid(OIL_NAME).setDensity(800).setViscosity(1500);
		FluidRegistry.registerFluid(oil);
		oilBlock = new BlockFluidOil(oil,BlockFluidClasicMg.fluidMaterial);
		GameRegistry.registerBlock(oilBlock, "block"+OIL_NAME);
		oil.setBlock(oilBlock);
		

		//heavy oil
		heavyOil = new Fluid(HEAVY_OIL).setDensity(600).setViscosity(2000);
		FluidRegistry.registerFluid(heavyOil);
		heavyOilBlock = new BlockFluidHeavyOil(heavyOil,BlockFluidClasicMg.fluidMaterial);
		GameRegistry.registerBlock(heavyOilBlock, "block"+HEAVY_OIL);
		heavyOil.setBlock(heavyOilBlock);
		

		//light oil
		lightOil = new Fluid(LIGHT_OIL).setDensity(300).setViscosity(1200);
		FluidRegistry.registerFluid(lightOil);
		lightOilBlock = new BlockFluidLightOil(lightOil,BlockFluidClasicMg.fluidMaterial);
		GameRegistry.registerBlock(lightOilBlock, "block"+LIGHT_OIL);
		lightOil.setBlock(lightOilBlock);
		

		//natural gas
		naturalGas = new Fluid(NATURAL_GAS).setDensity(-1000).setViscosity(1000).setGaseous(true);
		FluidRegistry.registerFluid(naturalGas);
		naturalGasBlock = new BlockFluidNaturalGas(naturalGas,BlockFluidClasicMg.fluidMaterial);
		GameRegistry.registerBlock(naturalGasBlock, "block"+NATURAL_GAS);
		naturalGas.setBlock(naturalGasBlock);
		
		//hot crude
		hotCrude = new Fluid(HOT_CRUDE).setDensity(800).setViscosity(1000).setTemperature(450);
		FluidRegistry.registerFluid(hotCrude);
		hotCrudeBlock = new BlockFluidHotCrude(hotCrude,BlockFluidClasicMg.fluidMaterial);
		GameRegistry.registerBlock(hotCrudeBlock, "block"+HOT_CRUDE);
		hotCrude.setBlock(hotCrudeBlock);
	}

	public static void registerFuels() {
		
		if(BuildcraftFuelRegistry.fuel == null){
			BuildcraftFuelRegistry.fuel = new FluidFuelHandler();
			Log.info("Creating a IFuelManager");
		}
		BuildcraftFuelRegistry.fuel.addFuel(lightOil, 80, 25000);
		BuildcraftFuelRegistry.fuel.addFuel(heavyOil, 60, 25000);
		BuildcraftFuelRegistry.fuel.addFuel(naturalGas, 40, 75000);
		BuildcraftFuelRegistry.fuel.addFuel(oil, 30, 5000);
	}
}
