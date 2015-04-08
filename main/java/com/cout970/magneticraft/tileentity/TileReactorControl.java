package com.cout970.magneticraft.tileentity;

import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileReactorControl extends TileHeatConductor{

	public boolean is_activate;
	public int deep = -1;
	public int percent = 10;

	@Override
	public IHeatConductor initHeatCond() {
		return new HeatConductor(this, 2000, 1000);
	}
	
	public void onNeigChange(){
		super.onNeigChange();
		if(Powered && !is_activate){
			is_activate = true;
			activate();
		}else if(is_activate && !Powered){
			is_activate = false;
			desactivate();
		}
	}

	private void desactivate() {
		deep = -1;
		for(int i = 0;i<3;i++){
			TileEntity t = worldObj.getTileEntity(xCoord, yCoord+1+i, zCoord);
			if(t instanceof TileReactorVessel){
				((TileReactorVessel) t).desactivateControlsRods();
			}else{
				break;
			}
		}
	}

	private void activate() {
		deep = -1;
		for(int i = 0;i<3;i++){
			TileEntity t = worldObj.getTileEntity(xCoord, yCoord+1+i, zCoord);
			if(t instanceof TileReactorVessel){
				((TileReactorVessel) t).activateControlRods(percent);
			}else{
				break;
			}
			deep = i;
		}
	}

}
