package com.cout970.magneticraft.api.computer;

public interface IModuleROM extends IHardwareComponent {

    void loadToRAM(IModuleMemoryController ram);
}
