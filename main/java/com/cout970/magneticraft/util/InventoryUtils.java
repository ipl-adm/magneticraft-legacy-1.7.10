package com.cout970.magneticraft.util;

import com.cout970.magneticraft.api.util.MgUtils;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.oredict.OreDictionary;

public class InventoryUtils {

	public static ItemStack addition(ItemStack a, ItemStack b) {
		if(a == null && b == null)return null;
		if(a == null && b != null)return b.copy();
		if(b == null && a != null)return a.copy();
		return new ItemStack(a.getItem(),a.stackSize+b.stackSize,a.getItemDamage());
	}
	
	public static boolean canCombine(ItemStack a, ItemStack b, int limit){
		if(a == null || b == null)return true;
		if(a.getItem() != b.getItem())return false;
		if(a.getItemDamage() != b.getItemDamage())return false;
		if(a.stackTagCompound != b.stackTagCompound)return false;
		if(a.stackSize + b.stackSize > limit)return false;
		if(a.stackSize + b.stackSize > a.getMaxStackSize())return false;
		return true;
	}

	public static ItemStack getItemStack(InventoryComponent in) {
		for(int i = 0; i<in.getSizeInventory();i++){
			if(in.getStackInSlot(i) != null){
				ItemStack it = in.getStackInSlot(i);
				in.setInventorySlotContents(i, null);
				return it;
			}
		}
		return null;
	}

	public static int findCombination(InventoryComponent in, ItemStack st) {
		for(int i = 0; i<in.getSizeInventory();i++){
			if(in.getStackInSlot(i) != null){
				if(canCombine(in.getStackInSlot(i), st, 128) && in.getStackInSlot(i).stackSize < in.getStackInSlot(i).getMaxStackSize()){
					return i;
				}
			}
		}
		return -1;
	}

	public static int getSlotForStack(InventoryComponent in,ItemStack st) {
		for(int i = 0; i<in.getSizeInventory();i++){
			if(in.getStackInSlot(i) != null){
				if(canCombine(in.getStackInSlot(i), st, in.getStackInSlot(i).getMaxStackSize())){
					return i;
				}
			}else return i;
		}
		return -1;
	}

	public static void traspass(IInventory a,IInventory b, int source, int target) {
		ItemStack e = a.getStackInSlot(source);
		ItemStack f = b.getStackInSlot(target);
		if(f == null){
			if(e.stackSize <= b.getInventoryStackLimit()){
				b.setInventorySlotContents(target, e);
				a.setInventorySlotContents(source, null);
			}else{
				ItemStack copy = e.copy();
				copy.stackSize = b.getInventoryStackLimit();
				b.setInventorySlotContents(target, copy);
				e.stackSize -= copy.stackSize;
			}
		}else{
			if(!canCombine(e, f, 128))return;
			if(f.stackSize < b.getInventoryStackLimit()){
				int amount = Math.min(b.getInventoryStackLimit()-f.stackSize, e.stackSize);
				if(amount > 0){
					f.stackSize += amount;
					e.stackSize -= amount;
					if(e.stackSize <= 0){
						a.setInventorySlotContents(source, null);
					}
				}
			}
		}
		
	}

	public static void remove(IInventory inv, int slot, int amount) {
		inv.decrStackSize(slot, amount);		
	}

	public static void saveInventory(IInventory inv,NBTTagCompound nbtTagCompound, String name) {
		NBTTagList list = new NBTTagList();
		for (int currentIndex = 0; currentIndex < inv.getSizeInventory(); ++currentIndex) {
			if (inv.getStackInSlot(currentIndex) != null) {
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setByte("Slot", (byte) currentIndex);
				inv.getStackInSlot(currentIndex).writeToNBT(nbt);
				list.appendTag(nbt);
			}
		}
		nbtTagCompound.setTag(name, list);
	}
	
	public static void loadInventory(IInventory inv, NBTTagCompound nbtTagCompound, String name) {
		NBTTagList tagList = nbtTagCompound.getTagList(name, 10);
		for (int i = 0; i < tagList.tagCount(); ++i) {
			NBTTagCompound tagCompound = (NBTTagCompound) tagList.getCompoundTagAt(i);
			byte slot = tagCompound.getByte("Slot");
			if (slot >= 0 && slot < inv.getSizeInventory()) {
				inv.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(tagCompound));
			}
		}
	}

	public static boolean areExaticlyEqual(ItemStack a, ItemStack b){
		if(a == null && b == null)return true;
		if(a != null && b != null && a.getItem() != null && b.getItem() != null){
			if(OreDictionary.itemMatches(a, b, true))return true;
		}
		return false;
	}

}
