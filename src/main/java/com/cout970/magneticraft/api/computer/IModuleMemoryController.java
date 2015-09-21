package com.cout970.magneticraft.api.computer;

import net.minecraft.nbt.NBTTagCompound;

public interface IModuleMemoryController extends IHardwareComponent {

    int readWord(int pos);

    byte readByte(int pos);

    void writeByte(int pos, byte dato);

    void writeWord(int pos, int dato);

    boolean isLittleEndian();

    void setLittelEndian(boolean little);

    int getMemorySize();

    void clear();

    void loadMemory(NBTTagCompound nbt);

    void saveMemory(NBTTagCompound nbt);

    IComputer getComputer();

    void setComputer(IComputer c);
}
