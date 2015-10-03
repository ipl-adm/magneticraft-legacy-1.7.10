package com.cout970.magneticraft.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotUnitary extends Slot {

    public SlotUnitary(IInventory inv, int id, int x, int y) {
        super(inv, id, x, y);
    }

    @Override
    public int getSlotStackLimit() {
        return 1;
    }
}
