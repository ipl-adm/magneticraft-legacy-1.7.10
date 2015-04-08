package com.cout970.magneticraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.client.gui.component.IGuiSync;


public class ContainerBasic extends Container{

	public TileEntity tile;
	
	public ContainerBasic(InventoryPlayer p, TileEntity t){
		super();
		tile = t;
	}
	
	public ItemStack transferStackInSlot(EntityPlayer p, int s){return null;};
	
	public ItemStack transfer(EntityPlayer player, int slot,int inv) {return null;}
	
	public void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				 addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                         8 + j * 18, 84 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
		}
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer p) {
		return true;
	}
	
	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		if(tile instanceof IGuiSync){
			for (int i = 0; i < crafters.size(); i++) {
				((IGuiSync) tile).sendGUINetworkData(this, (ICrafting) crafters.get(i));
			}
		}
	}

	@Override
	public void updateProgressBar(int i, int value) {
		if(tile instanceof IGuiSync)
			((IGuiSync) tile).getGUINetworkData(i, value);
	}

}
