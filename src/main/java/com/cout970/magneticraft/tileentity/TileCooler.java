package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileCooler extends TileHeatConductor {

    @Override
    public IHeatConductor initHeatCond() {
        return new HeatConductor(this, 1400D, 1000.0D);
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        if (heat.getTemperature() > 25) {
            double a = heat.getTemperature() - 25;
            a *= a;
            a *= 0.0002;
            this.heat.drainCalories(EnergyConverter.RFtoCALORIES(a));
        }
    }

}
