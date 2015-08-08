package com.cout970.magneticraft;

import static com.cout970.magneticraft.ManagerBlocks.*;
import static com.cout970.magneticraft.ManagerItems.*;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ManagerCraft {

	public static void init(){
		//ingot blocks
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingot_block_copper), 	new Object[]{"iii","iii","iii",'i',"ingotCopper"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingot_block_tungsten), new Object[]{"iii","iii","iii",'i',"ingotTungsten"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingot_block_carbide),	new Object[]{"iii","iii","iii",'i',"ingotCarbide"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingot_block_zinc), 	new Object[]{"iii","iii","iii",'i',"ingotZinc"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingot_block_brass), 	new Object[]{"iii","iii","iii",'i',"ingotBrass"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dust_block_salt), 		new Object[]{"iii","iii","iii",'i',"dustSalt"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dust_block_sulfur), 	new Object[]{"iii","iii","iii",'i',"dustSulfur"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotCopper, 9), 		new Object[]{"i",'i',"blockCopper"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotTungsten, 9), 	new Object[]{"i",'i',"blockTungsten"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotCarbide, 9), 		new Object[]{"i",'i',"blockCarbide"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotZinc, 9), 		new Object[]{"i",'i',"blockZinc"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotBrass, 9), 		new Object[]{"i",'i',"blockBrass"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dustSalt, 9), 			new Object[]{"i",'i',"blockSalt"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dustSulfur, 9), 		new Object[]{"i",'i',"blockSulfur"}));
		//misc blocks
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(housing), 			new Object[]{"ici","csc","ici",'i',"ingotIron",'c',"ingotCarbide",'s', Blocks.stone}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(battery), 			new Object[]{"wbw","bcb","wbw",'w',"ingotIron",'c',"ingotCopper",'b',battery_item}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(furnace), 			new Object[]{"iri","ihi","ibi",'i',"ingotIron",'r',"dustRedstone",'b',small_battery,'h',housing}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(transformer_lm), 	new Object[]{"vcv","lcm","iii",'l',cablelow,'m',cablemedium,'c',copper_coil,'i',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(transformer_mh), 	new Object[]{"vcv","mch","iii",'h',cablehigh,'m',cablemedium,'c',copper_coil,'i',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(kinetic), 			new Object[]{"ici","ctc","ici",'c',copper_coil,'i',"ingotIron",'t',"ingotCarbide"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(basic_gen), 		new Object[]{"cbc","hag","cmc",'c',"ingotCopper",'m',housing,'b',small_battery,'h',partheatcable,'g',"blockGlass",'a',alternator}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(partheatcable,8),	new Object[]{"bbb","bsb","bbb",'b',Items.brick,'s',"dustSalt"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(firebox), 			new Object[]{"bbb","fhf","bbb",'b',Items.brick,'f',Blocks.furnace,'h',partheatcable}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(boiler), 			new Object[]{"ccc","chc","ccc",'h',housing,'c',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cooler), 			new Object[]{"viv","ihi","viv",'h',partheatcable,'i',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crusher), 			new Object[]{"pvp","mhm","ibi",'h',chassis,'i',"ingotIron",'m',motor,'b',small_battery,'p',Blocks.piston}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chassis,9), 		new Object[]{"viv","ihi","viv",'h',housing,'i',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(multi_io), 		new Object[]{"hc",'h',chassis,'c',Blocks.chest}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(conveyor_l,6), 	new Object[]{"ggg","iii",'g',"itemPlastic",'i',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pumpJack), 		new Object[]{"iii","viv","mid",'i',"ingotIron",'m',motor,'d',drill}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mirror), 			new Object[]{"viv","ihi","viv",'h',"stickIron",'i',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mirror), 			new Object[]{"viv","ihi","viv",'h',"stickIron",'i',"ingotSilver"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(windturbine), 		new Object[]{"iii","bmt","iii",'t',"ingotIron",'i',"ingotCarbide",'m',alternator,'b',small_battery}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(permagnet,2), 		new Object[]{"vtv","tmt","vtv",'m',magnet,'t',"ingotTungsten"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fluidhopper), 		new Object[]{"igi","igi","viv",'i',"ingotIron",'g',"blockGlass"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heat_resist),		new Object[]{"iii","iti","iii",'i',Items.clay_ball,'t',partheatcable}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(breaker), 			new Object[]{"iii","iti","idi",'i',"ingotIron",'t',housing,'d',drill}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steam_engine), 	new Object[]{"tgt","cjc","iii",'i',"ingotIron",'t',"ingotCarbide",'c',"ingotCopper",'g',small_battery,'j',alternator}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(geothermal), 		new Object[]{"ttt","tht","tdt",'t',"ingotCarbide",'h',partheatcable,'d',drill}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(thermopile), 		new Object[]{"itc","shd","itc",'i',"ingotIron",'d',heatCoilCopper,'s',heatCoilIron,'h',housing,'t',"ingotCarbide",'c',"ingotCopper"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(multi_energy_low), new Object[]{"mc",'m',chassis,'c',cablelow}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(inserter), 		new Object[]{"vvi","viv","cmc",'m',motor,'i',"ingotIron",'c',"ingotCarbide"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(grinder), 			new Object[]{"cmc","ihi","cbc",'m',motor,'i',"ingotIron",'c',"ingotCarbide",'h',chassis,'b',small_battery}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(miner), 			new Object[]{"pcp","mhm","pdp",'m',motor,'d',drill,'c',cablemedium,'h',housing,'p',"itemPlastic"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tesla_coil), 		new Object[]{"cic","cic","bib",'i',"ingotIron",'b',"ingotCopper",'c',copper_coil}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(airlock), 			new Object[]{"gmg","ghg","gbg",'g',"blockGlass",'h',housing,'m',motor,'b',Items.bucket}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(biomass_burner), 	new Object[]{"bbb","fhf","bbb",'b',"ingotCarbide",'f',Blocks.furnace,'h',partheatcable}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stirling), 		new Object[]{"ibi","ihi","ici",'i',"ingotCarbide",'h',chassis,'c',alternator,'b',small_battery}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(infinite_water), 	new Object[]{"gbg","bhb","gbg",'g',"blockGlass",'h',housing,'b',Items.bucket}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refinery), 		new Object[]{"ccc","tht","ccc",'c',"ingotCarbide",'h',chassis,'t',cooler}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refinery_gap,4), 	new Object[]{"viv","chc","viv",'c',"ingotCopper",'h',chassis,'i',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refinery_tank,3), 	new Object[]{"rrr","vtv",'r',refinery_gap,'t',copper_tank}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(copper_tank,2), 	new Object[]{"cgc","gvg","cgc",'c',"ingotCopper",'g',"blockGlass"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heater), 			new Object[]{"iti","tht","iti",'t',heatCoilCopper,'h',housing,'i',"ingotCarbide"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(solar_tower_core), new Object[]{"ttt","tht","ttt",'h',partheatcable,'t',"ingotTungsten"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(solarpanel), 		new Object[]{"qqq","iii","ccc",'q',"dustQuartz",'i',photoelectricDust,'c',"itemPlastic"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(polimerizer), 		new Object[]{"cgc","ghg","cgc",'c',"ingotCarbide",'h',chassis,'g',Items.glass_bottle}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(multi_energy_medium), new Object[]{"mc",'m',chassis,'c',cablemedium}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(combustion_engine),new Object[]{"ivi","imi","pbp",'i',"ingotIron",'m',alternator,'p',"itemPlastic",'b',small_battery}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heat_sink), 		new Object[]{"bbb","iii",'i',"ingotIron",'b',Blocks.iron_bars}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steam_turbine), 	new Object[]{"ccc","mmm","chc",'c',"ingotCarbide",'m',alternator,'h',chassis}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(brickFurnace), 	new Object[]{"bbb","bfb","bcb",'b',Items.brick,'c',partheatcable,'f',Blocks.furnace}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crafter), 			new Object[]{"bbb","bfb","bcb",'b',"ingotIron",'c',Items.redstone,'f',Blocks.crafting_table}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(multi_heat), 		new Object[]{"hc",'c',partheatcable,'h', chassis}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(oil_distillery), 	new Object[]{"ihi","iti","ici",'t',refinery_gap,'i',"ingotCarbide",'c',chassis,'h',heater}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diode), 			new Object[]{"vpv","cqc","vpv",'p',"itemPlastic",'q',"dustQuartz",'c',cablelow}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(resistance), 		new Object[]{"cbc",'b',Items.brick,'c',cablelow}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pole_tier1), 		new Object[]{"cwc","vwv","vwv",'w',"logWood",'c',heavy_copper_coil}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pole_cable_wire),	new Object[]{"cwc","vwv","vwa",'w',"logWood",'c',heavy_copper_coil,'a',cablelow}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sifter),			new Object[]{"cbc","mhm","cbc",'c',"ingotCarbide",'b',refinery_gap,'h',chassis,'m',motor}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(electric_switch),	new Object[]{"vlv","chc",'c',cablelow,'h',housing,'l',Blocks.lever}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rc_alternator),	new Object[]{"iei","iai","ccc",'c',"ingotCarbide",'i',"ingotIron",'a',alternator,'e',"dustLead"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(eu_alternator),	new Object[]{"iei","iai","ccc",'c',"ingotCarbide",'i',"ingotIron",'a',alternator,'e',"dustQuartz"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rf_alternator),	new Object[]{"iei","iai","ccc",'c',"ingotCarbide",'i',"ingotIron",'a',alternator,'e',"dustRedstone"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mb_controls),		new Object[]{"tlt","lhl","tlt",'t',"ingotCarbide",'l',Blocks.lever,'h',chassis}));

		//items
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(small_battery),	new Object[]{"scs","sis","scs",'i',"ingotIron",'c',"ingotCopper",'s',"dustSulfur"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(battery_item), 	new Object[]{"scs","sis","scs",'i',"ingotIron",'c',"ingotCopper",'s',"blockSulfur"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(copper_coil, 4), 	new Object[]{"vcv","cbc","vcv",'c',"ingotCopper",'b',Blocks.iron_bars}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heavy_copper_coil, 4), new Object[]{"ccc","cbc","ccc",'c',"ingotCopper",'b',Blocks.iron_bars}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(drill), 			new Object[]{"i","i","d",'d',"gemDiamond",'i',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(motor), 			new Object[]{"jjc","iii","jjc",'c',"ingotCopper",'i',"ingotIron",'j',copper_coil}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(alternator), 		new Object[]{"jmc","iii","jmc",'m',magnet,'i',"ingotIron",'j',copper_coil,'c',"ingotCopper"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heatCoilCopper), 	new Object[]{"cc","cc",'c',"ingotCopper"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heatCoilIron), 	new Object[]{"cc","cc",'c',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heatCoilTungsten), new Object[]{"cc","cc",'c',"ingotTungsten"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(photoelectricDust,9), new Object[]{"ccc","cdc","ccc",'c',"dustQuartz",'d',"dustDiamond"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotCarbide,8), 	new Object[]{"ccc","ctc","ccc",'c',Items.coal,'t',"ingotTungsten"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(volt), 			new Object[]{"cgc","cgc","lmh",'c',"ingotBrass",'g',"paneGlass",'l',cablelow,'m',cablemedium,'h',cablehigh}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(therm), 			new Object[]{"cgc","cgc","crc",'c',"ingotIron",'g',"paneGlass",'r',"dustRedstone"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(map), 				new Object[]{"vpv","ptp","vpv",'p',Items.paper,'t',new ItemStack(Items.dye,1,0)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wrench), 			new Object[]{"ii","ti","ti",'i',"ingotIron",'t',new ItemStack(Items.dye,1,1)}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(string_fabric), 	new Object[]{"iii","iii","iii",'i',Items.string}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(turbine_wing), 	new Object[]{"sf","sf","sv",'s',Items.stick,'f',string_fabric}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(turbine_0), 		new Object[]{"vwv","wpw","vwv",'w',turbine_wing,'p',"plankWood"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(turbine_1), 		new Object[]{"vwv","wpw","vwv",'w',turbine_wing,'p',turbine_0}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(turbine_2), 		new Object[]{"vwv","wpw","vwv",'w',turbine_wing,'p',turbine_1}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(oil_prospector), 	new Object[]{"b","i","t",'b',small_battery,'i',"ingotIron",'t',"ingotTungsten"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tool_sword), 		new Object[]{"di","di","vb",'b',small_battery,'i',"ingotIron",'d',"dustDiamond"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tool_chainsaw), 	new Object[]{"vii","imb",'b',small_battery,'i',"ingotIron",'m',motor}));
		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(dustBrass), 				 "dustZinc", "dustCopper"));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stick_iron, 4), 	new Object[]{"i","i",'i',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(magnet), 			new Object[]{"lrl","rir","lrl",'r', Items.redstone,'l',"dyeBlue",'i',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tool_charger), 	new Object[]{"cic","ibi",'c', cablelow,'b', battery_item,'i',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tool_jackhammer), 	new Object[]{"ibi","vmv","vdv",'m',motor,'d',drill,'b', small_battery,'i',"ingotIron"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgrade_drop), 	new Object[]{"ppp","pip","ppp",'p',"itemPlastic",'i',"ingotCopper"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgrade_suck), 	new Object[]{"ppp","pip","ppp",'p',"itemPlastic",'i',"ingotCarbide"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgrade_speed), 	new Object[]{"ppp","pip","ppp",'p',"itemPlastic",'i',motor}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgrade_slow), 	new Object[]{"ppp","pip","ppp",'p',"itemPlastic",'i',magnet}));

		//multipart
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cablelow,2), 		new Object[]{"w","c","w",'c',"ingotCopper",'w',Blocks.wool}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cablelow,2), 		new Object[]{"w","c",'c',"ingotCopper",'w',"itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wire_copper,4), 	new Object[]{"vwv","wcw",'c',"ingotCopper",'w',Blocks.wool}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wire_copper,4), 	new Object[]{"wcw",'c',"ingotCopper",'w',"itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cablemedium), 		new Object[]{"w","c","w",'c',cablelow,'w',Blocks.wool}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cablehigh), 		new Object[]{"w","c","w",'c',cablemedium,'w',Blocks.wool}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cablemedium), 		new Object[]{"w","c",'c',cablelow,'w',"itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cablehigh), 		new Object[]{"w","c",'c',cablemedium,'w',"itemRubber"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(partcopperpipe,8), new Object[]{"cvc","cgc","cvc",'c',"ingotCopper",'g',"blockGlass"}));
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(partironpipe,8), 	new Object[]{"cvc","cgc","cvc",'c',"ingotIron",'g',"blockGlass"}));
	}
}
