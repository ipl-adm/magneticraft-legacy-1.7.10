package com.cout970.magneticraft.container;

import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import com.cout970.magneticraft.util.InventoryComponent;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;

public class ContainerShelvingUnit extends ContainerBasic {
    public TileShelvingUnit shelf;
    public ContainerShelvingUnit(InventoryPlayer inv, TileEntity t) {
        super(t);
        shelf = (TileShelvingUnit) t;
        for (int i = 0; i < TileShelvingUnit.MAX_SHELVES; i++) {
            InventoryComponent tabInv = shelf.getInv(i);
            for (int s = 0; s < tabInv.getSizeInventory(); s++) {
                int y = s / 9;
                int x = s % 9;
                addSlotToContainer(new SlotToggleable(tabInv, s, 8 + x * 18, 18 + y * 18));
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
}
