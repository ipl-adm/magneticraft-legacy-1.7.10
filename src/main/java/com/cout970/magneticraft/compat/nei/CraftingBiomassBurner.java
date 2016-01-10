package com.cout970.magneticraft.compat.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.access.RecipeBiomassBurner;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CraftingBiomassBurner extends TemplateRecipeHandler {

    List<RecipeBiomassBurner> recipes = new ArrayList<>();

    @Override
    public String getRecipeName() {
        return "Biomass Burner";
    }

    @Override
    public String getGuiTexture() {
        return "magneticraft:textures/gui/nei/biomass_burner.png";
    }

    @Override
    public void loadTransferRects() {
        transferRects.add(new RecipeTransferRect(new Rectangle(75, 18, 16, 16), getRecipesID()));
    }

    private String getRecipesID() {
        return "mg_biomas_burner";
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        if (outputId.equals(getRecipesID())) {
            recipes.addAll(MgRecipeRegister.biomassBurner.stream().collect(Collectors.toList()));
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
    }


    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        recipes.addAll(MgRecipeRegister.biomassBurner.stream().filter(recipe -> (recipe != null) && recipe.matches(ingredient)).collect(Collectors.toList()));
    }

    @Override
    public PositionedStack getResultStack(int recipe) {
        return null;//new PositionedStack(recipes.get(recipe).getFuel(),96,20);
    }

    @Override
    public List<PositionedStack> getOtherStacks(int recipe) {
        return new ArrayList<>();
    }

    @Override
    public List<PositionedStack> getIngredientStacks(int recipe) {
        List<PositionedStack> need = new ArrayList<>();
        need.add(new PositionedStack(recipes.get(recipe).getFuel(), 75, 36));
        return need;
    }

    @Override
    public int numRecipes() {
        return recipes.size();
    }

    @Override
    public void drawExtras(int recipe) {
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/heatbar.png"));
        int heat = (int) (EnergyConverter.FUELtoCALORIES(recipes.get(recipe).getBurnTime()));
        int scale = (int) (44 * (heat / 750f) / 1400f);
        RenderUtil.drawTexturedModalRectScaled(102, 9 + (44 - scale), 0, 44 - scale, 6, scale, 12, 45);
        RenderUtil.drawString(String.format("%.1fkcal", (float) heat / 1000f), 115, 30, RenderUtil.fromRGB(255, 255, 255), false);
    }
}