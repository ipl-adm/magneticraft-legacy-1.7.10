package com.cout970.magneticraft.compact.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.GuiCrafter;

public class NEIMagneticraftConfig implements IConfigureNEI{

	@Override
	public String getName() {
		return "Magneticraft";
	}

	@Override
	public String getVersion() {
		return Magneticraft.VERSION;
	}

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new CraftingCrusher());
		API.registerUsageHandler(new CraftingCrusher());
		API.registerRecipeHandler(new CraftingGrinder());
		API.registerUsageHandler(new CraftingGrinder());
		API.registerRecipeHandler(new CraftingPolimerizer());
		API.registerUsageHandler(new CraftingPolimerizer());
		
		API.registerGuiOverlayHandler(GuiCrafter.class, new CrafterOverlayHandler(), "crafting");
	}

}
