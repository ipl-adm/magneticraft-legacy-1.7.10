package com.cout970.magneticraft.tileentity.multiblock;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.ConnectionClass;
import com.cout970.magneticraft.api.util.IConnectable;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.multiblock.types.MultiblockOilDistillery;
import net.minecraft.nbt.NBTTagCompound;

public class TileMB_Energy_Low extends TileMB_Base implements IElectricTile {

    private ElectricConductor cond = new ElectricConductor(this) {
        @Override
        public boolean isAbleToConnect(IConnectable e, VecInt v) {
            return true;
        }

        @Override
        public ConnectionClass getConnectionClass(VecInt v) {
            return ConnectionClass.CABLE_LOW;
        }
    };

    @Override
    public IElectricConductor[] getConds(VecInt dir, int Vtier) {
        if (Vtier != 0) return null;
        if (this.multi instanceof MultiblockOilDistillery && !VecInt.NULL_VECTOR.equals(dir)) {
            if (dire != null && !dire.opposite().toVecInt().equals(dir)) return null;
        }
        return new IElectricConductor[]{cond};
    }

    @Override
    public void onNeigChange() {
        super.onNeigChange();
        cond.disconnect();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        cond.recache();
        cond.iterate();
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        cond.load(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        cond.save(nbt);
    }
}
