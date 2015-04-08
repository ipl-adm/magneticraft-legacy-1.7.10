package com.cout970.magneticraft;

import static com.cout970.magneticraft.ManagerBlocks.getOre;
import static com.cout970.magneticraft.ManagerBlocks.oreSalt;
import static com.cout970.magneticraft.ManagerBlocks.oreSulfur;
import static com.cout970.magneticraft.ManagerItems.dustDiamond;
import static com.cout970.magneticraft.ManagerItems.dustObsidian;
import static com.cout970.magneticraft.ManagerItems.dustQuartz;
import static com.cout970.magneticraft.ManagerItems.dustSalt;
import static com.cout970.magneticraft.ManagerItems.dustSulfur;
import static com.cout970.magneticraft.ManagerItems.getDust;
import static com.cout970.magneticraft.ManagerItems.getIngot;
import static com.cout970.magneticraft.ManagerItems.getSand;
import static com.cout970.magneticraft.ManagerItems.gravelOre;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

import com.cout970.magneticraft.api.acces.MgRecipeRegister;
import com.cout970.magneticraft.api.acces.MgRegister;
import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.items.ItemGravelOre;
import com.cout970.magneticraft.util.ThermopileDecay;

import cpw.mods.fml.common.registry.GameRegistry;

public class ManagerRecipe {

	public static void registerFurnaceRecipes(){

		for(ItemGravelOre i : gravelOre){
			ItemStack ingot = ManagerOreDict.getOre("ingot"+i.locName);
			if(ingot != null){
				ingot = ingot.copy();
				ingot.stackSize = 1;
				GameRegistry.addSmelting(i, ingot, 1.0f);
				ingot = ingot.copy();
				ingot.stackSize = 2;
				GameRegistry.addSmelting(getSand(i.locName,1), ingot, 1.0f);
			}
		}

		for(String g : new String[]{"Copper","Tungsten"}){
			GameRegistry.addSmelting(getOre(g, 1), getIngot(g,1), 1.0f);
		}

		for(ItemGravelOre z : gravelOre){
			MgRecipeRegister.registerCrusherRecipe(getOre(z.locName,1), new ItemStack(z,1), getDust(z.locName, 1), 5, null, 0);
			MgRecipeRegister.registerGrinderRecipe(new ItemStack(z,1), getSand(z.locName,1), getDustExtra(z.locName, 1,0), 0.15f, getDustExtra(z.locName, 1,1), 0.05f);
		}
		
		MgRecipeRegister.registerGrinderRecipe(new ItemStack(Items.diamond),new ItemStack(dustDiamond),null,0,null,0);
		MgRecipeRegister.registerGrinderRecipe(new ItemStack(Items.quartz),new ItemStack(dustQuartz),new ItemStack(dustQuartz),0.5f,null,0);
		MgRecipeRegister.registerGrinderRecipe(new ItemStack(Blocks.obsidian),new ItemStack(dustObsidian,8),null,0,null,0);
		
		MgRecipeRegister.registerCrusherRecipe(new ItemStack(oreSulfur,1),new ItemStack(dustSulfur,2),new ItemStack(dustSulfur,1),20,null,0);
		MgRecipeRegister.registerCrusherRecipe(new ItemStack(Blocks.coal_ore,1),new ItemStack(Items.coal,2),new ItemStack(Items.coal,1),15,null,0);
		MgRecipeRegister.registerCrusherRecipe(new ItemStack(Blocks.lapis_ore,1),new ItemStack(Items.dye,8,4),new ItemStack(Items.dye,1,4),40,null,0);
		MgRecipeRegister.registerCrusherRecipe(new ItemStack(Blocks.diamond_ore,1),new ItemStack(Items.diamond,1),new ItemStack(Items.diamond,1),30,new ItemStack(Items.diamond,2),1);
		MgRecipeRegister.registerCrusherRecipe(new ItemStack(Blocks.redstone_ore,1),new ItemStack(Items.redstone,5),new ItemStack(Items.redstone,2),40,null,0);
		MgRecipeRegister.registerCrusherRecipe(new ItemStack(Blocks.emerald_ore,1),new ItemStack(Items.emerald,1),new ItemStack(Items.emerald,1),30,new ItemStack(Items.emerald,2),1);
		MgRecipeRegister.registerCrusherRecipe(new ItemStack(Blocks.quartz_ore,1),new ItemStack(Items.quartz,2),new ItemStack(Items.quartz,1),40,new ItemStack(dustQuartz,1),5);
		MgRecipeRegister.registerCrusherRecipe(new ItemStack(oreSalt,1),new ItemStack(dustSalt,2),new ItemStack(dustSalt,1),40,null,0);
		
		MgRecipeRegister.registerRefineryRecipe(FluidRegistry.getFluidStack("oil", 10), FluidRegistry.getFluidStack("lightoil", 5), FluidRegistry.getFluidStack("heavyoil", 3), FluidRegistry.getFluidStack("naturalgas", 2));
	}

	public static void registerThermopileRecipes() {
		MgRegister.addThermopileDecay(new ThermopileDecay());
		MgRegister.addThermopileSource(new BlockInfo(Blocks.air,-1),1,false);
		MgRegister.addThermopileSource(new BlockInfo(Blocks.snow,-1),100,false);
		MgRegister.addThermopileSource(new BlockInfo(Blocks.ice,-1),100,false);
		MgRegister.addThermopileSource(new BlockInfo(Blocks.snow_layer,-1),50,false);
		MgRegister.addThermopileSource(new BlockInfo(Blocks.torch,-1),5,true);
		MgRegister.addThermopileSource(new BlockInfo(Blocks.lit_pumpkin,-1),3,true);
		MgRegister.addThermopileSource(new BlockInfo(Blocks.water,-1),25,false);
		MgRegister.addThermopileSource(new BlockInfo(Blocks.lava,-1),100,true);
		MgRegister.addThermopileSource(new BlockInfo(Blocks.fire,-1),25,true);
	}
	
	public static void registerBiomassBurnerRecipes() {
		for(int i = 0; i < 4;i++)
			MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.log,1,i), 1600, true);
		for(int i = 0; i < 6;i++)
			MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.sapling,1,i), 200, true);
		for(int i = 0; i < 4;i++)
			MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.leaves,1,i), 20, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.web,1,0), 50, true);
		for(int i = 1; i < 3;i++)
			MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.tallgrass,1,i), 50, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.deadbush,1,0), 50, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.yellow_flower,1,0), 50, true);
		for(int i = 0; i < 9;i++)
			MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.red_flower,1,i), 50, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.red_mushroom,1,0), 100, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.brown_mushroom,1,0), 100, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.vine,1,0), 100, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.waterlily,1,0), 50, true);
		for(int i = 0; i < 2;i++)
			MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.log2,1,i), 1600, true);
		for(int i = 0; i < 2;i++)
			MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.leaves2,1,i), 20, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.cactus,1,0), 150, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.pumpkin,1,0), 200, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.lit_pumpkin,1,0), 200, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.melon_block,1,0), 800, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Items.melon,1,0), 80, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.hay_block,1,0), 1800, true);
		for(int i = 0; i < 6;i++)
			MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.double_plant,1,i), 20, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.wheat,1,0), 200, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.reeds,1,0), 50, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.carrots,1,0), 200, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Blocks.potatoes,1,0), 200, true);
		
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Items.carrot,1,0), 200, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Items.potato,1,0), 200, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Items.wheat,1,0), 200, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Items.wheat_seeds,1,0), 100, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Items.pumpkin_seeds,1,0), 100, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Items.melon_seeds,1,0), 100, true);
		MgRegister.addBiomassBurnerRecipe(new ItemStack(Items.reeds,1,0), 100, true);
	}
	
	public static ItemStack getDustExtra(String name, int amount, int num){

		if(name.equalsIgnoreCase("Copper")){
			if(num == 0)return getDust("Gold", amount);
			else return getDust("Iron", amount);

		}else if(name.equalsIgnoreCase("Iron")){
			if(num == 0)return getDust("Niquel", amount);
			else return getDust("Aluminium", amount);

		}else if(name.equalsIgnoreCase("Gold")){
			if(num == 0)return getDust("Copper", amount);
			else return getDust("Silver", amount);

		}else if(name.equalsIgnoreCase("Tungsten")){
			if(num == 0)return getDust("Iron", amount);

		}else if(name.equalsIgnoreCase("Uranium")){
			if(num == 0)return getDust("Thorium", amount);
			else return getDust("Plutonium", amount);
			
		}else if(name.equalsIgnoreCase("Thorium")){
			if(num == 0)return getDust("Uranium", amount);
			else return getDust("Plutonium", amount);
			
		}else if(name.equalsIgnoreCase("Aluminium")){
			if(num == 0)return getDust("Iron", amount);
			else return getDust("Titanium", amount);
			
		}else if(name.equalsIgnoreCase("Lead")){
			if(num == 0)return getDust("Silver", amount);
			else return getDust("Thorium", amount);
			
		}else if(name.equalsIgnoreCase("Silver")){
			if(num == 0)return getDust("Lead", amount);
			else return getDust("Copper", amount);
			
		}else if(name.equalsIgnoreCase("Nickel")){
			if(num == 0)return getDust("Iron", amount);
			else return getDust("Titanium", amount);
			
		}else if(name.equalsIgnoreCase("Tin")){
			if(num == 0)return getDust("Iron", amount);
			
		}else if(name.equalsIgnoreCase("Titanium")){
			if(num == 0)return getDust("Iron", amount);
			else return getDust("Niquel", amount);
			
		}else if(name.equalsIgnoreCase("Zinc")){
			if(num == 0)return getDust("Iron", amount);
			else return getDust("Niquel", amount);
		}
		return null;
	}
}















