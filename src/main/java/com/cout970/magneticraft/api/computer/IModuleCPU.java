package com.cout970.magneticraft.api.computer;

import net.minecraft.nbt.NBTTagCompound;

public interface IModuleCPU extends IHardwareComponent {

    void iterate();

    boolean isRunning();

    void start();

    void stop();

    void haltTick();

    int getRegister(int reg);

    void setRegister(int reg, int value);

    int getRegPC();

    void setRegPC(int value);

    void connectMemory(IModuleMemoryController ram);

    void loadRegisters(NBTTagCompound nbt);

    void saveRegisters(NBTTagCompound nbt);

}
