package com.cout970.magneticraft.api.access;

import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.api.util.ThermophileFuel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cout970
 */
public class MgRecipeRegister {

    //solids
    public static List<RecipeCrusher> crusher = new ArrayList<>();
    public static List<RecipeGrinder> grinder = new ArrayList<>();
    public static List<RecipeSifter> sifter = new ArrayList<>();
    public static List<RecipeCrushingTable> crushing_table = new ArrayList<>();
    public static List<ThermophileFuel> thermopileSources = new ArrayList<>();
    public static List<IThermophileDecay> thermopileDecays = new ArrayList<>();
    public static List<RecipeBiomassBurner> biomassBurner = new ArrayList<>();
    //fluids
    public static List<RecipeRefinery> refinery = new ArrayList<>();
    public static List<RecipeOilDistillery> oilDistillery = new ArrayList<>();
    public static List<RecipePolymerizer> polymerizer = new ArrayList<>();


    public static boolean registerCrusherRecipe(ItemStack in, ItemStack out0, ItemStack out1, float prob1, ItemStack out2, float prob2) {
        if (in == null || out0 == null) return false;
        RecipeCrusher a = new RecipeCrusher(in, out0, out1, prob1, out2, prob2);
        if (!crusher.contains(a)) {
            crusher.add(a);
            return true;
        }
        return false;
    }

    public static boolean registerGrinderRecipe(ItemStack in, ItemStack out0, ItemStack out1, float prob1, ItemStack out2, float prob2) {
        if (in == null || out0 == null) return false;
        RecipeGrinder a = new RecipeGrinder(in, out0, out1, prob1, out2, prob2);
        if (!grinder.contains(a)) {
            grinder.add(a);
            return true;
        }
        return false;
    }

    public static boolean registerRefineryRecipe(FluidStack in, FluidStack a, FluidStack b, FluidStack c) {
        if (in == null) return false;
        RecipeRefinery recipe = new RecipeRefinery(in, a, b, c);
        if (!refinery.contains(recipe)) {
            refinery.add(recipe);
            return true;
        }
        return false;
    }

    public static boolean registerOilDistilleryRecipe(FluidStack in, FluidStack out, double cost) {
        if (in == null || out == null) return false;
        RecipeOilDistillery recipe = new RecipeOilDistillery(in, out, cost);
        if (!oilDistillery.contains(recipe)) {
            oilDistillery.add(recipe);
            return true;
        }
        return false;
    }


    public static boolean addBiomassBurnerRecipe(ItemStack item, int burnTime, boolean ignoreNBT) {
        if (item == null && burnTime <= 0) return false;
        RecipeBiomassBurner r = new RecipeBiomassBurner(item, burnTime, !ignoreNBT);
        if (!biomassBurner.contains(r)) {
            biomassBurner.add(r);
            return true;
        }
        return false;
    }

    /**
     * @param b    block that interact
     * @param temp heat or cold value, negatives values are not allowed
     * @param heat true if is heat registration or false if is cold
     * @return true if the register work
     */
    public static boolean addThermopileSource(BlockInfo b, double temp, boolean heat) {
        if (b == null || temp == 0) return false;
        if (temp < 0) return false;
        ThermophileFuel f = new ThermophileFuel(b, temp, heat);
        if (!thermopileSources.contains(f)) {
            thermopileSources.add(f);
        }
        return true;
    }

    /**
     * @param t thermopile listener
     * @return false if the registry fails
     */
    public static boolean addThermopileDecay(IThermophileDecay t) {
        if (t == null || thermopileDecays.contains(t)) return false;
        thermopileDecays.add(t);
        return true;
    }

    public static boolean registerSifterRecipe(ItemStack in, ItemStack out, ItemStack extra, float prob) {
        if (in == null || out == null) return false;
        RecipeSifter a = new RecipeSifter(in, out, extra, prob);
        if (!sifter.contains(a)) {
            sifter.add(a);
            return true;
        }
        return false;
    }

    public static boolean registerPolymerizerRecipe(FluidStack fluid, ItemStack in, ItemStack out, double temperature) {
        if (in == null || out == null || fluid == null) return false;
        RecipePolymerizer a = new RecipePolymerizer(fluid, in, out, temperature);
        if (!polymerizer.contains(a)) {
            polymerizer.add(a);
            return true;
        }
        return false;
    }

    public static boolean registerHammerTableRecipe(ItemStack in, ItemStack out) {
        if (in == null || out == null) return false;
        RecipeCrushingTable a = new RecipeCrushingTable(in, out);
        if (!crushing_table.contains(a)) {
            crushing_table.add(a);
            return true;
        }
        return false;
    }
}
