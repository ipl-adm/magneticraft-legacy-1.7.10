package com.cout970.magneticraft;

import com.cout970.magneticraft.compat.ManagerIntegration;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import static com.cout970.magneticraft.ManagerBlocks.*;
import static com.cout970.magneticraft.ManagerItems.*;

public class ManagerCraft {

    public static void init() {
        //ingot blocks
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingot_block_copper), "iii", "iii", "iii", 'i', "ingotCopper"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingot_block_tungsten), "iii", "iii", "iii", 'i', "ingotTungsten"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingot_block_carbide), "iii", "iii", "iii", 'i', "ingotCarbide"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingot_block_zinc), "iii", "iii", "iii", 'i', "ingotZinc"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingot_block_brass), "iii", "iii", "iii", 'i', "ingotBrass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dust_block_salt), "iii", "iii", "iii", 'i', "dustSalt"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dust_block_sulfur), "iii", "iii", "iii", 'i', "dustSulfur"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotCopper, 9), "i", 'i', "blockCopper"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotTungsten, 9), "i", 'i', "blockTungsten"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotCarbide, 9), "i", 'i', "blockCarbide"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotZinc, 9), "i", 'i', "blockZinc"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotBrass, 9), "i", 'i', "blockBrass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dustSalt, 9), "i", 'i', "blockSalt"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(dustSulfur, 9), "i", 'i', "blockSulfur"));
        //decoration blocks
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(brickLime, 4), "xx", "xx", 'x', "limestone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(burntBrickLime, 4), "xx", "xx", 'x', burntLime));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tileLime, 4), true, "xy", "yx", 'x', "limestone", 'y', burntLime));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(roofTile, 2), "x x", " x ", "x x", 'x', "ingotBrick"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(slabRoofTileSingle, 6), "xxx", 'x', roofTile));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stairsRoofTile, 4), true, "x  ", "xx ", "xxx", 'x', roofTile));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(slabBrickLimeSingle, 6), "xxx", 'x', brickLime));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stairsBrickLime, 4), true, "x  ", "xx ", "xxx", 'x', brickLime));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(slabBurntLimeSingle, 6), "xxx", 'x', burntLime));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stairsBurntLime, 4), true, "x  ", "xx ", "xxx", 'x', burntLime));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(slabOreLimeSingle, 6), "xxx", 'x', "limestone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stairsOreLime, 4), true, "x  ", "xx ", "xxx", 'x', "limestone"));
        //misc blocks
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(housing), "ici", "csc", "ici", 'i', "ingotIron", 'c', "ingotCarbide", 's', Blocks.stone));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(battery), "wbw", "bcb", "wbw", 'w', "ingotIron", 'c', "ingotCopper", 'b', battery_item));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(furnace), "iri", "ihi", "ibi", 'i', "ingotIron", 'r', "dustRedstone", 'b', small_battery, 'h', housing));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(transformer_lm), true, "vcv", "lcm", "iii", 'l', part_copper_cable_low, 'm', part_copper_cable_medium, 'c', copper_coil, 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(transformer_mh), true, "vcv", "mch", "iii", 'h', part_copper_cable_high, 'm', part_copper_cable_medium, 'c', copper_coil, 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(basic_gen), true, "cbc", "hag", "cmc", 'c', "ingotCopper", 'm', housing, 'b', small_battery, 'h', partheatcable, 'g', "blockGlass", 'a', alternator));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(partheatcable, 8), "bbb", "bsb", "bbb", 'b', Items.brick, 's', "blockSalt"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(firebox), "bbb", "fhf", "bbb", 'b', Items.brick, 'f', Blocks.furnace, 'h', partheatcable));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(boiler), "ccc", "chc", "ccc", 'h', housing, 'c', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cooler), "viv", "ihi", "viv", 'h', partheatcable, 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crusher), "pvp", "mhm", "ibi", 'h', chassis, 'i', "ingotIron", 'm', motor, 'b', small_battery, 'p', Blocks.piston));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(chassis, 9), "viv", "ihi", "viv", 'h', housing, 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(multi_io), "hc", 'h', chassis, 'c', Blocks.chest));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(conveyor_l, 6), "ggg", "iii", 'g', "sheetPlastic", 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pumpJack), true, "iii", "viv", "mid", 'i', "ingotIron", 'm', motor, 'd', drill));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mirror), "viv", "ihi", "viv", 'h', "stickIron", 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mirror), "viv", "ihi", "viv", 'h', "stickIron", 'i', "ingotSilver"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(windturbine), true, "iii", "bmt", "iii", 't', "ingotIron", 'i', "ingotCarbide", 'm', alternator, 'b', small_battery));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(permagnet, 2), "vtv", "tmt", "vtv", 'm', magnet, 't', "ingotTungsten"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fluidhopper), "igi", "igi", "viv", 'i', "ingotIron", 'g', "blockGlass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heat_resist), "iii", "iti", "iii", 'i', Items.clay_ball, 't', partheatcable));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(breaker), "iii", "iti", "idi", 'i', "ingotIron", 't', housing, 'd', drill));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steam_engine), "tgt", "cjc", "iii", 'i', "ingotIron", 't', "ingotCarbide", 'c', "ingotCopper", 'g', small_battery, 'j', alternator));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(geothermal), "ttt", "tht", "tdt", 't', "ingotCarbide", 'h', partheatcable, 'd', drill));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(thermopile), true, "itc", "shd", "itc", 'i', "ingotIron", 'd', heatCoilCopper, 's', heatCoilIron, 'h', housing, 't', "ingotCarbide", 'c', "ingotCopper"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(multi_energy_low), "mc", 'm', chassis, 'c', part_copper_cable_low));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(inserter), true, "vvi", "viv", "cmc", 'm', motor, 'i', "ingotIron", 'c', "ingotCarbide"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(grinder), "cmc", "ihi", "cbc", 'm', motor, 'i', "ingotIron", 'c', "ingotCarbide", 'h', chassis, 'b', small_battery));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(miner), "pcp", "mhm", "pdp", 'm', motor, 'd', drill, 'c', part_copper_cable_medium, 'h', housing, 'p', "sheetPlastic"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tesla_coil), "cic", "cic", "bib", 'i', "ingotIron", 'b', "ingotCopper", 'c', copper_coil));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(airlock), "gmg", "ghg", "gbg", 'g', "blockGlass", 'h', housing, 'm', motor, 'b', Items.bucket));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(biomass_burner), "bbb", "fhf", "bbb", 'b', "ingotCarbide", 'f', Blocks.furnace, 'h', partheatcable));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stirling), "ibi", "ihi", "ici", 'i', "ingotCarbide", 'h', chassis, 'c', alternator, 'b', small_battery));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(infinite_water), "gbg", "bhb", "gbg", 'g', "blockGlass", 'h', housing, 'b', Items.bucket));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refinery), "ccc", "tht", "ccc", 'c', "ingotCarbide", 'h', chassis, 't', cooler));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refinery_gap, 4), "viv", "chc", "viv", 'c', "ingotCopper", 'h', chassis, 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(refinery_tank, 3), "rrr", "vtv", 'r', refinery_gap, 't', copper_tank));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(copper_tank, 2), "cgc", "gvg", "cgc", 'c', "ingotCopper", 'g', "blockGlass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heater), "iti", "tht", "iti", 't', heatCoilCopper, 'h', housing, 'i', "ingotCarbide"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(solar_tower_core), "ttt", "tht", "ttt", 'h', partheatcable, 't', "ingotTungsten"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(solarpanel), "qqq", "iii", "ccc", 'q', "dustQuartz", 'i', photoelectricDust, 'c', "sheetPlastic"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(polimerizer), "cgc", "ghg", "cgc", 'c', "ingotCarbide", 'h', chassis, 'g', Items.glass_bottle));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(multi_energy_medium), "mc", 'm', chassis, 'c', part_copper_cable_medium));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(combustion_engine), "ivi", "imi", "pbp", 'i', "ingotIron", 'm', alternator, 'p', "sheetPlastic", 'b', small_battery));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heat_sink), "bbb", "iii", 'i', "ingotIron", 'b', Blocks.iron_bars));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(steam_turbine), "ccc", "mmm", "chc", 'c', "ingotCarbide", 'm', alternator, 'h', chassis));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(brickFurnace), "bbb", "bfb", "bcb", 'b', Items.brick, 'c', partheatcable, 'f', Blocks.furnace));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crafter), "bbb", "bfb", "bcb", 'b', "ingotIron", 'c', Items.redstone, 'f', Blocks.crafting_table));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(multi_heat), "hc", 'c', partheatcable, 'h', chassis));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(oil_distillery), "ihi", "iti", "ici", 't', refinery_gap, 'i', "ingotCarbide", 'c', chassis, 'h', heater));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(diode), "vpv", "cqc", "vpv", 'p', "sheetPlastic", 'q', "dustQuartz", 'c', part_copper_cable_low));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(resistance), "cbc", 'b', Items.brick, 'c', part_copper_cable_low));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pole_tier1), "cwc", "vwv", "vwv", 'w', "logWood", 'c', heavy_copper_coil));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(pole_cable_wire), "cwc", "vwv", "vwa", 'w', "logWood", 'c', heavy_copper_coil, 'a', part_copper_cable_low));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(sifter), "cbc", "mhm", "cbc", 'c', "ingotCarbide", 'b', refinery_gap, 'h', chassis, 'm', motor));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(electric_switch), "vlv", "chc", 'c', part_copper_cable_low, 'h', housing, 'l', Blocks.lever));
        if (ManagerIntegration.RAILCRAFT) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rc_alternator), "iei", "iai", "ccc", 'c', "ingotCarbide", 'i', "ingotIron", 'a', alternator, 'e', "dustLead"));
        }
        if (ManagerIntegration.IC2) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(eu_alternator), "iei", "iai", "ccc", 'c', "ingotCarbide", 'i', "ingotIron", 'a', alternator, 'e', "dustQuartz"));
        }
        if (ManagerIntegration.COFH_ENERGY) {
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rf_alternator), "iei", "iai", "ccc", 'c', "ingotCarbide", 'i', "ingotIron", 'a', alternator, 'e', "dustRedstone"));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(kinetic), "ici", "ctc", "ici", 'c', copper_coil, 'i', "ingotIron", 't', "ingotCarbide"));
        }
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(mb_controls), "tlt", "lhl", "tlt", 't', "ingotCarbide", 'l', Blocks.lever, 'h', chassis));

        //items
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(small_battery), "scs", "sis", "scs", 'i', "ingotIron", 'c', "ingotCopper", 's', "dustSulfur"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(battery_item), "scs", "sis", "scs", 'i', "ingotIron", 'c', "ingotCopper", 's', "blockSulfur"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(copper_coil, 4), "vcv", "cbc", "vcv", 'c', "ingotCopper", 'b', Blocks.iron_bars));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heavy_copper_coil, 4), "ccc", "cbc", "ccc", 'c', "ingotCopper", 'b', Blocks.iron_bars));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(drill), "i", "i", "d", 'd', "gemDiamond", 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(motor), true, "jjc", "iii", "jjc", 'c', "ingotCopper", 'i', "ingotIron", 'j', copper_coil));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(alternator),true,  "jmc", "iii", "jmc", 'm', magnet, 'i', "ingotIron", 'j', copper_coil, 'c', "ingotCopper"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heatCoilCopper), "cc", "cc", 'c', "ingotCopper"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heatCoilIron), "cc", "cc", 'c', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(heatCoilTungsten), "cc", "cc", 'c', "ingotTungsten"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(photoelectricDust, 9), "ccc", "cdc", "ccc", 'c', "dustQuartz", 'd', "dustDiamond"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ingotCarbide, 8), "ccc", "ctc", "ccc", 'c', Items.coal, 't', "ingotTungsten"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(voltmeter), "cgc", "cgc", "lmh", 'c', "ingotBrass", 'g', "paneGlass", 'l', part_copper_cable_low, 'm', part_copper_cable_medium, 'h', part_copper_cable_high));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(thermometer), "cgc", "cgc", "crc", 'c', "ingotIron", 'g', "paneGlass", 'r', "dustRedstone"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(map_positioner), "vpv", "ptp", "vpv", 'p', Items.paper, 't', "dyeBlack"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(wrench), true, "ii", "ti", "ti", 'i', "ingotIron", 't', "dyeRed"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(string_fabric), "iii", "iii", "iii", 'i', Items.string));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(turbine_wing), true, "sf", "sf", "sv", 's', Items.stick, 'f', string_fabric));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(turbine_0), "vwv", "wpw", "vwv", 'w', turbine_wing, 'p', "plankWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(turbine_1), "vwv", "wpw", "vwv", 'w', turbine_wing, 'p', turbine_0));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(turbine_2), "vwv", "wpw", "vwv", 'w', turbine_wing, 'p', turbine_1));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(oil_prospector), "b", "i", "t", 'b', small_battery, 'i', "ingotIron", 't', "ingotTungsten"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tool_sword), true, "di", "di", "vb", 'b', small_battery, 'i', "ingotIron", 'd', "dustDiamond"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tool_chainsaw), true, "vii", "imb", 'b', small_battery, 'i', "ingotIron", 'm', motor));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(dustBrass), "dustZinc", "dustCopper"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(stick_iron, 4), "i", "i", 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(magnet), "lrl", "rir", "lrl", 'r', Items.redstone, 'l', "dyeBlue", 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tool_charger), "cic", "ibi", 'c', part_copper_cable_low, 'b', battery_item, 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(tool_jackhammer), "ibi", "vmv", "vdv", 'm', motor, 'd', drill, 'b', small_battery, 'i', "ingotIron"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgrade_drop), "ppp", "pip", "ppp", 'p', "sheetPlastic", 'i', "ingotCopper"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgrade_suck), "ppp", "pip", "ppp", 'p', "sheetPlastic", 'i', "ingotCarbide"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgrade_speed), "ppp", "pip", "ppp", 'p', "sheetPlastic", 'i', motor));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(upgrade_slow), "ppp", "pip", "ppp", 'p', "sheetPlastic", 'i', magnet));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hammer_stone), true, "xx ", "xzx", " z ", 'x', "cobblestone", 'z', "stickWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(hammer_iron), true, "xx ", "xzx", " z ", 'x', "ingotIron", 'z', "stickWood"));

        //multipart
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part_copper_cable_low, 2), "w", "c", "w", 'c', "ingotCopper", 'w', Blocks.wool));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part_copper_cable_low, 2), "w", "c", 'c', "ingotCopper", 'w', "itemRubber"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part_copper_wire, 4), "vwv", "wcw", 'c', "ingotCopper", 'w', Blocks.wool));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part_copper_wire, 4), "wcw", 'c', "ingotCopper", 'w', "itemRubber"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part_copper_cable_medium), "w", "c", "w", 'c', part_copper_cable_low, 'w', Blocks.wool));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part_copper_cable_high), "w", "c", "w", 'c', part_copper_cable_medium, 'w', Blocks.wool));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part_copper_cable_medium), "w", "c", 'c', part_copper_cable_low, 'w', "itemRubber"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part_copper_cable_high), "w", "c", 'c', part_copper_cable_medium, 'w', "itemRubber"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part_copper_pipe, 8), "cvc", "cgc", "cvc", 'c', "ingotCopper", 'g', "blockGlass"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(part_iron_pipe, 8), "cvc", "cgc", "cvc", 'c', "ingotIron", 'g', "blockGlass"));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(crushing_table), "xxx", "yyy", "y y", 'x', new ItemStack(Blocks.stone_slab, 1, 0), 'y', "logWood"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(shelving_unit), "xyx", "xyx", "xyx", 'x', new ItemStack(Blocks.stone_slab, 1, 0), 'y', Blocks.iron_bars));
    }
}
