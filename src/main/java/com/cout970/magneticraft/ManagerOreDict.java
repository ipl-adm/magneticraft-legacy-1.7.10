package com.cout970.magneticraft;

import static com.cout970.magneticraft.ManagerBlocks.*;
import static com.cout970.magneticraft.ManagerItems.*;

import com.cout970.magneticraft.items.ItemBasic;
import com.cout970.magneticraft.items.ItemMeta;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ManagerOreDict {
	public static ItemMeta chunks = new ItemMeta("chunk_!lower_name!", "ores/chunk_!lower_name!", "chunk!name!", oreNames);
	public static ItemMeta rubble = new ItemMeta("rubble_!lower_name!", "ores/rubble_!lower_name!", "rubble!name!", oreNames);
	public static ItemMeta pebbles = new ItemMeta("pebbles_!lower_name!", "ores/pebbles_!lower_name!", "pebbles!name!", oreNames);
	public static ItemMeta dust = new ItemMeta("dust_!lower_name!", "ores/dust_!lower_name!", "dust!name!", oreNames);
	
	public static void registerOreDict(){
		
		OreDictionary.registerOre("oreCopper", oreCopper);
		OreDictionary.registerOre("oreTungsten", oreTungsten);
		OreDictionary.registerOre("oreSulfur", oreSulfur);
		OreDictionary.registerOre("oreUranium", oreUranium);
		OreDictionary.registerOre("oreThorium", oreThorium);
		OreDictionary.registerOre("oreSalt", oreSalt);
		OreDictionary.registerOre("oreZinc", oreZinc);
		
		OreDictionary.registerOre("dustSulfur", dustSulfur);
		OreDictionary.registerOre("dustQuartz", dustQuartz);
		OreDictionary.registerOre("dustObsidian", dustObsidian);
		OreDictionary.registerOre("dustSalt", dustSalt);
		OreDictionary.registerOre("dustDiamond", dustDiamond);
		OreDictionary.registerOre("dustBrass", dustBrass);
		
		OreDictionary.registerOre("ingotCopper",ingotCopper);
		OreDictionary.registerOre("ingotTungsten",ingotTungsten);
		OreDictionary.registerOre("ingotCarbide",ingotCarbide);
		OreDictionary.registerOre("ingotZinc",ingotZinc);
		OreDictionary.registerOre("ingotBrass",ingotBrass);
		OreDictionary.registerOre("itemPlastic",plastic);
		OreDictionary.registerOre("itemRubber",rubber);
		
		OreDictionary.registerOre("blockCopper",ingot_block_copper);
		OreDictionary.registerOre("blockTungsten",ingot_block_tungsten);
		OreDictionary.registerOre("blockCarbide",ingot_block_carbide);
		OreDictionary.registerOre("blockZinc",ingot_block_zinc);
		OreDictionary.registerOre("blockBrass",ingot_block_brass);
		OreDictionary.registerOre("blockSulfur",dust_block_sulfur);
		OreDictionary.registerOre("blockSalt",dust_block_salt);
		
		OreDictionary.registerOre("stickIron", stick_iron);
		
/*		for(ItemProduct i : chunks) OreDictionary.registerOre(i.getOreDictName(), i);
//		for(ItemProduct i : chunks_clean) OreDictionary.registerOre(i.getOreDictName(), i);
		for(ItemProduct i : rubble) OreDictionary.registerOre(i.getOreDictName(), i);
//		for(ItemProduct i : rubble_clean) OreDictionary.registerOre(i.getOreDictName(), i);
		for(ItemProduct i : pebbles) OreDictionary.registerOre(i.getOreDictName(), i);
//		for(ItemProduct i : pebbles_clean) OreDictionary.registerOre(i.getOreDictName(), i);
		for(ItemProduct i : dust) OreDictionary.registerOre(i.getOreDictName(), i); */
		int meta_amount = oreNames.length;
		for (int i = 0; i < meta_amount; i++) {
			OreDictionary.registerOre(chunks.getOreDictName(i), new ItemStack(chunks, 1, i));
			OreDictionary.registerOre(rubble.getOreDictName(i), new ItemStack(rubble, 1, i));
			OreDictionary.registerOre(pebbles.getOreDictName(i), new ItemStack(pebbles, 1, i));
			OreDictionary.registerOre(dust.getOreDictName(i), new ItemStack(dust, 1, i));

		}

	}
	
	public static ItemStack getOre(String name){
		if(OreDictionary.doesOreNameExist(name)){
			for(ItemStack i : OreDictionary.getOres(name)){
				return i;
			}
		}
		return null;
	}

	public static ItemStack getOreWithPreference(String name) {
		if(OreDictionary.doesOreNameExist(name)){
			for(ItemStack i : OreDictionary.getOres(name)){
				if(i != null && i.getItem() instanceof ItemBasic){
					return i;
				}
			}
			return getOre(name);
		}
		return null;
	}
}
