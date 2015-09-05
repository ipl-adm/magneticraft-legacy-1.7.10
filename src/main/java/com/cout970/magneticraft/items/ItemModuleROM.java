package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.computer.IHardwareComponent;
import com.cout970.magneticraft.api.computer.IHardwareProvider;
import com.cout970.magneticraft.api.computer.prefab.ModuleROM;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import net.minecraft.item.ItemStack;

public class ItemModuleROM extends ItemBasic implements IHardwareProvider {

    public ItemModuleROM(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.InformationAgeTab);
    }

    @Override
    public IHardwareComponent getHardware(ItemStack item) {
        return new ModuleROM();
    }

    @Override
    public ModuleType getModuleType(ItemStack item) {
        return ModuleType.ROM;
    }
}
