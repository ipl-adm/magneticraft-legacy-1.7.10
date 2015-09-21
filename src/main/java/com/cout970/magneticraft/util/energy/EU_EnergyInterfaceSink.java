package com.cout970.magneticraft.util.energy;

import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.electricity.IIndexedConnection;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import ic2.api.energy.tile.IEnergySink;
import net.minecraft.tileentity.TileEntity;

public class EU_EnergyInterfaceSink implements IEnergyInterface {

    private IEnergySink tile;
    private MgDirection dir;

    public EU_EnergyInterfaceSink(IEnergySink t, MgDirection dir) {
        tile = t;
        this.dir = dir;
    }

    @Override
    public double applyEnergy(double watts) {
        return watts - EnergyConverter.EUtoW(tile.injectEnergy(dir.toForgeDir(), EnergyConverter.WtoEU(watts), 512));
    }

    @Override
    public double getCapacity() {
        return -1;
    }

    @Override
    public double getEnergyStored() {
        return -1;
    }

    @Override
    public double getMaxFlow() {
        return EnergyConverter.EUtoW(512);
    }

    @Override
    public boolean canConnect(VecInt f) {
        return true;
    }

    @Override
    public TileEntity getParent() {
        return null;
    }

    @Override
    public boolean canAcceptEnergy(IIndexedConnection f) {
        return true;
    }

}
