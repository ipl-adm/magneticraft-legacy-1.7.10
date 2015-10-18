package com.cout970.magneticraft.container;

import com.cout970.magneticraft.tileentity.TileBreaker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerBreaker extends ContainerBasic {

    public ContainerBreaker(InventoryPlayer p, TileEntity t) {
        super(t);
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                addSlotToContainer(new Slot((IInventory) tile, j + i * 3, 107 + j * 18, 26 + i * 18));
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                addSlotToContainer(new Slot(((TileBreaker) tile).filter, j + i * 3, 17 + j * 18, 26 + i * 18) {
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
        bindPlayerInventory(p);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return transfer(player, slot, new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0});
    }
}
