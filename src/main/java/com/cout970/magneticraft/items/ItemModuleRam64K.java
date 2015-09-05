package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.computer.IHardwareComponent;
import com.cout970.magneticraft.api.computer.IHardwareProvider;
import com.cout970.magneticraft.api.computer.prefab.ModuleMemoryController;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import net.minecraft.item.ItemStack;

public class ItemModuleRam64K extends ItemBasic implements IHardwareProvider {

    public ItemModuleRam64K(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.InformationAgeTab);
    }

    @Override
    public IHardwareComponent getHardware(ItemStack item) {
        return new ModuleMemoryController(0x10000, false, 8);
    }

    @Override
    public ModuleType getModuleType(ItemStack item) {
        return ModuleType.RAM;
    }
}
