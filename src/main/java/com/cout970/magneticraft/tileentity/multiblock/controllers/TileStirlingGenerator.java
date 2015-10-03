package com.cout970.magneticraft.tileentity.multiblock.controllers;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.util.*;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Base;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Random;

public class TileStirlingGenerator extends TileMB_Base implements IInventoryManaged, IGuiSync, IElectricTile, IHeatTile {

    private static final double MAX_PRODUCTION = 1200;
    public IHeatConductor heat;
    public InventoryComponent inv = new InventoryComponent(this, 1, "Stirling generator");
    public IElectricConductor cond;
    public int oldHeat;
    private int progress;
    private boolean working;
    private int maxProgres;
    public int drawCounter = 0;
    private boolean burning;
    private double prodCounter;
    private double prodSecond;

    public void updateEntity() {
        super.updateEntity();
        if (drawCounter > 0) drawCounter--;
        if (!isActive()) return;
        if (cond == null || heat == null) {
            search();
        }
        if (isWorking()) {
            if (worldObj.getTotalWorldTime() % 2 == 0) {
                for (int i = 0; i < 2; i++) {
                    VecDouble vec = new VecDouble(xCoord + 0.5, yCoord - 0.2, zCoord + 0.5);
                    MgDirection dir = getDirection();
                    vec.add(dir.getOffsetX() * 0.3, 0, dir.getOffsetZ() * 0.3);
                    Random r = worldObj.rand;
                    dir = dir.step(MgDirection.UP);
                    vec.add(dir.getOffsetX() * (0.5 - r.nextDouble()), 0, dir.getOffsetZ() * (0.5 - r.nextDouble()));
                    if (r.nextBoolean())
                        worldObj.spawnParticle("flame", vec.getX(), vec.getY(), vec.getZ(), 0.003125 - 0.00625 * r.nextDouble(), 0.003125 * r.nextInt(15), 0.003125 - 0.00625 * r.nextDouble());
                    else
                        worldObj.spawnParticle("smoke", vec.getX(), vec.getY(), vec.getZ(), 0.003125 - 0.00625 * r.nextDouble(), 0.003125 * r.nextInt(15), 0.003125 - 0.00625 * r.nextDouble());
                }
            }
        }
        if (this.worldObj.isRemote) return;
        if (worldObj.getTotalWorldTime() % 20 == 0) {
            if (working && !isWorking()) {
                setActive(true);
            } else if (!working && isWorking()) {
                setActive(false);
            }
        }
        if (cond == null || heat == null) return;
        if (progress > 0) {
            //fuel to heat
            if (heat.getTemperature() < heat.getMaxTemp() - 200 && isControlled()) {
                int i = 12;//burning speed
                if (progress - i < 0) {
                    heat.applyCalories(EnergyConverter.FUELtoCALORIES(progress));
                    progress = 0;
                } else {
                    progress -= i;
                    heat.applyCalories(EnergyConverter.FUELtoCALORIES(i));
                }

            }
        }
        if (progress <= 0) {
            working = false;
            if (getInv().getStackInSlot(0) != null && isControlled()) {
                int fuel = TileEntityFurnace.getItemBurnTime(getInv().getStackInSlot(0));
                if (fuel > 0 && heat.getTemperature() < heat.getMaxTemp()) {
                    progress = fuel;
                    maxProgres = fuel;
                    if (getInv().getStackInSlot(0) != null) {
                        getInv().getStackInSlot(0).stackSize--;
                        if (getInv().getStackInSlot(0).stackSize <= 0) {
                            getInv().setInventorySlotContents(0, getInv().getStackInSlot(0).getItem().getContainerItem(getInv().getStackInSlot(0)));
                        }
                    }
                    working = true;
                    markDirty();
                }
            }
        }

        if (heat.getTemperature() > 30 && cond.getVoltage() < ElectricConstants.MAX_VOLTAGE) {
            int prod = (int) Math.min(MAX_PRODUCTION, (heat.getTemperature() - 30) * 10);
            heat.drainCalories(EnergyConverter.WtoCALORIES(prod));
            cond.applyPower(prod);
            prodCounter += prod;
        }

        if (worldObj.getTotalWorldTime() % 20 == 0) {
            prodSecond = prodCounter;
            prodCounter = 0;
        }

        if (((int) heat.getTemperature()) != oldHeat && worldObj.provider.getWorldTime() % 10 == 0) {
            sendUpdateToClient();
            oldHeat = (int) heat.getTemperature();
        }
    }

    private void search() {
        VecInt dir = getDirection().opposite().toVecInt();
        TileEntity tile = MgUtils.getTileEntity(this, dir);
        if (tile instanceof IHeatTile) {
            IHeatConductor[] comp = ((IHeatTile) tile).getHeatCond(VecInt.NULL_VECTOR);
            heat = comp[0];
        }
        tile = MgUtils.getTileEntity(this, dir.copy().multiply(2));
        if (tile instanceof IElectricTile) {
            cond = ((IElectricTile) tile).getConds(VecInt.NULL_VECTOR, 0)[0];
        }
    }

    private void setActive(boolean b) {
        burning = b;
        sendUpdateToClient();
    }

    public boolean isWorking() {
        return burning;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        progress = nbt.getInteger("Progress");
        maxProgres = nbt.getInteger("maxProgress");
        burning = nbt.getBoolean("Burning");
        working = nbt.getBoolean("Working");
        getInv().readFromNBT(nbt);
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("Progress", progress);
        nbt.setInteger("maxProgress", maxProgres);
        nbt.setBoolean("Burning", burning);
        nbt.setBoolean("Working", working);
        getInv().writeToNBT(nbt);
    }

    public InventoryComponent getInv() {
        return inv;
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        if (cond == null || heat == null) return;
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, progress);
        craft.sendProgressBarUpdate(cont, 2, cond.getStorage());
        craft.sendProgressBarUpdate(cont, 3, maxProgres);
        craft.sendProgressBarUpdate(cont, 4, (int) heat.getTemperature() * 10);
        craft.sendProgressBarUpdate(cont, 5, (int) prodSecond);
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (cond == null || heat == null) return;
        if (id == 0) cond.setVoltage(value);
        if (id == 1) progress = value;
        if (id == 2) cond.setStorage(value);
        if (id == 3) maxProgres = value;
        if (id == 4) heat.setTemperature(value / 10);
        if (id == 5) prodSecond = value / 20D;
    }

    @Override
    public void onDestroy(World w, VecInt p, Multiblock c, MgDirection e) {
        heat = null;
        cond = null;
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
    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        VecInt v1 = VecIntUtil.getRotatedOffset(getDirection().opposite(), -1, -1, 0);
        VecInt v2 = VecIntUtil.getRotatedOffset(getDirection().opposite(), 1, 0, 2);
        VecInt block = new VecInt(xCoord, yCoord, zCoord);

        return VecIntUtil.getAABBFromVectors(v1.add(block), v2.add(block));
    }

    @Override
    public IElectricConductor[] getConds(VecInt dir, int Vtier) {
        if (VecInt.NULL_VECTOR.equals(dir)) return new IElectricConductor[]{cond};
        return null;
    }

    @Override
    public IHeatConductor[] getHeatCond(VecInt c) {
        if (VecInt.NULL_VECTOR.equals(c)) return new IHeatConductor[]{heat};
        return null;
    }

    public IBarProvider getBurningTimeBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return null;
            }

            @Override
            public float getMaxLevel() {
                return Math.max(maxProgres, 1);
            }

            @Override
            public float getLevel() {
                return progress;
            }
        };
    }

    public IBarProvider getProductionBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return "Production " + (int) prodSecond + "W";
            }

            @Override
            public float getMaxLevel() {
                return (float) Math.max(MAX_PRODUCTION, 1);
            }

            @Override
            public float getLevel() {
                return (float) prodSecond;
            }
        };
    }

    public boolean isActive() {
        return getBlockMetadata() > 5;
    }
}
