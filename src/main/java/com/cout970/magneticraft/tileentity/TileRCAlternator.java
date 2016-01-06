package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.IConnectable;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import mods.railcraft.api.electricity.IElectricGrid;
import mods.railcraft.api.electricity.IElectricGrid.ChargeHandler.ConnectType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileRCAlternator extends TileConductorLow implements IElectricGrid {

    private ChargeHandler charge = new ChargeHandler(this, ConnectType.BLOCK);
    public int maxStorage = 10000;
    public double level = ElectricConstants.MAX_VOLTAGE;

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this) {
            @Override
            public void iterate() {
                super.iterate();
                if (!isControlled()) return;
                if (getVoltage() < level && charge.getCharge() > 0) {
                    int change;
                    change = (int) Math.min((level - getVoltage()) * 10, 512);
                    change = (int) Math.min(change, charge.getCharge());
                    applyPower(EnergyConverter.RCtoW(change));
                    charge.addCharge(-change);
                } else if (getVoltage() > level && charge.getCharge() < maxStorage) {
                    int change;
                    change = (int) Math.min((getVoltage() - level) * 10, 512);
                    change = (int) Math.min(change, maxStorage - charge.getCharge());
                    drainPower(EnergyConverter.RCtoW(change));
                    charge.addCharge(change);
                }
            }

            @Override
            public int getStorage() {
                return (int) charge.getCharge();
            }

            @Override
            public int getMaxStorage() {
                return maxStorage;
            }

            @Override
            public void setStorage(int charg) {
                charge.setCharge(charg);
            }

            @Override
            public void applyCharge(int charg) {
                charge.addCharge(charg);
                if (charge.getCharge() > maxStorage)
                    charge.addCharge(maxStorage);
            }

            @Override
            public void drainCharge(int charg) {
                charge.addCharge(-charg);
                if (charge.getCharge() < 0) charge.setCharge(0);
            }

            @Override
            public VecInt[] getValidConnections() {
                return new VecInt[]{getDirection().opposite().toVecInt()};
            }

            @Override
            public boolean isAbleToConnect(IConnectable c, VecInt d) {
                return getDirection().opposite().toVecInt().equals(d);
            }
        };
    }

    @Override
    public ChargeHandler getChargeHandler() {
        return charge;
    }

    @Override
    public TileEntity getTile() {
        return this;
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        charge.tick();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        charge.readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        charge.writeToNBT(nbt);
    }

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }

}
