package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.tileentity.TileComputer;

public class ContainerComputer extends ContainerBasic{

	public ContainerComputer(InventoryPlayer p, TileEntity t) {
		super(p, t);
		addSlotToContainer(new Slot(((TileComputer) t).getInv(), 0, 29, 25));
		addSlotToContainer(new Slot(((TileComputer) t).getInv(), 1, 48, 25));
		addSlotToContainer(new Slot(((TileComputer) t).getInv(), 2, 29, 48));
		addSlotToContainer(new Slot(((TileComputer) t).getInv(), 3, 129, 57));
		addSlotToContainer(new Slot(((TileComputer) t).getInv(), 4, 92, 57));
		bindPlayerInventory(p);
	}
}
