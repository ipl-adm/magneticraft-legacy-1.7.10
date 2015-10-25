package com.cout970.magneticraft.parts.electric;

import codechicken.multipart.TMultiPart;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricMultiPart;
import com.cout970.magneticraft.parts.MgPart;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

public abstract class PartElectric extends MgPart implements IElectricMultiPart {

    public IElectricConductor cond;
    public boolean toUpdate = true;
    public NBTTagCompound tempNBT;

    public PartElectric(Item i) {
        super(i);
    }

    public abstract void create();

    public IElectricConductor getElectricConductor(int t) {
        if (t == -1 || t == getTier()) return cond;
        return null;
    }

    public void update() {
        super.update();
        if (tile() == null) return;
        if (toUpdate) {
            if (cond == null)
                create();
            toUpdate = false;
            updateConnections();
        }
        if (world().isRemote && world().getTotalWorldTime() % 10 == 0) {
            updateConnections();
        }
        if (tempNBT != null) {
            cond.load(tempNBT);
            tempNBT = null;
        }
        cond.recache();
        cond.iterate();
    }

    @Override
    public void onNeighborChanged() {
        super.onNeighborChanged();
        toUpdate = true;
        if (cond != null) {
            cond.disconnect();
        }
    }

    @Override
    public void onPartChanged(TMultiPart part) {
        onNeighborChanged();
    }

    @Override
    public void onAdded() {
        onNeighborChanged();
    }

    public void save(NBTTagCompound nbt) {
        super.save(nbt);
        if (tile() == null) return;
        if (cond != null)
            cond.save(nbt);
    }

    public void load(NBTTagCompound nbt) {
        super.load(nbt);
        tempNBT = nbt;
    }

    public abstract int getTier();

    public abstract void updateConnections();
}
