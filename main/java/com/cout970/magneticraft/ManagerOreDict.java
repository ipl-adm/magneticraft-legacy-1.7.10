package com.cout970.magneticraft;

import static com.cout970.magneticraft.ManagerBlocks.oreCopper;
import static com.cout970.magneticraft.ManagerBlocks.oreSalt;
import static com.cout970.magneticraft.ManagerBlocks.oreSulfur;
import static com.cout970.magneticraft.ManagerBlocks.oreThorium;
import static com.cout970.magneticraft.ManagerBlocks.oreTungsten;
import static com.cout970.magneticraft.ManagerBlocks.oreUranium;
import static com.cout970.magneticraft.ManagerItems.dustDiamond;
import static com.cout970.magneticraft.ManagerItems.dusts;
import static com.cout970.magneticraft.ManagerItems.gravelOre;
import static com.cout970.magneticraft.ManagerItems.ingotCarbide;
import static com.cout970.magneticraft.ManagerItems.ingots;
import static com.cout970.magneticraft.ManagerItems.plastic;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.cout970.magneticraft.items.ItemGravelOre;

public class ManagerOreDict {
	
	public static void registerOreDict(){
		for(ItemGravelOre d : gravelOre)
		OreDictionary.registerOre("chunk"+d.locName, d);
		
		OreDictionary.registerOre("oreCopper",oreCopper);
		OreDictionary.registerOre("oreTungsten",oreTungsten);
		OreDictionary.registerOre("oreSulfur",oreSulfur);
		OreDictionary.registerOre("oreUranium",oreUranium);
		OreDictionary.registerOre("oreThorium",oreThorium);
		OreDictionary.registerOre("oreSalt",oreSalt);
		
		OreDictionary.registerOre("dustDiamond",dustDiamond);
		OreDictionary.registerOre("ingotCarbide",ingotCarbide);
		OreDictionary.registerOre("itemPlastic",plastic);
		
		for(String i : dusts.keySet()){
			OreDictionary.registerOre("dust"+i, dusts.get(i));
		}
		for(String i : ingots.keySet()){
			OreDictionary.registerOre("ingot"+i, ingots.get(i));
		}
		for(ItemGravelOre z : gravelOre){
			OreDictionary.registerOre("gravel"+z.locName, z);
		}
	}
	
	public static ItemStack getOre(String name){
		for(ItemStack i : OreDictionary.getOres(name)){
			return i;
		}
		return null;
	}
}
