package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.BufferedConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

public class TileBasicGenerator extends TileConductorLow implements IFluidHandler1_8, IGuiSync, IInventoryManaged, IHeatTile {

    //cook
    public float progress = 0;
    public int maxProgres;
    //heat values 25-120
    public int maxHeat = 500;
    public IHeatConductor heat = new HeatConductor(this, 1400, 1000);
    //gui data
    public int steamProduction;
    public int electricProduction;

    public float steamProductionM;
    public float electricProductionM;
    //steam tank
    public TankMg steam = new TankMg(this, 4000);
    public TankMg water = new TankMg(this, 2000);

    public static final int speed = 4;

    private InventoryComponent inv = new InventoryComponent(this, 1, "Basic Generator");
    private int oldHeat;
    private boolean working;

    @Override
    public IElectricConductor initConductor() {
        return new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW, 160000, ElectricConstants.GENERATOR_DISCHARGE, ElectricConstants.GENERATOR_CHARGE);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        heat.iterate();
        if (((int) heat.getTemperature()) != oldHeat && worldObj.getTotalWorldTime() % 20 == 0) {
            sendUpdateToClient();
            oldHeat = (int) heat.getTemperature();
        }
        if (worldObj.getTotalWorldTime() % 20 == 0) {
            steamProduction = 0;
            electricProduction = 0;
            if (working && !isActive()) {
                setActive(true);
            } else if (!working && isActive()) {
                setActive(false);
            }
        }
        if (progress > 0) {
            //fuel to temp
            if (heat.getTemperature() < maxHeat && isControlled()) {
                heat.applyCalories(EnergyConversor.FUELtoCALORIES(speed));
                if (progress - speed < 0) {
                    progress = 0;
                    working = false;
                } else {
                    progress -= speed;
                }
            }
        }
        //temp to steam
        if (heat.getTemperature() > 100) {
            int change = Math.min(water.getFluidAmount(), EnergyConversor.STEAMtoWATER(steam.getCapacity() - steam.getFluidAmount()));
            change = Math.min(change, speed);
            if (change > 0) {
                heat.drainCalories(EnergyConversor.WATERtoSTEAM_HEAT(change));
                water.drain(change, true);
                steam.fill(FluidRegistry.getFluidStack("steam", EnergyConversor.WATERtoSTEAM(change)), true);
                steamProduction += EnergyConversor.WATERtoSTEAM(change);
            }
        }
        //steam to power
        int gas = Math.min(steam.getFluidAmount(), (int) EnergyConversor.WtoSTEAM(2000));
        if (gas > 0 && cond.getVoltage() < ElectricConstants.MAX_VOLTAGE && isControlled()) {
            cond.applyPower(EnergyConversor.STEAMtoW(gas));
            electricProduction += EnergyConversor.STEAMtoW(gas);
            steam.drain(gas, true);
        }

        if (progress <= 0) {
            ItemStack a = getInv().getStackInSlot(0);
            if (a != null && isControlled()) {
                int fuel = TileEntityFurnace.getItemBurnTime(a);
                if (fuel > 0 && cond.getVoltage() < ElectricConstants.MAX_VOLTAGE && steam.getFluidAmount() < 1) {
                    progress = fuel;
                    maxProgres = fuel;
                    a.stackSize--;
                    if (a.stackSize <= 0) {
                        a = a.getItem().getContainerItem(a);
                    }
                    getInv().setInventorySlotContents(0, a);
                    working = true;
                    markDirty();
                } else working = false;
            } else working = false;
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

    //Save & Load

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        progress = nbt.getFloat("progres");
        heat.load(nbt);
        water.readFromNBT(nbt, "water");
        steam.readFromNBT(nbt, "steam");
        getInv().readFromNBT(nbt);
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setFloat("progres", progress);
        heat.save(nbt);
        water.writeToNBT(nbt, "water");
        steam.writeToNBT(nbt, "steam");
        getInv().writeToNBT(nbt);
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, (cond.getStorage() & 0xFFFF));
        craft.sendProgressBarUpdate(cont, 2, ((cond.getStorage() & 0xFFFF0000) >>> 16));
        craft.sendProgressBarUpdate(cont, 3, (int) progress);
        craft.sendProgressBarUpdate(cont, 4, maxProgres);
        craft.sendProgressBarUpdate(cont, 5, (int) Math.ceil(heat.getTemperature()));
        craft.sendProgressBarUpdate(cont, 6, steam.getFluidAmount());
        craft.sendProgressBarUpdate(cont, 7, water.getFluidAmount());
        if (worldObj.getTotalWorldTime() % 20 == 0) {
            craft.sendProgressBarUpdate(cont, 8, steamProduction);
            craft.sendProgressBarUpdate(cont, 9, electricProduction);
        }
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 0)
            cond.setVoltage(value);
        if (id == 1)
            cond.setStorage(value & 0xFFFF);
        if (id == 2)
            cond.setStorage(cond.getStorage() | (value << 16));
        if (id == 3)
            progress = value;
        if (id == 4)
            maxProgres = value;
        if (id == 5)
            heat.setTemperature(value);
        if (id == 6)
            steam.setFluid(FluidRegistry.getFluidStack("steam", value));
        if (id == 7)
            water.setFluid(FluidRegistry.getFluidStack("water", value));
        if (id == 8)
            steamProductionM = value / 20f;
        if (id == 9)
            electricProductionM = value / 20f;
    }

    @Override
    public int fillMg(MgDirection from, FluidStack resource, boolean doFill) {
        if (resource != null && resource.getFluidID() == FluidRegistry.getFluidID("water"))
            return water.fill(resource, doFill);
        return 0;
    }

    @Override
    public FluidStack drainMg_F(MgDirection from, FluidStack resource,
                                boolean doDrain) {
        return drainMg(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drainMg(MgDirection from, int maxDrain, boolean doDrain) {
        return steam.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFillMg(MgDirection from, Fluid fluid) {
        return FluidRegistry.WATER == fluid;
    }

    @Override
    public boolean canDrainMg(MgDirection from, Fluid fluid) {
        return FluidRegistry.getFluid("steam") == fluid;
    }

    @Override
    public FluidTankInfo[] getTankInfoMg(MgDirection from) {
        return new FluidTankInfo[]{water.getInfo(), steam.getInfo()};
    }

    @Override
    public IHeatConductor[] getHeatCond(VecInt c) {
        return new IHeatConductor[]{heat};
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
        return getInv().isItemValidForSlot(a, b);
    }

    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return this.fillMg(MgDirection.getDirection(from.ordinal()), resource, doFill);
    }

    public FluidStack drain(ForgeDirection from, FluidStack resource,
                            boolean doDrain) {
        return this.drainMg_F(MgDirection.getDirection(from.ordinal()), resource, doDrain);
    }

    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return this.drainMg(MgDirection.getDirection(from.ordinal()), maxDrain, doDrain);

    }

    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return this.canFillMg(MgDirection.getDirection(from.ordinal()), fluid);
    }

    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return this.canDrainMg(MgDirection.getDirection(from.ordinal()), fluid);
    }

    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return this.getTankInfoMg(MgDirection.getDirection(from.ordinal()));
    }

    public IBarProvider getBurningTimeBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return null;
            }

            @Override
            public float getMaxLevel() {
                return maxProgres;
            }

            @Override
            public float getLevel() {
                return progress;
            }
        };
    }
}
