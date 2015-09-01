package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.computer.IPeripheral;
import com.cout970.magneticraft.api.computer.IPeripheralProvider;
import com.cout970.magneticraft.api.computer.prefab.MonitorPeripheral;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IClientInformer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileTextMonitor extends TileBase implements IClientInformer, IGuiSync, IPeripheralProvider {

    public MonitorPeripheral monitor = new MonitorPeripheral(this);


    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        monitor.load(nbt);
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        monitor.save(nbt);
    }

    @Override
    public void saveInfoToMessage(NBTTagCompound nbt) {
        monitor.saveInfoToMessage(nbt);
    }

    @Override
    public void loadInfoFromMessage(NBTTagCompound nbt) {
        monitor.loadInfoFromMessage(nbt);
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        sendUpdateToClient();
    }

    @Override
    public void getGUINetworkData(int id, int value) {
    }

    @Override
    public TileEntity getParent() {
        return this;
    }

    @Override
    public IPeripheral[] getPeripherals() {
        return new IPeripheral[]{monitor};
    }
}
