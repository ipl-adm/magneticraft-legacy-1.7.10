package com.cout970.magneticraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;

public class ContainerBrickFurnace extends ContainerBasic {
    public static final int slotCount = 2;

    public ContainerBrickFurnace(InventoryPlayer p, TileEntity t) {
        super(t);
        this.addSlotToContainer(new Slot((IInventory) tile, 0, 62, 31));
        this.addSlotToContainer(new Slot((IInventory) tile, 1, 118, 32) {
            public boolean isItemValid(ItemStack p_75214_1_) {
                return false;
            }
        });
        bindPlayerInventory(p);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        if (slot >= slotCount) {
            Slot slotObject = (Slot) inventorySlots.get(slot);
            ItemStack is1 = slotObject.getStack();
            if (is1 == null) {
                return null;
            }
            if (FurnaceRecipes.smelting().getSmeltingResult(is1) == null) {
                return null;
            }
        }
        return transfer(player, slot, new int[]{3, 2});
    }
}
