package com.cout970.magneticraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerCrusher extends ContainerBasic{

	public ContainerCrusher(InventoryPlayer p, TileEntity t) {
		super(p, t);
		addSlotToContainer(new Slot((IInventory) t, 0, 51, 31));
		addSlotToContainer(new Slot((IInventory) t, 1, 101, 31));
		addSlotToContainer(new Slot((IInventory) t, 2, 119, 31));
		addSlotToContainer(new Slot((IInventory) t, 3, 137, 31));
		bindPlayerInventory(p);
	}

	public ItemStack transferStackInSlot(EntityPlayer player, int slot){
		return transfer(player, slot, new int[]{3,2,2,2});
	}
}