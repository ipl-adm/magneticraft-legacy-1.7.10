package com.cout970.magneticraft.items;

import java.io.File;

import com.cout970.magneticraft.api.computer.IStorageDevice;
import com.cout970.magneticraft.api.computer.prefab.ComputerUtils;
import com.cout970.magneticraft.api.util.NBTUtils;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import net.minecraft.item.ItemStack;

public class ItemHardDrive extends ItemBasic implements IStorageDevice {

    public static final int DISK_SIZE = 0x100000; //1024kB

    public ItemHardDrive(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.InformationAgeTab);
    }

    @Override
    public File getAssociateFile(ItemStack i) {
        NBTUtils.sanityCheck(i);
        return ComputerUtils.getFileFromItemStack(i);
    }

    @Override
    public String getDiskLabel(ItemStack i) {
        String label = NBTUtils.getString("Label", i);
        if (label == null) return "";
        return "" + label;
    }

    @Override
    public String getSerialNumber(ItemStack i) {
        return "" + NBTUtils.getString("SerialNumber", i);
    }

    @Override
    public int getSize(ItemStack i) {
        return DISK_SIZE;
    }

    @Override
    public void setDiskLabel(ItemStack i, String label) {
        NBTUtils.setString("Label", i, label);
    }

    @Override
    public int getAccessTime(ItemStack i) {
        return 0;
    }

    @Override
    public boolean isHardDrive(ItemStack i) {
        return true;
    }

    @Override
    public boolean isFloppyDrive(ItemStack i) {
        return false;
    }
}
