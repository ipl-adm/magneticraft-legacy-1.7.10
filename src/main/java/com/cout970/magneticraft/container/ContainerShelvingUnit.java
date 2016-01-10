package com.cout970.magneticraft.container;

import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import com.cout970.magneticraft.util.InventoryComponent;
import invtweaks.api.container.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import java.util.*;
import java.util.stream.Collectors;

import static com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit.CRATE_SIZE;
import static com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit.MAX_CRATES;
import static com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit.SHELF_CRATES;
import static java.util.Comparator.comparingInt;

@SuppressWarnings("unchecked")
@ChestContainer
public class ContainerShelvingUnit extends ContainerBasic {
    public TileShelvingUnit shelf;
    public int curInv;
    public List<SlotShelvingUnit> currentSlots = new ArrayList<>();
    private boolean isFiltered = false;

    public ContainerShelvingUnit(InventoryPlayer inv, TileEntity t) {
        super(t);
        curInv = 0;
        shelf = (TileShelvingUnit) t;
        for (int i = 0; i < TileShelvingUnit.MAX_SHELVES; i++) {
            InventoryComponent tabInv = shelf.getInv(i);
            tabInv.openInventory();
            for (int s = 0; s < tabInv.getSizeInventory(); s++) {
                int y = s / 9;
                int x = s % 9;
                SlotShelvingUnit slot = new SlotShelvingUnit(tabInv, s, 8 + x * 18, 18 + y * 18, i);
                if (inventorySlots.size() >= (shelf.getCrateCount() * CRATE_SIZE)) {
                    slot.lock();
                }
                addSlotToContainer(slot);
            }
        }
        bindPlayerInventory(inv);
    }

    @Override
    public void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        8 + j * 18, 122 + i * 18));
            }
        }
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 180));
        }

    }

    @ContainerSectionCallback
    public Map<ContainerSection, List<Slot>> getSections() {
        Map<ContainerSection, List<Slot>> retMap = new EnumMap<>(ContainerSection.class);
        List<Slot> inventory = (List<Slot>) inventorySlots.stream()
                .filter(s -> !(s instanceof SlotShelvingUnit))
                .sorted(comparingInt(s -> ((Slot) s).slotNumber))
                .collect(Collectors.toList());
        List<? extends Slot> curSlots;
        if (isFiltered) {
            curSlots = currentSlots;
        } else {
            curSlots = (List<Slot>) inventorySlots.stream()
                    .filter(s -> s instanceof SlotShelvingUnit)
                    .filter(s -> ((SlotShelvingUnit) s).invNum == curInv)
                    .collect(Collectors.toList());
        }

        //DON'T add mappings for INVENTORY, it breaks everything. All hail InvTweaks...
        retMap.put(ContainerSection.INVENTORY_HOTBAR, inventory.subList(0, 9));
        retMap.put(ContainerSection.INVENTORY_NOT_HOTBAR, inventorySlots.subList(10, 36));
        retMap.put(ContainerSection.CHEST, (List<Slot>) curSlots);
        return retMap;
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        int[] slots = new int[MAX_CRATES * CRATE_SIZE];
        Arrays.fill(slots, 0);
        Arrays.fill(slots, SHELF_CRATES * CRATE_SIZE * curInv, Math.min(shelf.getCrateCount() * CRATE_SIZE, SHELF_CRATES * CRATE_SIZE * (curInv + 1)), 3);
        return transfer(player, slot, slots);
    }

    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);
        for (int i = 0; i < TileShelvingUnit.MAX_SHELVES; i++) {
            shelf.getInv(i).closeInventory();
        }
    }

    @Override
    public boolean enchantItem(EntityPlayer player, int data) {
        curInv = data;
        return true;
    }

    public void adjustSlots(int scroll, String filter, int level) {
        adjustSlots(curInv, scroll, filter, level);
    }

    public void adjustSlots(int curInv, int scroll, String filter) {
        adjustSlots(curInv, scroll, filter, 2);
    }

    public void adjustSlots(int curInv, int scroll, String filter, int level) {
        if (shelf.getCrateCount() == 0) {
            inventorySlots.stream().filter(s -> s instanceof SlotShelvingUnit).forEach(s -> {
                ((SlotShelvingUnit) s).lock();
                ((SlotShelvingUnit) s).hide();
            });
            return;
        }
        if (curInv != this.curInv) {
            this.curInv = curInv;
            level = 2;
        }

        renewSlots(level, filter);
        isFiltered = filter.isEmpty();
        if (isFiltered) {
            for (int i = 0; i < inventorySlots.size(); i++) {
                Object s = inventorySlots.get(i);
                if (s instanceof SlotShelvingUnit) {
                    SlotShelvingUnit st = (SlotShelvingUnit) s;
                    st.reset();
                    st.yDisplayPosition = st.baseY - 18 * scroll;
                    if ((st.yDisplayPosition < 17) || (st.yDisplayPosition > 106) || (st.invNum != curInv)) {
                        st.hide();
                    }
                    if ((i >= (shelf.getCrateCount() * TileShelvingUnit.CRATE_SIZE)) || (st.invNum != curInv)) {
                        st.lock();
                    }
                }
            }
        } else {
            inventorySlots.stream().filter(s -> s instanceof SlotShelvingUnit).forEach(s -> {
                ((SlotShelvingUnit) s).reset();
                ((SlotShelvingUnit) s).hide();
                ((SlotShelvingUnit) s).lock();
            });

            for (int i = 0; i < currentSlots.size(); i++) {
                SlotShelvingUnit st = currentSlots.get(i);
                st.unlock();
                st.xDisplayPosition = 8 + 18 * (i % 9);
                st.yDisplayPosition = 18 + 18 * (i / 9 - scroll);
                if ((st.yDisplayPosition < 17) || (st.yDisplayPosition > 106)) {
                    st.hide();
                }
            }
        }
    }

    public void renewSlots(int mode, String filter) {
        if (mode == 0) {
            return;
        }
        if (mode == 1) {
            currentSlots = currentSlots.stream()
                    .filter(s -> MgUtils.matchesPattern(s.getStack(), filter))
                    .collect(Collectors.toList());
        }
        if (mode == 2) {
            currentSlots = (List<SlotShelvingUnit>) inventorySlots.stream()
                    .filter(s -> s instanceof SlotShelvingUnit)
                    .filter(s -> (((SlotShelvingUnit) s).invNum == curInv) && MgUtils.matchesPattern(((SlotShelvingUnit) s).getStack(), filter))
                    .collect(Collectors.toList());

        }
    }
}
