package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.tileentity.TileBase;

public class ContainerDroidRED extends ContainerBasic{

	public ContainerDroidRED(InventoryPlayer p, TileEntity t) {
		super(p, t);
		for(int i = 0; i< 4;i++){
			for(int j = 0; j< 4;j++){
				addSlotToContainer(new Slot(((TileBase) t).getInv(), i*4+j, 18*j+268, 18*i+235));
			}
		}
		bindPlayerInventory(p);
	}

	public void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				 addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                         8 + j * 18 + 87, 84 + i * 18 + 151));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18+87, 142+151));
		}
	}
}
