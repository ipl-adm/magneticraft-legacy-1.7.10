package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.ManagerConfig;
import com.cout970.magneticraft.api.acces.RecipeCrusher;
import com.cout970.magneticraft.block.BlockMg;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileHammerTable extends TileBase{

	private ItemStack ore;
	public int progress;
	public int maxProgress;
	
	public void onBlockBreaks(){
		if(ore != null){
			BlockMg.dropItem(ore, worldObj.rand, xCoord, yCoord, zCoord, worldObj);
		}
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		ore = ItemStack.loadItemStackFromNBT(nbt);
		progress = nbt.getInteger("progress");
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		if(ore != null){
			ore.writeToNBT(nbt);
		}
		nbt.setInteger("progress", progress);
	}

	public void setInput(ItemStack i) {
		ore = i;
	}
	
	public ItemStack getInput(){
		return ore;
	}

	public void tick(int maxHits) {
		maxProgress = maxHits;
		progress++;
//		addParticles();
		if(progress >= maxProgress){
			maxProgress = 0;
			progress = 0;
			RecipeCrusher rec = RecipeCrusher.getRecipe(ore);
			ore = null;
			ItemStack out = rec.getOutput2().copy();
			if(ManagerConfig.hammerTableDrops){
				BlockMg.dropItem(out, worldObj.rand, xCoord, yCoord, zCoord, worldObj);
			}else{
				ore = out;
			}
		}
	}

	public boolean canWork() {
		return ore != null && RecipeCrusher.getRecipe(ore) != null;
	}
}
