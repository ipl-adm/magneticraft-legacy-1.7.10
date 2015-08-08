package com.cout970.magneticraft;

import java.util.List;

import com.cout970.magneticraft.compact.ManagerIntegration;
import com.cout970.magneticraft.compact.minetweaker.MgMinetweaker;
import com.cout970.magneticraft.handlers.GuiHandler;
import com.cout970.magneticraft.handlers.HandlerBuckets;
import com.cout970.magneticraft.handlers.SolidFuelHandler;
import com.cout970.magneticraft.proxy.IProxy;
import com.cout970.magneticraft.tileentity.TileMiner;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.energy.EnergyInterfaceFactory;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.world.WorldGenManagerMg;
import com.google.common.collect.Lists;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.MinecraftForge;


@Mod(modid = Magneticraft.ID, name = Magneticraft.NAME, version = Magneticraft.VERSION, guiFactory = Magneticraft.GUI_FACTORY, dependencies = "required-after:ForgeMultipart")
public class Magneticraft{
	
	public final static String ID = "Magneticraft";
	public final static String NAME = "Magneticraft";
	public final static String VERSION = "0.3.4";
	public final static String ENERGY_STORED_NAME = "J";
	public final static String GUI_FACTORY = "com.cout970.magneticraft.handlers.MgGuiFactory";
	public static final boolean DEBUG = false;
	
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

		proxy.init();

		ManagerIntegration.searchCompatibilities();

		if(DEBUG){
			LangHelper.registerNames();
			LangHelper.setupLangFile();
		}

		ManagerOreDict.registerOreDict();
		Log.info("preInit Done");
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event){
		Log.info("Starting Init");
		NetworkRegistry.INSTANCE.registerGuiHandler(Instance, new GuiHandler());
		MB_Register.init();
		EnergyInterfaceFactory.init();
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
		HandlerBuckets.INSTANCE = new HandlerBuckets();
		MinecraftForge.EVENT_BUS.register(HandlerBuckets.INSTANCE);
		Log.info("Init Done");
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event){
		Log.info("Starting postInit");
		if(Loader.isModLoaded("MineTweaker3")){
			Log.info("Seting up minetweaker compatibility");
			MgMinetweaker.init();
		}
		ForgeChunkManager.setForcedChunkLoadingCallback(Instance, new MinerChunkCallBack());
//		if(DEBUG)printOreDict();
		Log.info("postInit Done");
	}
	
//	private void printOreDict() {
//		for(String i : OreDictionary.getOreNames()){
//			Log.debug(i+" "+ManagerOreDict.getOre(i));
//		}
//	}

	public class MinerChunkCallBack implements ForgeChunkManager.OrderedLoadingCallback {

		@Override
		public void ticketsLoaded(List<Ticket> tickets, World world) {
			for (Ticket ticket : tickets) {
				int x = ticket.getModData().getInteger("quarryX");
				int y = ticket.getModData().getInteger("quarryY");
				int z = ticket.getModData().getInteger("quarryZ");
				TileEntity tq = world.getTileEntity(x, y, z);
				if(tq instanceof TileMiner){
					((TileMiner)tq).forceChunkLoading(ticket);
				}
			}
		}

		@Override
		public List<Ticket> ticketsLoaded(List<Ticket> tickets, World world, int maxTicketCount) {
			List<Ticket> validTickets = Lists.newArrayList();
			if(ManagerConfig.ChunkLoading){
				for (Ticket ticket : tickets) {
					int x = ticket.getModData().getInteger("quarryX");
					int y = ticket.getModData().getInteger("quarryY");
					int z = ticket.getModData().getInteger("quarryZ");

					Block block = world.getBlock(x, y, z);
					if (block == ManagerBlocks.miner) {
						validTickets.add(ticket);
					}
				}
			}
			return validTickets;
		}
	}
}
