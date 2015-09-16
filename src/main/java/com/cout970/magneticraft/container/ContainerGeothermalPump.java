package com.cout970.magneticraft.container;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;

public class ContainerGeothermalPump extends ContainerBasic {

    public ContainerGeothermalPump(InventoryPlayer p, TileEntity t) {
        super(t);
        bindPlayerInventory(p);
    }

}
