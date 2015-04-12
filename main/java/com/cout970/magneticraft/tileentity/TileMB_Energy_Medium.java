package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.electricity.CableCompound;
import com.cout970.magneticraft.api.electricity.Conductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileConductorMedium;

public class TileMB_Energy_Medium extends TileMB_Base implements IElectricTile{
	
	public IElectricConductor cond = new Conductor(this,2,ElectricConstants.RESISTANCE_COPPER_2X2);
	
	@Override
	public CableCompound getConds(VecInt dir, int tier) {
		if(tier != 2 && tier != -1)return null;
		return new CableCompound(cond);
	}
	
	@Override
	public void onNeigChange(){
		super.onNeigChange();
		cond.disconect();
	}
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		cond.recache();
		cond.iterate();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		cond.load(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		cond.save(nbt);
	}

}
