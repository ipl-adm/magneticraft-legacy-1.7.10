package com.cout970.magneticraft.container;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotToggleable extends Slot {
    public static final int HIDE_OFFSET = 1 << 20;
    public int baseX, baseY;
    public boolean hidden = false;

    public SlotToggleable(IInventory inv, int index, int dx, int dy) {
        super(inv, index, dx, dy);
        baseX = dx;
        baseY = dy;
    }

    public void hide() {
        if (!hidden) {
            hidden = true;
            xDisplayPosition += HIDE_OFFSET;
            yDisplayPosition += HIDE_OFFSET;
        }
    }

    public void show() {
        if (hidden) {
            hidden = false;
            xDisplayPosition -= HIDE_OFFSET;
            yDisplayPosition -= HIDE_OFFSET;
        }
    }
}
