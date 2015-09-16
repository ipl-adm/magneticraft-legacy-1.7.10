package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerRefinery extends ContainerBasic {

    public ContainerRefinery(InventoryPlayer p, TileEntity t) {
        super(t);
        bindPlayerInventory(p);
    }
}