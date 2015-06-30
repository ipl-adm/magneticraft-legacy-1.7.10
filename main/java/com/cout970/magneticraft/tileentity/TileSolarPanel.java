package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ConnectionClass;
import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileSolarPanel extends TileConductorLow{

	@Override
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		if (this.cond.getVoltage() <= ElectricConstants.MAX_VOLTAGE){
            if (this.worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord)){
                if (this.worldObj.isDaytime()){
                    if (!this.worldObj.provider.hasNoSky){
                        this.cond.applyPower(EnergyConversor.RFtoW(5));
                    }
                }
            }
        }
	}

	@Override
	public IElectricConductor initConductor() {
		return new ElectricConductor(this){			
			@Override
			public boolean isAbleToConnect(IElectricConductor e, VecInt v) {
				return e.getConnectionClass(v.getOpposite()) == ConnectionClass.FULL_BLOCK || e.getConnectionClass(v.getOpposite()) == ConnectionClass.SLAB_BOTTOM || VecInt.fromDirection(MgDirection.DOWN).equals(v);
			}
			
			@Override
			public ConnectionClass getConnectionClass(VecInt v) {
				if(v.toMgDirection() == MgDirection.DOWN)return ConnectionClass.FULL_BLOCK;
				return ConnectionClass.SLAB_BOTTOM;
			}
		};
	}
}
