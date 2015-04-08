package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerElectricFurnace extends ContainerBasic{

	public ContainerElectricFurnace(InventoryPlayer p, TileEntity t) {
		super(p, t);
		this.addSlotToContainer(new Slot((IInventory) tile,0,62,17));
		this.addSlotToContainer(new Slot((IInventory) tile,1,118,32){
			public boolean isItemValid(ItemStack p_75214_1_)
		    {
		        return false;
		    }
		});
		this.addSlotToContainer(new SlotUnitary((IInventory) tile,2,62,53));
		bindPlayerInventory(p);
	}

}
