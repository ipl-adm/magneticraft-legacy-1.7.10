package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.VecInt;

import java.util.Arrays;

public class TileInfiniteEnergy extends TileBase implements IElectricTile {

    private IElectricConductor cond0 = new ElectricConductor(this, 0, 0.1) {
        @Override
        public void computeVoltage() {
            V = ElectricConstants.MAX_VOLTAGE * getVoltageMultiplier();
            I = 0;
            if(currents != null)
                Arrays.fill(currents, 0D);
            Itot = Iabs * 0.5;
            Iabs = 0;
        }
    };
    private IElectricConductor cond1 = new ElectricConductor(this, 1, 0.1) {
        @Override
        public void computeVoltage() {
            V = ElectricConstants.MAX_VOLTAGE * getVoltageMultiplier();
            I = 0;
            Itot = Iabs * 0.5;
            Iabs = 0;
        }
    };

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        cond0.recache();
        cond1.recache();

        cond0.iterate();
        cond1.iterate();
    }

    @Override
    public IElectricConductor[] getConds(VecInt dir, int Vtier) {
        if (Vtier == 0) return new IElectricConductor[]{cond0};
        if (Vtier == 1) return new IElectricConductor[]{cond1};
        return null;
    }

}
