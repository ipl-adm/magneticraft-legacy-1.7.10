package com.cout970.magneticraft;

import buildcraft.api.fuels.BuildcraftFuelRegistry;
import com.cout970.magneticraft.block.fluids.*;
import com.cout970.magneticraft.handlers.FluidFuelHandler;
import com.cout970.magneticraft.util.Log;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import mods.railcraft.api.fuel.FuelManager;
import net.minecraft.block.Block;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import blusunrize.immersiveengineering.api.energy.DieselHandler;

import java.util.HashMap;

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

    public static HashMap<Fluid, BlockFuel> fuels;

    public static void initFluids() {
        //steam
        steam = new Fluid(STEAM_NAME).setDensity(-5000).setViscosity(1000).setTemperature(373).setGaseous(true);
        FluidRegistry.registerFluid(steam);
        steamBlock = new BlockFluidSteam(steam, BlockFluidClassicMg.fluidMaterial);
        GameRegistry.registerBlock(steamBlock, "block" + STEAM_NAME);
        steam.setBlock(steamBlock);


        //oil
        oil = new Fluid(OIL_NAME).setDensity(800).setViscosity(1500);
        FluidRegistry.registerFluid(oil);
        oilBlock = new BlockFluidOil(oil, BlockFluidClassicMg.fluidMaterial);
        GameRegistry.registerBlock(oilBlock, "block" + OIL_NAME);
        oil.setBlock(oilBlock);


        //heavy oil
        heavyOil = new Fluid(HEAVY_OIL).setDensity(600).setViscosity(2000);
        FluidRegistry.registerFluid(heavyOil);
        heavyOilBlock = new BlockFluidHeavyOil(heavyOil, BlockFluidClassicMg.fluidMaterial);
        GameRegistry.registerBlock(heavyOilBlock, "block" + HEAVY_OIL);
        heavyOil.setBlock(heavyOilBlock);


        //light oil
        lightOil = new Fluid(LIGHT_OIL).setDensity(300).setViscosity(1200);
        FluidRegistry.registerFluid(lightOil);
        lightOilBlock = new BlockFluidLightOil(lightOil, BlockFluidClassicMg.fluidMaterial);
        GameRegistry.registerBlock(lightOilBlock, "block" + LIGHT_OIL);
        lightOil.setBlock(lightOilBlock);


        //natural gas
        naturalGas = new Fluid(NATURAL_GAS).setDensity(-1000).setViscosity(1000).setGaseous(true);
        FluidRegistry.registerFluid(naturalGas);
        naturalGasBlock = new BlockFluidNaturalGas(naturalGas, BlockFluidClassicMg.fluidMaterial);
        GameRegistry.registerBlock(naturalGasBlock, "block" + NATURAL_GAS);
        naturalGas.setBlock(naturalGasBlock);

        //hot crude
        hotCrude = new Fluid(HOT_CRUDE).setDensity(800).setViscosity(1000).setTemperature(450);
        FluidRegistry.registerFluid(hotCrude);
        hotCrudeBlock = new BlockFluidHotCrude(hotCrude, BlockFluidClassicMg.fluidMaterial);
        GameRegistry.registerBlock(hotCrudeBlock, "block" + HOT_CRUDE);
        hotCrude.setBlock(hotCrudeBlock);
    }

    public static void registerFuels() {
        BlockFuel lightOilFuel = new BlockFuel(lightOil, 80, 25000);
        BlockFuel heavyOilFuel = new BlockFuel(heavyOil, 60, 25000);
        BlockFuel naturalGasFuel = new BlockFuel(naturalGas, 40, 75000);
        BlockFuel oilFuel = new BlockFuel(oil, 30, 5000);

        fuels = new HashMap<>();
        fuels.put(lightOil, lightOilFuel);
        fuels.put(heavyOil, heavyOilFuel);
        fuels.put(naturalGas, naturalGasFuel);
        fuels.put(oil, oilFuel);
    }

    @Optional.Method(modid = "BuildCraft|Core")
    public static void registerBCFuels() {
        if (BuildcraftFuelRegistry.fuel == null) {
            BuildcraftFuelRegistry.fuel = new FluidFuelHandler();
            Log.info("Creating a IFuelManager");
        }
        Log.info("Registering Fuels into buildcraft fuel registry");
        BuildcraftFuelRegistry.fuel.addFuel(lightOil, 80, 25000);
        BuildcraftFuelRegistry.fuel.addFuel(heavyOil, 60, 25000);
        BuildcraftFuelRegistry.fuel.addFuel(naturalGas, 40, 75000);
        BuildcraftFuelRegistry.fuel.addFuel(oil, 30, 5000);
    }

    @Optional.Method(modid = "ImmersiveEngineering")
    public static void registerIEFuels() {
        Log.info("Registering fuels for IE Diesel Generator");
        DieselHandler.registerFuel(lightOil, 500);
        DieselHandler.registerFuel(heavyOil, 375);
        DieselHandler.registerFuel(naturalGas,750);
        //Diesel doesn't accept BC oil, not adding magneticraft either.
    }

    @Optional.Method(modid = "Railcraft")
    public static void registerRCFuels() {
        Log.info("Registering Fuels into railcraft fuel manager");
        FuelManager.addBoilerFuel(lightOil, 96000);
        FuelManager.addBoilerFuel(heavyOil, 75000);
        FuelManager.addBoilerFuel(naturalGas, 150000);
        FuelManager.addBoilerFuel(oil, 7500);
    }
}
