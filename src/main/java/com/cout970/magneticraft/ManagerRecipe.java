package com.cout970.magneticraft;

import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.util.ThermophileDecay;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import static com.cout970.magneticraft.ManagerBlocks.*;
import static com.cout970.magneticraft.ManagerItems.*;

public class ManagerRecipe {

    public static void registerFurnaceRecipes() {

        for (int i = 0; i < oreNames.length; i++) {
            int i1 = altNames[i] == -1 ? i : altNames[i];
            ItemStack extra_1 = extraNames[i][0] == null ? null : ManagerOreDict.getOreWithPreference("dust" + extraNames[i][0]);
            //Crusher Recipes
            MgRecipeRegister.registerCrusherRecipe(ManagerOreDict.getOre("ore" + oreNames[i]), new ItemStack(chunks, 1, i), new ItemStack(dust, 1, i1), 0.05F, extra_1, 0.05F);

            //Crushing Table Recipes
            MgRecipeRegister.registerHammerTableRecipe(ManagerOreDict.getOre("ore" + oreNames[i]), new ItemStack(chunks, 1, i));

            //Grinder Recipes
            MgRecipeRegister.registerGrinderRecipe(new ItemStack(chunks, 1, i), new ItemStack(rubble, 1, i), new ItemStack(dust, 1, i1), 0.05F, extra_1, 0.05F);
//			MgRecipeRegister.registerGrinderRecipe(new ItemStack(chunks_clean.get(i)), new ItemStack(rubble.get(i)), new ItemStack(dust.get(i)), 0.05F, extra_1, 0.05F);

            //Grinder Recipes
            MgRecipeRegister.registerGrinderRecipe(new ItemStack(rubble, 1, i), new ItemStack(pebbles, 1, i), new ItemStack(dust, 1, i1), 0.05F, extra_1, 0.05F);
//			MgRecipeRegister.registerGrinderRecipe(new ItemStack(rubble_clean.get(i)), new ItemStack(pebbles.get(i)), new ItemStack(dust.get(i)), 0.05F, extra_1, 0.05F);

//			MgRecipeRegister.registerSifterRecipe(new ItemStack(pebbles_clean, 1, i), new ItemStack(dust.get(i), 2), extra_1, 0.05F);
            MgRecipeRegister.registerSifterRecipe(new ItemStack(pebbles, 1, i), new ItemStack(dust, 3, i1), extra_1, 0.05F);

            ItemStack ingot = ManagerOreDict.getOreWithPreference("ingot" + oreNames[i1]);
            if (ingot != null) {
                if (!oreNames[i].equals("Galena")) {
                    MgRecipeRegister.registerGrinderRecipe(ingot, new ItemStack(dust, 1, i), null, 0F, null, 0F);
                }
                ingot = ingot.copy();
                ingot.stackSize = 2;
                GameRegistry.addSmelting(new ItemStack(chunks, 1, i), ingot, 1.0F);
//				GameRegistry.addSmelting(new ItemStack(chunks_clean.get(i)), ingot, 1.0F);
                GameRegistry.addSmelting(new ItemStack(rubble, 1, i), ingot, 1.0F);
//				GameRegistry.addSmelting(new ItemStack(rubble_clean.get(i)), ingot, 1.0F);
                GameRegistry.addSmelting(new ItemStack(pebbles, 1, i), ingot, 1.0F);
//				GameRegistry.addSmelting(new ItemStack(pebbles_clean.get(i)), ingot, 1.0F);
                ItemStack ing2 = ingot.copy();
                ing2.stackSize = 1;
                GameRegistry.addSmelting(new ItemStack(dust, 1, i), ing2, 1.0F);
            }
        }
        GameRegistry.addSmelting(new ItemStack(oreCopper), new ItemStack(ingotCopper), 1.0F);
        GameRegistry.addSmelting(new ItemStack(oreTungsten), new ItemStack(ingotTungsten), 1.0F);
        GameRegistry.addSmelting(new ItemStack(oreZinc), new ItemStack(ingotZinc), 1.0F);
        GameRegistry.addSmelting(new ItemStack(dustBrass), new ItemStack(ingotBrass), 1.0F);
        GameRegistry.addSmelting(oreLime, new ItemStack(burntLime), 0.1F);
        GameRegistry.addSmelting(cobbleLime, new ItemStack(oreLime), 0F);
        GameRegistry.addSmelting(burntCobbleLime, new ItemStack(burntLime), 0F);

        MgRecipeRegister.registerGrinderRecipe(new ItemStack(Items.diamond), new ItemStack(dustDiamond), null, 0, null, 0);
        MgRecipeRegister.registerGrinderRecipe(new ItemStack(Items.quartz), new ItemStack(dustQuartz), new ItemStack(dustQuartz), 0.5f, null, 0);
        MgRecipeRegister.registerGrinderRecipe(new ItemStack(Blocks.obsidian), new ItemStack(dustObsidian, 8), null, 0, null, 0);

        MgRecipeRegister.registerCrusherRecipe(new ItemStack(oreSulfur, 1), new ItemStack(dustSulfur, 2), new ItemStack(dustSulfur, 1), 0.20f, null, 0);
        MgRecipeRegister.registerCrusherRecipe(new ItemStack(Blocks.coal_ore, 1), new ItemStack(Items.coal, 2), new ItemStack(Items.coal, 1), 0.15f, null, 0);
        MgRecipeRegister.registerCrusherRecipe(new ItemStack(Blocks.lapis_ore, 1), new ItemStack(Items.dye, 8, 4), new ItemStack(Items.dye, 1, 4), 0.40f, null, 0);
        MgRecipeRegister.registerCrusherRecipe(new ItemStack(Blocks.diamond_ore, 1), new ItemStack(Items.diamond, 1), new ItemStack(Items.diamond, 1), 0.30f, new ItemStack(Items.diamond, 2), 0.01f);
        MgRecipeRegister.registerCrusherRecipe(new ItemStack(Blocks.redstone_ore, 1), new ItemStack(Items.redstone, 5), new ItemStack(Items.redstone, 2), 0.40f, null, 0);
        MgRecipeRegister.registerCrusherRecipe(new ItemStack(Blocks.emerald_ore, 1), new ItemStack(Items.emerald, 1), new ItemStack(Items.emerald, 1), 0.30f, new ItemStack(Items.emerald, 2), 0.01f);
        MgRecipeRegister.registerCrusherRecipe(new ItemStack(Blocks.quartz_ore, 1), new ItemStack(Items.quartz, 2), new ItemStack(Items.quartz, 1), 0.40f, new ItemStack(dustQuartz, 1), 0.05f);
        MgRecipeRegister.registerCrusherRecipe(new ItemStack(oreSalt, 1), new ItemStack(dustSalt, 2), new ItemStack(dustSalt, 1), 0.40f, null, 0);
        MgRecipeRegister.registerCrusherRecipe(new ItemStack(oreLime), new ItemStack(cobbleLime), null, 0, null, 0);
        MgRecipeRegister.registerCrusherRecipe(new ItemStack(burntLime), new ItemStack(burntCobbleLime), null, 0, null, 0);

        MgRecipeRegister.registerHammerTableRecipe(new ItemStack(oreLime), new ItemStack(cobbleLime));
        MgRecipeRegister.registerHammerTableRecipe(new ItemStack(burntLime), new ItemStack(burntCobbleLime));

        MgRecipeRegister.registerOilDistilleryRecipe(FluidRegistry.getFluidStack("oil", 20), FluidRegistry.getFluidStack("hotcrude", 20), EnergyConverter.RFtoW(160));
        MgRecipeRegister.registerRefineryRecipe(FluidRegistry.getFluidStack("hotcrude", 20), FluidRegistry.getFluidStack("lightoil", 7), FluidRegistry.getFluidStack("heavyoil", 6), FluidRegistry.getFluidStack("naturalgas", 7));

        MgRecipeRegister.registerPolymerizerRecipe(FluidRegistry.getFluidStack(ManagerFluids.NATURAL_GAS, 500), new ItemStack(ManagerItems.dustSulfur), new ItemStack(ManagerItems.rubber), 200);
        MgRecipeRegister.registerPolymerizerRecipe(FluidRegistry.getFluidStack(ManagerFluids.NATURAL_GAS, 500), new ItemStack(Items.coal), new ItemStack(ManagerItems.plastic), 300);
    }

    public static void registerThermopileRecipes() {
        MgRecipeRegister.addThermopileDecay(new ThermophileDecay());
        MgRecipeRegister.addThermopileSource(new BlockInfo(Blocks.air, -1), 1, false);
        MgRecipeRegister.addThermopileSource(new BlockInfo(Blocks.snow, -1), 100, false);
        MgRecipeRegister.addThermopileSource(new BlockInfo(Blocks.ice, -1), 100, false);
        MgRecipeRegister.addThermopileSource(new BlockInfo(Blocks.snow_layer, -1), 50, false);
        MgRecipeRegister.addThermopileSource(new BlockInfo(Blocks.torch, -1), 5, true);
        MgRecipeRegister.addThermopileSource(new BlockInfo(Blocks.lit_pumpkin, -1), 3, true);
        MgRecipeRegister.addThermopileSource(new BlockInfo(Blocks.water, -1), 25, false);
        MgRecipeRegister.addThermopileSource(new BlockInfo(Blocks.lava, -1), 100, true);
        MgRecipeRegister.addThermopileSource(new BlockInfo(Blocks.fire, -1), 25, true);
    }

    public static void registerBiomassBurnerRecipes() {
        for (int i = 0; i < 4; i++)
            MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.log, 1, i), 1600, true);
        for (int i = 0; i < 6; i++)
            MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.sapling, 1, i), 200, true);
        for (int i = 0; i < 4; i++)
            MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.leaves, 1, i), 20, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.web, 1, 0), 50, true);
        for (int i = 1; i < 3; i++)
            MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.tallgrass, 1, i), 50, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.deadbush, 1, 0), 50, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.yellow_flower, 1, 0), 50, true);
        for (int i = 0; i < 9; i++)
            MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.red_flower, 1, i), 50, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.red_mushroom, 1, 0), 100, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.brown_mushroom, 1, 0), 100, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.vine, 1, 0), 100, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.waterlily, 1, 0), 50, true);
        for (int i = 0; i < 2; i++)
            MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.log2, 1, i), 1600, true);
        for (int i = 0; i < 2; i++)
            MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.leaves2, 1, i), 20, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.cactus, 1, 0), 150, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.pumpkin, 1, 0), 200, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.lit_pumpkin, 1, 0), 200, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.melon_block, 1, 0), 800, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Items.melon, 1, 0), 80, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.hay_block, 1, 0), 1800, true);
        for (int i = 0; i < 6; i++)
            MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.double_plant, 1, i), 20, true);

        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Items.carrot, 1, 0), 200, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Items.potato, 1, 0), 200, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Items.wheat, 1, 0), 200, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Items.wheat_seeds, 1, 0), 100, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Items.pumpkin_seeds, 1, 0), 100, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Items.melon_seeds, 1, 0), 100, true);
        MgRecipeRegister.addBiomassBurnerRecipe(new ItemStack(Items.reeds, 1, 0), 100, true);
        ItemStack liana = ManagerOreDict.getOre("plantLiana");
        if (liana != null)
            MgRecipeRegister.addBiomassBurnerRecipe(liana.copy(), 400, true);
    }
}
