package com.cout970.magneticraft.container;

import com.cout970.magneticraft.Magneticraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SlotShelvingUnit extends Slot implements ISlotToggleable, ISlotLockable {
    public static final int HIDE_OFFSET = 1 << 20;
    public int baseX, baseY, invNum;
    public boolean hidden, locked;
    public static ResourceLocation BG_LOCKED = new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/slot_locked.png");

    public SlotShelvingUnit(IInventory inv, int index, int dx, int dy, int number) {
        super(inv, index, dx, dy);
        hidden = locked = false;
        baseX = dx;
        baseY = dy;
        invNum = number;
    }

    @Override
    public void hide() {
        if (!hidden) {
            hidden = true;
            xDisplayPosition += HIDE_OFFSET;
            yDisplayPosition += HIDE_OFFSET;
        }
    }

    public void reset() {
        xDisplayPosition = baseX;
        yDisplayPosition = baseY;
        hidden = false;
        locked = false;
    }

    @Override
    public void show() {
        if (hidden) {
            hidden = false;
            xDisplayPosition -= HIDE_OFFSET;
            yDisplayPosition -= HIDE_OFFSET;
        }
    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public void lock() {
        locked = true;
    }

    @Override
    public void unlock() {
        locked = false;
    }

    public boolean canTakeStack(EntityPlayer player) {
        return !locked;
    }

    @Override
    public boolean isItemValid(ItemStack is) {
        return !locked;
    }

    @Override
    public int getSlotStackLimit() {
        return (locked) ? 0 : super.getSlotStackLimit();
    }
}
