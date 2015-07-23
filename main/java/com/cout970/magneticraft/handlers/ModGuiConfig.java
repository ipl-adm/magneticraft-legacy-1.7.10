package com.cout970.magneticraft.handlers;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerConfig;

import cpw.mods.fml.client.config.GuiConfig;

public class ModGuiConfig extends GuiConfig {

	public ModGuiConfig(GuiScreen parentScreen) {
		super(parentScreen,
				new ConfigElement(ManagerConfig.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				Magneticraft.ID,
				false,
				false,
				GuiConfig.getAbridgedConfigPath(ManagerConfig.config.toString()));
	}

}