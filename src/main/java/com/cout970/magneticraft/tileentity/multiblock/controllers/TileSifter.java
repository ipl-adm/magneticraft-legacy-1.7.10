package com.cout970.magneticraft.tileentity.multiblock.controllers;

import com.cout970.magneticraft.api.access.RecipeSifter;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.ElectricUtils;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VecIntUtil;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Base;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Inv;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.InventoryUtils;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileSifter extends TileMB_Base {

    public int drawCounter;
    public boolean working;
    public int speed = 100;
    public int progress;
    public float[] leverCount = new float[4];
    public boolean[] leverState = new boolean[4];
    public IElectricConductor cond;
    public InventoryComponent in, out;
    private InventoryComponent inv = new InventoryComponent(this, 3, "Sifter");
    private long time;

    public InventoryComponent getInv() {
        if (inv == null) inv = new InventoryComponent(this, 3, "Sifter");
        return inv;
    }

    public void onBlockBreaks() {
        if (worldObj.isRemote) return;
        BlockMg.dropItem(getInv().getStackInSlot(0), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
        BlockMg.dropItem(getInv().getStackInSlot(1), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
        BlockMg.dropItem(getInv().getStackInSlot(2), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
    }

    public void updateEntity() {
        super.updateEntity();
        if (drawCounter > 0) drawCounter--;
        if (!isActive()) return;
        if (worldObj.getTotalWorldTime() % 40 == 0) {
            cond = null;
        }
        if (cond == null || in == null || out == null) {
            search();
            if (cond == null || in == null || out == null)
                return;
        }

        if (worldObj.isRemote) return;
        if (worldObj.getTotalWorldTime() % 40 == 0) {
            if (cond.getParent() instanceof TileBase)
                ((TileBase) cond.getParent()).sendUpdateToClient();
        }
        if (working) {
            if (getInv().getStackInSlot(0) == null) {
                inputItem();
            }
            if (canCraft()) {
                if (cond.getVoltage() > ElectricConstants.MACHINE_WORK) {
                    cond.drainPower(EnergyConverter.RFtoW(40) * (100F / speed));
                    progress++;
                    if (progress > speed) {
                        craft();
                        progress = 0;
                    }
                }
            } else {
                progress = 0;
            }
        }
    }

    @Override
    public void onDestroy(World w, VecInt p, Multiblock c, MgDirection e) {
    }

    private void inputItem() {
        if (getInv().getStackInSlot(1) == null) {
            for (int i = 0; i < in.getSizeInventory(); i++) {
                ItemStack it = in.getStackInSlot(i);
                RecipeSifter r = RecipeSifter.getRecipe(it);
                if (r != null) {
                    ItemStack item = it.copy();
                    in.decrStackSize(i, 1);
                    item.stackSize = 1;
                    getInv().setInventorySlotContents(0, item);
                    getInv().setInventorySlotContents(1, r.getOutput().copy());
                    if (r.getProb() >= worldObj.rand.nextFloat() && r.getExtra() != null) {
                        getInv().setInventorySlotContents(2, r.getExtra().copy());
                    } else {
                        getInv().setInventorySlotContents(2, null);
                    }
                    return;
                }
            }
        } else {
            outputItem();
        }
    }

    private boolean canCraft() {
        return getInv().getStackInSlot(0) != null;
    }

    private void craft() {
        getInv().setInventorySlotContents(0, null);
        outputItem();
    }

    private void outputItem() {
        if (getInv().getStackInSlot(1) != null) {
            if (InventoryUtils.dropIntoInventory(getInv().getStackInSlot(1), out)) {
                getInv().setInventorySlotContents(1, null);
            }
        }
        if (getInv().getStackInSlot(2) != null) {
            if (InventoryUtils.dropIntoInventory(getInv().getStackInSlot(2), out)) {
                getInv().setInventorySlotContents(2, null);
            }
        }
    }

    private void search() {
        VecInt vec = new VecInt(this).add(getDirection().opposite().toVecInt());
        TileEntity tile = vec.getTileEntity(worldObj);
        if (tile != null) {
            IElectricConductor[] comp = ElectricUtils.getElectricCond(tile, VecInt.NULL_VECTOR, 0);
            if (comp != null) {
                cond = comp[0];
            }
        }
        vec = new VecInt(this).add(getDirection().opposite().toVecInt()).add(0, 1, 0);
        tile = vec.getTileEntity(worldObj);
        if (tile instanceof TileMB_Inv) {
            in = ((TileMB_Inv) tile).getInv();
        }

        vec = new VecInt(this).add(getDirection().opposite().toVecInt());
        vec.add(getDirection().opposite().step(MgDirection.DOWN).toVecInt());
        tile = vec.getTileEntity(worldObj);
        if (tile instanceof TileMB_Inv) {
            out = ((TileMB_Inv) tile).getInv();
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        getInv().readFromNBT(nbt);
        working = nbt.getBoolean("Work");
        progress = nbt.getInteger("Progress");
        speed = nbt.getInteger("Speed");
        for (int i = 0; i < 4; i++) {
            leverState[i] = nbt.getBoolean("Lever_" + i);
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        getInv().writeToNBT(nbt);
        nbt.setBoolean("Work", working);
        nbt.setInteger("Progress", progress);
        nbt.setInteger("Speed", speed);
        for (int i = 0; i < 4; i++) {
            nbt.setBoolean("Lever_" + i, leverState[i]);
        }
    }

    public boolean isActive() {
        return getBlockMetadata() > 5;
    }

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        VecInt v2 = VecIntUtil.getRotatedOffset(getDirection().opposite(), 1, 2, 3);
        VecInt block = new VecInt(xCoord, yCoord, zCoord);

        return VecIntUtil.getAABBFromVectors(block, v2.add(block));
    }

    public void switchClick(int id) {
        leverCount[id] = 50;
        leverState[id] ^= true;
        working = leverState[0];
        int speed = 100;
        if (leverState[1]) speed -= 25;
        if (leverState[2]) speed -= 25;
        if (leverState[3]) speed -= 25;
        this.speed = speed;
        sendUpdateToClient();
    }

    public float getDelta() {
        long aux = time;
        time = System.nanoTime();
        return time - aux;
    }
}
