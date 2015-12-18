package com.cout970.magneticraft.items.block;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.NBTUtils;
import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.items.*;
import com.cout970.magneticraft.parts.fluid.PartCopperPipe;
import com.cout970.magneticraft.parts.fluid.PartIronPipe;
import com.cout970.magneticraft.tileentity.TileMiner;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

import static com.cout970.magneticraft.ManagerBlocks.*;
import static com.cout970.magneticraft.ManagerItems.*;

public class ManagerTooltip {

	public static String format = EnumChatFormatting.DARK_AQUA + "";
	public static String energy = EnumChatFormatting.GREEN + "";

	public static List<String> getTootip(ItemStack item, EntityPlayer player, boolean flag) {
		List<String> list = new ArrayList<>();

		// I think this class should be moved to other package
		if (item.getItem() instanceof ItemBlockMg) {
			Block b = ((ItemBlockMg) item.getItem()).field_150939_a;
			if (b == airlock) {
				list.add(format + "Removes all nearby water blocks every 10 seconds");
				list.add(format + "Uses " + (int) EnergyConverter.RFtoW(10) + "J per block destroyed");
			} else if (b == basic_gen) {
				list.add(format + "Generates electricity from solid fuels ");
				list.add(format + "or heat, needs water to work");
				list.add(format + "Produces up to 400W using " + (EnergyConverter
						.FUELtoCALORIES(4) / 1000) + " kcal/t");
			} else if (b == battery) {
				list.add(format + "Stores up to " + (int) EnergyConverter.RFtoW(2000000) + "J");
				list.add(format + "Keeps energy when broken");
			} else if (b == biomass_burner) {
				list.add(format + "Generates heat from biomass, such as wood,");
				list.add(format + " saplings, flowers, or some types of plants");
				list.add(format + "Produces up to " + (EnergyConverter.RFtoCALORIES(40) / 1000) + " kcal/t");
			} else if (b == boiler) {
				list.add(format + "Generates steam from heat and water");
				list.add(format + "Produces up to 80mB using " + (EnergyConverter
						.WtoCALORIES(EnergyConverter.STEAMtoW(80)) / 1000) + " kcal/t");
			} else if (b == breaker) {
				list.add(format + "Breaks up to 16 blocks in a straight line");
				list.add(format + "Uses " + EnergyConverter.RFtoW(500) / 1000 + "kW per block mined");
			} else if (b == brickFurnace) {
				list.add(format + "A furnace fueled by heat. Speed proportional to temperature");
			} else if (b == combustion_engine) {
				list.add(format + "Generates electricity from liquid fuel, ");
				list.add(format + "power depends on fuel type");
				list.add(format + "Heat generated during work slows ");
				list.add(format + "engine down if not removed");
			} else if (b == cpu) {
				list.add(format + "[WIP] Fully implemented MIPS emulator, ");
				list.add(format + "needs a MIPS CPU, a RAM module and a ROM modules");
			} else if (b == conveyor_l) {
				list.add(format + "Used to move objects");
				list.add(format + "Right-click to put an item on the");
				list.add(format + " belt or use an Inserter");
			} else if (b == cooler) {
				list.add(format + "Dissipates heat, work better ");
				list.add(format + "with high temperatures");
			} else if (b == copper_tank) {
				list.add(format + "Stores up to 16000 mB of a fluid,");
				list.add(format + "keeps its contents when picked up");
			} else if (b == crafter) {
				list.add(format + "Automatically crafts items using given recipe");
				list.add(format + "Uses items from internal inventory ");
				list.add(format + "as well as adjacent inventories");
			} else if (b == crusher) {
				list.add(format + "Multiblock Structure, place and right-click");
				list.add(format + "to see the blueprint");
				list.add(format + "Crushes ores, increasing yield by several times");
			} else if (b == diode) {
				list.add(format + "Restricts current flow direction");
			} else if (b == droid_red) {
				list.add(format + "[WIP]");
			} else if (b == furnace) {
				list.add(format + "A furnace fueled by electricity");
				list.add(format + "Requires heating coil to work");
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
				list.add(format + "Produces up tp " + (EnergyConverter.RFtoCALORIES(60) / 1000) + " kcal/t");
			} else if (b == fluidhopper) {
				list.add(format + "Transfers fluids from one tank to ");
				list.add(format + "another through an internal buffer");
				list.add(format + "Can be used to fill or empty buckets");
			} else if (b == geothermal) {
				list.add(format + "Pumps lava and uses it to generate heat");
				list.add(format + "Will mine blocks on its way to lava");
				list.add(format + "Produces " + EnergyConverter.FUELtoCALORIES(12) / 1000 + " kcal/t");
			} else if (b == grinder) {
				list.add(format + "Multiblock Structure, place and right-click");
				list.add(format + "to see the blueprint");
				list.add(format + "Grinds objects for further processing or any other use");
			} else if (b == heater) {
				list.add(format + "Generates heat from electricity");
			} else if (b == heat_resist) {
				list.add(format + "Slows heat transfer");
			} else if (b == heat_sink) {
				list.add(format + "Dissipates heat. Works better than Cooler at ");
				list.add(format + "lower temperatures, but melts at much lower temperature");
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
						format + "Mines blocks and puts them into adjacent inventories" + (ManagerIntegration.BUILDCRAFT ? " or Transport Pipes" : ""));
				list.add(format + "Uses medium voltage. Mining speed scales with exact voltage");
				list.add(format + "Uses " + (int) EnergyConverter.RFtoW(TileMiner.MINING_COST_PER_BLOCK) + "J per block mined");
			} else if (b == mirror) {
				list.add(format + "Focuses sunlight onto a Solar Tower Core");
				list.add(format + "Use Position Map to bind mirrors to the Core");
				list.add(format + "Every mirror generates " +
						EnergyConverter.RFtoCALORIES(2) / 1000 + " kcal/t if nothing is blocking the sunlight");
			} else if (b == monitor) {
				list.add(format + "[WIP] A means of interacting with adjacent computer");
			} else if (b == oil_distillery) {
				list.add(format + "Multiblock Structure, place and right-click ");
				list.add(format + "to see the blueprint");
				list.add(format + "Uses electricity to heat crude oil, preparing it for refining");
			} else if (b == permagnet) {
				list.add(format + "Stops Block Breaker from breaking ");
				list.add(format + "anything beyond the magnet");
			} else if (b == polimerizer) {
				list.add(format + "Multiblock Structure, place and right-click");
				list.add(format + "to see the blueprint");
				list.add(format + "Creates new materials using electricity and Natural Gas");
			} else if (b == pumpJack) {
				list.add(format + "Extracts oil from oil deposits");
				list.add(format + "Requires electricity to operate");
				list.add(format + "Use Oil Prospector to find deposits");
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
				list.add(format + "Multiblock Structure, place and right-click ");
				list.add(format + "to see the blueprint");
			} else if (b == resistance) {
				list.add(format + "Limit the energy flow");
			} else if (b == rf_alternator) {
				list.add(format + "Generates electricity using Redstone Flux");
			} else if (b == sifter) {
				list.add(format + "Multiblock Structure, place and right-click ");
				list.add(format + "to see the blueprint");
			} else if (b == solarpanel) {
				list.add(format + "Generates " + (int) EnergyConverter.RFtoW(5) + "W");
			} else if (b == solar_tower_core) {
				list.add(format + "Concentrates heat from nearby mirrors");
				list.add(format + "Use Position Map to orient mirrors");
			} else if (b == steam_engine) {
				list.add(format + "Generates electricity from steam");
				list.add(format + "Can consume up to 40mB/t and generate up to " + EnergyConverter
						.STEAMtoW(40) / 1000 + " kW");
			} else if (b == steam_turbine) {
				list.add(format + "Multiblock Structure, place and right-click to see the blueprint");
				list.add(format + "Generates electricity from steam");
				list.add(format + "Can produce up to " + (EnergyConverter.STEAMtoW(1200) / 1000) + "kW");
			} else if (b == stirling) {
				list.add(format + "Multiblock Structure, place and right-click ");
				list.add(format + "to see the blueprint");
				list.add(format + "Generates electricity and heat from solid fuel");
				list.add(format + "Can produce up to " + 1.2F + " kW");
			} else if (b == tesla_coil) {
				list.add(format + "Charges items in nearby players' inventories");
			} else if (b == thermopile) {
				list.add(format + "Generates electricity from temperature ");
				list.add(format + "difference of adjacent blocks");
			} else if (b == transformer_lm) {
				list.add(format + "Transforms voltage from low to medium and vice versa");
			} else if (b == transformer_mh) {
				list.add(format + "Transforms voltage from medium to high and vice versa");
			} else if (b == void_inv) {
				list.add(format + "Creative Only");
				list.add(format + "Destroys items put into it");
			} else if (b == windturbine) {
				list.add(format + "Uses wind to generate electricity. ");
				list.add(format + "Power depends on the turbine used");
			} else if (b == wooden_shaft) {
				list.add(format + "[WIP]");
			} else if (b == shelving_unit) {
				list.add(format + "Item storage of variable size.");
				list.add(format + "Right-click with chest to add space.");
				list.add(format + "Shift-right-click with empty hand ");
				list.add(format + "to remove chest from shelf.");
			}
		}else if(item.getItem() instanceof ItemBasic){
			Item i = item.getItem();
			if(i == floppy_disk){
				list.add(format + ItemFloppyDisk.DISK_SIZE / 1024 + "kB of storage");
				if(((ItemFloppyDisk) i).getDiskLabel(item) != null && !((ItemFloppyDisk) i).getDiskLabel(item).equals(""))
				list.add(format + "Name: " + ((ItemFloppyDisk) i).getDiskLabel(item));
			}else if(i == hard_drive){
				list.add(format + ItemHardDrive.DISK_SIZE / 1024 + "kB of storage");
				if(((ItemHardDrive) i).getDiskLabel(item) != null && !((ItemHardDrive) i).getDiskLabel(item).equals(""))
		        list.add(format + "Name: " + ((ItemHardDrive) i).getDiskLabel(item));
			}else if(i == heatCoilCopper){
				list.add(format + "Makes the electric furnace work 2 times");
				list.add(format + "faster than a normal furnace, uses " + ((ItemHeatCoilCopper) i).getElectricConsumption() + "W");
			}else if(i == heatCoilIron){
				list.add(format + "Makes the electric furnace work 4 times");
				list.add(format + "faster than a normal furnace, uses " + ((ItemHeatCoilIron) i).getElectricConsumption() + "W");
			}else if(i == heatCoilTungsten){
				list.add(format + "Makes the electric furnace work 8 times");
				list.add(format + "faster than a normal furnace, uses " + ((ItemHeatCoilTungsten) i).getElectricConsumption() + "W");
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
				list.add(format + "Orients mirrors to a Solar Tower Core");
				list.add(format + "shift-click to apply to all mirrors in 10x10 area");
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
				list.add(format + "Used to measure voltage and current ");
				list.add(format + "flowing through an electric conductor");
			}else if(i == tool_charger){
				list.add(format + "Charges tools in the inventory ");
				list.add(format + "using energy from batteries");
			}else if(i == small_battery){
				list.add(format + (int) EnergyConverter.WtoRF(((ItemSmallBattery) i).getCharge(item)) + "/" + (int) EnergyConverter.WtoRF(((ItemSmallBattery) i).getMaxCharge(item)) + " RF");
			}else if(i == oil_prospector){
				list.add(format + "Looks for underground oil deposits in 3x3 area");
			}else if(i == battery_item){
				list.add(format + (int) EnergyConverter.WtoRF(((ItemBattery) i).getCharge(item)) + "/" + (int) EnergyConverter.WtoRF(((ItemBattery) i).getMaxCharge(item)) + " RF");
			}
		}else if(item.getItem() instanceof ItemPartBase){
			Item i = item.getItem();
			if(i == part_optic_fiber){
				list.add(format + "[WIP]");
			}else if(i == part_iron_pipe){
				list.add(format + "Transfers fluids. ");
				list.add(format + "Right-click connection with a wrench to change mode");
		        list.add(format + "Can transfer " + PartIronPipe.MAX_EXTRACT + "mB/t per side");
			}else if(i == part_copper_pipe){
				list.add(format + "Transfers fluids. Right-click connection with a wrench to change mode");
		        list.add(format + "Can transfer " + PartCopperPipe.MAX_EXTRACT + "mB/t per side");
			}else if(i == part_copper_wire){
				list.add(format + "Has a resistance of " + ElectricConstants.RESISTANCE_COPPER_LOW + " Ohms");
			}else if(i == part_copper_cable_low){
				list.add(format + "Has a resistance of " + ElectricConstants.RESISTANCE_COPPER_LOW + " Ohms");
			}else if(i == part_copper_cable_medium){
				list.add(format + "Has a resistance of " + ElectricConstants.RESISTANCE_COPPER_MED + " Ohms");
			}else if(i == part_copper_cable_high){
				list.add(format + "Has a resistance of " + ElectricConstants.RESISTANCE_COPPER_HIGH + " Ohms");
			}
		}
		return list;
	}
}
