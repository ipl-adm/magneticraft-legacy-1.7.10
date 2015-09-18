package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.TileHeatConductor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

public class TileBrickFurnace extends TileHeatConductor implements IInventoryManaged, ISidedInventory, IGuiSync {

    private static final float MAX_PROGRESS = 100;
    private InventoryComponent inv = new InventoryComponent(this, 2, "Brick Furnace");
    private float progress;
    private boolean working;

    @Override
    public IHeatConductor initHeatCond() {
        return new HeatConductor(this, 1400, 1000);
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
        if (heat.getTemperature() > 80 && isControlled()) {
            if (canSmelt()) {
                double speed = getSpeed();
                heat.drainCalories(speed);
                progress += EnergyConverter.CALORIEStoFUEL(speed);
                if (progress >= MAX_PROGRESS) {
                    smelt();
                    progress -= MAX_PROGRESS;
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

    private double getSpeed() {
        return EnergyConverter.FUELtoCALORIES(heat.getTemperature() / 100d);
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) heat.getTemperature());
        craft.sendProgressBarUpdate(cont, 1, (int) progress);
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 0) heat.setTemperature(value);
        if (id == 1) progress = value;
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

    private void setActive(boolean b) {
        if (b)
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() % 6 + 6, 2);
        else
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() % 6, 2);
    }

    public boolean isActive() {
        return getBlockMetadata() > 5;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        super.readFromNBT(nbtTagCompound);
        getInv().readFromNBT(nbtTagCompound);
        progress = nbtTagCompound.getFloat("P");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        getInv().writeToNBT(nbtTagCompound);
        nbtTagCompound.setFloat("P", progress);
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

    public boolean isItemValidForSlot(int a, ItemStack b) {
        return a == 0;
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

    public IBarProvider getProgresBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return null;
            }

            @Override
            public float getMaxLevel() {
                return MAX_PROGRESS;
            }

            @Override
            public float getLevel() {
                return progress;
            }
        };
    }
}
