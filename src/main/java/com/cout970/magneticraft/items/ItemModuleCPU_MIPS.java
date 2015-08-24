package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.computer.IHardwareComponent;
import com.cout970.magneticraft.api.computer.IHardwareProvider;
import com.cout970.magneticraft.api.computer.prefab.ModuleCPU_MIPS;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

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

    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
        super.addInformation(item, player, list, flag);
        list.add(ItemBlockMg.format + "Still WIP");
    }
}
