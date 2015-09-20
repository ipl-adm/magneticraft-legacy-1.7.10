package com.cout970.magneticraft.api.computer;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * This can be implemented by a TileEntity or used as a Field, but is only needed for @IComputer
 *
 * @author Cout970
 */
public interface IModuleDiskDrive extends IHardwareComponent {

    /**
     * called every tick by the tileEntity
     */
    void iterate();

    /**
     * Can't be null
     *
     * @return a 128 byte buffer
     */
    byte[] getRawBuffer();

    /**
     * @return current sector to read or write
     */
    int getSector();

    /**
     * @param sector the sector to read or write
     */
    void setSector(int sector);

    /**
     * read from the disk to the buffer using the current sector
     */
    void readToBuffer();

    /**
     * write the buffer into the disk in the current sector
     */
    void writeToFile();

    /**
     * @return the disk inside os the drive
     */
    ItemStack getDisk();

    /**
     * @param i ItemStack with an IStorageDevice
     * @return true if the disk is acepted, false if already ocuped or invalid disk
     */
    boolean insertDisk(ItemStack i);

    /**
     * This only load the buffer and the sector, not the item inside
     *
     * @param nbt
     */
    void load(NBTTagCompound nbt);

    /**
     * This only save the buffer and the sector, not the item inside
     *
     * @param nbt
     */
    void save(NBTTagCompound nbt);
}
