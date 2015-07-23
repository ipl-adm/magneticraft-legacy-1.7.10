package com.cout970.magneticraft.compact.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.acces.MgRecipeRegister;
import com.cout970.magneticraft.api.acces.RecipeBiomassBurner;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.util.RenderUtil;

public class CraftingBiomassBurner extends TemplateRecipeHandler{
	
	List<RecipeBiomassBurner> recipes = new ArrayList<RecipeBiomassBurner>();

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
			for (RecipeBiomassBurner recipe : MgRecipeRegister.biomassBurner)
				recipes.add(recipe);
		}  else super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result){}


	@Override
	public void loadUsageRecipes(ItemStack ingredient){
		for (RecipeBiomassBurner recipe : MgRecipeRegister.biomassBurner){
			if(recipe.matches(ingredient))recipes.add(recipe);
		}
	}
	
	@Override
	public PositionedStack getResultStack(int recipe)
	{
		return null;//new PositionedStack(recipes.get(recipe).getFuel(),96,20);
	}
	
	@Override
	public List<PositionedStack> getOtherStacks(int recipe)
	{
		List<PositionedStack> a = new ArrayList<PositionedStack>();
		return a;
	}
	@Override
	public List<PositionedStack> getIngredientStacks(int recipe)
	{
		List<PositionedStack> need = new ArrayList<PositionedStack>();
		need.add(new PositionedStack(recipes.get(recipe).getFuel(), 75, 36));
		return need;
	}

	@Override
	public int numRecipes()
	{
		return recipes.size();
	}

	@Override
	public void drawExtras(int recipe)
	{
		int ticks = 100;
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/heatbar.png"));
		int heat = (int) (EnergyConversor.FUELtoCALORIES(recipes.get(recipe).getBurnTime()));
		int scale = (int) (44*(heat/750f)/1400f);
		RenderUtil.drawTexturedModalRectScaled(102, 9+(44-scale), 0, 44-scale, 6, scale, 12, 45);
		RenderUtil.drawString(String.format("%.1fkcal", (float)heat/1000f), 115, 30, RenderUtil.fromRGB(255, 255, 255), false);
	}
}