package com.cout970.magneticraft.parts.micro;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.multipart.TMultiPart;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatMultipart;
import com.cout970.magneticraft.util.Log;

public abstract class PartHeat extends MgPart implements IHeatMultipart{

	public IHeatConductor heat;
	public boolean toUpdate = true;
	public int oldHeat;
	public NBTTagCompound tempNBT;
	
	public PartHeat(Item i) {
		super(i);
	}
	
	@Override
	public IHeatConductor getHeatConductor() {
		return heat;
	}
	
	public abstract void create();
	
	public void update(){
		super.update();
		if(tile() == null)return;
		if(toUpdate){
			if(heat == null)
				create();
			toUpdate = false;
			updateConnections();
		}
		if(world().isRemote && world().getWorldTime() % 10 == 0){
			updateConnections();
		}
		if(!world().isRemote && world().getWorldTime() % 20 == 0){
			if(((int)heat.getTemperature()) != oldHeat){
				oldHeat = (int) heat.getTemperature();
				sendDescUpdate();
			}
		}
		if(tempNBT != null){
			heat.load(tempNBT);
			tempNBT = null;
		}
		heat.iterate();
	}

	public void writeDesc(MCDataOutput p){
		super.writeDesc(p);
		if(heat != null){
			p.writeDouble(heat.getTemperature());
		}else{
			p.writeDouble(0);
		}
	}

	public void readDesc(MCDataInput p){
		super.readDesc(p);
		if(heat != null){
			heat.setTemperature(p.readDouble());
		}else{
			p.readDouble();
		}
	}
	
	@Override
	public void onNeighborChanged() {
		super.onNeighborChanged();
		toUpdate = true;
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
		if(heat != null)
			heat.save(nbt);
	}

	public void load(NBTTagCompound nbt){
		super.load(nbt);
		tempNBT = nbt;
	}

	public abstract void updateConnections();
}
