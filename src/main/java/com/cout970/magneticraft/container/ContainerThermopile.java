package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerThermopile extends ContainerBasic {

    public ContainerThermopile(InventoryPlayer p, TileEntity t) {
        super(p, t);
        bindPlayerInventory(p);
    }
}
