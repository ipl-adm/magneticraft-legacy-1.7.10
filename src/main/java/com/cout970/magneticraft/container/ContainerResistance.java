package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerResistance extends ContainerBasic {

    public ContainerResistance(InventoryPlayer p, TileEntity t) {
        super(t);
        bindPlayerInventory(p);
    }

}
