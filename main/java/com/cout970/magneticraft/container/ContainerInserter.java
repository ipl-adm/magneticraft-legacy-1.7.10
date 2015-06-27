package com.cout970.magneticraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.tileentity.TileCrafter;
import com.cout970.magneticraft.tileentity.TileInserter;

public class ContainerInserter extends ContainerBasic{

	public ContainerInserter(InventoryPlayer p, TileEntity t) {
		super(p, t);
		for(int i = 0;i<3;i++)
			for(int j = 0;j<3;j++)
				addSlotToContainer(new Slot(((TileInserter)t).filter, i+j*3, 17+i*18, 17+j*18){
					
					public int getSlotStackLimit() {
						return 0;
					}
					
					public boolean isItemValid(ItemStack p_75214_1_) {
						return true;
					}

					public boolean canTakeStack(EntityPlayer p_82869_1_) {
				        this.inventory.setInventorySlotContents(getSlotIndex(), null);
				        return false;
					}
				});
		addSlotToContainer(new Slot(((TileInserter)t).getInv(),0,92,17));
		bindPlayerInventory(p);
	}
}
