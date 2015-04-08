package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileReactorActivator extends TileHeatConductor{

	public boolean is_active;
	public List<TileReactorVessel> vessels = new ArrayList<TileReactorVessel>();
	
	@Override
	public IHeatConductor initHeatCond() {
		return new HeatConductor(this, 2000, 1000);
	}

	public void onNeigChange(){
		super.onNeigChange();
		if(Powered && !is_active){
			is_active = true;
		}else if(is_active && !Powered){
			is_active = false;
		}
		search();
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(is_active){
			for(TileReactorVessel v : vessels){
				v.addRadiation(0.05);
			}
		}
	}

	private void search() {
		vessels.clear();
		for(int i = 0;i<3;i++){
			TileEntity t = MgUtils.getTileEntity(this, MgDirection.DOWN.getVecInt().add(0, i, 0));
			if(t instanceof TileReactorVessel){
				vessels.add((TileReactorVessel) t);
			}else{
				break;
			}
		}
	}
	
}
