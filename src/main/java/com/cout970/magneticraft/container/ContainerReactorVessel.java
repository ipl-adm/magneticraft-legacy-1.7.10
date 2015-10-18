package com.cout970.magneticraft.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerReactorVessel extends ContainerBasic {

    public ContainerReactorVessel(InventoryPlayer p, TileEntity t) {
        super(t);
        addSlotToContainer(new Slot((IInventory) t, 0, 66, 30));
        bindPlayerInventory(p);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return transfer(player, slot, new int[]{3});
    }
}
