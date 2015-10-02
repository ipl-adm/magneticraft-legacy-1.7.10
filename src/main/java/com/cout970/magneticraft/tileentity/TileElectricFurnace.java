package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.BufferedConductor;
import com.cout970.magneticraft.api.tool.IFurnaceCoil;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

public class TileElectricFurnace extends TileConductorLow implements IInventoryManaged, IGuiSync, ISidedInventory {

    public InventoryComponent inv = new InventoryComponent(this, 3, "Electric Furnace");
    public int progress = 0;
    private boolean working;

    public TileElectricFurnace() {
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        if (worldObj.getTotalWorldTime() % 20 == 0) {
            if (working && !isActive()) {
                setActive(true);
            } else if (!working && isActive()) {
                setActive(false);
            }
        }
        if (cond.getVoltage() >= ElectricConstants.MACHINE_WORK && isControlled()) {
            if (canSmelt()) {
                progress++;
                cond.drainPower(getConsumption());
                if (progress >= getMaxProgres()) {
                    smelt();
                    progress = 0;
                }
                working = true;
            } else {
                working = false;
                progress = 0;
            }
        } else {
            working = false;
        }
    }

    private void setActive(boolean b) {
        if (b)
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() + 6, 2);
        else
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() - 6, 2);
    }

    public boolean isActive() {
        return getBlockMetadata() > 5;
    }

    public double getConsumption() {
        if (getInv().getStackInSlot(2) == null) return 0;
        if (!(getInv().getStackInSlot(2).getItem() instanceof IFurnaceCoil)) return 0;
        return ((IFurnaceCoil) getInv().getStackInSlot(2).getItem()).getElectricConsumption();
    }

    private void smelt() {
        if (canSmelt()) {
            ItemStack a = getInv().getStackInSlot(0);
            ItemStack b = getInv().getStackInSlot(1);
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(a);

            if (b == null) {
                getInv().setInventorySlotContents(1, itemstack.copy());
            } else if (b.isItemEqual(itemstack)) {
                b.stackSize += itemstack.stackSize;
            }
            --a.stackSize;
            if (a.stackSize <= 0) {
                getInv().setInventorySlotContents(0, null);
            }
        }
    }

    private boolean canSmelt() {
        ItemStack a = getInv().getStackInSlot(2);
        if (a == null) return false;
        if (!(a.getItem() instanceof IFurnaceCoil)) return false;
        if (getInv().getStackInSlot(0) == null) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(getInv().getStackInSlot(0));
            if (itemstack == null) return false;
            if (getInv().getStackInSlot(1) == null) return true;
            if (!getInv().getStackInSlot(1).isItemEqual(itemstack)) return false;
            int result = getInv().getStackInSlot(1).stackSize + itemstack.stackSize;
            return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
        }
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return (slot == 0) || ((slot == 2) && (item.getItem() instanceof IFurnaceCoil));
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        getInv().readFromNBT(nbtTagCompound);
        progress = nbtTagCompound.getInteger("P");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        getInv().writeToNBT(nbtTagCompound);
        nbtTagCompound.setInteger("P", progress);
    }

    @Override
    public IElectricConductor initConductor() {
        return new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW, 80000, ElectricConstants.MACHINE_DISCHARGE, ElectricConstants.MACHINE_CHARGE);
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, progress);
        craft.sendProgressBarUpdate(cont, 2, (cond.getStorage() & 0xFFFF));
        craft.sendProgressBarUpdate(cont, 3, ((cond.getStorage() & 0xFFFF0000) >>> 16));
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 0)
            cond.setVoltage(value);
        if (id == 1)
            progress = value;
        if (id == 2)
            cond.setStorage(value & 0xFFFF);
        if (id == 3)
            cond.setStorage(cond.getStorage() | (value << 16));
    }

    public int getMaxProgres() {
        if (getInv().getStackInSlot(2) == null) return -1;
        if (!(getInv().getStackInSlot(2).getItem() instanceof IFurnaceCoil)) return -1;
        return ((IFurnaceCoil) getInv().getStackInSlot(2).getItem()).getCookTime();
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[]{0, 1};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return slot == 0;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return slot == 1;
    }

    public InventoryComponent getInv() {
        return inv;
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

    public IBarProvider getProgresBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return null;
            }

            @Override
            public float getMaxLevel() {
                return getMaxProgres();
            }

            @Override
            public float getLevel() {
                return progress;
            }
        };
    }
}
