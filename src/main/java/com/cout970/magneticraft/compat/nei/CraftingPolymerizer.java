package com.cout970.magneticraft.compat.nei;

import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;
import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.access.RecipePolymerizer;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CraftingPolymerizer extends TemplateRecipeHandler {

    public List<RecipePolymerizer> recipes = new ArrayList<>();
    private static ResourceLocation tank = new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png");
    private static ResourceLocation heat = new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/heatbar.png");

    @Override
    public String getRecipeName() {
        return "Polymerizer";
    }

    @Override
    public String getGuiTexture() {
        return "magneticraft:textures/gui/nei/polimerizer.png";
    }

    @Override
    public void loadTransferRects() {

        transferRects.add(new RecipeTransferRect(new Rectangle(68, 21, 24, 15), getRecipesID()));
    }

    private String getRecipesID() {
        return "mg_polymerizer";
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results) {
        super.loadCraftingRecipes(outputId, results);
    }

    @Override
    public void loadCraftingRecipes(ItemStack result) {
        recipes.addAll(MgRecipeRegister.polymerizer.stream().filter(rec -> (rec != null) && MgUtils.areEqual(result, rec.getOutput(), true)).collect(Collectors.toList()));
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient) {
        recipes.addAll(MgRecipeRegister.polymerizer.stream().filter(rec -> (rec != null) && MgUtils.areEqual(ingredient, rec.getInput(), true)).collect(Collectors.toList()));
    }

    @Override
    public PositionedStack getResultStack(int recipe) {
        return new PositionedStack(recipes.get(recipe).getOutput(), 119, 25);
    }

    @Override
    public List<PositionedStack> getOtherStacks(int recipe) {
        return new ArrayList<>();
    }

    @Override
    public List<PositionedStack> getIngredientStacks(int recipe) {
        List<PositionedStack> need = new ArrayList<>();
        need.add(new PositionedStack(recipes.get(recipe).getInput(), 63, 25));
        return need;
    }

    @Override
    public int numRecipes() {
        return recipes.size();
    }

    @Override
    public void drawExtras(int recipe) {
        RecipePolymerizer rec = recipes.get(recipe);
        RenderUtil.drawString(rec.getFluid().getLocalizedName(), 45, 60, RenderUtil.fromRGB(255, 255, 255), true);
        RenderUtil.bindTexture(TextureMap.locationBlocksTexture);
        drawTexturedModelRectFromIcon(36, 14, rec.getFluid().getFluid().getIcon(), 18, 39);
        RenderUtil.bindTexture(tank);
        RenderUtil.drawTexturedModalRectScaled(35, 13, 0, 0, 18, 39, 20, 41);
        RenderUtil.bindTexture(heat);
        int scale = Math.min(44, (int) (rec.getTemperature() * 44f / 1400f));
        RenderUtil.drawTexturedModalRectScaled(15, 9 + (44 - scale), 0, 44 - scale, 6, scale, 12, 45);
    }

    public void drawTexturedModelRectFromIcon(int p_94065_1_, int p_94065_2_, IIcon p_94065_3_, int p_94065_4_, int p_94065_5_) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (p_94065_1_), (double) (p_94065_2_ + p_94065_5_), (double) 0, (double) p_94065_3_.getMinU(), (double) p_94065_3_.getMaxV());
        tessellator.addVertexWithUV((double) (p_94065_1_ + p_94065_4_), (double) (p_94065_2_ + p_94065_5_), (double) 0, (double) p_94065_3_.getMaxU(), (double) p_94065_3_.getMaxV());
        tessellator.addVertexWithUV((double) (p_94065_1_ + p_94065_4_), (double) (p_94065_2_), (double) 0, (double) p_94065_3_.getMaxU(), (double) p_94065_3_.getMinV());
        tessellator.addVertexWithUV((double) (p_94065_1_), (double) (p_94065_2_), (double) 0, (double) p_94065_3_.getMinU(), (double) p_94065_3_.getMinV());
        tessellator.draw();
    }
}