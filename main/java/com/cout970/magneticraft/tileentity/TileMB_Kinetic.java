package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.cout970.magneticraft.api.kinetic.IKineticConductor;
import com.cout970.magneticraft.api.kinetic.KineticConductor;
import com.cout970.magneticraft.api.kinetic.KineticType;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.multiblock.MB_Tile_Remplaced;
import com.cout970.magneticraft.util.tile.TileKineticConductor;

public class TileMB_Kinetic extends TileKineticConductor implements MB_Tile_Remplaced{

	public List<ItemStack> drops = new ArrayList<ItemStack>();
	
	@Override
	public MgDirection[] getValidSides() {
		return new MgDirection[]{getDirection(),getDirection().opposite()};
	}

	public MgDirection getDirection() {
		return MgDirection.getDirection(getBlockMetadata());
	}

	@Override
	public IKineticConductor initKineticCond() {
		return new KineticConductor(this, 50);
	}
	
	public void updateEntity(){
		super.updateEntity();
	}
	

	
	@Override
	public void setDrops(List<ItemStack> drop) {
		drops.clear();
		if(drop != null){
			drops.addAll(drop);
		}
	}

	@Override
	public List<ItemStack> getDrops() {
		return drops;
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		NBTTagList list = new NBTTagList();
		nbt.setInteger("dropSize", drops.size());
		for(int i=0;i<drops.size();i++){
			NBTTagCompound t = new NBTTagCompound();
			if(drops.get(i) != null){
				drops.get(i).writeToNBT(t);
			}
			list.appendTag(t);
		}
		nbt.setTag("Drops", list);
	}

	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		drops.clear();
		int dropSize = nbt.getInteger("dropSize");
		ItemStack item = null;
		NBTTagList list = nbt.getTagList("Drops",10);
		for(int i=0;i<dropSize;i++){
			NBTTagCompound t = list.getCompoundTagAt(i);
			item = ItemStack.loadItemStackFromNBT(t);
			drops.add(item);
		}
	}
}
