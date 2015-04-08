package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerPolimerizer extends ContainerBasic{

	public ContainerPolimerizer(InventoryPlayer p, TileEntity t) {
		super(p, t);
		bindPlayerInventory(p);
		addSlotToContainer(new Slot((IInventory) t,0,68,36));
		addSlotToContainer(new Slot((IInventory) t,1,124,36));
	}

}
