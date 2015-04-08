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
import com.cout970.magneticraft.api.acces.RecipeGrinder;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.util.RenderUtil;

public class CraftingGrinder extends TemplateRecipeHandler{
	
	List<RecipeGrinder> recipes = new ArrayList<RecipeGrinder>();

	@Override
	public String getRecipeName() {
		return "Grinder";
	}

	@Override
	public String getGuiTexture() {
		return "magneticraft:textures/gui/nei/grinder.png";
	}

	@Override
	public void loadTransferRects() {

		transferRects.add(new RecipeTransferRect(new Rectangle(68, 21, 24, 15), getRecipesID()));
	}

	private String getRecipesID() {
		return "mg_grinder";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {

		if (outputId.equals(getRecipesID())) {
			for (RecipeGrinder recipe : MgRecipeRegister.grinder)
				recipes.add(recipe);
		}  else super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result)
	{
		for (RecipeGrinder recipe : MgRecipeRegister.grinder){
			if(MgUtils.areEcuals(recipe.output, result, true))recipes.add(recipe);
			else if(MgUtils.areEcuals(recipe.output2, result, true))recipes.add(recipe);
			else if(MgUtils.areEcuals(recipe.output3, result, true))recipes.add(recipe);
		}
	}


	@Override
	public void loadUsageRecipes(ItemStack ingredient){
		for (RecipeGrinder recipe : MgRecipeRegister.grinder){
			if(recipe.matches(ingredient))recipes.add(recipe);
		}
	}
	
	@Override
	public PositionedStack getResultStack(int recipe)
	{
		return new PositionedStack(recipes.get(recipe).output,96,20);
	}
	
	@Override
	public List<PositionedStack> getOtherStacks(int recipe)
	{
		List<PositionedStack> a = new ArrayList<PositionedStack>();
		if(recipes.get(recipe).output2 != null) a.add(new PositionedStack(recipes.get(recipe).output2, 114, 20));
		if(recipes.get(recipe).output3 != null) a.add(new PositionedStack(recipes.get(recipe).output3, 132, 20));
		return a;
	}
	@Override
	public List<PositionedStack> getIngredientStacks(int recipe)
	{
		List<PositionedStack> need = new ArrayList<PositionedStack>();
		need.add(new PositionedStack(recipes.get(recipe).input, 46, 20));
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
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/progresbar1.png"));
		RenderUtil.drawTexturedModalRectScaled(69, 20, 0, 0, (int)(22*((cycleticks % ticks / (float)ticks))), 16, 22*2, 16);
		if(recipes.get(recipe).output2 != null){
			String s = (int)(recipes.get(recipe).prob2*100)+"%";
			RenderUtil.drawString(s, 122, 44, RenderUtil.fromRGB(255, 255, 255), true);
		}
		if(recipes.get(recipe).output3 != null){
			String s = (int)(recipes.get(recipe).prob3*100)+"%";
			RenderUtil.drawString(s, 144, 44, RenderUtil.fromRGB(255, 255, 255), true);
		}
	}
}