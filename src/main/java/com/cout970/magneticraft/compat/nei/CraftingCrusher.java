package com.cout970.magneticraft.compat.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.access.RecipeCrusher;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CraftingCrusher extends TemplateRecipeHandler {

    List<RecipeCrusher> recipes = new ArrayList<>();

    @Override
    public String getRecipeName() {
        return "Crusher";
    }

    @Override
    public String getGuiTexture() {
        return "magneticraft:textures/gui/nei/crusher.png";
    }

    @Override
    public void loadTransferRects() {

        transferRects.add(new RecipeTransferRect(new Rectangle(68, 21, 24, 15), getRecipesID()));
    }

    private String getRecipesID() {
        return "mg_crusher";
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {

        if (outputId.equals(getRecipesID())) {
            recipes.addAll(MgRecipeRegister.crusher.stream().collect(Collectors.toList()));
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        for (RecipeCrusher recipe : MgRecipeRegister.crusher) {
            if (MgUtils.areEqual(recipe.getOutput(), result, true)) recipes.add(recipe);
            else if (MgUtils.areEqual(recipe.getOutput2(), result, true)) recipes.add(recipe);
            else if (MgUtils.areEqual(recipe.getOutput3(), result, true)) recipes.add(recipe);
        }
    }


    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        recipes.addAll(MgRecipeRegister.crusher.stream().filter(recipe -> (recipe != null) && recipe.matches(ingredient)).collect(Collectors.toList()));
    }

    @Override
    public PositionedStack getResultStack(int recipe) {
        return new PositionedStack(recipes.get(recipe).getOutput(), 96, 20);
    }

    @Override
    public List<PositionedStack> getOtherStacks(int recipe) {
        List<PositionedStack> a = new ArrayList<>();
        if (recipes.get(recipe).getOutput2() != null)
            a.add(new PositionedStack(recipes.get(recipe).getOutput2(), 114, 20));
        if (recipes.get(recipe).getOutput3() != null)
            a.add(new PositionedStack(recipes.get(recipe).getOutput3(), 132, 20));
        return a;
    }

    @Override
    public List<PositionedStack> getIngredientStacks(int recipe) {
        List<PositionedStack> need = new ArrayList<>();
        need.add(new PositionedStack(recipes.get(recipe).getInput(), 46, 20));
        return need;
    }

    @Override
    public int numRecipes() {
        return recipes.size();
    }

    @Override
    public void drawExtras(int recipe) {
        int ticks = 100;
        Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/progressbar1.png"));
        RenderUtil.drawTexturedModalRectScaled(69, 20, 0, 0, (int) (22 * ((cycleticks % ticks / (float) ticks))), 16, 22 * 2, 16);
        if (recipes.get(recipe).getOutput2() != null) {
            String s = (int) (recipes.get(recipe).getProb2() * 100) + "%";
            RenderUtil.drawString(s, 122, 44, RenderUtil.fromRGB(255, 255, 255), true);
        }
        if (recipes.get(recipe).getOutput3() != null) {
            String s = (int) (recipes.get(recipe).getProb3() * 100) + "%";
            RenderUtil.drawString(s, 144, 44, RenderUtil.fromRGB(255, 255, 255), true);
        }
    }
}