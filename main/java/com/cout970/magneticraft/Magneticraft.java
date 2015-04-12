package com.cout970.magneticraft;

import com.cout970.magneticraft.compact.ManagerIntegration;
import com.cout970.magneticraft.handlers.SolidFuelHandler;
import com.cout970.magneticraft.handlers.GuiHandler;
import com.cout970.magneticraft.proxy.IProxy;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.world.WorldGenManagerMg;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;


@Mod(modid = Magneticraft.ID, name = Magneticraft.NAME, version = Magneticraft.VERSION, dependencies = "required-after:ForgeMultipart;after:*")
public class Magneticraft{
	
	public final static String ID = "Magneticraft";
	public final static String NAME = "Magneticraft";
	public final static String VERSION = "0.0.4";
	public final static String ENERGY_STORED_NAME = "J";
	
	@Instance(NAME)
	public static Magneticraft Instance;
	
	@SidedProxy(clientSide="com.cout970.magneticraft.proxy.ClientProxy",
				serverSide="com.cout970.magneticraft.proxy.ServerProxy")
	public static IProxy proxy;
	
	public static ManagerMultiPart registry;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event){
		Log.info("Starting preInit");
		ManagerConfig.init(event.getSuggestedConfigurationFile());
		
		ManagerBlocks.initBlocks();
		ManagerBlocks.registerBlocks();
		ManagerBlocks.registerTileEntities();
		
		ManagerItems.initItems();
		ManagerItems.registerItems();
		
		ManagerFluids.initFluids();
		ManagerFluids.registerFluidsBlocks();

		proxy.init();

		ManagerIntegration.searchCompatibilities();

		//
		//		LangHelper.registerNames();
		//		LangHelper.setupLangFile();
		//		
		ManagerOreDict.registerOreDict();
		Log.info("preInit Done");
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event){
		Log.info("Starting Init");
		NetworkRegistry.INSTANCE.registerGuiHandler(Instance, new GuiHandler());
		MB_Register.init();
		registry = new ManagerMultiPart();
		registry.init();
		GameRegistry.registerFuelHandler(new SolidFuelHandler());
		GameRegistry.registerWorldGenerator(new WorldGenManagerMg(), 11);
		ManagerCraft.init();
		ManagerRecipe.registerFurnaceRecipes();
		ManagerRecipe.registerThermopileRecipes();
		ManagerRecipe.registerBiomassBurnerRecipes();
		ManagerNetwork.registerMessages();
		ManagerFluids.registerFuels();
		Log.info("Init Done");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		Log.info("Starting postInit");
		Log.info("postInit Done");
	}
}
