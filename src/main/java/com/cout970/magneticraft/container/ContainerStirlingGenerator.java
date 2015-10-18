package com.cout970.magneticraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerStirlingGenerator extends ContainerBasic {

    public ContainerStirlingGenerator(InventoryPlayer p, TileEntity t) {
        super(t);
        this.addSlotToContainer(new Slot((IInventory) tile, 0, 80, 47));
        bindPlayerInventory(p);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return transfer(player, slot, new int[]{3});
    }
}
