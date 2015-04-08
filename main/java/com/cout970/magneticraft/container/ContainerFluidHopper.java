package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerFluidHopper extends ContainerBasic{

	public ContainerFluidHopper(InventoryPlayer p, TileEntity t) {
		super(p, t);
		addSlotToContainer(new Slot((IInventory) t, 0, 53, 25));
		addSlotToContainer(new Slot((IInventory) t, 1, 53, 48));
		bindPlayerInventory(p);
	}

}
