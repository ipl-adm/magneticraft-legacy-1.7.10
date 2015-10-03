package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerShelvingUnit extends ContainerBasic {
    public ContainerShelvingUnit(InventoryPlayer inv, TileEntity t) {
        super(t);
        bindPlayerInventory(inv);
    }
}
