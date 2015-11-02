package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.ConnectionClass;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IGuiListener;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class TileResistance extends TileBase implements IGuiListener, IGuiSync, IElectricTile {

    public static final VecInt[] color_array = new VecInt[]{new VecInt(0, 0, 0), new VecInt(102, 51, 50), new VecInt(255, 0, 0), new VecInt(255, 102, 0), new VecInt(255, 255, 0), new VecInt(51, 204, 51), new VecInt(103, 102, 255), new VecInt(205, 102, 255), new VecInt(147, 147, 147), new VecInt(255, 255, 255)};
    public IElectricConductor cond1;
    public IElectricConductor cond2;
    public int line1 = 0, line2 = 1, line3 = 0;
    public int select = 0;
    public double I = 0;
    public double resistance = 1;

    public TileResistance() {
        initConductors();
    }

    @Override
    public void onNeigChange() {
        super.onNeigChange();
        cond1.disconnect();
        cond2.disconnect();
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        cond1.recache();
        cond2.recache();

        cond1.iterate();
        cond2.iterate();

        double V = cond1.getVoltage() - cond2.getVoltage();
        I = V / getResistance();
        cond2.applyCurrent(I);
        cond1.applyCurrent(-I);
    }

    public void initConductors() {
        cond1 = new ElectricConductor(this) {
            @Override
            public VecInt[] getValidConnections() {
                return new VecInt[]{getDirection().toVecInt()};
            }

            @Override
            public ConnectionClass getConnectionClass(VecInt v) {
                return ConnectionClass.CABLE_LOW;
            }
        };

        cond2 = new ElectricConductor(this) {
            @Override
            public VecInt[] getValidConnections() {
                return new VecInt[]{getDirection().opposite().toVecInt()};
            }

            @Override
            public ConnectionClass getConnectionClass(VecInt v) {
                return ConnectionClass.CABLE_LOW;
            }
        };
    }

    @Override
    public IElectricConductor[] getConds(VecInt dir, int tier) {
        if (tier != 0) return null;
        if (getDirection().opposite().toVecInt().equals(dir))
            return new IElectricConductor[]{cond2};
        if (getDirection().toVecInt().equals(dir))
            return new IElectricConductor[]{cond1};
        if (VecInt.NULL_VECTOR.equals(dir))
            return new IElectricConductor[]{cond1, cond2};
        return null;
    }

    public double getResistance() {
        return resistance;
    }

    public void setResistance(double res) {
        resistance = res;
    }

    @Override
    public void onMessageReceive(int id, int data) {
        if (id == 0) {
            line1 = data;
        } else if (id == 1) {
            line2 = data;
        } else if (id == 2) {
            line3 = data;
        } else if (id == -2) {
            select = data;
        }
        updateResistance();
        sendUpdateToClient();
    }

    private void updateResistance() {
        if (line1 == 0 && line2 == 0) return;
        double res = 1;
        res *= line2;
        res += line1 * 10;
        for (int i = 0; i < line3; i++)
            res *= 10;
        if (res <= 0) res = 1;
        setResistance(res);
    }

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 1, (int) (I * 1000));
        craft.sendProgressBarUpdate(cont, 2, (int) (cond1.getVoltage() * 1000));
        craft.sendProgressBarUpdate(cont, 3, (int) (cond2.getVoltage() * 1000));
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 1) I = value / 1000d;
        else if (id == 2) cond1.setVoltage(value / 1000d);
        else if (id == 3) cond2.setVoltage(value / 1000d);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        line1 = Math.abs(nbt.getInteger("Line_1") % color_array.length);
        line2 = Math.abs(nbt.getInteger("Line_2") % color_array.length);
        line3 = Math.abs(nbt.getInteger("Line_3") % color_array.length);
        select = nbt.getInteger("Select");
        NBTTagList list = nbt.getTagList("Conductors", 10);
        cond1.load(list.getCompoundTagAt(0));
        cond2.load(list.getCompoundTagAt(1));
        updateResistance();
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Line_1", line1);
        nbt.setInteger("Line_2", line2);
        nbt.setInteger("Line_3", line3);
        updateResistance();
        nbt.setInteger("Select", select);
        NBTTagList list = new NBTTagList();
        NBTTagCompound nbtCond1 = new NBTTagCompound();
        cond1.save(nbtCond1);
        list.appendTag(nbtCond1);
        NBTTagCompound nbtCond2 = new NBTTagCompound();
        cond2.save(nbtCond2);
        list.appendTag(nbtCond2);
        nbt.setTag("Conductors", list);
    }
}
