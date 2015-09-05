package com.cout970.magneticraft.items.block;

import static com.cout970.magneticraft.ManagerBlocks.*;
import static com.cout970.magneticraft.ManagerItems.*;

import java.util.ArrayList;
import java.util.List;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.NBTUtils;
import com.cout970.magneticraft.items.ItemBasic;
import com.cout970.magneticraft.items.ItemBattery;
import com.cout970.magneticraft.items.ItemFloppyDisk;
import com.cout970.magneticraft.items.ItemHardDrive;
import com.cout970.magneticraft.items.ItemHeatCoilCopper;
import com.cout970.magneticraft.items.ItemHeatCoilIron;
import com.cout970.magneticraft.items.ItemHeatCoilTungsten;
import com.cout970.magneticraft.items.ItemPartBase;
import com.cout970.magneticraft.items.ItemSmallBattery;
import com.cout970.magneticraft.items.ItemThoriumRod;
import com.cout970.magneticraft.items.ItemUraniumRod;
import com.cout970.magneticraft.parts.fluid.PartCopperPipe;
import com.cout970.magneticraft.parts.fluid.PartIronPipe;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class ManagerTooltip {

	public static String format = EnumChatFormatting.DARK_AQUA + "";
	public static String energy = EnumChatFormatting.GREEN + "";

	public static List<String> getTootip(ItemStack item, EntityPlayer player, boolean flag) {
		List<String> list = new ArrayList<String>();

		// I think this class should be moved to other package
		if (item.getItem() instanceof ItemBlockMg) {
			Block b = ((ItemBlockMg) item.getItem()).field_150939_a;
			if (b == airlock) {
				list.add(format + "Removes all nearby water blocks every 10 seconds");
				list.add(format + "Uses " + (int) EnergyConversor.RFtoW(10) + "J per block destroyed");
			} else if (b == basic_gen) {
				list.add(format + "Generates electricity from solid fuels or heat, needs water to work");
				list.add(format + "Produces up to 400W using " + (EnergyConversor
						.FUELtoCALORIES(4) / 1000) + " kcal/t");
			} else if (b == battery) {
				list.add(format + "Stores up to " + (int) EnergyConversor.RFtoW(2000000) + "J");
				list.add(format + "Keeps energy when broken");
			} else if (b == biomass_burner) {
				list.add(
						format + "Generates heat from biomass, such as wood, saplings, flowers, or some types of plants");
				list.add(format + "Produces up tp " + (EnergyConversor.RFtoCALORIES(40) / 1000) + " kcal/t");
			} else if (b == boiler) {
				list.add(format + "Generates steam from heat and water");
				list.add(format + "Produces up to 80mB using " + (EnergyConversor
						.WtoCALORIES(EnergyConversor.STEAMtoW(80)) / 1000) + " kcal/t");
			} else if (b == breaker) {
				list.add(format + "Breaks up to 16 blocks in a straight line");
				list.add(format + "Uses " + EnergyConversor.RFtoW(500) / 1000 + "kW per block mined");
			} else if (b == brickFurnace) {
				list.add(format + "A furnace fueled by heat. Speed proportional to temperature");
			} else if (b == combustion_engine) {
				list.add(format + "Generates electricity from liquid fuel, power depends on fuel type");
				list.add(format + "Heat generated during work slows engine down if not removed");
			} else if (b == cpu) {
				list.add(
						format + "[WIP] Fully implemented MIPS emulator, needs a MIPS CPU, a RAM module and a ROM modules");
			} else if (b == conveyor_l) {
				list.add(format + "Used to move objects");
				list.add(format + "Right-click to put an item on the belt or use an Inserter");
			} else if (b == cooler) {
				list.add(format + "Dissipates heat, work better with high temperatures");
			} else if (b == copper_tank) {
				list.add(format + "Stores up to 16000 mB of fluid, keeps the fluid when is picked up");
			} else if (b == crafter) {
				list.add(format + "Automatically crafts items using given recipe");
				list.add(format + "Uses items from internal inventory as well as adjacent inventories");
			} else if (b == crusher) {
				list.add(format + "Multiblock Structure, place and right-click to see the blueprint");
				list.add(format + "Crushes ores, increasing yield by several times");
			} else if (b == diode) {
				list.add(format + "Restricts current flow direction");
			} else if (b == droid_red) {
				list.add(format + "[WIP]");
			} else if (b == furnace) {
				list.add(format + "A furnace fueled by electricity. Requires heating coil to work");
			} else if (b == pole_cable_wire) {
				list.add(format + "Connects cables with electrical poles");
			} else if (b == pole_tier1) {
				list.add(format + "Cooler cables. Use Heavy Copper Coil to connect poles");
				list.add(format + "Shift-right-click with a wrench to change connection mode");
			} else if (b == electric_switch) {
				list.add(format + "Stops energy flow when redstone signal is applied");
			} else if (b == eu_alternator) {
				list.add(format + "Generates electricity from EU");
			} else if (b == firebox) {
				list.add(format + "Generate heat from solid fuel");
				list.add(format + "Produces up tp " + (EnergyConversor.RFtoCALORIES(40) / 1000) + " kcal/t");
			} else if (b == fluidhopper) {
				list.add(format + "Transfers fluids from one tank to another through an internal buffer");
				list.add(format + "Can be used to fill or empty buckets");
			} else if (b == geothermal) {
				list.add(format + "Pumps lava and uses it to generate heat");
				list.add(format + "Will mine blocks on its way to lava");
				list.add(format + "Produces " + EnergyConversor.FUELtoCALORIES(12) / 1000 + " kcal/t");
			} else if (b == grinder) {
				list.add(format + "Multiblock Structure, place and right-click to see the blueprint");
				list.add(format + "Grinds objects for further processing or any other use");
			} else if (b == heater) {
				list.add(format + "Generates heat from electricity");
			} else if (b == heat_resist) {
				list.add(format + "Slows heat transfer");
			} else if (b == heat_sink) {
				list.add(
						format + "Dissipates heat. Works better than Cooler at lower temperatures, but melts at much lower temperature");
			} else if (b == infinite_energy) {
				list.add(format + "Creative only");
			} else if (b == infinite_steam) {
				list.add(format + "Supplies adjacent blocks with steam");
				list.add(format + "Creative only");
			} else if (b == infinite_water) {
				list.add(format + "Supplies adjacent blocks with water");
			} else if (b == inserter) {
				list.add(format + "Moves items from conveyor belts and inventories");
			} else if (b == kinetic) {
				list.add(format + "Generates Redstone Flux from electricity");
				list.add(format + "Produces up to 400 RF/t, uses medium voltage");
			} else if (b == miner) {
				list.add(
						format + "Mines blocks and puts them into adjacent inventories" + (Magneticraft.BUILDCRAFT ? " or Transport Pipes" : ""));
				list.add(format + "Uses medium voltage. Mining speed scales with exact voltage");
				list.add(format + "Uses " + (int) EnergyConversor.RFtoW(500) + "J per block mined");
			} else if (b == mirror) {
				list.add(
						format + "Focuses sunlight onto a Solar Tower Core. Use Position Map to bind mirrors to the Core");
				list.add(format + "Every mirror generates " + EnergyConversor
						.RFtoCALORIES(2) / 1000 + " kcal/t if nothing is blocking the sunlight");
			} else if (b == monitor) {
				list.add(format + "[WIP] A means of interacting with adjacent computer");
			} else if (b == oil_distillery) {
				list.add(format + "Multiblock Structure, place and right-click to see the blueprint");
				list.add(format + "Uses electricity to heat crude oil, preparing it for refining");
			} else if (b == permagnet) {
				list.add(format + "Stops Block Breaker from breaking anything beyond the magnet");
			} else if (b == polimerizer) {
				list.add(format + "Multiblock Structure, place and right-click to see the blueprint");
				list.add(format + "Creates new materials using electricity and Natural Gas");
			} else if (b == pumpJack) {
				list.add(
						format + "Extracts oil from oil deposits. Requires electricity to operate. Use Oil Prospector to find deposits");
			} else if (b == rc_alternator) {
				list.add(format + "Generates electricity from RailCraft Charge");
			} else if (b == reactor_vessel) {
				list.add(format + "[WIP] Part of a nuclear reactor");
			} else if (b == reactor_activator) {
				list.add(format + "[WIP] Part of a nuclear reactor");
			} else if (b == reactor_wall) {
				list.add(format + "[WIP] Part of a nuclear reactor");
			} else if (b == reactor_control_rods) {
				list.add(format + "[WIP] Part of a nuclear reactor");
			} else if (b == reactor_controller) {
				list.add(format + "[WIP] Part of a nuclear reactor");
			} else if (b == refinery) {
				list.add(format + "Multiblock Structure, place and right-click to see the blueprint");
			} else if (b == resistance) {
				list.add(format + "Limit the energy flow");
			} else if (b == rf_alternator) {
				list.add(format + "Generates electrcity using Redstone Flux");
			} else if (b == sifter) {
				list.add(format + "Multiblock Structure, place and right-click to see the blueprint");
			} else if (b == solarpanel) {
				list.add(format + "Generates " + (int) EnergyConversor.RFtoW(5) + "W");
			} else if (b == solar_tower_core) {
				list.add(
						format + "Concentrates heat from nearby mirrors. Use Position Map to orient mirrors");
			} else if (b == steam_engine) {
				list.add(format + "Generates electricity from steam");
				list.add(format + "Can consume up to 40mB/t and generate up to " + EnergyConversor
						.STEAMtoW(40) / 1000 + " kW");
			} else if (b == steam_turbine) {
				list.add(format + "Multiblock Structure, place and right-click to see the blueprint");
				list.add(format + "Generates electricity from steam");
				list.add(format + "Can produce up to " + (EnergyConversor.STEAMtoW(1200) / 1000) + "kW");
			} else if (b == stirling) {
				list.add(format + "Multiblock Structure, place and right-click to see the blueprint");
				list.add(format + "Generates electricity and heat from solid fuel");
				list.add(format + "Can produce up to " + 1.2F + " kW");
			} else if (b == tesla_coil) {
				list.add(format + "Charges items in nearby players' inventories");
			} else if (b == thermopile) {
				list.add(format + "Generates electricity from temperature difference of adjacent blocks");
			} else if (b == transformer_lm) {
				list.add(format + "Transforms voltage from low to medium and vice versa");
			} else if (b == transformer_mh) {
				list.add(format + "Transforms voltage from medium to high and vice versa");
			} else if (b == void_inv) {
				list.add(format + "Creative Only");
				list.add(format + "Destroys items put into it");
			} else if (b == windturbine) {
				list.add(format + "Uses wind to generate electricity. Power depends on a turbine used");
			} else if (b == wooden_shaft) {
				list.add(format + "[WIP]");
			}
		}else if(item.getItem() instanceof ItemBasic){
			Item i = item.getItem();
			if(i == floppy_disk){
				list.add(format + (int) (ItemFloppyDisk.DISK_SIZE / 1024) + "kB of storage");
				if(((ItemFloppyDisk) i).getDiskLabel(item) != null && !((ItemFloppyDisk) i).getDiskLabel(item).equals(""))
				list.add(format + "Name: " + ((ItemFloppyDisk) i).getDiskLabel(item));
			}else if(i == hard_drive){
				list.add(format + (int) (ItemHardDrive.DISK_SIZE / 1024) + "kB of storage");
				if(((ItemHardDrive) i).getDiskLabel(item) != null && !((ItemHardDrive) i).getDiskLabel(item).equals(""))
		        list.add(format + "Name: " + ((ItemHardDrive) i).getDiskLabel(item));
			}else if(i == heatCoilCopper){
				list.add(format + "Makes the electric furnace work 2 times faster than a normal furnace, uses " + ((ItemHeatCoilCopper) i).getElectricConsumption() + "W");
			}else if(i == heatCoilIron){
				list.add(format + "Makes the electric furnace work 4 times faster than a normal furnace, uses " + ((ItemHeatCoilIron) i).getElectricConsumption() + "W");
			}else if(i == heatCoilTungsten){
				list.add(format + "Makes the electric furnace work 8 times faster than a normal furnace, uses " + ((ItemHeatCoilTungsten) i).getElectricConsumption() + "W");
			}else if(i == heavy_copper_coil){
				list.add(format + "Used to manually connect two poles");
		        if (NBTUtils.getBoolean("Connected", item)) {
		            int x, y, z;
		            x = NBTUtils.getInteger("xCoord", item);
		            y = NBTUtils.getInteger("yCoord", item);
		            z = NBTUtils.getInteger("zCoord", item);
		            list.add("Linked to: " + x + ", " + y + ", " + z);
		        }
			}else if(i == map_positioner){
				list.add(format + "Orients mirrors to a Solar Tower Core, shift-click to apply to all mirrors in 10x10 area");
			}else if(i == chip_cpu_mips){
				list.add(format + "[WIP]");
			}else if(i == chip_ram){
				list.add(format + "[WIP]");
			}else if(i == chip_rom){
				list.add(format + "[WIP]");
			}else if(i == thermometer){
				list.add(format + "Used to measure temperature");
			}else if(i == thorium_rod){
				list.add(format + String.format("%.2f", NBTUtils.getDouble(ItemThoriumRod.NBT_GRAMS_NAME, item)) + "/" + ItemThoriumRod.INITIAL_NUMBER_OF_GRAMES);
		        list.add(format + "[WIP]");
			}else if(i == uranium_rod){
				list.add(format + String.format("%.2f", NBTUtils.getDouble(ItemUraniumRod.NBT_GRAMS_NAME, item)) + "/" + ItemUraniumRod.INITIAL_NUMBER_OF_GRAMES);
		        list.add(format + "[WIP]");
			}else if(i == turbine_0 || i == turbine_1 || i == turbine_2){
				list.add(format + "Used by Wind Turbine to generate electricity");
			}else if(i == voltmeter){
				list.add(format + "Used to measure voltage and current flowing through an electric conductor");
			}else if(i == tool_charger){
				list.add(format + "Charges tools in the inventory using energy from batteries");
			}else if(i == small_battery){
				list.add(format + (int) EnergyConversor.WtoRF(((ItemSmallBattery) i).getCharge(item)) + "/" + (int) EnergyConversor.WtoRF(((ItemSmallBattery) i).getMaxCharge(item)) + " RF");
			}else if(i == oil_prospector){
				list.add(format + "Looks for underground oil deposits in 3x3 area");
			}else if(i == battery_item){
				list.add(format + (int) EnergyConversor.WtoRF(((ItemBattery) i).getCharge(item)) + "/" + (int) EnergyConversor.WtoRF(((ItemBattery) i).getMaxCharge(item)) + " RF");
			}
		}else if(item.getItem() instanceof ItemPartBase){
			Item i = item.getItem();
			if(i == part_optic_fiber){
				list.add(format + "[WIP]");
			}else if(i == part_iron_pipe){
				list.add(format + "Transfers fluids. Right-click connection with a wrench to change mode");
		        list.add(format + "Can transfer " + PartIronPipe.MAX_EXTRACT + "mB/t per side");
			}else if(i == part_copper_pipe){
				list.add(format + "Transfers fluids. Right-click connection with a wrench to change mode");
		        list.add(format + "Can transfer " + PartCopperPipe.MAX_EXTRACT + "mB/t per side");
			}else if(i == part_copper_wire){
				list.add(format + "Has a resistance of " + ElectricConstants.RESISTANCE_COPPER_LOW + " Omhs");
			}else if(i == part_copper_cable_low){
				list.add(format + "Has a resistance of " + ElectricConstants.RESISTANCE_COPPER_LOW + " Omhs");
			}else if(i == part_copper_cable_medium){
				list.add(format + "Has a resistance of " + ElectricConstants.RESISTANCE_COPPER_MED + " Omhs");
			}else if(i == part_copper_cable_high){
				list.add(format + "Has a resistance of " + ElectricConstants.RESISTANCE_COPPER_HIGH + " Omhs");
			}
		}
		return list;
	}
}
