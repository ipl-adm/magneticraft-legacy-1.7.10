package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.tool.IFurnaceCoil;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.MachineElectricConductor;
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
    public float progress = 0;
    private boolean working;
    private float consumption, consumptionAux;

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
            consumption = consumptionAux / 20;
            consumptionAux = 0;
        }
        if (cond.getVoltage() > ElectricConstants.MACHINE_WORK && isControlled()) {
            if (canSmelt()) {
                double speed = getEfficiency(cond.getVoltage(), ElectricConstants.MACHINE_WORK, ElectricConstants.BATTERY_CHARGE);

                if(speed > 0) {
                    progress += speed;
                    cond.drainPower(getConsumption()*speed);
                    consumptionAux += (float) (getConsumption()*speed);
                    if (progress >= getMaxProgress()) {
                        smelt();
                        progress = 0;
                    }
                    working = true;
                }
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
        progress = nbtTagCompound.getFloat("P");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        super.writeToNBT(nbtTagCompound);
        getInv().writeToNBT(nbtTagCompound);
        nbtTagCompound.setFloat("P", progress);
    }

    @Override
    public IElectricConductor initConductor() {
        return new MachineElectricConductor(this);
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, (int)progress);
        craft.sendProgressBarUpdate(cont, 2, (int)(consumption*16));
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        switch (id) {
            case 0:
                cond.setVoltage(value);
                break;
            case 1:
                progress = value;
                break;
            case 2:
                consumption = value / 16;
                break;
        }
    }

    public int getMaxProgress() {
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

    public IBarProvider getProgressBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return null;
            }

            @Override
            public float getMaxLevel() {
                return getMaxProgress();
            }

            @Override
            public float getLevel() {
                return progress;
            }
        };
    }

    public IBarProvider getConsumptionBar() {
        return new IBarProvider() {
            @Override
            public String getMessage() {
                return String.format("%.2fW", consumption);
            }

            @Override
            public float getLevel() {
                return (float) Math.min(consumption, getConsumption());
            }

            @Override
            public float getMaxLevel() {
                return (float)getConsumption();
            }
        };
    }
}
