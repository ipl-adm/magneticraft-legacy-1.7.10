package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerBattery extends ContainerBasic{

	public ContainerBattery(InventoryPlayer p, TileEntity t) {
		super(p, t);
		bindPlayerInventory(p);
		addSlotToContainer(new Slot((IInventory) t, 0, 102, 16));
		addSlotToContainer(new Slot((IInventory) t, 1, 102, 48));
	}

}
