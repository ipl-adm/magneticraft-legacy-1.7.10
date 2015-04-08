package com.cout970.magneticraft.tileentity;

import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileHeatCable extends TileHeatConductor{

	public byte conMask = -1;

	@Override
	public IHeatConductor initHeatCond() {
		return new HeatConductor(this, 1400, 1000.0D);
	}
	
	public void reconect(){
		conMask = 0;
		for(MgDirection d : MgDirection.values()){
			TileEntity t = MgUtils.getTileEntity(this, d);
			IHeatConductor c = MgUtils.getHeatCond(t, d.getVecInt().getOpposite());
			if(c != null){
				conMask |= (1 << d.ordinal());
			}
		}
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(conMask == -1){
			reconect();
		}else if(worldObj.getWorldTime() % 20 == 0){
			reconect();
		}
		
	}
	
	public void onNeigChange(){
		super.onNeigChange();
		conMask = -1;
	}
}
