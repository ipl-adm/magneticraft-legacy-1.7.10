package com.cout970.magneticraft.container;

import com.cout970.magneticraft.tileentity.TileCrafter;
import com.cout970.magneticraft.util.InventoryCrafterAux;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerCrafter extends ContainerBasic {

    public ContainerCrafter(InventoryPlayer p, TileEntity t) {
        super(t);
        addSlotToContainer(new Slot(((TileCrafter) t).getResult(), 0, 71, 35) {

            public int getSlotStackLimit() {
                return 0;
            }

            public boolean isItemValid(ItemStack p_75214_1_) {
                return false;
            }

            public boolean canTakeStack(EntityPlayer p_82869_1_) {
                return false;
            }
        });
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                addSlotToContainer(new Slot(((TileCrafter) t).getRecipe(), i + j * 3, 8 + i * 18, 17 + j * 18) {

                    public int getSlotStackLimit() {
                        return 0;
                    }

                    public boolean isItemValid(ItemStack p_75214_1_) {
                        return true;
                    }

                    public boolean canTakeStack(EntityPlayer p_82869_1_) {
                        this.inventory.setInventorySlotContents(getSlotIndex(), null);
                        return false;
                    }

                    public void onSlotChanged() {
                        InventoryCrafterAux comp = (InventoryCrafterAux) inventory;
                        comp.tile.refreshRecipe();
                        comp.tile.refreshItemMatches();
                        this.inventory.markDirty();
                    }
                });
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                addSlotToContainer(new Slot((IInventory) t, i + j * 4, 98 + i * 18, 8 + j * 18) {
                    public void onSlotChanged() {
                        TileCrafter comp = (TileCrafter) inventory;
                        comp.refreshItemMatches();
                        this.inventory.markDirty();
                        super.onSlotChanged();
                    }
                });
        bindPlayerInventory(p);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return transfer(player, slot, new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3});
    }

}
