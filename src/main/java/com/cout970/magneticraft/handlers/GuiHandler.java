package com.cout970.magneticraft.handlers;

import com.cout970.magneticraft.client.gui.*;
import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.container.*;
import com.cout970.magneticraft.tileentity.*;
import com.cout970.magneticraft.tileentity.multiblock.*;
import com.cout970.magneticraft.tileentity.multiblock.controllers.*;
import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z) {
    	if(ID == 1){
    		return new ContainerGuideBook(player.inventory, null);
    	}
    	
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileElectricFurnace) {
            return new ContainerElectricFurnace(player.inventory, tile);
        }
        if (tile instanceof TileBattery) {
            return new ContainerBattery(player.inventory, tile);
        }
        if (tile instanceof TileBasicGenerator) {
            return new ContainerBasicGenerator(player.inventory, tile);
        }
        if (tile instanceof TileFireBox) {
            return new ContainerFireBox(player.inventory, tile);
        }
        if (tile instanceof TileBoiler) {
            return new ContainerBoiler(player.inventory, tile);
        }
        if (tile instanceof TileFluidHopper) {
            return new ContainerFluidHopper(player.inventory, tile);
        }
        if (tile instanceof TileWindTurbine) {
            return new ContainerWindMill(player.inventory, tile);
        }
        if (tile instanceof TileGeothermalPump) {
            return new ContainerGeothermalPump(player.inventory, tile);
        }
        if (tile instanceof TileSteamEngine) {
            return new ContainerSteamEngine(player.inventory, tile);
        }
        if (tile instanceof TileReactorVessel) {
            return new ContainerReactorVessel(player.inventory, tile);
        }
        if (tile instanceof TileCrusher) {
            return new ContainerCrusher(player.inventory, tile);
        }
        if (tile instanceof TileMB_Inv) {
            return new ContainerMB_Inv(player.inventory, tile);
        }
        if (tile instanceof TileMiner) {
            return new ContainerMiner(player.inventory, tile);
        }
        if (tile instanceof TileKineticGenerator) {
            return new ContainerKineticGenerator(player.inventory, tile);
        }
        if (tile instanceof TileBiomassBurner) {
            return new ContainerBiomassBurner(player.inventory, tile);
        }
        if (tile instanceof TileStirlingGenerator) {
            return new ContainerStirlingGenerator(player.inventory, tile);
        }
        if (tile instanceof TileGrinder) {
            return new ContainerGrinder(player.inventory, tile);
        }
        if (tile instanceof TileRefinery) {
            return new ContainerRefinery(player.inventory, tile);
        }
        if (tile instanceof TilePolymerizer) {
            return new ContainerPolymerizer(player.inventory, tile);
        }
        if (tile instanceof TileSteamTurbineControl) {
            return new ContainerTurbine(player.inventory, tile);
        }
        if (tile instanceof TileCombustionEngine) {
            return new ContainerCombustionEngine(player.inventory, tile);
        }
        if (tile instanceof TileBrickFurnace) {
            return new ContainerBrickFurnace(player.inventory, tile);
        }
        if (tile instanceof TileCrafter) {
            return new ContainerCrafter(player.inventory, tile);
        }
        if (tile instanceof TileTextMonitor) {
            return new ContainerMonitor(player.inventory, tile);
        }
        if (tile instanceof TileComputer) {
            return new ContainerComputer(player.inventory, tile);
        }
        if (tile instanceof TileDroidRED) {
            return new ContainerDroidRED(player.inventory, tile);
        }
        if (tile instanceof TileOilDistillery) {
            return new ContainerOilDistillery(player.inventory, tile);
        }
        if (tile instanceof TileInserter) {
            return new ContainerInserter(player.inventory, tile);
        }
        if (tile instanceof TileResistance) {
            return new ContainerResistance(player.inventory, tile);
        }
        if (tile instanceof TileThermopile) {
            return new ContainerThermopile(player.inventory, tile);
        }
        if (tile instanceof TileBreaker) {
            return new ContainerBreaker(player.inventory, tile);
        }
        if (tile instanceof TileShelvingUnit) {
            return new ContainerShelvingUnit(player.inventory, tile);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world,
                                      int x, int y, int z) {
    	
    	if(ID == 1){
    		return new GuiGuideBook(new ContainerGuideBook(player.inventory, null));
    	}
    	
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TileElectricFurnace) {
            return new GuiElectricFurnace(new ContainerElectricFurnace(player.inventory, tile), tile);
        }
        if (tile instanceof TileBattery) {
            return new GuiBattery(new ContainerBattery(player.inventory, tile), tile);
        }
        if (tile instanceof TileBasicGenerator) {
            return new GuiBasicGenerator(new ContainerBasicGenerator(player.inventory, tile), tile);
        }
        if (tile instanceof TileFireBox) {
            return new GuiFireBox(new ContainerFireBox(player.inventory, tile), tile);
        }
        if (tile instanceof TileBoiler) {
            return new GuiBoiler(new ContainerBoiler(player.inventory, tile), tile);
        }
        if (tile instanceof TileFluidHopper) {
            return new GuiFluidHopper(new ContainerFluidHopper(player.inventory, tile), tile);
        }
        if (tile instanceof TileWindTurbine) {
            return new GuiWindTurbine(new ContainerWindMill(player.inventory, tile), tile);
        }
        if (tile instanceof TileGeothermalPump) {
            return new GuiGeothermalPump(new ContainerGeothermalPump(player.inventory, tile), tile);
        }
        if (tile instanceof TileSteamEngine) {
            return new GuiSteamEngine(new ContainerSteamEngine(player.inventory, tile), tile);
        }
        if (tile instanceof TileReactorVessel) {
            return new GuiReactorVessel(new ContainerReactorVessel(player.inventory, tile), tile);
        }
        if (tile instanceof TileCrusher) {
            return new GuiCrusher(new ContainerCrusher(player.inventory, tile), tile);
        }
        if (tile instanceof TileMB_Inv) {
            return new GuiMB_Inv(new ContainerMB_Inv(player.inventory, tile), tile);
        }
        if (tile instanceof TileMiner) {
            return new GuiMiner(new ContainerMiner(player.inventory, tile), tile);
        }
        if (ManagerIntegration.COFH_ENERGY && (tile instanceof TileKineticGenerator)) {
            return new GuiKineticGenerator(new ContainerKineticGenerator(player.inventory, tile), tile);
        }
        if (tile instanceof TileBiomassBurner) {
            return new GuiBiomassBurner(new ContainerBiomassBurner(player.inventory, tile), tile);
        }
        if (tile instanceof TileStirlingGenerator) {
            return new GuiStirlingGenerator(new ContainerStirlingGenerator(player.inventory, tile), tile);
        }
        if (tile instanceof TileGrinder) {
            return new GuiGrinder(new ContainerGrinder(player.inventory, tile), tile);
        }
        if (tile instanceof TileRefinery) {
            return new GuiRefinery(new ContainerRefinery(player.inventory, tile), tile);
        }
        if (tile instanceof TilePolymerizer) {
            return new GuiPolymerizer(new ContainerPolymerizer(player.inventory, tile), tile);
        }
        if (tile instanceof TileSteamTurbineControl) {
            return new GuiSteamTurbine(new ContainerTurbine(player.inventory, tile), tile);
        }
        if (tile instanceof TileCombustionEngine) {
            return new GuiCombustionEngine(new ContainerCombustionEngine(player.inventory, tile), tile);
        }
        if (tile instanceof TileBrickFurnace) {
            return new GuiBrickFurnace(new ContainerBrickFurnace(player.inventory, tile), tile);
        }
        if (tile instanceof TileCrafter) {
            return new GuiCrafter(new ContainerCrafter(player.inventory, tile), tile);
        }
        if (tile instanceof TileTextMonitor) {
            return new GuiTextMonitor(new ContainerMonitor(player.inventory, tile), tile);
        }
        if (tile instanceof TileComputer) {
            return new GuiComputer(new ContainerComputer(player.inventory, tile), tile);
        }
        if (tile instanceof TileDroidRED) {
            return new GuiDroidRED(new ContainerDroidRED(player.inventory, tile), tile);
        }
        if (tile instanceof TileOilDistillery) {
            return new GuiOilDistillery(new ContainerOilDistillery(player.inventory, tile), tile);
        }
        if (tile instanceof TileInserter) {
            return new GuiInserter(new ContainerInserter(player.inventory, tile), tile);
        }
        if (tile instanceof TileResistance) {
            return new GuiResistance(new ContainerResistance(player.inventory, tile), tile);
        }
        if (tile instanceof TileThermopile) {
            return new GuiThermopile(new ContainerThermopile(player.inventory, tile), tile);
        }
        if (tile instanceof TileBreaker) {
            return new GuiBreaker(new ContainerBreaker(player.inventory, tile), tile);
        }
        if (tile instanceof TileShelvingUnit) {
            return new GuiShelvingUnit(new ContainerShelvingUnit(player.inventory, tile), tile);
        }
        return null;
    }

}
