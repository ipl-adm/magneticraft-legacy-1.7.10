package com.cout970.magneticraft.parts.heat;

import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.multipart.TMultiPart;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatMultipart;
import com.cout970.magneticraft.parts.MgPart;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public abstract class PartHeat extends MgPart implements IHeatMultipart {

    public IHeatConductor heat;
    public boolean toUpdate = true;
    public int oldHeat = -1;
    public List<NBTTagCompound> tempNBT = new ArrayList<>();
    private int counter;

    public PartHeat(Item i) {
        super(i);
    }

    @Override
    public IHeatConductor getHeatConductor() {
        return heat;
    }

    public abstract void create();

    public void update() {
        super.update();
        if (tile() == null) return;
        if (toUpdate) {
            if (heat == null) create();
            toUpdate = false;
            updateConnections();
            oldHeat = -1;
        }
        if (!tempNBT.isEmpty()) {
            heat.load(tempNBT.get(0));
            tempNBT.remove(0);
        }
        if (W().isRemote && W().getTotalWorldTime() % 10 == 0) {
            updateConnections();
        }
        if (!W().isRemote && W().getTotalWorldTime() % 20 == 0) {
            counter++;
            if (((int) heat.getTemperature()) != oldHeat || counter >= 10) {
                oldHeat = (int) heat.getTemperature();
                counter = 0;
                sendDescUpdate();
            }
        }
        if (W().isRemote) return;
        heat.iterate();
    }

    public void writeDesc(MCDataOutput p) {
        super.writeDesc(p);
        if (heat != null) {
            p.writeDouble(heat.getTemperature());
        } else {
            p.writeDouble(0);
        }
    }

    public void readDesc(MCDataInput p) {
        super.readDesc(p);
        if (heat != null) {
            heat.setTemperature(p.readDouble());
        } else {
            p.readDouble();
        }
    }

    @Override
    public void onNeighborChanged() {
        super.onNeighborChanged();
        toUpdate = true;
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
        if (heat != null)
            heat.save(nbt);
    }

    public void load(NBTTagCompound nbt) {
        super.load(nbt);
        tempNBT.add(nbt);
    }

    public abstract void updateConnections();
}
