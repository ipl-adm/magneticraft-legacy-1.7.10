package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerMiner extends ContainerBasic {

    public ContainerMiner(InventoryPlayer p, TileEntity t) {
        super(t);
        this.bindPlayerInventory(p);
    }
}
