package com.cout970.magneticraft.container;

import com.cout970.magneticraft.tileentity.TileInserter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

public class ContainerInserter extends ContainerBasic {

    public ContainerInserter(InventoryPlayer p, TileEntity t) {
        super(t);
        addSlotToContainer(new Slot(((TileInserter) t).getInv(), 0, 92, 17));
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                addSlotToContainer(new Slot(((TileInserter) t).filter, i + j * 3, 17 + i * 18, 17 + j * 18) {

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
                });
        addSlotToContainer(new Slot(((TileInserter) t).upgrades, 0, 133, 9));
        addSlotToContainer(new Slot(((TileInserter) t).upgrades, 1, 151, 9));
        addSlotToContainer(new Slot(((TileInserter) t).upgrades, 2, 133, 27));
        addSlotToContainer(new Slot(((TileInserter) t).upgrades, 3, 151, 27));
        bindPlayerInventory(p);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        return transfer(player, slot, new int[]{3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2});
    }
}
