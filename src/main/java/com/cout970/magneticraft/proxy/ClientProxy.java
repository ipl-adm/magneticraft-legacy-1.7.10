package com.cout970.magneticraft.proxy;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.client.itemrenderer.*;
import com.cout970.magneticraft.client.tilerender.*;
import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.handlers.TooltipHandler;
import com.cout970.magneticraft.tileentity.*;
import com.cout970.magneticraft.tileentity.multiblock.controllers.*;
import com.cout970.magneticraft.tileentity.pole.TileElectricPoleCableWire;
import com.cout970.magneticraft.tileentity.pole.TileElectricPoleTier1;
import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.OutdatedJavaException;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;


public class ClientProxy implements IProxy {

    @Override
    public void init() {
        if (!SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_8)) {
            Log.info("Java is outdated, throwing exception");
            throw new OutdatedJavaException();
        }

        MinecraftForge.EVENT_BUS.register(new TooltipHandler());

        MinecraftForgeClient.registerItemRenderer(ManagerItems.part_copper_cable_low, new ItemRenderCableLow());
        MinecraftForgeClient.registerItemRenderer(ManagerItems.part_copper_cable_medium, new ItemRenderCableMedium());
        MinecraftForgeClient.registerItemRenderer(ManagerItems.part_copper_cable_high, new ItemRenderCableHigh());
        MinecraftForgeClient.registerItemRenderer(ManagerItems.part_copper_pipe, new ItemRenderCopperPipe());
        MinecraftForgeClient.registerItemRenderer(ManagerItems.part_iron_pipe, new ItemRenderIronPipe());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.pumpJack), new ItemRenderPumpJack());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.transformer_lm), new ItemRenderTransformerLM());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.fluidhopper), new ItemRenderFluidHopper());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.steam_engine), new ItemRenderSteamEngine());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.reactor_vessel), new ItemRenderReactorVessel());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.reactor_control_rods), new ItemRenderReactorControl());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.reactor_activator), new ItemRenderReactorActivator());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.transformer_mh), new ItemRenderTransformerMH());
        MinecraftForgeClient.registerItemRenderer(ManagerItems.partheatcable, new ItemRenderHeatCable());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.inserter), new ItemRenderInserter());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.tesla_coil), new ItemRenderTeslaCoil());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.heat_sink), new ItemRenderHeatSink());
        MinecraftForgeClient.registerItemRenderer(ManagerItems.part_copper_wire, new ItemRenderWireCopper());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.combustion_engine), new ItemRenderCombustionEngine());
        MinecraftForgeClient.registerItemRenderer(ManagerItems.tool_sword, new ItemRenderElectricSword());
        MinecraftForgeClient.registerItemRenderer(ManagerItems.tool_chainsaw, new ItemRenderChainsaw());
        MinecraftForgeClient.registerItemRenderer(ManagerItems.tool_jackhammer, new ItemRenderJackHammer());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.conveyor_l), new ItemRenderConveyorLow());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.monitor), new ItemRenderMonitor());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.pole_tier1), new ItemRenderElectricPoleTier1());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.droid_red), new ItemRenderDroidRED());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.hand_crank_gen), new ItemRenderHandCrank());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.wooden_shaft), new ItemRenderWoodenShaft());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.diode), new ItemRenderDiode());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.resistance), new ItemRenderResistance());
        if (ManagerIntegration.COFH_ENERGY) {
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.rf_alternator), new ItemRenderRFAlternator());
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.kinetic), new ItemRenderKineticGenerator());
        }
        if (ManagerIntegration.IC2) {
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.eu_alternator), new ItemRenderEUAlternator());
        }
        if (ManagerIntegration.RAILCRAFT) {
            MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.rc_alternator), new ItemRenderRCAlternator());
        }
        if (Magneticraft.DEBUG) {
        }
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.copper_tank), new ItemRenderCopperTank());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.pole_cable_wire), new ItemRenderPoleCableWire());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.crushing_table), new ItemRenderHammerTable());
        MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ManagerBlocks.shelving_unit), new ItemRenderShelvingUnit());

        ClientRegistry.bindTileEntitySpecialRenderer(TilePumpJack.class, new TileRenderPumpjack());
        ClientRegistry.bindTileEntitySpecialRenderer(TileMirror.class, new TileRenderMirror());
        ClientRegistry.bindTileEntitySpecialRenderer(TileWindTurbine.class, new TileRenderWindTurbine());
        ClientRegistry.bindTileEntitySpecialRenderer(TileFluidHopper.class, new TileRenderFluidHopper());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSteamEngine.class, new TileRenderSteamEngine());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTransformerLow_Medium.class, new TileRenderTransformerLow_Medium());
        ClientRegistry.bindTileEntitySpecialRenderer(TileReactorVessel.class, new TileRenderReactorVessel());
        ClientRegistry.bindTileEntitySpecialRenderer(TileReactorControlRods.class, new TileRenderReactorControl());
        ClientRegistry.bindTileEntitySpecialRenderer(TileInserter.class, new TileRenderInserter());
        ClientRegistry.bindTileEntitySpecialRenderer(TileReactorActivator.class, new TileRenderReactorActivator());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTransformerMedium_High.class, new TileRenderTransformer_MH());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTeslaCoil.class, new TileRenderTeslaCoil());
        ClientRegistry.bindTileEntitySpecialRenderer(TileConveyorBelt.class, new TileRenderConveyorBelt());
        ClientRegistry.bindTileEntitySpecialRenderer(TileRefinery.class, new TileRenderRefinery());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCopperTank.class, new TileRenderCopperTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCombustionEngine.class, new TileRenderCombustionEngine());
        ClientRegistry.bindTileEntitySpecialRenderer(TileHeatSink.class, new TileRenderHeatSink());
        ClientRegistry.bindTileEntitySpecialRenderer(TileGrinder.class, new TileRenderGrinder());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrusher.class, new TileRenderCrusher());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePolymerizer.class, new TileRenderPolymerizer());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSteamTurbineControl.class, new TileRenderTurbine());
        ClientRegistry.bindTileEntitySpecialRenderer(TileStirlingGenerator.class, new TileRenderStirling());
        ClientRegistry.bindTileEntitySpecialRenderer(TileTextMonitor.class, new TileRenderMonitor());
        ClientRegistry.bindTileEntitySpecialRenderer(TileElectricPoleTier1.class, new TileRenderElectricPoleTier1());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDroidRED.class, new TileRenderDroidRED());
        ClientRegistry.bindTileEntitySpecialRenderer(TileOilDistillery.class, new TileRenderOilDistillery());
        ClientRegistry.bindTileEntitySpecialRenderer(TileGrindingMill.class, new TileRenderGrindingMill());
        ClientRegistry.bindTileEntitySpecialRenderer(TileWoodenShaft.class, new TileRenderWoodenShaft());
        ClientRegistry.bindTileEntitySpecialRenderer(TileHandCrankGenerator.class, new TileRenderHandCrank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileDiode.class, new TileRenderDiode());
        ClientRegistry.bindTileEntitySpecialRenderer(TileResistance.class, new TileRenderResistance());
        if (ManagerIntegration.COFH_ENERGY) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileRFAlternator.class, new TileRenderRFAlternator());
            ClientRegistry.bindTileEntitySpecialRenderer(TileKineticGenerator.class, new TileRenderKineticGenerator());
        }
        if (ManagerIntegration.IC2) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileEUAlternator.class, new TileRenderEUAlternator());
        }
        if (ManagerIntegration.RAILCRAFT) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileRCAlternator.class, new TileRenderRCAlternator());
        }
        if (Magneticraft.DEBUG) {
            ClientRegistry.bindTileEntitySpecialRenderer(TileSprinkler.class, new TileRenderSprinkler());
        }
        ClientRegistry.bindTileEntitySpecialRenderer(TileElectricPoleCableWire.class, new TileRenderPoleCableWire());
        ClientRegistry.bindTileEntitySpecialRenderer(TileSifter.class, new TileRenderSifter());
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrushingTable.class, new TileRenderCrushingTable());
        ClientRegistry.bindTileEntitySpecialRenderer(TilePressureTank.class, new TileRenderPressureTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileShelvingUnit.class, new TileRenderShelvingUnit());
    }
}
