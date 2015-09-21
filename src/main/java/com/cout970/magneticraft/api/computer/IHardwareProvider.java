package com.cout970.magneticraft.api.computer;

import net.minecraft.item.ItemStack;

public interface IHardwareProvider {

    IHardwareComponent getHardware(ItemStack item);

    ModuleType getModuleType(ItemStack item);

    enum ModuleType {
        CPU, RAM, ROM, DISK, GPU, PERIPHERAL
    }
}
