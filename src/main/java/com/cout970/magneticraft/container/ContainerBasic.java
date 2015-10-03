package com.cout970.magneticraft.container;

import com.cout970.magneticraft.client.gui.component.IGuiSync;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;


public class ContainerBasic extends Container {

    public TileEntity tile;

    public ContainerBasic(TileEntity t) {
        super();
        tile = t;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer p, int s) {
        return null;
    }

    /**
     * io 1 = accept, 2 = can move, 3 both
     */
    public ItemStack transfer(EntityPlayer player, int slotIndex, int[] io) {
        ItemStack copy = null;
        Slot slot = (Slot) inventorySlots.get(slotIndex);

        if (slot != null && slot.getHasStack()) {
            ItemStack item = slot.getStack();
            copy = item.copy();
            if (slotIndex < io.length) {//special slots
                if ((io[slotIndex] & 2) > 0) {
                    if (!mergeItemStack(item, io.length, inventorySlots.size(), true)) {
                        return null;
                    }
                }
            } else {//player slots
                boolean placed = false;
                for (int i = 0; i < io.length; i++) {
                    if ((io[i] & 1) > 0) {
                        if (mergeItemStack(item, i, i + 1, true)) {
                            placed = true;
                            break;
                        }
                    }
                }
                if (!placed) return null;
            }

            if (item.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return copy;
    }

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
        if (tile instanceof IGuiSync) {
            for (Object crafter : crafters) {
                ((IGuiSync) tile).sendGUINetworkData(this, (ICrafting) crafter);
            }
        }
    }

    @Override
    public void updateProgressBar(int i, int value) {
        if (tile instanceof IGuiSync)
            ((IGuiSync) tile).getGUINetworkData(i, value);
    }

}
