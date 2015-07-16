package com.cout970.magneticraft;

import static com.cout970.magneticraft.ManagerBlocks.*;
import static com.cout970.magneticraft.ManagerItems.*;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.cout970.magneticraft.items.ItemProduct;

public class ManagerOreDict {
	
	public static void registerOreDict(){
		
		OreDictionary.registerOre("oreCopper",oreCopper);
		OreDictionary.registerOre("oreTungsten",oreTungsten);
		OreDictionary.registerOre("oreSulfur",oreSulfur);
		OreDictionary.registerOre("oreUranium",oreUranium);
		OreDictionary.registerOre("oreThorium",oreThorium);
		OreDictionary.registerOre("oreSalt",oreSalt);
		
		OreDictionary.registerOre("dustSulfur", dustSulfur);
		OreDictionary.registerOre("dustQuartz", dustQuartz);
		OreDictionary.registerOre("dustObsidian", dustObsidian);
		OreDictionary.registerOre("dustSalt", dustSalt);
		OreDictionary.registerOre("dustDiamond", dustDiamond);
		
		OreDictionary.registerOre("ingotCopper",ingotCopper);
		OreDictionary.registerOre("ingotTungsten",ingotTungsten);
		OreDictionary.registerOre("ingotCarbide",ingotCarbide);
		OreDictionary.registerOre("itemPlastic",plastic);
		
		for(ItemProduct i : chunks) OreDictionary.registerOre(i.getOreDictName(), i);
//		for(ItemProduct i : chunks_clean) OreDictionary.registerOre(i.getOreDictName(), i);
		for(ItemProduct i : rubble) OreDictionary.registerOre(i.getOreDictName(), i);
//		for(ItemProduct i : rubble_clean) OreDictionary.registerOre(i.getOreDictName(), i);
		for(ItemProduct i : pebbles) OreDictionary.registerOre(i.getOreDictName(), i);
//		for(ItemProduct i : pebbles_clean) OreDictionary.registerOre(i.getOreDictName(), i);
		for(ItemProduct i : dust) OreDictionary.registerOre(i.getOreDictName(), i);
	}
	
	public static ItemStack getOre(String name){
		for(ItemStack i : OreDictionary.getOres(name)){
			return i;
		}
		return null;
	}
}
