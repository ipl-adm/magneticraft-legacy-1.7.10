package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.computer.IHardwareComponent;
import com.cout970.magneticraft.api.computer.IHardwareProvider;
import com.cout970.magneticraft.api.computer.prefab.ModuleCPU_MIPS;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import net.minecraft.item.ItemStack;

public class ItemModuleCPU_MIPS extends ItemBasic implements IHardwareProvider {

    public ItemModuleCPU_MIPS(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.InformationAgeTab);
    }

    @Override
    public IHardwareComponent getHardware(ItemStack item) {
        return new ModuleCPU_MIPS();
    }

    @Override
    public ModuleType getModuleType(ItemStack item) {
        return ModuleType.CPU;
    }
}
