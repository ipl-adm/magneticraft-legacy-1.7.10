package com.cout970.magneticraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerBiomassBurner extends ContainerBasic {

    public ContainerBiomassBurner(InventoryPlayer inventory, TileEntity tile) {
        super(tile);
        this.addSlotToContainer(new Slot((IInventory) tile, 0, 80, 47));
        this.bindPlayerInventory(inventory);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return transfer(player, slot, new int[]{3});
    }
}
