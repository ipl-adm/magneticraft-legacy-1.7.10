package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.electricity.CableCompound;
import com.cout970.magneticraft.api.electricity.Conductor;
import com.cout970.magneticraft.api.electricity.ConnectionClass;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

public class TileTransformerLow_Medium extends TileBase implements IElectricTile{

	public IElectricConductor low = new Conductor(this, ElectricConstants.RESISTANCE_COPPER_2X2){
		@Override
		public boolean isAbleToConnect(IElectricConductor e, VecInt v) {
			return e.getConnectionClass(v.getOpposite()) == ConnectionClass.FULL_BLOCK || e.getConnectionClass(v.getOpposite()) == ConnectionClass.CABLE_LOW;
		}
		
		@Override
		public ConnectionClass getConnectionClass(VecInt v) {
			return ConnectionClass.CABLE_LOW;
		}
	};
	public IElectricConductor medium = new Conductor(this,2, ElectricConstants.RESISTANCE_COPPER_2X2);
	public double flow;

	@Override
	public CableCompound getConds(VecInt dir, int tier) {
		if(VecInt.NULL_VECTOR == dir){
			return tier == 0 ? new CableCompound(low) : tier == 2 ? new CableCompound(medium) : tier == -1 ? new CableCompound(low) : null;
		}
		MgDirection d = dir.toMgDirection();
		if(d == MgDirection.getDirection(getBlockMetadata()) && tier == 0)return new CableCompound(low);
		if(d == MgDirection.getDirection(getBlockMetadata()).opposite() && tier == 2)return new CableCompound(medium);
		return null;
	}

	@Override
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		low.recache();
		medium.recache();

		low.iterate();
		medium.iterate();
		
		double resistence = low.getResistance() + medium.getResistance();
		double difference = low.getVoltage()*(medium.getVoltageMultiplier()/low.getVoltageMultiplier()) - medium.getVoltage();
		double change = flow;
		double slow = change * resistence;
		flow += ((difference - slow) * medium.getIndScale())/medium.getVoltageMultiplier();
		change += (difference * medium.getCondParallel())/medium.getVoltageMultiplier();
		low.applyCurrent(-change);
		medium.applyCurrent(change);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		NBTTagCompound nbtl = nbt.getCompoundTag("c1");
        NBTTagCompound nbtm = nbt.getCompoundTag("c2");
		low.load(nbtl);
		medium.load(nbtm);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		NBTTagCompound nbtl = new NBTTagCompound();
		low.save(nbtl);
		NBTTagCompound nbtm = new NBTTagCompound();
		medium.save(nbtm);
		nbt.setTag("c1", nbtl);
		nbt.setTag("c2", nbtm);
		nbt.setDouble("Flow", flow);
	}
}
