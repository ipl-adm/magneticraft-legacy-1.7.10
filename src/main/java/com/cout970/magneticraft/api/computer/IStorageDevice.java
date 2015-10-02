package com.cout970.magneticraft.api.computer;

import net.minecraft.item.ItemStack;

import java.io.File;

/**
 * @author Cout970
 */
public interface IStorageDevice {

    /**
     * If is null, an IOException has occurred
     *
     * @param i device ItemStack
     * @return
     */
    File getAssociateFile(ItemStack i);

    /**
     * Can be null
     *
     * @param i device ItemStack
     * @return the disk label
     */
    String getDiskLabel(ItemStack i);

    void setDiskLabel(ItemStack i, String label);

    /**
     * Can be null, but after call getAssociateFile, must be a valid number, Also it's a 16 digits number in hexadecimal
     *
     * @param i device ItemStack
     * @return the unique serial number used to get the save file, "disk_"+serialNumber+".img"
     */
    String getSerialNumber(ItemStack i);

    /**
     * @param i device ItemStack
     * @return the number of sector of 128bytes in the disk
     */
    int getSize(ItemStack i);

    /**
     * @param i device ItemStack
     * @return the amount of game ticks to wait to read or write in the disk
     */
    int getAccessTime(ItemStack i);

    boolean isHardDrive(ItemStack i);

    boolean isFloppyDrive(ItemStack i);
}
