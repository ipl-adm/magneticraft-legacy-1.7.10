package com.cout970.magneticraft.container;

import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import com.cout970.magneticraft.util.InventoryComponent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.Arrays;

import static com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit.CRATE_SIZE;
import static com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit.MAX_CRATES;
import static com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit.SHELF_CRATES;

public class ContainerShelvingUnit extends ContainerBasic {
    public TileShelvingUnit shelf;
    public int curInv;

    public ContainerShelvingUnit(InventoryPlayer inv, TileEntity t) {
        super(t);
        curInv = 0;
        shelf = (TileShelvingUnit) t;
        for (int i = 0; i < TileShelvingUnit.MAX_SHELVES; i++) {
            InventoryComponent tabInv = shelf.getInv(i);
            tabInv.openInventory();
            for (int s = 0; s < tabInv.getSizeInventory(); s++) {
                int y = s / 9;
                int x = s % 9;
                SlotShelvingUnit slot = new SlotShelvingUnit(tabInv, s, 8 + x * 18, 18 + y * 18, i);
                if (inventorySlots.size() >= (shelf.getCrateCount() * CRATE_SIZE)) {
                    slot.lock();
                }
                addSlotToContainer(slot);
            }
        }
        bindPlayerInventory(inv);
    }

    @Override
    public void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        8 + j * 18, 122 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 180));
        }

    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        int[] slots = new int[MAX_CRATES * CRATE_SIZE];
        Arrays.fill(slots, 0);
        Arrays.fill(slots, SHELF_CRATES * CRATE_SIZE * curInv, Math.min(shelf.getCrateCount() * CRATE_SIZE, SHELF_CRATES * CRATE_SIZE * (curInv + 1)), 3);
        return transfer(player, slot, slots);
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        for (int i = 0; i < TileShelvingUnit.MAX_SHELVES; i++) {
            shelf.getInv(i).closeInventory();
        }
    }

    @Override
    public boolean enchantItem(EntityPlayer player, int data) {
        curInv = data;
        return true;
    }
}
