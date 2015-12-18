package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.ConnectionClass;
import com.cout970.magneticraft.api.util.IConnectable;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.nbt.NBTTagCompound;

public class TileTransformerLow_Medium extends TileBase implements IElectricTile {

    public IElectricConductor low = new ElectricConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW) {
        @Override
        public boolean isAbleToConnect(IConnectable e, VecInt v) {
            return (v.toMgDirection() == MgDirection.getDirection(getBlockMetadata()))
                    && ((e.getConnectionClass(v.getOpposite()) == ConnectionClass.FULL_BLOCK)
                    || (e.getConnectionClass(v.getOpposite()) == ConnectionClass.CABLE_LOW));
        }

        @Override
        public ConnectionClass getConnectionClass(VecInt v) {
            return ConnectionClass.CABLE_LOW;
        }
    };
    public IElectricConductor medium = new ElectricConductor(this, 1, ElectricConstants.RESISTANCE_COPPER_LOW){
        public double getVoltageCapacity() {
            return ElectricConstants.CABLE_MEDIUM_CAPACITY;
        }
    };
    public double flow;

    @Override
    public IElectricConductor[] getConds(VecInt dir, int tier) {
        if (VecInt.NULL_VECTOR.equals(dir)) {
            return tier == 0 ? new IElectricConductor[]{low} : tier == 1 ? new IElectricConductor[]{medium} : null;
        }
        MgDirection d = dir.toMgDirection();
        if (d == MgDirection.getDirection(getBlockMetadata()) && tier == 0)
            return new IElectricConductor[]{low};
        if (d == MgDirection.getDirection(getBlockMetadata()).opposite() && tier == 1)
            return new IElectricConductor[]{medium};
        return null;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        low.recache();
        medium.recache();
        for(int i = 0; i < 2; i++) {
            low.iterate();
            medium.iterate();

            double resistence = low.getResistance() + medium.getResistance();
            double difference = low.getVoltage() * (medium.getVoltageMultiplier() / low.getVoltageMultiplier()) - medium.getVoltage();
            double change = flow;
            double slow = change * resistence;
            flow += ((difference - slow) * medium.getIndScale()) / medium.getVoltageMultiplier();
            change += (difference * 0.5D) / medium.getVoltageMultiplier();
            low.applyCurrent(-change);
            medium.applyCurrent(change);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagCompound nbtl = nbt.getCompoundTag("c1");
        NBTTagCompound nbtm = nbt.getCompoundTag("c2");
        low.load(nbtl);
        medium.load(nbtm);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagCompound nbtl = new NBTTagCompound();
        low.save(nbtl);
        NBTTagCompound nbtm = new NBTTagCompound();
        medium.save(nbtm);
        nbt.setTag("c1", nbtl);
        nbt.setTag("c2", nbtm);
        nbt.setDouble("Flow", flow);
    }
}
