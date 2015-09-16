package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerOilDistillery extends ContainerBasic {

    public ContainerOilDistillery(InventoryPlayer p, TileEntity t) {
        super(t);
        bindPlayerInventory(p);
    }

}
