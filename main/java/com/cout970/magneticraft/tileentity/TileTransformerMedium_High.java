package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.electricity.CableCompound;
import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

public class TileTransformerMedium_High extends TileBase implements IElectricTile{

	public IElectricConductor medium = new ElectricConductor(this,2, ElectricConstants.RESISTANCE_COPPER_2X2);
	public IElectricConductor high = new ElectricConductor(this,4, ElectricConstants.RESISTANCE_COPPER_2X2);
	public double flow;

	@Override
	public CableCompound getConds(VecInt dir,int tier) {
		if(VecInt.NULL_VECTOR == dir){
			return tier == 2 ? new CableCompound(medium) : tier == 4 ? new CableCompound(high) : tier == -1 ? new CableCompound(medium) : null;
		}
		MgDirection d = dir.toMgDirection();
		if(d == MgDirection.getDirection(getBlockMetadata()) && tier == 2)return new CableCompound(medium);
		if(d == MgDirection.getDirection(getBlockMetadata()).opposite() && tier == 4)return new CableCompound(high);
		return null;
	}

	@Override
	public void updateEntity(){
		super.updateEntity();
		high.recache();
		medium.recache();
		medium.iterate();
		high.iterate();
		
		double resistence = medium.getResistance() + high.getResistance();
		double difference = medium.getVoltage()*(high.getVoltageMultiplier()/medium.getVoltageMultiplier()) - high.getVoltage();
		double change = flow;
		double slow = change * resistence;
		flow += ((difference - slow) * high.getIndScale())/high.getVoltageMultiplier();
		change += (difference * high.getCondParallel())/high.getVoltageMultiplier();
		medium.applyCurrent(-change);
		high.applyCurrent(change);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		NBTTagCompound nbtl = nbt.getCompoundTag("c2");
        NBTTagCompound nbtm = nbt.getCompoundTag("c1");
		high.load(nbtl);
		medium.load(nbtm);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		NBTTagCompound nbtl = new NBTTagCompound();
		high.save(nbtl);
		NBTTagCompound nbtm = new NBTTagCompound();
		medium.save(nbtm);
		nbt.setTag("c2", nbtl);
		nbt.setTag("c1", nbtm);
	}
}
