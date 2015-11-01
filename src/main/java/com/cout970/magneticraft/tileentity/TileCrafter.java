package com.cout970.magneticraft.tileentity;


import java.util.ArrayList;
import java.util.List;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IGuiListener;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.InventoryCrafterAux;
import com.cout970.magneticraft.util.InventoryUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileCrafter extends TileBase implements IInventoryManaged, IGuiSync, IGuiListener {

    private InventoryComponent inv = new InventoryComponent(this, 16, "Crafter") {
        @Override
        public void setInventorySlotContents(int slot, ItemStack itemStack) {
            super.setInventorySlotContents(slot, itemStack);
            if (worldObj == null || worldObj.isRemote) return;
            itemMatches = -1;
        }
    };
    private InventoryComponent invResult = new InventoryComponent(this, 1, "Result");
    private InventoryCrafting recipe = new InventoryCrafterAux(this, 3, 3);
    private List<InvSlot> checkedInvs = new ArrayList<>();
    private List<TankInfo> checkedTanks = new ArrayList<>();
    private int itemMatches = -1;
    private IRecipe craftRecipe;
    //private boolean nextCraft = false;
    private int craftState = 0; // 0 = awaiting high signal, 1 = ready to craft, 2 = awaiting low signal
    public RedstoneState state = RedstoneState.NORMAL;

    public InventoryComponent getInv() {
        return inv;
    }

    public void onNeigChange() {
        super.onNeigChange();
        if (isPowered() && (craftState == 0)) craftState = 1;
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        if (isPowered()) {
            if (craftState == 0) {
                craftState = 1;
            }
        } else {
            if (craftState == 2) {
                craftState = 0;
            }
        }
        if (itemMatches == -1) {
            refreshItemMatches();
        }
        if (worldObj.getTotalWorldTime() % 40 == 0) {
            refreshItemMatches();
        }
        if (isControlled()) {
            if (craft())
                refreshItemMatches();
            craftState = 2;
        }
    }

    public boolean isControlled() {
        if (state == RedstoneState.NORMAL) return !powered;
        if (state == RedstoneState.INVERTED) return powered;
        return (craftState == 1);
    }

    @SuppressWarnings("unchecked")
	public void refreshRecipe() {
        craftRecipe = null;
        for (Object rec : CraftingManager.getInstance().getRecipeList()) {
            if (rec instanceof IRecipe) {
                if (((IRecipe) rec).matches(recipe, worldObj)) {
                    craftRecipe = (IRecipe) rec;
                    break;
                }
            }
        }
        
        if (craftRecipe != null) {
            ItemStack result = craftRecipe.getCraftingResult(recipe);
            if (result == null) {
                craftRecipe = null;
            } else {
                invResult.setInventorySlotContents(0, result);
            }
        } else {
            invResult.setInventorySlotContents(0, null);
        }
    }

    public void refreshItemMatches() {
        itemMatches = 0;
        checkedInvs.clear();
        checkedTanks.clear();
        if (craftRecipe == null) {
            refreshRecipe();
            if (craftRecipe == null) {
                return;
            }
        }
        ItemStack result = craftRecipe.getCraftingResult(recipe);
        for (int slot = 0; slot < 9; slot++) {
            if (recipe.getStackInSlot(slot) == null) {
                itemMatches |= 1 << slot;
                continue;
            }
            if (findItemsFromInventory(slot, this, checkedInvs, result, MgDirection.UP)) {
                itemMatches |= 1 << slot;
            } else {
                for (MgDirection dir : MgDirection.values()) {
                    TileEntity t = MgUtils.getTileEntity(this, dir);
                    if (t instanceof IInventory) {
                        IInventory side = (IInventory) t;
                        if (findItemsFromInventory(slot, side, checkedInvs, result, dir)) {
                            itemMatches |= 1 << slot;
                            break;
                        }
                    }
                    FluidStack fluid = FluidContainerRegistry.getFluidForFilledItem(recipe.getStackInSlot(slot));
                    if (fluid != null) {
                        if (t instanceof IFluidHandler) {
                            IFluidHandler tank = (IFluidHandler) t;
                            boolean breaked = false;
                            for (MgDirection d : MgDirection.values()) {
                                FluidStack f = tank.drain(d.toForgeDir(), 1000, false);
                                if (f != null && MgUtils.areEqual(fluid, f)) {
                                    TankInfo comp = new TankInfo(tank, dir);
                                    if (checkedTanks.contains(comp)) {
                                        TankInfo comp2 = checkedTanks.get(checkedTanks.indexOf(comp));
                                        FluidStack f2 = tank.drain(d.toForgeDir(), comp2.amount + FluidContainerRegistry.getContainerCapacity(recipe.getStackInSlot(slot)), false);
                                        if (f2 != null && f2.amount == comp2.amount + FluidContainerRegistry.getContainerCapacity(recipe.getStackInSlot(slot))) {
                                            comp2.amount += FluidContainerRegistry.getContainerCapacity(recipe.getStackInSlot(slot));
                                            checkedTanks.add(comp);
                                            itemMatches |= 1 << slot;
                                            breaked = true;
                                            break;
                                        }
                                    } else {
                                        FluidStack f2 = tank.drain(d.toForgeDir(), FluidContainerRegistry.getContainerCapacity(recipe.getStackInSlot(slot)), false);
                                        if (f2 != null && f2.amount == FluidContainerRegistry.getContainerCapacity(recipe.getStackInSlot(slot))) {
                                            comp.amount = FluidContainerRegistry.getContainerCapacity(recipe.getStackInSlot(slot));
                                            checkedTanks.add(comp);
                                            itemMatches |= 1 << slot;
                                            breaked = true;
                                            break;
                                        }
                                    }
                                }
                            }
                            if (breaked) break;
                        }
                    }
                }
            }
        }
    }

    public boolean craft() {
        if (itemMatches == 0x1FF && craftRecipe != null) {
            ItemStack item = craftRecipe.getCraftingResult(recipe);
            if (item != null) {
                int slot = InventoryUtils.getSlotForStack(getInv(), item);
                if (slot != -1) {
                    for (InvSlot s : checkedInvs) {
                        InventoryUtils.remove(s.inv, s.slot, s.amount, getInv());
                    }
                    for (TankInfo s : checkedTanks) {
                        s.handler.drain(s.dir.toForgeDir(), s.amount, true);
                    }
                    if (InventoryUtils.canCombine(getInv().getStackInSlot(slot), item, 64)) {
                        ItemStack result = InventoryUtils.addition(getInv().getStackInSlot(slot), item);
                        getInv().setInventorySlotContents(slot, result);
                    } else {
                        if (!InventoryUtils.dropIntoInventory(item, getInv())) {
                            BlockMg.dropItem(item, getWorldObj().rand, xCoord, yCoord, zCoord, getWorldObj());
                        }
                    }
                    checkedInvs.clear();
                    return true;
                }
            }
        }
        return false;
    }

    private boolean findItemsFromInventory(int slot, IInventory inv, List<InvSlot> visited, ItemStack result, MgDirection dir) {
        for (int i = 0; i < inv.getSizeInventory(); i++) {
            ItemStack content = inv.getStackInSlot(i);
            if (content != null && (content.stackSize > 0) && canAccess(inv, i, dir)) {
                if (replaceMatrix(recipe, result, content, slot)) {
                    InvSlot pos = new InvSlot(i, inv);
                    if (!visited.contains(pos)) {
                        visited.add(pos);
                        return true;
                    } else {
                        InvSlot ex = visited.get(visited.indexOf(pos));
                        if (content.stackSize > ex.amount) {
                            ex.amount++;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean canAccess(IInventory inv, int slot, MgDirection dir) {
        if (inv instanceof ISidedInventory) {
            ISidedInventory sided = (ISidedInventory) inv;
            for (int i : sided.getAccessibleSlotsFromSide(dir.ordinal())) {
                if (i == slot) {
                    if (sided.canExtractItem(slot, sided.getStackInSlot(slot), dir.ordinal())) {
                        return true;
                    }
                    break;
                }
            }
            return false;
        }
        return true;
    }

    public boolean replaceMatrix(InventoryCrafting craft, ItemStack result, ItemStack stack, int slot) {
        ItemStack item = craft.getStackInSlot(slot);
        craft.setInventorySlotContents(slot, stack);
        boolean ret = false;
        if (craftRecipe.matches(craft, worldObj)) {
            ItemStack newResult = craftRecipe.getCraftingResult(craft);
            if (result == null || result.getItem() == null) return false;
            ret = OreDictionary.itemMatches(result, newResult, true);
        }
        craft.setInventorySlotContents(slot, item);
        return ret;
    }

    public int getSizeInventory() {
        return getInv().getSizeInventory();
    }

    public ItemStack getStackInSlot(int s) {
        return getInv().getStackInSlot(s);
    }

    public ItemStack decrStackSize(int a, int b) {
        return getInv().decrStackSize(a, b);
    }

    public ItemStack getStackInSlotOnClosing(int a) {
        return getInv().getStackInSlotOnClosing(a);
    }

    public void setInventorySlotContents(int a, ItemStack b) {
        getInv().setInventorySlotContents(a, b);
    }

    public String getInventoryName() {
        return getInv().getInventoryName();
    }

    public boolean hasCustomInventoryName() {
        return getInv().hasCustomInventoryName();
    }

    public int getInventoryStackLimit() {
        return getInv().getInventoryStackLimit();
    }

    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    public void openInventory() {
    }

    public void closeInventory() {
    }

    public boolean isItemValidForSlot(int a, ItemStack b) {
        return getInv().isItemValidForSlot(a, b);
    }

    public InventoryCrafting getRecipe() {
        return recipe;
    }

    public InventoryComponent getResult() {
        return invResult;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        inv.readFromNBT(nbt, "Inv_1");
        InventoryUtils.loadInventory(recipe, nbt, "Inv_2");
        invResult.readFromNBT(nbt, "Inv_3");
        itemMatches = nbt.getInteger("Matches");
        state = RedstoneState.values()[nbt.getByte("State")];
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        inv.writeToNBT(nbt, "Inv_1");
        InventoryUtils.saveInventory(recipe, nbt, "Inv_2");
        invResult.writeToNBT(nbt, "Inv_3");
        nbt.setInteger("Matches", itemMatches);
        nbt.setByte("State", (byte) state.ordinal());
    }

    public class InvSlot {

        public ItemStack content;
        public int amount;
        public int slot;
        public IInventory inv;

        public InvSlot(int slot, IInventory inv) {
            this.inv = inv;
            this.slot = slot;
            this.inv = inv;
            this.amount = 1;
        }

        public boolean equals(Object obj) {
            if (obj instanceof InvSlot) {
                InvSlot pos = (InvSlot) obj;
                return pos.slot == slot && pos.inv == inv && InventoryUtils.areExactlyEqual(content, pos.content);
            }
            return false;
        }
    }

    public class TankInfo {

        public MgDirection dir;
        public IFluidHandler handler;
        public int amount;

        public TankInfo(IFluidHandler f, MgDirection dir) {
            this.dir = dir;
            this.handler = f;
        }

        public boolean equals(Object o) {
            return o instanceof TankInfo && ((TankInfo) o).dir == this.dir && ((TankInfo) o).handler == this.handler;
        }
    }

    public enum RedstoneState {
        NORMAL, INVERTED, PULSE
    }

    public static RedstoneState step(RedstoneState state) {
        return state == RedstoneState.NORMAL ? RedstoneState.INVERTED : state == RedstoneState.INVERTED ? RedstoneState.PULSE : RedstoneState.NORMAL;
    }

    public void setRedstoneState(RedstoneState e) {
        state = e;
        sendUpdateToClient();
    }

    public boolean found(int j) {
        return itemMatches != -1 && (craftRecipe == null || (itemMatches & (1 << j)) > 0);
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, itemMatches);

    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 0) itemMatches = value;
    }

    @Override
    public void onMessageReceive(int id, int data) {
        if (id == 0) {
            if (data == 1) {
                for (int i = 0; i < 9; i++)
                    getRecipe().setInventorySlotContents(i, null);
                refreshRecipe();
            } else if (data == 0) {
                if (state == RedstoneState.PULSE) craftState = 1;
            }
        }
    }
}
