package com.cout970.magneticraft.items;

import java.util.List;




import com.cout970.magneticraft.api.util.EnergyConversor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import static com.cout970.magneticraft.ManagerBlocks.*;

public class ItemBlockMg extends ItemBlock{
	
	public static String format = EnumChatFormatting.DARK_AQUA+"";
	public static String energy = EnumChatFormatting.GREEN+"";
	
	public ItemBlockMg(Block b) {
		super(b);
	}

	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
		super.addInformation(item, player, list, flag);
		Block b = field_150939_a;
		if(b == airlock){
			list.add(format+"Every 10 seconds eliminates all the near water blocks");
			list.add(format+"Uses "+(int)EnergyConversor.RFtoW(10)+"J per block destroyed");
		}else if(b == basic_gen){
			list.add(format+"Generates electricity from solid fuels or heat, needs water to work");
			list.add(format+"Produces up to 400W using "+(EnergyConversor.FUELtoCALORIES(4)/1000)+" kcal/t");
		}else if(b == battery){
			list.add(format+"Stores up to "+(int) EnergyConversor.RFtoW(2000000)+"J");
			list.add(format+"Keep the energy when is picked");
		}else if(b == biomass_burner){
			list.add(format+"Generates heat from wood, saplings, flowers, and some of plants");
			list.add(format+"Produces up tp "+(EnergyConversor.RFtoCALORIES(40)/1000)+" kcal/t");
		}else if(b == boiler){
			list.add(format+"Makes steam using water and heat");
			list.add(format+"Produces up to 80mB using "+(EnergyConversor.WtoCALORIES(EnergyConversor.STEAMtoW(80))/1000)+" Kcal/t");
		}else if(b == breaker){
			list.add(format+"Breaks the blocks in front, can break up to 16 blocks distance");
			list.add(format+"Uses "+EnergyConversor.RFtoW(500)/1000+"kW per block mined");
		}else if(b == brickFurnace){
			list.add(format+"Works like a normal furnace but using heat");
		}else if(b == combustion_engine){
			list.add(format+"Generated electricity from liquid fuels, the production depends on the fuel");
			list.add(format+"When the engine works generates a small amount of heat that slow it down");
		}else if(b == cpu){
			list.add(format+"Fully implemented MIPS emulator, needs a MIPS cpu, a ram module and a rom module");
			list.add(format+"RED OS still WIP");
		}else if(b == conveyor_l){
			list.add(format+"Moves objects around the world");
			list.add(format+"Right click to add an item, you can use a inseter to add or extract items");
		}else if(b == cooler){
			list.add(format+"Dissipates heat, work better with high temperatures");
		}else if(b == copper_tank){
			list.add(format+"Stores up to 16000 mB of fluid, keeps the fluid when is picked up");
		}else if(b == crafter){
			list.add(format+"Craft items at the speed of light!");
		}else if(b == crusher){
			list.add(format+"Multiblock Structure, place the block and right click to see the blueprint");
		}else if(b == diode){
			list.add(format+"Only allow the current to flow in one direction");
		}else if(b == droid_red){
			list.add(format+"Still WIP");
		}else if(b == furnace){
			list.add(format+"Needs a heat coil to work, the speed depends on the coil");
		}else if(b == pole_cable_wire){
			list.add(format+"Connects cables with electric poles");
		}else if(b == pole_tier1){
			list.add(format+"Works like cables but more cool!");
		}else if(b == electric_switch){
			list.add(format+"Only allow the energy to flow then the block don't receive redstone signal");
		}else if(b == eu_alternator){
			list.add(format+"Generates electricity from EU power");
		}else if(b == firebox){
			list.add(format+"Generate heat using solid fuels");
			list.add(format+"Produces up tp "+(EnergyConversor.RFtoCALORIES(40)/1000)+" kcal/t");
		}else if(b == fluidhopper){
			list.add(format+"Works like a hopper with fluids");
		}else if(b == geothermal){
			list.add(format+"Mines for lava and uses it to make heat");
			list.add(format+"Produces "+EnergyConversor.FUELtoCALORIES(12)/1000+"kcal/t");
		}else if(b == grinder){
			list.add(format+"Multiblock Structure, place the block and right click to see the blueprint");
		}else if(b == heater){
			list.add(format+"Uses electricity to make heat");
		}else if(b == heat_resist){
			list.add(format+"Makes heat to move slow.");
		}else if(b == heat_sink){
			list.add(format+"Dissipates heat, work better with low temperatures");
		}else if(b == infinite_energy){
			list.add(format+"Creative only");
		}else if(b == infinite_steam){
			list.add(format+"Creative only");
		}else if(b == infinite_water){
			list.add(format+"An infinite amount of water");
		}else if(b == inserter){
			list.add(format+"Moves items from conveyor belts and inventories");
		}else if(b == kinetic){
			list.add(format+"Generates RF energy from electricity");
			list.add(format+"Produces up to 400RF, uses medium voltage");
		}else if(b == miner){
			list.add(format+"Run faster as with more voltage, uses medium voltage");
			list.add(format+"Uses "+(int)EnergyConversor.RFtoW(500)+"J per block mined");
		}else if(b == mirror){
			list.add(format+"Focus the sunlight into a Solar Tower Core, use a Map Position to orient the mirror");
			list.add(format+"Every mirror generates "+EnergyConversor.RFtoCALORIES(2)/1000+" kcal/t");
		}else if(b == monitor){
			list.add(format+"Should be placed next to a computer");
		}else if(b == oil_distillery){
			list.add(format+"Multiblock Structure, place the block and right click to see the blueprint");
		}else if(b == permagnet){
			list.add(format+"Stop the block breaker");
		}else if(b == polimerizer){
			list.add(format+"Multiblock Structure, place the block and right click to see the blueprint");
		}else if(b == pumpJack){
			list.add(format+"Extract oil from the ground, use an oil prospector to choose a place to put the pump");
		}else if(b == rc_alternator){
			list.add(format+"Generates electricity from Railcraft Charge");
		}else if(b == reactor_vessel){
			list.add(format+"Still WIP");
		}else if(b == reactor_activator){
			list.add(format+"Still WIP");
		}else if(b == reactor_wall){
			list.add(format+"Still WIP");
		}else if(b == reactor_control_rods){
			list.add(format+"Still WIP");
		}else if(b == reactor_controller){
			list.add(format+"Still WIP");
		}else if(b == refinery){
			list.add(format+"Multiblock Structure, place the block and right click to see the blueprint");
		}else if(b == resistance){
			list.add(format+"Limit the energy flow");
		}else if(b == rf_alternator){
			list.add(format+"Generates electrcity using RF power");
		}else if(b == sifter){
			list.add(format+"Multiblock Structure, place the block and right click to see the blueprint");
		}else if(b == solarpanel){
			list.add(format+"Generates "+(int)EnergyConversor.RFtoW(5)+"W");
		}else if(b == solar_tower_core){
			list.add(format+"Use a Map Position to orientate mirrors to this block to generate heat");
		}else if(b == steam_engine){
			list.add(format+"Use steam to generate electricity");
			list.add(format+"Can consume up to 40mB/t and generate "+EnergyConversor.STEAMtoW(40)/1000+" kW");
		}else if(b == steam_turbine){
			list.add(format+"Multiblock Structure, place the block and right click to see the blueprint");
			list.add(format+"Can produce up to "+(EnergyConversor.STEAMtoW(1200)/1000)+"kW");
		}else if(b == stirling){
			list.add(format+"Multiblock Structure, place the block and right click to see the blueprint");
			list.add(format+"Can produce up to "+1.2F+"kW");
		}else if(b == tesla_coil){
			list.add(format+"Charge items on the player inventory");
		}else if(b == thermopile){
			list.add(format+"Uses temperature differences to make electricity");
		}else if(b == transformer_lm){
			list.add(format+"Connect medium voltage cables with low voltage cables");
		}else if(b == transformer_mh){
			list.add(format+"Connect high voltage cables with medium voltage cables");
		}else if(b == void_inv){
			list.add(format+"Creative Only");
		}else if(b == windturbine){
			list.add(format+"Generates electricity from wind, the production depends on the turbine");
		}else if(b == wooden_shaft){
			list.add(format+"Still WIP");
		}
	}

}
