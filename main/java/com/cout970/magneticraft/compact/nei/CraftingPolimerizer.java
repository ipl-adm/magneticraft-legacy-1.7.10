package com.cout970.magneticraft.compact.nei;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import codechicken.nei.PositionedStack;
import codechicken.nei.recipe.TemplateRecipeHandler;

import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.util.RenderUtil;

public class CraftingPolimerizer  extends TemplateRecipeHandler{

	boolean matches = false;
	
	@Override
	public String getRecipeName() {
		return "Polimerizer";
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
		return "mg_polimerizer";
	}
	
	@Override
	public void loadCraftingRecipes(String outputId, Object... results) {
		super.loadCraftingRecipes(outputId, results);
	}

	@Override
	public void loadCraftingRecipes(ItemStack result)
	{
		if(MgUtils.areEcuals(result, new ItemStack(ManagerItems.plastic),true)){
			matches = true;
		}else{
			matches = false;
		}
	}


	@Override
	public void loadUsageRecipes(ItemStack ingredient){
		if(MgUtils.areEcuals(ingredient, new ItemStack(ManagerItems.dustSulfur),true)){
			matches = true;
		}else{
			matches = false;
		}
	}
	
	@Override
	public PositionedStack getResultStack(int recipe)
	{
		return new PositionedStack(new ItemStack(ManagerItems.plastic),119,25);
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
		need.add(new PositionedStack(new ItemStack(ManagerItems.dustSulfur), 63,25));
		return need;
	}

	@Override
	public int numRecipes()
	{
		return matches ? 1 : 0;
	}

	@Override
	public void drawExtras(int recipe)
	{
		RenderUtil.drawString("Light Oil", 45, 60, RenderUtil.fromRGB(255, 255, 255), true);
	}
}