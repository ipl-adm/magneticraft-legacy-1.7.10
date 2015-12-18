package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.tile.AverageBar;
import com.cout970.magneticraft.util.tile.MachineElectricConductor;
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

    private static final float MAX_ENERGY_PRODUCTION = 400;
    //cook
    public float progress = 0;
    public int maxProgres;
    //heat values 25-120
    public int maxHeat = 500;
    public IHeatConductor heat = new HeatConductor(this, 1500, 1000);
    //gui data
    public AverageBar steamProd = new AverageBar(20);
    public AverageBar electricProd = new AverageBar(20);
    //steam tank
    public TankMg steam = new TankMg(this, 4000);
    public TankMg water = new TankMg(this, 2000);

    public static final int speed = 4;

    private InventoryComponent inv = new InventoryComponent(this, 1, "Basic Generator");
    private int oldHeat;
    private boolean working;

    @Override
    public IElectricConductor initConductor() {
        return new MachineElectricConductor(this);
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
        steamProd.tick();
        electricProd.tick();
        if (worldObj.getTotalWorldTime() % 20 == 0) {
            if (working && !isActive()) {
                setActive(true);
            } else if (!working && isActive()) {
                setActive(false);
            }
        }
        if (progress > 0) {
            //fuel to temp
            if (heat.getTemperature() < maxHeat && isControlled()) {
                heat.applyCalories(EnergyConverter.FUELtoCALORIES(speed));
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
            int change = Math.min(water.getFluidAmount(), EnergyConverter.STEAMtoWATER(steam.getCapacity() - steam.getFluidAmount()));
            change = Math.min(change, speed);
            if (change > 0) {
                heat.drainCalories(EnergyConverter.WATERtoSTEAM_HEAT(change));
                water.drain(change, true);
                steam.fill(FluidRegistry.getFluidStack("steam", EnergyConverter.WATERtoSTEAM(change)), true);
                steamProd.addValue(EnergyConverter.WATERtoSTEAM(change));
            }
        }
        //steam to power
        int gas = Math.min(steam.getFluidAmount(), (int) EnergyConverter.WtoSTEAM(MAX_ENERGY_PRODUCTION));
        if (gas > 0 && cond.getVoltage() < ElectricConstants.MAX_VOLTAGE && isControlled()) {
            cond.applyPower(EnergyConverter.STEAMtoW(gas));
            electricProd.addValue((float) EnergyConverter.STEAMtoW(gas));
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
        craft.sendProgressBarUpdate(cont, 3, (int) progress);
        craft.sendProgressBarUpdate(cont, 4, maxProgres);
        craft.sendProgressBarUpdate(cont, 5, (int) Math.ceil(heat.getTemperature()));
        craft.sendProgressBarUpdate(cont, 6, steam.getFluidAmount());
        craft.sendProgressBarUpdate(cont, 7, water.getFluidAmount());
        craft.sendProgressBarUpdate(cont, 8, (int) (steamProd.getAverage()*16));
        craft.sendProgressBarUpdate(cont, 9, (int) (electricProd.getAverage()*16));
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        switch (id) {
            case 0:
                cond.setVoltage(value);
                break;
            case 3:
                progress = value;
                break;
            case 4:
                maxProgres = value;
                break;
            case 5:
                heat.setTemperature(value);
                break;
            case 6:
                steam.setFluid(FluidRegistry.getFluidStack("steam", value));
                break;
            case 7:
                water.setFluid(FluidRegistry.getFluidStack("water", value));
                break;
            case 8:
                steamProd.setStorage(value / 16f);
                break;
            case 9:
                electricProd.setStorage(value / 16f);
                break;
        }
    }

    @Override
    public int fillMg(MgDirection from, FluidStack resource, boolean doFill) {
        if (resource != null) {
            if (resource.getFluidID() == FluidRegistry.getFluidID("water"))
                return water.fill(resource, doFill);
            if (resource.getFluidID() == FluidRegistry.getFluidID("steam")) {
                return steam.fill(resource, doFill);
            }
        }
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

    public IBarProvider getEnergyProductionBar() {
        return new IBarProvider() {
            @Override
            public String getMessage() {
                return String.format("%dW", (int)electricProd.getStorage());
            }

            @Override
            public float getLevel() {
                return electricProd.getStorage();
            }

            @Override
            public float getMaxLevel() {
                return MAX_ENERGY_PRODUCTION;
            }
        };
    }

    public IBarProvider getSteamProductionBar() {
        return new IBarProvider() {
            @Override
            public String getMessage() {
                return String.format("%.2f mB/t", steamProd.getStorage());
            }

            @Override
            public float getLevel() {
                return steamProd.getStorage();
            }

            @Override
            public float getMaxLevel() {
                return EnergyConverter.WATERtoSTEAM(speed);
            }
        };
    }
}
