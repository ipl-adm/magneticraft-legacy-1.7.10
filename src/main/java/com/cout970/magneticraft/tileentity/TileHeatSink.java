package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileHeatSink extends TileHeatConductor {

    @Override
    public IHeatConductor initHeatCond() {
        return new HeatConductor(this, 600, 800);
    }

    @Override
    public IHeatConductor[] getHeatCond(VecInt c) {
        return new IHeatConductor[]{heat};
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        if (heat.getTemperature() > 25) {
            double diff = Math.min(heat.getTemperature() - 25, 125);
            heat.drainCalories(EnergyConverter.RFtoCALORIES(diff * 0.05));
        }
    }

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }
}
