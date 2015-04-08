package com.cout970.magneticraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerFireBox extends ContainerBasic{

	public ContainerFireBox(InventoryPlayer inventory, TileEntity tile){
		super(inventory, tile);
		this.addSlotToContainer(new Slot((IInventory) tile,0,80,47));
		this.bindPlayerInventory(inventory);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p_75145_1_) {
		return true;
	}

}
