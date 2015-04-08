package com.cout970.magneticraft.parts.micro;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import codechicken.multipart.TMultiPart;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IPartConductor;

public abstract class ElectricPart extends MgPart implements IPartConductor{

	public IElectricConductor cond;
	public boolean toUpdate = true;
	public NBTTagCompound tempNBT;
	
	public ElectricPart(Item i) {
		super(i);
	}
	
	public abstract void create();
	
	public IElectricConductor getCond(int t){
		if(t == -1 || t == getTier())return cond;
		return null;
	}
	
	public void update(){
		super.update();
		if(tile() == null)return;
		if(toUpdate){
			if(cond == null)
				create();
			toUpdate = false;
			updateConnections();
		}
		if(world().isRemote && world().getWorldTime() % 10 == 0){
			updateConnections();
		}
		if(tempNBT != null){
			cond.load(tempNBT);
			tempNBT = null;
		}
		cond.recache();
		cond.iterate();
	}
	
	@Override
	public void onNeighborChanged() {
		super.onNeighborChanged();
		toUpdate = true;
		if(cond != null){
			cond.disconect();
		}
	}

	@Override
	public void onPartChanged(TMultiPart part) {
		onNeighborChanged();
	}

	@Override
	public void onAdded() {
		onNeighborChanged();
	}
	
	public void save(NBTTagCompound nbt){
		super.save(nbt);
		if(tile() == null)return;
		if(cond != null)
			cond.save(nbt);
	}

	public void load(NBTTagCompound nbt){
		super.load(nbt);
		tempNBT = nbt;
	}
	
	public abstract int getTier();

	public abstract void updateConnections();
}
