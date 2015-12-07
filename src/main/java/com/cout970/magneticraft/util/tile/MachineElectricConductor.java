package com.cout970.magneticraft.util.tile;

import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by cout970 on 04/12/2015.
 */
public class MachineElectricConductor extends ElectricConductor {

    public MachineElectricConductor(TileEntity tile) {
        super(tile);
    }

    public double getVoltageCapacity() {
        return EnergyConverter.RFtoW(0.065D);//aprox 750RF
    }
}
