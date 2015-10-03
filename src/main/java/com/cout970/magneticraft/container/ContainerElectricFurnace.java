package com.cout970.magneticraft.container;

import com.cout970.magneticraft.api.tool.IFurnaceCoil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;

public class ContainerElectricFurnace extends ContainerBasic {
    public static final int slotCount = 3;

    public ContainerElectricFurnace(InventoryPlayer p, TileEntity t) {
        super(t);
        this.addSlotToContainer(new Slot((IInventory) tile, 0, 62, 17));
        this.addSlotToContainer(new Slot((IInventory) tile, 1, 118, 32) {
            public boolean isItemValid(ItemStack p_75214_1_) {
                return false;
            }
        });
        this.addSlotToContainer(new SlotUnitary((IInventory) tile, 2, 62, 53));
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
            Item item = is1.getItem();
            if (item instanceof IFurnaceCoil) {
                return transfer(player, slot, new int[]{2, 2, 3});
            }
            if (FurnaceRecipes.smelting().getSmeltingResult(is1) == null) {
                return null;
            }
        }
        return transfer(player, slot, new int[]{3, 2, 3});
    }
}
