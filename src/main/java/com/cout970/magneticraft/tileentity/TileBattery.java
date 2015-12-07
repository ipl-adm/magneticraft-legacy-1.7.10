package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IBatteryItem;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IBlockWithData;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.AverageBar;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileBattery extends TileConductorLow implements IGuiSync, IInventoryManaged, ISidedInventory, IBlockWithData {

    private InventoryComponent inv = new InventoryComponent(this, 2, "Battery");
    public static int BATTERY_CHARGE_SPEED = (int) EnergyConverter.RFtoW(400);//RF
    private AverageBar chargeRate = new AverageBar(10);
    private AverageBar dischargeRate = new AverageBar(10);

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this) {

            public int storage = 0;
            public int maxStorage = (int) EnergyConverter.RFtoW(2000000);
            public double min = ElectricConstants.BATTERY_DISCHARGE;
            public double max = ElectricConstants.BATTERY_CHARGE;

            public void iterate() {
                super.iterate();
                if (!isControlled()) return;
                if (getVoltage() > max && storage < maxStorage) {
                    int change;
                    change = (int) Math.min((getVoltage() - max) * 80, EnergyConverter.RFtoW(400));
                    change = Math.min(change, maxStorage - storage);
                    drainPower(change);
                    chargeRate.addValue(change);
                    storage += change;
                } else if (getVoltage() < min && storage > 0) {
                    int change;
                    change = (int) Math.min((min - getVoltage()) * 80, EnergyConverter.RFtoW(400));
                    change = Math.min(change, storage);
                    applyPower(change);
                    dischargeRate.addValue(change);
                    storage -= change;
                }
            }

            @Override
            public int getStorage() {
                return storage;
            }

            @Override
            public int getMaxStorage() {
                return maxStorage;
            }

            @Override
            public void setStorage(int charge) {
                storage = charge;
            }

            @Override
            public void applyCharge(int charge) {
                storage += charge;
                if (storage > maxStorage)
                    storage = maxStorage;
            }

            @Override
            public void drainCharge(int charge) {
                storage -= charge;
                if (storage < 0) storage = 0;
            }

            @Override
            public void save(NBTTagCompound nbt) {
                super.save(nbt);
                nbt.setInteger("Storage", storage);
            }

            @Override
            public void load(NBTTagCompound nbt) {
                super.load(nbt);
                storage = nbt.getInteger("Storage");
            }
        };
    }

    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            if (worldObj.getTotalWorldTime() % 40 == 0) {
                int amount = (int) Math.floor(cond.getStorage() * 11F / cond.getMaxStorage());
                int meta = getBlockMetadata();
                if (amount != meta) {
                    worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, amount, 3);
                }
            }
            chargeRate.tick();
            dischargeRate.tick();
            ItemStack i = getInv().getStackInSlot(0);
            if (i != null) {
                if (i.getItem() instanceof IBatteryItem) {
                    IBatteryItem b = (IBatteryItem) i.getItem();
                    if (b.canAcceptCharge(i)) {
                        int canFill = Math.min(b.getMaxCharge(i) - b.getCharge(i), cond.getStorage());
                        canFill = Math.min(canFill, BATTERY_CHARGE_SPEED);
                        if (canFill > 0) {
                            b.charge(i, canFill);
                            cond.drainCharge(canFill);
                        }
                    }
                }
            }
            i = getInv().getStackInSlot(1);
            if (i != null) {
                if (i.getItem() instanceof IBatteryItem) {
                    IBatteryItem b = (IBatteryItem) i.getItem();
                    if (b.canExtractCharge(i)) {
                        int canDrain = Math.min(b.getCharge(i), cond.getMaxStorage() - cond.getStorage());
                        canDrain = Math.min(canDrain, BATTERY_CHARGE_SPEED);
                        if (canDrain > 0) {
                            b.discharge(i, canDrain);
                            cond.applyCharge(canDrain);
                        }
                    }
                }
            }
        }
    }

    public InventoryComponent getInv() {
        return inv;
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, (cond.getStorage() & 0xFFFF));
        craft.sendProgressBarUpdate(cont, 2, ((cond.getStorage() & 0xFFFF0000) >>> 16));
        craft.sendProgressBarUpdate(cont, 3, (int) (chargeRate.getAverage()*1024));
        craft.sendProgressBarUpdate(cont, 4, (int) (dischargeRate.getAverage()*1024));
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        switch (id) {
            case 0:
                cond.setVoltage(value);
                break;
            case 1:
                cond.setStorage(value & 0xFFFF);
                break;
            case 2:
                cond.setStorage(cond.getStorage() | (value << 16));
                break;
            case 3:
                chargeRate.setStorage(value/1024f);
                break;
            case 4:
                dischargeRate.setStorage(value/1024f);
                break;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int s) {
        if (s == 0 || s == 1) return new int[]{0};
        return new int[]{1};
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,
                                 int p_102007_3_) {
        return true;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
                                  int p_102008_3_) {
        return true;
    }

    @Override
    public void saveData(NBTTagCompound nbt) {
        nbt.setInteger("Stored", cond.getStorage());
    }

    @Override
    public void loadData(NBTTagCompound nbt) {
        cond.setStorage(nbt.getInteger("Stored"));
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

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        getInv().readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        getInv().writeToNBT(nbt);
    }

    public double getChargeRate() {
        return chargeRate.getStorage();
    }

    public double getDischargeRate() {
        return dischargeRate.getStorage();
    }
}
