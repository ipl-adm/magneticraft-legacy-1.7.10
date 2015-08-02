package com.cout970.magneticraft.items;

import java.io.File;
import java.util.List;

import com.cout970.magneticraft.api.computer.IStorageDevice;
import com.cout970.magneticraft.api.computer.prefab.ComputerUtils;
import com.cout970.magneticraft.api.util.NBTUtils;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ItemFloppyDiskCustom extends ItemBasic implements IStorageDevice{

	public static final int DISK_SIZE = 0x40000; //256kB
	private String file;
	
	public ItemFloppyDiskCustom(String unlocalizedname, String file) {
		super(unlocalizedname);
		this.file = file;
		setCreativeTab(CreativeTabsMg.InformationAgeTab);
	}

	@Override
	public File getAsociateFile(ItemStack i) {
		NBTUtils.sanityCheck(i);
		return ComputerUtils.getFileFromName(file);
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
		super.addInformation(item, player, list, flag);
		list.add(ItemBlockMg.format+(int)(DISK_SIZE/1024)+"kB of storage");
	}

	@Override
	public boolean isHardDrive(ItemStack i) {
		return false;
	}

	@Override
	public boolean isFloppyDrive(ItemStack i) {
		return true;
	}
}
