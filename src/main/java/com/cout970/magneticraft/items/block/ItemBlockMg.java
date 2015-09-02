package com.cout970.magneticraft.items.block;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.EnergyConversor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.List;

import static com.cout970.magneticraft.ManagerBlocks.*;

public class ItemBlockMg extends ItemBlock {

    public static String format = EnumChatFormatting.DARK_AQUA + "";
    public static String energy = EnumChatFormatting.GREEN + "";

    public ItemBlockMg(Block b) {
        super(b);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
        super.addInformation(item, player, list, flag);
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
        	list.add(EnumChatFormatting.DARK_GRAY+"<press shift for more info>");
        	return;
        }
        Block b = field_150939_a;
        if (b == airlock) {
            list.add(format + "Removes all nearby water blocks every 10 seconds");
            list.add(format + "Uses " + (int) EnergyConversor.RFtoW(10) + "J per block destroyed");
        } else if (b == basic_gen) {
            list.add(format + "Generates electricity from solid fuels or heat, needs water to work");
            list.add(format + "Produces up to 400W using " + (EnergyConversor.FUELtoCALORIES(4) / 1000) + " kcal/t");
        } else if (b == battery) {
            list.add(format + "Stores up to " + (int) EnergyConversor.RFtoW(2000000) + "J");
            list.add(format + "Keeps energy when broken");
        } else if (b == biomass_burner) {
            list.add(format + "Generates heat from biomass, such as wood, saplings, flowers, or some types of plants");
            list.add(format + "Produces up tp " + (EnergyConversor.RFtoCALORIES(40) / 1000) + " kcal/t");
        } else if (b == boiler) {
            list.add(format + "Generates steam from heat and water");
            list.add(format + "Produces up to 80mB using " + (EnergyConversor.WtoCALORIES(EnergyConversor.STEAMtoW(80)) / 1000) + " kcal/t");
        } else if (b == breaker) {
            list.add(format + "Breaks up to 16 blocks in a straight line");
            list.add(format + "Uses " + EnergyConversor.RFtoW(500) / 1000 + "kW per block mined");
        } else if (b == brickFurnace) {
            list.add(format + "A furnace fueled by heat. Speed proportional to temperature");
        } else if (b == combustion_engine) {
            list.add(format + "Generates electricity from liquid fuel, power depends on fuel type");
            list.add(format + "Heat generated during work slows engine down if not removed");
        } else if (b == cpu) {
            list.add(format + "[WIP] Fully implemented MIPS emulator, needs a MIPS CPU, a RAM module and a ROM modules");
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
            list.add(format + "Dissipates heat. Works better than Cooler at lower temperatures, but melts at much lower temperature");
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
            list.add(format + "Mines blocks and puts them into adjacent inventories" + (Magneticraft.BUILDCRAFT ? " or Transport Pipes" : ""));
            list.add(format + "Uses medium voltage. Mining speed scales with exact voltage");
            list.add(format + "Uses " + (int) EnergyConversor.RFtoW(500) + "J per block mined");
        } else if (b == mirror) {
            list.add(format + "Focuses sunlight onto a Solar Tower Core. Use Position Map to bind mirrors to the Core");
            list.add(format + "Every mirror generates " + EnergyConversor.RFtoCALORIES(2) / 1000 + " kcal/t if nothing is blocking the sunlight");
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
            list.add(format + "Extracts oil from oil deposits. Requires electricity to operate. Use Oil Prospector to find deposits");
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
            list.add(format + "Concentrates heat from nearby mirrors. Use Position Map to orient mirrors");
        } else if (b == steam_engine) {
            list.add(format + "Generates electricity from steam");
            list.add(format + "Can consume up to 40mB/t and generate up to " + EnergyConversor.STEAMtoW(40) / 1000 + " kW");
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
    }

}
