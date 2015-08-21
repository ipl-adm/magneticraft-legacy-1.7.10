package com.cout970.magneticraft;

import static com.cout970.magneticraft.ManagerBlocks.dust_block_salt;
import static com.cout970.magneticraft.ManagerBlocks.dust_block_sulfur;
import static com.cout970.magneticraft.ManagerBlocks.ingot_block_brass;
import static com.cout970.magneticraft.ManagerBlocks.ingot_block_carbide;
import static com.cout970.magneticraft.ManagerBlocks.ingot_block_copper;
import static com.cout970.magneticraft.ManagerBlocks.ingot_block_tungsten;
import static com.cout970.magneticraft.ManagerBlocks.ingot_block_zinc;
import static com.cout970.magneticraft.ManagerBlocks.oreCopper;
import static com.cout970.magneticraft.ManagerBlocks.oreSalt;
import static com.cout970.magneticraft.ManagerBlocks.oreSulfur;
import static com.cout970.magneticraft.ManagerBlocks.oreThorium;
import static com.cout970.magneticraft.ManagerBlocks.oreTungsten;
import static com.cout970.magneticraft.ManagerBlocks.oreUranium;
import static com.cout970.magneticraft.ManagerBlocks.oreZinc;
import static com.cout970.magneticraft.ManagerItems.chunks;
import static com.cout970.magneticraft.ManagerItems.dust;
import static com.cout970.magneticraft.ManagerItems.dustBrass;
import static com.cout970.magneticraft.ManagerItems.dustDiamond;
import static com.cout970.magneticraft.ManagerItems.dustObsidian;
import static com.cout970.magneticraft.ManagerItems.dustQuartz;
import static com.cout970.magneticraft.ManagerItems.dustSalt;
import static com.cout970.magneticraft.ManagerItems.dustSulfur;
import static com.cout970.magneticraft.ManagerItems.ingotBrass;
import static com.cout970.magneticraft.ManagerItems.ingotCarbide;
import static com.cout970.magneticraft.ManagerItems.ingotCopper;
import static com.cout970.magneticraft.ManagerItems.ingotTungsten;
import static com.cout970.magneticraft.ManagerItems.ingotZinc;
import static com.cout970.magneticraft.ManagerItems.pebbles;
import static com.cout970.magneticraft.ManagerItems.plastic;
import static com.cout970.magneticraft.ManagerItems.rubber;
import static com.cout970.magneticraft.ManagerItems.rubble;
import static com.cout970.magneticraft.ManagerItems.stick_iron;

import com.cout970.magneticraft.items.ItemBasic;
import com.cout970.magneticraft.items.ItemProduct;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class ManagerOreDict {
	
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
		
		for(ItemProduct i : chunks) OreDictionary.registerOre(i.getOreDictName(), i);
//		for(ItemProduct i : chunks_clean) OreDictionary.registerOre(i.getOreDictName(), i);
		for(ItemProduct i : rubble) OreDictionary.registerOre(i.getOreDictName(), i);
//		for(ItemProduct i : rubble_clean) OreDictionary.registerOre(i.getOreDictName(), i);
		for(ItemProduct i : pebbles) OreDictionary.registerOre(i.getOreDictName(), i);
//		for(ItemProduct i : pebbles_clean) OreDictionary.registerOre(i.getOreDictName(), i);
		for(ItemProduct i : dust) OreDictionary.registerOre(i.getOreDictName(), i);
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
