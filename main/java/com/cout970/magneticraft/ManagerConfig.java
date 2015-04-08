package com.cout970.magneticraft;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

import com.cout970.magneticraft.world.WorldGenManagerMg;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ManagerConfig {

	public static Configuration config;
	
	public static void init(File file) {
		if(config == null){
			config = new Configuration(file);
			LoadConfigs();
		}
	}
	
	@SubscribeEvent
	public void onconfigurationChangeEvent(ConfigChangedEvent.OnConfigChangedEvent event){
		if(event.modID.equalsIgnoreCase(Magneticraft.ID)){
			LoadConfigs();
		}
	}
	
	public static void LoadConfigs(){
		config.load();
		
		WorldGenManagerMg.GenCopper = config.getBoolean("copper", "ores", true, "Generation of copper");
		WorldGenManagerMg.GenTungsten = config.getBoolean("tungsten", "ores", true, "Generation of tungsten");
		WorldGenManagerMg.GenSulfur = config.getBoolean("sulfur", "ores", true, "Generation of sulfur");
		WorldGenManagerMg.GenUranium = config.getBoolean("uranium", "ores", true, "Generation of uranium");
		WorldGenManagerMg.GenThorium = config.getBoolean("thorium", "ores", true, "Generation of thorium");
		WorldGenManagerMg.GenSalt = config.getBoolean("salt", "ores", true, "Generation of salt");
		
		if(config.hasChanged()){
			config.save();
		}
	}
}
