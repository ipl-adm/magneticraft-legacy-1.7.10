package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IIndexedConnection;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileElectricSwitch extends TileConductorLow {

    private boolean powerCut;

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this) {
            @Override
            public boolean canFlowPower(IIndexedConnection con) {
                return !powerCut;
            }
        };
    }

    @Override
    public IElectricConductor[] getConds(VecInt dir, int tier) {
        if (tier != 0 && tier != -1) return null;
        return new IElectricConductor[]{cond};
    }

    public void onNeigChange() {
        powerCut = powered = isPowered();
    }

    public void setResistance(double res) {
        cond.setResistance(res);
    }

    public double getResistance() {
        return cond.getResistance();
    }

}
