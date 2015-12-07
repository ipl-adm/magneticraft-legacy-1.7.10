package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.nbt.NBTTagCompound;

public class TileTransformerMedium_High extends TileBase implements IElectricTile {

    public IElectricConductor medium = new ElectricConductor(this, 1, ElectricConstants.RESISTANCE_COPPER_LOW){
        public double getVoltageCapacity() {
            return ElectricConstants.CABLE_MEDIUM_CAPACITY;
        }
    };
    public IElectricConductor high = new ElectricConductor(this, 2, ElectricConstants.RESISTANCE_COPPER_LOW){
        public double getVoltageCapacity() {
            return ElectricConstants.CABLE_HIGH_CAPACITY;
        }
    };
    public double flow;

    @Override
    public IElectricConductor[] getConds(VecInt dir, int tier) {
        if (VecInt.NULL_VECTOR.equals(dir)) {
            return tier == 1 ? new IElectricConductor[]{medium} : tier == 2 ? new IElectricConductor[]{high} : null;
        }
        MgDirection d = dir.toMgDirection();
        if (d == MgDirection.getDirection(getBlockMetadata()) && tier == 1) return new IElectricConductor[]{medium};
        if (d == MgDirection.getDirection(getBlockMetadata()).opposite() && tier == 2)
            return new IElectricConductor[]{high};
        return null;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if(worldObj.isRemote)
            return;
        high.recache();
        medium.recache();
        for(int i = 0; i < 2; i++) {
            medium.iterate();
            high.iterate();

            double resistence = medium.getResistance() + high.getResistance();
            double difference = medium.getVoltage() * (high.getVoltageMultiplier() / medium.getVoltageMultiplier()) - high.getVoltage();
            double change = flow;
            double slow = change * resistence;
            flow += ((difference - slow) * high.getIndScale()) / high.getVoltageMultiplier();
            change += (difference * 0.5D) / high.getVoltageMultiplier();
            medium.applyCurrent(-change);
            high.applyCurrent(change);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagCompound nbtl = nbt.getCompoundTag("c2");
        NBTTagCompound nbtm = nbt.getCompoundTag("c1");
        high.load(nbtl);
        medium.load(nbtm);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagCompound nbtl = new NBTTagCompound();
        high.save(nbtl);
        NBTTagCompound nbtm = new NBTTagCompound();
        medium.save(nbtm);
        nbt.setTag("c2", nbtl);
        nbt.setTag("c1", nbtm);
    }
}
