package com.cout970.magneticraft;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import com.cout970.magneticraft.util.NamedBlock;
import com.cout970.magneticraft.util.NamedItem;

public class LangHelper {

	public static List<String> unloc = new ArrayList<String>();
	public static List<String> name = new ArrayList<String>();
	
	public static void addName(Object obj,String name){
		if(obj == null)return;
		if(name == null)return;
		if(obj instanceof ItemStack){
			LangHelper.put(((ItemStack) obj).getUnlocalizedName(), name);
		}else if(obj instanceof Block){
			LangHelper.put(((Block) obj).getUnlocalizedName(), name);
		}else if(obj instanceof Item){
			LangHelper.put(((Item) obj).getUnlocalizedName(), name);
		}else if(obj instanceof Fluid){
			LangHelper.put(((Fluid) obj).getUnlocalizedName(), name);
		}else if(obj instanceof String){
			LangHelper.put((String) obj, name);
		}
	}
	
	public static void put(String a, String b){
		unloc.add(a);
		name.add(b);
	}

	public static void setupLangFile(){
		File f = new File("I:/Development/Minecraft Mod 1.7.10/Magneticraft/src/main/resources/assets/magneticraft/lang/en_US.lang");
		Writer w;
		try {
			w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
			for(String s : unloc){
				if(s.contains("fluid.")){
					w.write(s+"="+name.get(unloc.indexOf(s))+"\n");
				}else{
					w.write(s+".name="+name.get(unloc.indexOf(s))+"\n");
				}
			}
			w.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void registerNames(){
		
		for(NamedItem i : ManagerItems.named)
			addName(i.item, i.name);
		
		for(NamedBlock i : ManagerBlocks.named)
			addName(i.block, i.name);
		
		//creative tab
		addName("itemGroup","Magneticraft");
		
		//fluids
		addName("fluid."+ManagerFluids.STEAM_NAME,"Steam");
		addName("fluid."+ManagerFluids.OIL_NAME,"Crude Oil");
		addName("fluid."+ManagerFluids.HEAVY_OIL,"Heavy Oil");
		addName("fluid."+ManagerFluids.LIGHT_OIL,"Light Oil");
		addName("fluid."+ManagerFluids.NATURAL_GAS,"Natural Gas");
		addName("fluid."+ManagerFluids.HOT_CRUDE,"Hot Crude");
		//fluidblocks
		addName("tile.mg_steam_block","Steam");
		addName("tile.mg_oil_block","Crude Oil");
		addName("tile.mg_heavy_oil_block","Heavy Oil");
		addName("tile.mg_light_oil_block","Light Oil");
		addName("tile.mg_natural_gas_block","Natural Gas");
		addName("tile.mg_hot_crude_block","Hot Crude");
		
	}
}
