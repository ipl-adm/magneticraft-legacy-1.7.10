package com.cout970.magneticraft.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cout970.magneticraft.client.gui.GuiBasicGenerator;
import com.cout970.magneticraft.client.gui.GuiBattery;
import com.cout970.magneticraft.client.gui.GuiBiomassBurner;
import com.cout970.magneticraft.client.gui.GuiBoiler;
import com.cout970.magneticraft.client.gui.GuiBrickFurnace;
import com.cout970.magneticraft.client.gui.GuiCPU;
import com.cout970.magneticraft.client.gui.GuiCombustionEngine;
import com.cout970.magneticraft.client.gui.GuiCrafter;
import com.cout970.magneticraft.client.gui.GuiCrusher;
import com.cout970.magneticraft.client.gui.GuiElectricFurnace;
import com.cout970.magneticraft.client.gui.GuiFireBox;
import com.cout970.magneticraft.client.gui.GuiFluidHopper;
import com.cout970.magneticraft.client.gui.GuiGeothermalPump;
import com.cout970.magneticraft.client.gui.GuiGrinder;
import com.cout970.magneticraft.client.gui.GuiKineticGenerator;
import com.cout970.magneticraft.client.gui.GuiMB_Inv;
import com.cout970.magneticraft.client.gui.GuiMiner;
import com.cout970.magneticraft.client.gui.GuiMonitor;
import com.cout970.magneticraft.client.gui.GuiPolimerizer;
import com.cout970.magneticraft.client.gui.GuiReactorVessel;
import com.cout970.magneticraft.client.gui.GuiRefinery;
import com.cout970.magneticraft.client.gui.GuiSteamEngine;
import com.cout970.magneticraft.client.gui.GuiStirlingGenerator;
import com.cout970.magneticraft.client.gui.GuiTurbine;
import com.cout970.magneticraft.client.gui.GuiWindMill;
import com.cout970.magneticraft.container.ContainerBasicGenerator;
import com.cout970.magneticraft.container.ContainerBattery;
import com.cout970.magneticraft.container.ContainerBiomassBurner;
import com.cout970.magneticraft.container.ContainerBoiler;
import com.cout970.magneticraft.container.ContainerBrickFurnace;
import com.cout970.magneticraft.container.ContainerCPU;
import com.cout970.magneticraft.container.ContainerCombustionEngine;
import com.cout970.magneticraft.container.ContainerCrafter;
import com.cout970.magneticraft.container.ContainerCrusher;
import com.cout970.magneticraft.container.ContainerElectricFurnace;
import com.cout970.magneticraft.container.ContainerFireBox;
import com.cout970.magneticraft.container.ContainerFluidHopper;
import com.cout970.magneticraft.container.ContainerGeothermalPump;
import com.cout970.magneticraft.container.ContainerGrinder;
import com.cout970.magneticraft.container.ContainerKineticGenerator;
import com.cout970.magneticraft.container.ContainerMB_Inv;
import com.cout970.magneticraft.container.ContainerMiner;
import com.cout970.magneticraft.container.ContainerMonitor;
import com.cout970.magneticraft.container.ContainerPolimerizer;
import com.cout970.magneticraft.container.ContainerReactorVessel;
import com.cout970.magneticraft.container.ContainerRefinery;
import com.cout970.magneticraft.container.ContainerSteamEngine;
import com.cout970.magneticraft.container.ContainerStirlingGenerator;
import com.cout970.magneticraft.container.ContainerTurbine;
import com.cout970.magneticraft.container.ContainerWindMill;
import com.cout970.magneticraft.tileentity.TileBasicGenerator;
import com.cout970.magneticraft.tileentity.TileBattery;
import com.cout970.magneticraft.tileentity.TileBiomassBurner;
import com.cout970.magneticraft.tileentity.TileBoiler;
import com.cout970.magneticraft.tileentity.TileBrickFurnace;
import com.cout970.magneticraft.tileentity.TileCPU;
import com.cout970.magneticraft.tileentity.TileCombustionEngine;
import com.cout970.magneticraft.tileentity.TileCrafter;
import com.cout970.magneticraft.tileentity.TileCrusher;
import com.cout970.magneticraft.tileentity.TileElectricFurnace;
import com.cout970.magneticraft.tileentity.TileFireBox;
import com.cout970.magneticraft.tileentity.TileFluidHopper;
import com.cout970.magneticraft.tileentity.TileGeothermalPump;
import com.cout970.magneticraft.tileentity.TileGrinder;
import com.cout970.magneticraft.tileentity.TileKineticGenerator;
import com.cout970.magneticraft.tileentity.TileMB_Inv;
import com.cout970.magneticraft.tileentity.TileMiner;
import com.cout970.magneticraft.tileentity.TileMonitor;
import com.cout970.magneticraft.tileentity.TilePolymerizer;
import com.cout970.magneticraft.tileentity.TileReactorVessel;
import com.cout970.magneticraft.tileentity.TileRefinery;
import com.cout970.magneticraft.tileentity.TileSteamEngine;
import com.cout970.magneticraft.tileentity.TileStirlingGenerator;
import com.cout970.magneticraft.tileentity.TileTurbineControl;
import com.cout970.magneticraft.tileentity.TileWindTurbine;

import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);

		if(tile instanceof TileElectricFurnace){
			return new ContainerElectricFurnace(player.inventory, tile);
		}
		if(tile instanceof TileBattery){
			return new ContainerBattery(player.inventory, tile);
		}
		if(tile instanceof TileBasicGenerator){
			return new ContainerBasicGenerator(player.inventory, tile);
		}
		if(tile instanceof TileFireBox){
			return new ContainerFireBox(player.inventory, tile);
		}
		if(tile instanceof TileBoiler){
			return new ContainerBoiler(player.inventory, tile);
		}
		if(tile instanceof TileFluidHopper){
			return new ContainerFluidHopper(player.inventory, tile);
		}
		if(tile instanceof TileWindTurbine){
			return new ContainerWindMill(player.inventory, tile);
		}
		if(tile instanceof TileGeothermalPump){
			return new ContainerGeothermalPump(player.inventory, tile);
		}
		if(tile instanceof TileSteamEngine){
			return new ContainerSteamEngine(player.inventory, tile);
		}
		if(tile instanceof TileReactorVessel){
			return new ContainerReactorVessel(player.inventory, tile);
		}
		if(tile instanceof TileCrusher){
			return new ContainerCrusher(player.inventory, tile);
		}
		if(tile instanceof TileMB_Inv){
			return new ContainerMB_Inv(player.inventory, tile);
		}
		if(tile instanceof TileMiner){
			return new ContainerMiner(player.inventory, tile);
		}
		if(tile instanceof TileKineticGenerator){
			return new ContainerKineticGenerator(player.inventory, tile);
		}
		if(tile instanceof TileBiomassBurner){
			return new ContainerBiomassBurner(player.inventory, tile);
		}
		if(tile instanceof TileStirlingGenerator){
			return new ContainerStirlingGenerator(player.inventory, tile);
		}
		if(tile instanceof TileGrinder){
			return new ContainerGrinder(player.inventory, tile);
		}
		if(tile instanceof TileRefinery){
			return new ContainerRefinery(player.inventory, tile);
		}
		if(tile instanceof TilePolymerizer){
			return new ContainerPolimerizer(player.inventory, tile);
		}
		if(tile instanceof TileTurbineControl){
			return new ContainerTurbine(player.inventory, tile);
		}
		if(tile instanceof TileCombustionEngine){
			return new ContainerCombustionEngine(player.inventory, tile);
		}
		if(tile instanceof TileBrickFurnace){
			return new ContainerBrickFurnace(player.inventory, tile);
		}
		if(tile instanceof TileCrafter){
			return new ContainerCrafter(player.inventory, tile);
		}
		if(tile instanceof TileMonitor){
			return new ContainerMonitor(player.inventory, tile);
		}
		if(tile instanceof TileCPU){
			return new ContainerCPU(player.inventory, tile);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world,
			int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);

		if(tile instanceof TileElectricFurnace){
			return new GuiElectricFurnace(new ContainerElectricFurnace(player.inventory, tile), tile);
		}
		if(tile instanceof TileBattery){
			return new GuiBattery(new ContainerBattery(player.inventory, tile), tile);
		}
		if(tile instanceof TileBasicGenerator){
			return new GuiBasicGenerator(new ContainerBasicGenerator(player.inventory, tile), tile);
		}
		if(tile instanceof TileFireBox){
			return new GuiFireBox(new ContainerFireBox(player.inventory, tile), tile);
		}
		if(tile instanceof TileBoiler){
			return new GuiBoiler(new ContainerBoiler(player.inventory, tile), tile);
		}
		if(tile instanceof TileFluidHopper){
			return new GuiFluidHopper(new ContainerFluidHopper(player.inventory, tile), tile);
		}
		if(tile instanceof TileWindTurbine){
			return new GuiWindMill(new ContainerWindMill(player.inventory, tile), tile);
		}
		if(tile instanceof TileGeothermalPump){
			return new GuiGeothermalPump(new ContainerGeothermalPump(player.inventory, tile), tile);
		}
		if(tile instanceof TileSteamEngine){
			return new GuiSteamEngine(new ContainerSteamEngine(player.inventory, tile), tile);
		}
		if(tile instanceof TileReactorVessel){
			return new GuiReactorVessel(new ContainerReactorVessel(player.inventory, tile), tile);
		}
		if(tile instanceof TileCrusher){
			return new GuiCrusher(new ContainerCrusher(player.inventory, tile), tile);
		}
		if(tile instanceof TileMB_Inv){
			return new GuiMB_Inv(new ContainerMB_Inv(player.inventory, tile), tile);
		}
		if(tile instanceof TileMiner){
			return new GuiMiner(new ContainerMiner(player.inventory, tile), tile);
		}
		if(tile instanceof TileKineticGenerator){
			return new GuiKineticGenerator(new ContainerKineticGenerator(player.inventory, tile), tile);
		}
		if(tile instanceof TileBiomassBurner){
			return new GuiBiomassBurner(new ContainerBiomassBurner(player.inventory, tile), tile);
		}
		if(tile instanceof TileStirlingGenerator){
			return new GuiStirlingGenerator(new ContainerStirlingGenerator(player.inventory, tile), tile);
		}
		if(tile instanceof TileGrinder){
			return new GuiGrinder(new ContainerGrinder(player.inventory, tile), tile);
		}
		if(tile instanceof TileRefinery){
			return new GuiRefinery(new ContainerRefinery(player.inventory, tile), tile);
		}
		if(tile instanceof TilePolymerizer){
			return new GuiPolimerizer(new ContainerPolimerizer(player.inventory, tile), tile);
		}
		if(tile instanceof TileTurbineControl){
			return new GuiTurbine(new ContainerTurbine(player.inventory, tile), tile);
		}
		if(tile instanceof TileCombustionEngine){
			return new GuiCombustionEngine(new ContainerCombustionEngine(player.inventory, tile), tile);
		}
		if(tile instanceof TileBrickFurnace){
			return new GuiBrickFurnace(new ContainerBrickFurnace(player.inventory, tile), tile);
		}
		if(tile instanceof TileCrafter){
			return new GuiCrafter(new ContainerCrafter(player.inventory, tile), tile);
		}
		if(tile instanceof TileMonitor){
			return new GuiMonitor(new ContainerMonitor(player.inventory, tile), tile);
		}
		if(tile instanceof TileCPU){
			return new GuiCPU(new ContainerCPU(player.inventory, tile), tile);
		}
		return null;
	}

}
