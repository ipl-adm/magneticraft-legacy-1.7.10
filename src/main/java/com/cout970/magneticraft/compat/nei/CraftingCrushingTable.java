package com.cout970.magneticraft.compat.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.access.RecipeCrushingTable;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.util.RenderUtil;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CraftingCrushingTable extends TemplateRecipeHandler {

    List<RecipeCrushingTable> recipes = new ArrayList<>();

    @Override
    public String getRecipeName() {
        return "Crushing Table";
    }

    @Override
    public String getGuiTexture() {
        return "magneticraft:textures/gui/nei/crushing_table.png";
    }

    @Override
    public void loadTransferRects() {

        transferRects.add(new RecipeTransferRect(new Rectangle(68, 21, 24, 15), getRecipesID()));
    }

    private String getRecipesID() {
        return "mg_crushing_table";
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {

        if (outputId.equals(getRecipesID())) {
            recipes.addAll(MgRecipeRegister.crushing_table.stream().collect(Collectors.toList()));
        } else super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        recipes.addAll(MgRecipeRegister.crushing_table.stream().filter(recipe -> (recipe != null) && MgUtils.areEqual(recipe.getOutput(), result, true)).collect(Collectors.toList()));
    }


    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        recipes.addAll(MgRecipeRegister.crushing_table.stream().filter(recipe -> (recipe != null) && recipe.matches(ingredient)).collect(Collectors.toList()));
    }

    @Override
    public int recipiesPerPage() {
        return 1;
    }

    @Override
    public PositionedStack getResultStack(int recipe) {
        return new PositionedStack(recipes.get(recipe).getOutput(), 96, 20);
    }

    @Override
    public List<PositionedStack> getOtherStacks(int recipe) {
        return Collections.emptyList();
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
    }
}