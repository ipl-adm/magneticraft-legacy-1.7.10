package com.cout970.magneticraft.util.tile;

import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tileentity.TileBase;

public abstract class TileHeatConductor extends TileBase implements IHeatTile{

	public IHeatConductor heat = initHeatCond();
	public int oldHeat;
	
	@Override
	public IHeatConductor getHeatCond(VecInt c) {
		return heat;
	}
	
	public abstract IHeatConductor initHeatCond();

	public void updateEntity(){
		super.updateEntity();
		if(!this.worldObj.isRemote){
			heat.iterate();
			if(((int)heat.getTemperature()) != oldHeat && worldObj.provider.getWorldTime() % 10 == 0){
				sendUpdateToClient();
				oldHeat = (int) heat.getTemperature();
			}
		}
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		heat.load(nbt);
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		heat.save(nbt);
	}

//	@Override
//	public int getTemperature() {
//		return (int) (heat.getTemperature()*ConversionRatio);
//	}
//
//	@Override
//	public void setTemperature(int T) {
//		heat.setTemperature(T/ConversionRatio);
//	}
//
//	@Override
//	public void addTemperature(int T) {
//		heat.applyCalories(T/ConversionRatio);
//	}
//
//	@Override
//	public String getName() {
//		return "Magneticraft Heat Handler";
//	}
//
//	@Override
//	public int getMaxTemperature() {
//		return (int) heat.getMaxTemp();
//	}
//
//	@Override
//	public void onOverheat(World world, int x, int y, int z) {
//		
//	}
//
//	@Override
//	public boolean canBeFrictionHeated() {
//		return false;
//	}
}
