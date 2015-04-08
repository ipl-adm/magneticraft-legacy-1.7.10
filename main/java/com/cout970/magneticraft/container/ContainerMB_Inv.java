package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerMB_Inv extends ContainerBasic{

	public ContainerMB_Inv(InventoryPlayer p, TileEntity t) {
		super(p, t);
		addSlotToContainer(new Slot((IInventory) t, 0, 66, 30));
		addSlotToContainer(new Slot((IInventory) t, 1, 84, 30));
		addSlotToContainer(new Slot((IInventory) t, 2, 66, 48));
		addSlotToContainer(new Slot((IInventory) t, 3, 84, 48));
		bindPlayerInventory(p);
	}

}
