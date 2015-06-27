package com.cout970.magneticraft.items;

import java.io.File;

import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.api.computer.IFloppyDisk;
import com.cout970.magneticraft.api.computer.impl.ComputerUtils;
import com.cout970.magneticraft.api.util.NBTUtils;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

public class ItemFloppyDisk extends ItemBasic implements IFloppyDisk{

	public static final int DISK_SIZE = 0x40000; //256kB
	
	public ItemFloppyDisk(String unlocalizedname) {
		super(unlocalizedname);
		setCreativeTab(CreativeTabsMg.InformationAgeTab);
	}

	@Override
	public File getAsociateFile(ItemStack i) {
		NBTUtils.sanityCheck(i);
		return ComputerUtils.getFileFromItemStack(i);
	}

	@Override
	public String getDiskLabel(ItemStack i) {
		return ""+NBTUtils.getString("Label", i);
	}

	@Override
	public String getSerialNumber(ItemStack i) {
		return ""+NBTUtils.getString("SerialNumber", i);
	}

	@Override
	public int getSize(ItemStack i) {
		return DISK_SIZE;
	}

	@Override
	public int getAccessTime(ItemStack i) {
		return 5;
	}

	@Override
	public void setDiskLabel(ItemStack i, String label) {
		NBTUtils.setString("Label", i, label);
	}
}
