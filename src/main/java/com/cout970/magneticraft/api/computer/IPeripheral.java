package com.cout970.magneticraft.api.computer;

import net.minecraft.tileentity.TileEntity;


public interface IPeripheral {

    int getAddress();

    void setAddress(int address);

    boolean isActive();

    String getName();

    int readByte(int pointer);

    void writeByte(int pointer, int data);

    TileEntity getParent();
}
