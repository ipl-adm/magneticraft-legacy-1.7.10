package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.electricity.CompoundElectricCables;
import com.cout970.magneticraft.api.electricity.ConnectionClass;
import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.multiblock.types.MultiblockOilDistillery;

public class TileMB_Energy_Low extends TileMB_Base implements IElectricTile{

	private ElectricConductor cond = new ElectricConductor(this){
		@Override
		public boolean isAbleToConnect(IElectricConductor e, VecInt v) {
			return e.getConnectionClass(v.getOpposite()).orientation == null;
		}
		
		@Override
		public ConnectionClass getConnectionClass(VecInt v) {
			return ConnectionClass.CABLE_LOW;
		}
	};
	
	@Override
	public CompoundElectricCables getConds(VecInt dir, int Vtier) {
		if(Vtier != 0)return null;
		if(this.multi instanceof MultiblockOilDistillery && !VecInt.NULL_VECTOR.equals(dir)){
			if(dire != null && !dire.opposite().toVecInt().equals(dir))return null;
		}
		return new CompoundElectricCables(cond);
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
