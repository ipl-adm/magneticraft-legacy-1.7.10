package com.cout970.magneticraft.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.conveyor.IConveyor;
import com.cout970.magneticraft.api.conveyor.ItemBox;
import com.cout970.magneticraft.api.util.MgDirection;

public class MgBeltUtils {

	public static boolean isInventory(TileEntity t) {
		return t instanceof IInventory;
	}

	public static boolean isBelt(TileEntity t) {
		return t instanceof IConveyor;
	}

	public static boolean injectInBelt(IConveyor t, ItemBox box, MgDirection dir) {
		if(dir == t.getDir())return false;
		int var = dir.isPerpendicular(t.getDir()) ? (dir == t.getDir().step(MgDirection.UP) ? (!box.isOnLeft() ? 1 : 2) : (box.isOnLeft() ? 1: 2)) : 0;
		if(t.addItem(dir, var, box, false))return true;
		return false;
	}
	

	/**
	 * 
	 * @param v
	 * @param it
	 * @param dir
	 * @return excess
	 */
	public static int dropItemStackIntoInventory(IInventory v,ItemStack stack, MgDirection dir, boolean simulated) {
		ItemStack it = stack.copy();
		if(v instanceof ISidedInventory){
			ISidedInventory s = (ISidedInventory) v;
			if(s.getAccessibleSlotsFromSide(dir.ordinal()) == null)return it.stackSize;
			for(int i : s.getAccessibleSlotsFromSide(dir.ordinal())){
				if(s.canInsertItem(i, it, dir.ordinal())){
					int noAccepted = placeInSlot(v, i, it, simulated);
					if(noAccepted == 0)return 0;
					it.stackSize = noAccepted;
				}
			}
		}else{
			for(int i=0;i<v.getSizeInventory();i++){
				int noAccepted = placeInSlot(v, i, it, simulated);
				if(noAccepted == 0)return 0;
				it.stackSize = noAccepted;
			}
		}
		return it.stackSize;
	}

	public static int placeInSlot(IInventory a,int slot, ItemStack b, boolean simulated){
		ItemStack c = a.getStackInSlot(slot);
		if(c == null){
			int accepted = Math.min(a.getInventoryStackLimit(), b.stackSize);
			if(!simulated){
				ItemStack d = b.copy();
				d.stackSize = accepted;
				a.setInventorySlotContents(slot, d);
			}
			return b.stackSize-accepted;
		}else if(MatchesAll(c,b)){
			int space =  a.getInventoryStackLimit()-c.stackSize;
			int accepted = Math.min(space, b.stackSize);
			if(!simulated){
				ItemStack d = c.copy();
				d.stackSize += accepted;
				a.setInventorySlotContents(slot, d);
			}
			return b.stackSize-accepted;
		}
		return b.stackSize;
	}

	private static boolean MatchesAll(ItemStack a, ItemStack b) {
		return ItemStack.areItemStackTagsEqual(a, b) && a.isItemEqual(b);
	}

	public static int getSlotWithItemStack(IInventory t, MgDirection dir) {
		if(t instanceof ISidedInventory){
			if(((ISidedInventory) t).getAccessibleSlotsFromSide(dir.ordinal()) == null)return -1;
			for(int i : ((ISidedInventory) t).getAccessibleSlotsFromSide(dir.ordinal())){
				if(t.getStackInSlot(i) != null && ((ISidedInventory)t).canExtractItem(i, t.getStackInSlot(i), dir.ordinal())){
					return i;
				}
			}
		}else{
			for(int i=0;i<t.getSizeInventory();i++){
				if(t.getStackInSlot(i) != null){
					return i;
				}
			}
		}
		return -1;
	}

	public static boolean canInjectInBelt(IConveyor t, ItemBox box, MgDirection dir) {
		boolean var = dir.isPerpendicular(t.getDir()) ? (dir == t.getDir().step(MgDirection.UP) ? !box.isOnLeft() : box.isOnLeft()) : dir != t.getDir();
		if(t.addItem(dir, var ? 0 : 2, box, true))return true;
		return false;
	}

}
