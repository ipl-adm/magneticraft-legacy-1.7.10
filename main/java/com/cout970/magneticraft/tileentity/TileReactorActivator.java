package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileReactorActivator extends TileHeatConductor{

	public List<TileReactorVessel> vessels = new ArrayList<TileReactorVessel>();
	private int level;
	
	@Override
	public IHeatConductor initHeatCond() {
		return new HeatConductor(this, 2000, 1000);
	}

	public void onNeigChange(){
		super.onNeigChange();
		search();
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		if(worldObj.getWorldTime() % 20 == 0){
			search();
		}
		if(level > 0){
			for(TileReactorVessel v : vessels){
				v.addRadiation(Math.pow(10, level));
			}
		}
	}
	
	public void setLevel(int level){
		this.level = level;
	}
	
	public int getLevel(){
		return level;
	}

	private void search() {
		vessels.clear();
		for(int i = 0;i<5;i++){
			TileEntity t = MgUtils.getTileEntity(this, MgDirection.DOWN.getVecInt().add(0, -i, 0));
			if(t instanceof TileReactorVessel){
				vessels.add((TileReactorVessel) t);
			}else{
				break;
			}
		}
	}
	
}
