package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileVoltmeter extends TileConductorLow {

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this);
    }
}
