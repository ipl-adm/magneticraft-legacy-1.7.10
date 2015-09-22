package com.cout970.magneticraft.tileentity.multiblock.controllers;

import com.cout970.magneticraft.api.access.RecipeCrusher;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.BufferedConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Base;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.InventoryUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileCrusher extends TileMB_Base implements IGuiSync, IInventoryManaged, ISidedInventory {

    private float MAX_PROGRESS = 100;
    public float animation;
    public boolean auto;
    public float progress = 0;
    public int maxProgres = 100;
    public BufferedConductor cond = new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW, 160000, ElectricConstants.MACHINE_DISCHARGE, ElectricConstants.MACHINE_CHARGE);
    private double flow;
    private InventoryComponent inv = new InventoryComponent(this, 4, "Crusher");
    private InventoryComponent in;
    private InventoryComponent out;
    public int drawCounter;
    private long time;
    public boolean working;

    public InventoryComponent getInv() {
        return inv;
    }

    public void updateEntity() {
        super.updateEntity();

        if (drawCounter > 0) drawCounter--;
        if (!isActive()) return;
        if (worldObj.isRemote) return;
        if (worldObj.getTotalWorldTime() % 20 == 0) sendUpdateToClient();
        updateConductor();
        if (cond.getVoltage() >= ElectricConstants.MACHINE_WORK) {
            float speed = cond.getStorage() * 10f / cond.getMaxStorage();
            if (canCraft()) {
                working = true;
                if (speed > 0) {
                    progress += speed;
                    cond.drainPower(EnergyConverter.RFtoW(speed * 10));
                    if (progress >= MAX_PROGRESS) {
                        craft();
                        markDirty();
                        progress %= MAX_PROGRESS;
                    }
                } else {
                    working = false;
                }
            } else {
                working = false;
                progress = 0;
            }
        } else {
            working = false;
        }
        distributeItems();
    }

    private void distributeItems() {
        if (in == null) {
            if (getBlockMetadata() % 8 >= 4) {
                MgDirection d = getDirection().opposite();
                VecInt v = d.toVecInt().multiply(2).add(d.step(MgDirection.UP).toVecInt().getOpposite());
                TileEntity c = MgUtils.getTileEntity(this, v);
                if (c instanceof IInventoryManaged) {
                    in = ((IInventoryManaged) c).getInv();
                }
            } else {
                MgDirection d = getDirection().opposite();
                VecInt v = d.toVecInt().multiply(2).add(d.step(MgDirection.DOWN).toVecInt().getOpposite());
                TileEntity c = MgUtils.getTileEntity(this, v);
                if (c instanceof IInventoryManaged) {
                    in = ((IInventoryManaged) c).getInv();
                }
            }
        }
        if (out == null) {
            if (getBlockMetadata() % 8 < 4) {
                MgDirection d = getDirection().opposite();
                VecInt v = d.toVecInt().multiply(2).add(d.step(MgDirection.UP).toVecInt().multiply(3).getOpposite());
                TileEntity c = MgUtils.getTileEntity(this, v);
                if (c instanceof IInventoryManaged) {
                    out = ((IInventoryManaged) c).getInv();
                }
            } else {
                MgDirection d = getDirection().opposite();
                VecInt v = d.toVecInt().multiply(2).add(d.step(MgDirection.DOWN).toVecInt().multiply(3).getOpposite());
                TileEntity c = MgUtils.getTileEntity(this, v);
                if (c instanceof IInventoryManaged) {
                    out = ((IInventoryManaged) c).getInv();
                }
            }
        }

        if (in != null && out != null) {
            if (((TileBase) in.tile).isControlled()) {
                if (getInv().getStackInSlot(0) != null) {
                    int s = InventoryUtils.findCombination(in, getInv().getStackInSlot(0));
                    if (s != -1) {
                        InventoryUtils.traspass(in, this, s, 0);
                    }
                } else {
                    setInventorySlotContents(0, InventoryUtils.getItemStack(in));
                }
            }
            if (((TileBase) out.tile).isControlled()) {
                for (int i = 0; i < 3; i++) {
                    if (getInv().getStackInSlot(i + 1) != null) {
                        int s = InventoryUtils.getSlotForStack(out, getInv().getStackInSlot(i + 1));
                        if (s != -1) {
                            InventoryUtils.traspass(this, out, i + 1, s);
                        }
                    }
                }
            }
        }
    }

    @Override
    public MgDirection getDirection() {
        int meta = getBlockMetadata();
        return MgDirection.getDirection(meta % 4 + 2);
    }

    private void craft() {
        ItemStack a = getInv().getStackInSlot(0);
        RecipeCrusher r = RecipeCrusher.getRecipe(a);

        getInv().setInventorySlotContents(1,
                InventoryUtils.addition(r.getOutput(), getInv().getStackInSlot(1)));

        int intents = ((int) (r.getProb2())) + 1;
        for (int i = 0; i < intents; i++) {
            if (worldObj.rand.nextFloat() <= r.getProb2() - i) {
                getInv().setInventorySlotContents(
                        2,
                        InventoryUtils.addition(r.getOutput2(), getInv()
                                .getStackInSlot(2)));
            }
        }

        intents = ((int) (r.getProb3())) + 1;
        for (int i = 0; i < intents; i++) {
            if (worldObj.rand.nextFloat() <= r.getProb3() - i) {
                getInv().setInventorySlotContents(
                        3,
                        InventoryUtils.addition(r.getOutput3(), getInv()
                                .getStackInSlot(3)));
            }
        }

        a.stackSize--;
        if (a.stackSize <= 0) {
            getInv().setInventorySlotContents(0, null);
        }
    }

    private boolean canCraft() {
        ItemStack a = getInv().getStackInSlot(0);
        if (a == null)
            return false;
        RecipeCrusher r = RecipeCrusher.getRecipe(a);
        if (r == null)
            return false;
        if (getInv().getStackInSlot(1) != null)
            if (!InventoryUtils.canCombine(r.getOutput(),
                    getInv().getStackInSlot(1), getInv()
                            .getInventoryStackLimit()))
                return false;
        if (getInv().getStackInSlot(2) != null)
            if (!InventoryUtils.canCombine(r.getOutput2(), getInv()
                    .getStackInSlot(2), getInv().getInventoryStackLimit()))
                return false;
        if (getInv().getStackInSlot(3) != null)
            if (!InventoryUtils.canCombine(r.getOutput3(), getInv()
                    .getStackInSlot(3), getInv().getInventoryStackLimit()))
                return false;
        return true;
    }

    public void updateConductor() {
        cond.recache();
        cond.iterate();
        MgDirection d = getDirection().opposite();
        TileEntity c = MgUtils.getTileEntity(this, d.toVecInt().multiply(3));
        if (c instanceof IElectricTile) {
            IElectricConductor[] comp = ((IElectricTile) c).getConds(VecInt.NULL_VECTOR, 0);
            IElectricConductor cond2 = comp[0];
            double resistence = cond.getResistance() + cond2.getResistance();
            double difference = cond.getVoltage() - cond2.getVoltage();
            double change = flow;
            double slow = change * resistence;
            flow += ((difference - slow) * cond.getIndScale())
                    / cond.getVoltageMultiplier();
            change += (difference * cond.getCondParallel())
                    / cond.getVoltageMultiplier();
            cond.applyCurrent(-change);
            cond2.applyCurrent(change);
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        working = nbt.getBoolean("W");
        cond.load(nbt);
        getInv().readFromNBT(nbt);
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("W", working);
        cond.save(nbt);
        getInv().writeToNBT(nbt);
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, (cond.getStorage() & 0xFFFF));
        craft.sendProgressBarUpdate(cont, 2, ((cond.getStorage() & 0xFFFF0000) >>> 16));
        craft.sendProgressBarUpdate(cont, 3, (int) progress);
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
    }

    @Override
    public void onNeigChange() {
        super.onNeigChange();
        cond.disconect();
        in = null;
        out = null;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[]{};
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,
                                 int p_102007_3_) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
                                  int p_102008_3_) {
        return false;
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

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    public float getDelta() {
        long aux = time;
        time = System.nanoTime();
        return time - aux;
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

    public boolean isActive() {
        return getBlockMetadata() >= 8;
    }
}