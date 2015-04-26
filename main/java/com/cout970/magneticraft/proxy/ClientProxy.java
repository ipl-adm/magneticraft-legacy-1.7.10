package com.cout970.magneticraft.proxy;

import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderCableHigh;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderCableLow;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderCableMedium;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderChainsaw;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderCombustionEngine;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderCopperPipe;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderElectricSword;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderFluidHopper;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderHeatCable;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderHeatSink;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderInserter;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderKineticGenerator;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderPumpJack;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderReactorActivator;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderReactorControl;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderReactorVessel;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderSteamEngine;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderTeslaCoil;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderTransformerLM;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderTransformerMH;
import com.cout970.magneticraft.client.itemrenderer.ItemRenderWireCopper;
import com.cout970.magneticraft.client.tilerender.TileRenderCombustionEngine;
import com.cout970.magneticraft.client.tilerender.TileRenderConveyorBelt;
import com.cout970.magneticraft.client.tilerender.TileRenderCrusher;
import com.cout970.magneticraft.client.tilerender.TileRenderFluidHopper;
import com.cout970.magneticraft.client.tilerender.TileRenderGrinder;
import com.cout970.magneticraft.client.tilerender.TileRenderHeatCable;
import com.cout970.magneticraft.client.tilerender.TileRenderHeatSink;
import com.cout970.magneticraft.client.tilerender.TileRenderInserter;
import com.cout970.magneticraft.client.tilerender.TileRenderKineticGenerator;
import com.cout970.magneticraft.client.tilerender.TileRenderMgTank;
import com.cout970.magneticraft.client.tilerender.TileRenderMirror;
import com.cout970.magneticraft.client.tilerender.TileRenderPolimerizer;
import com.cout970.magneticraft.client.tilerender.TileRenderPumpJack;
import com.cout970.magneticraft.client.tilerender.TileRenderReactorActivator;
import com.cout970.magneticraft.client.tilerender.TileRenderReactorControl;
import com.cout970.magneticraft.client.tilerender.TileRenderReactorVessel;
import com.cout970.magneticraft.client.tilerender.TileRenderRefinery;
import com.cout970.magneticraft.client.tilerender.TileRenderSteamEngine;
import com.cout970.magneticraft.client.tilerender.TileRenderTeslaCoil;
import com.cout970.magneticraft.client.tilerender.TileRenderTransformerLow_Medium;
import com.cout970.magneticraft.client.tilerender.TileRenderTransformer_MH;
import com.cout970.magneticraft.client.tilerender.TileRenderTurbine;
import com.cout970.magneticraft.client.tilerender.TileRenderWindMill;
import com.cout970.magneticraft.tileentity.TileCombustionEngine;
import com.cout970.magneticraft.tileentity.TileConveyorBelt;
import com.cout970.magneticraft.tileentity.TileCrusher;
import com.cout970.magneticraft.tileentity.TileFluidHopper;
import com.cout970.magneticraft.tileentity.TileGrinder;
import com.cout970.magneticraft.tileentity.TileHeatCable;
import com.cout970.magneticraft.tileentity.TileHeatSink;
import com.cout970.magneticraft.tileentity.TileInserter;
import com.cout970.magneticraft.tileentity.TileKineticGenerator;
import com.cout970.magneticraft.tileentity.TileMgTank;
import com.cout970.magneticraft.tileentity.TileMirror;
import com.cout970.magneticraft.tileentity.TilePolimerizer;
import com.cout970.magneticraft.tileentity.TilePumpJack;
import com.cout970.magneticraft.tileentity.TileReactorActivator;
import com.cout970.magneticraft.tileentity.TileReactorControlRods;
import com.cout970.magneticraft.tileentity.TileReactorVessel;
import com.cout970.magneticraft.tileentity.TileRefinery;
import com.cout970.magneticraft.tileentity.TileSteamEngine;
import com.cout970.magneticraft.tileentity.TileTeslaCoil;
import com.cout970.magneticraft.tileentity.TileTransformerLow_Medium;
import com.cout970.magneticraft.tileentity.TileTransformerMedium_High;
import com.cout970.magneticraft.tileentity.TileTurbineControl;
import com.cout970.magneticraft.tileentity.TileWindTurbine;

import cpw.mods.fml.client.registry.ClientRegistry;


public class ClientProxy implements IProxy{

	@Override
	public void init() {
		MinecraftForgeClient.registerItemRenderer(ManagerItems.cablelow, new ItemRenderCableLow());
		MinecraftForgeClient.registerItemRenderer(ManagerItems.cablemedium, new ItemRenderCableMedium());
		MinecraftForgeClient.registerItemRenderer(ManagerItems.cablehigh, new ItemRenderCableHigh());
		MinecraftForgeClient.registerItemRenderer(ManagerItems.partcopperpipe, new ItemRenderCopperPipe());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.kinetic), new ItemRenderKineticGenerator());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.pumpJack), new ItemRenderPumpJack());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.transformer_lm), new ItemRenderTransformerLM());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.fluidhopper), new ItemRenderFluidHopper());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.steamengine), new ItemRenderSteamEngine());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.reactor_vessel), new ItemRenderReactorVessel());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.reactor_control_rods), new ItemRenderReactorControl());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.reactor_activator), new ItemRenderReactorActivator());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.transformer_mh), new ItemRenderTransformerMH());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.heat_cable), new ItemRenderHeatCable());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.inserter), new ItemRenderInserter());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.tesla_coil), new ItemRenderTeslaCoil());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.heat_sink), new ItemRenderHeatSink());
		MinecraftForgeClient.registerItemRenderer(ManagerItems.wire_copper, new ItemRenderWireCopper());
		MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.combustion_engine), new ItemRenderCombustionEngine());
		MinecraftForgeClient.registerItemRenderer(ManagerItems.sword, new ItemRenderElectricSword());
		MinecraftForgeClient.registerItemRenderer(ManagerItems.chainsaw, new ItemRenderChainsaw());

		
		ClientRegistry.bindTileEntitySpecialRenderer(TilePumpJack.class,new TileRenderPumpJack());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMirror.class,new TileRenderMirror());
		ClientRegistry.bindTileEntitySpecialRenderer(TileWindTurbine.class,new TileRenderWindMill());
		ClientRegistry.bindTileEntitySpecialRenderer(TileFluidHopper.class,new TileRenderFluidHopper());
		ClientRegistry.bindTileEntitySpecialRenderer(TileSteamEngine.class,new TileRenderSteamEngine());
		ClientRegistry.bindTileEntitySpecialRenderer(TileTransformerLow_Medium.class,new TileRenderTransformerLow_Medium());
		ClientRegistry.bindTileEntitySpecialRenderer(TileKineticGenerator.class,new TileRenderKineticGenerator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileReactorVessel.class,new TileRenderReactorVessel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileReactorControlRods.class,new TileRenderReactorControl());
		ClientRegistry.bindTileEntitySpecialRenderer(TileInserter.class,new TileRenderInserter());
		ClientRegistry.bindTileEntitySpecialRenderer(TileReactorActivator.class,new TileRenderReactorActivator());
		ClientRegistry.bindTileEntitySpecialRenderer(TileTransformerMedium_High.class,new TileRenderTransformer_MH());
		ClientRegistry.bindTileEntitySpecialRenderer(TileTeslaCoil.class,new TileRenderTeslaCoil());
		ClientRegistry.bindTileEntitySpecialRenderer(TileHeatCable.class,new TileRenderHeatCable());
		ClientRegistry.bindTileEntitySpecialRenderer(TileConveyorBelt.class,new TileRenderConveyorBelt());
		ClientRegistry.bindTileEntitySpecialRenderer(TileRefinery.class,new TileRenderRefinery());
		ClientRegistry.bindTileEntitySpecialRenderer(TileMgTank.class,new TileRenderMgTank());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCombustionEngine.class,new TileRenderCombustionEngine());
		ClientRegistry.bindTileEntitySpecialRenderer(TileHeatSink.class,new TileRenderHeatSink());
		ClientRegistry.bindTileEntitySpecialRenderer(TileGrinder.class,new TileRenderGrinder());
		ClientRegistry.bindTileEntitySpecialRenderer(TileCrusher.class,new TileRenderCrusher());
		ClientRegistry.bindTileEntitySpecialRenderer(TilePolimerizer.class,new TileRenderPolimerizer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileTurbineControl.class,new TileRenderTurbine());
	}
}
