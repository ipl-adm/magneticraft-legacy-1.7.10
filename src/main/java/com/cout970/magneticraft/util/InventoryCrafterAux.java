package com.cout970.magneticraft.util;

import com.cout970.magneticraft.tileentity.TileCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;

public class InventoryCrafterAux extends InventoryCrafting {

    public TileCrafter tile;

    public InventoryCrafterAux(TileCrafter craft, int w, int h) {
        super(new Container() {
            @Override
            public boolean canInteractWith(EntityPlayer p_75145_1_) {
                return true;
            }
        }, w, h);
        tile = craft;
    }

}
