package com.cout970.magneticraft.tileentity;

import java.util.List;

import com.cout970.magneticraft.api.acces.RecipeGrinder;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.BufferedConductor;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.InventoryUtils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileGrinder extends TileMB_Base implements IInventoryManaged, ISidedInventory, IGuiSync {

    public float speed;
    public int maxProgres = 100;
    public BufferedConductor cond = new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW, 160000, ElectricConstants.MACHINE_DISCHARGE, ElectricConstants.MACHINE_CHARGE);
    private float progress;
    private double flow;
    private InventoryComponent inv = new InventoryComponent(this, 4, "Grinder");
    private InventoryComponent in;
    private InventoryComponent out;
    public int drawCounter;
    public float rotation;
    private long time;
    private boolean working;
    private boolean active_w;

    public void updateEntity() {
        super.updateEntity();
        if (drawCounter > 0) drawCounter--;
        if (!isActive()) return;
        if (worldObj.isRemote) return;
        if (worldObj.getTotalWorldTime() % 20 == 0) {
            if (working && !isWorking()) {
                setActive(true);
            } else if (!working && isWorking()) {
                setActive(false);
            }
        }
        if (worldObj.getTotalWorldTime() % 10 == 0) {
            catchDrops();
        }
        updateConductor();
        if (cond.getVoltage() >= ElectricConstants.MACHINE_WORK) {
            speed = cond.getStorage() * 10f / cond.getMaxStorage();
            if (canCraft()) {
                if (speed > 0) {
                    progress += speed;
                    cond.drainPower(EnergyConversor.RFtoW(speed * 10));
                    if (progress >= maxProgres) {
                        craft();
                        markDirty();
                        progress %= maxProgres;
                    }
                    working = true;
                }
            } else {
                progress = 0;
                working = false;
            }
        }
        distributeItems();
    }

    @SuppressWarnings("rawtypes")
    private void catchDrops() {
        if (in == null) return;
        MgDirection dir = getDirection().opposite();
        VecInt vec1, vec2;
        vec1 = new VecInt(this).add(0, 4, 0).add(dir.toVecInt());
        vec2 = vec1.copy();
        vec1.add(-1, 0, -1);
        vec2.add(2, 1, 2);
        List l = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(vec1.getX(), vec1.getY(), vec1.getZ(), vec2.getX(), vec2.getY(), vec2.getZ()));
        if (l.isEmpty()) return;
        for (Object aL : l) {
            EntityItem e = (EntityItem) aL;
            ItemStack item = e.getEntityItem();
            if (InventoryUtils.dropIntoInventory(item, in)) {
                worldObj.removeEntity(e);
                e.setDead();
            } else {
                break;
            }
        }
    }

    private void setActive(boolean b) {
        active_w = b;
        sendUpdateToClient();
    }

    public boolean isWorking() {
        return active_w;
    }

    public boolean isActive() {
        return getBlockMetadata() > 5;
    }

    public InventoryComponent getInv() {
        return inv;
    }

    @Override
    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    public void updateConductor() {
        cond.recache();
        cond.iterate();
        MgDirection d = MgDirection.getDirection(getBlockMetadata()).opposite();
        VecInt vec = d.toVecInt().add(d.step(MgDirection.UP).toVecInt());
        TileEntity c = MgUtils.getTileEntity(this, vec);
        if (c instanceof IElectricTile) {
            valance((IElectricTile) c);
        }
        vec = d.toVecInt().add(d.step(MgDirection.DOWN).toVecInt());
        c = MgUtils.getTileEntity(this, vec);
        if (c instanceof IElectricTile) {
            valance((IElectricTile) c);
        }
    }

    public void valance(IElectricTile c) {
        IElectricConductor[] comp = c.getConds(VecInt.NULL_VECTOR, 0);
        IElectricConductor cond2 = comp[0];
        double resistance = cond.getResistance() + cond2.getResistance();
        double difference = cond.getVoltage() - cond2.getVoltage();
        double change = flow;
        double slow = change * resistance;
        flow += ((difference - slow) * cond.getIndScale()) / cond.getVoltageMultiplier();
        change += (difference * cond.getCondParallel()) / cond.getVoltageMultiplier();
        cond.applyCurrent(-change);
        cond2.applyCurrent(change);
    }

    private void distributeItems() {
        if (in == null) {
            MgDirection d = MgDirection.getDirection(getBlockMetadata()).opposite();
            VecInt v = d.toVecInt().add(new VecInt(0, 3, 0));
            TileEntity c = MgUtils.getTileEntity(this, v);
            if (c instanceof IInventoryManaged) {
                in = ((IInventoryManaged) c).getInv();
            }
        }
        if (out == null) {
            MgDirection d = MgDirection.getDirection(getBlockMetadata()).opposite();
            VecInt v = d.toVecInt().multiply(2);
            TileEntity c = MgUtils.getTileEntity(this, v);
            if (c instanceof IInventoryManaged) {
                out = ((IInventoryManaged) c).getInv();
            }
        }

        if (in != null && out != null) {
            if (((TileBase) in.tile).isControled()) {
                if (getInv().getStackInSlot(0) != null) {
                    int s = InventoryUtils.findCombination(in, getInv().getStackInSlot(0));
                    if (s != -1) {
                        InventoryUtils.traspass(in, this, s, 0);
                    }
                } else {
                    setInventorySlotContents(0, InventoryUtils.getItemStack(in));
                }
            }
            if (((TileBase) out.tile).isControled()) {
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

    private void craft() {
        ItemStack a = getInv().getStackInSlot(0);
        RecipeGrinder r = RecipeGrinder.getRecipe(a);

        assert r != null;
        getInv().setInventorySlotContents(1,
                InventoryUtils.addition(r.getOutput(), getInv().getStackInSlot(1)));

        int intents = ((int) r.getProb2()) + 1;
        for (int i = 0; i < intents; i++) {
            if (worldObj.rand.nextFloat() <= r.getProb2() - i) {
                getInv().setInventorySlotContents(2, InventoryUtils.addition(r.getOutput2(), getInv().getStackInSlot(2)));
            }
        }

        intents = ((int) (r.getProb3() / 100)) + 1;
        for (int i = 0; i < intents; i++) {
            if (worldObj.rand.nextFloat() <= r.getProb3() - i) {
                getInv().setInventorySlotContents(3, InventoryUtils.addition(r.getOutput3(), getInv().getStackInSlot(3)));
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
        RecipeGrinder r = RecipeGrinder.getRecipe(a);
        if (r == null)
            return false;
        if (getInv().getStackInSlot(1) != null)
            if (!InventoryUtils.canCombine(r.getOutput(), getInv().getStackInSlot(1), getInv().getInventoryStackLimit()))
                return false;
        if (getInv().getStackInSlot(2) != null)
            if (!InventoryUtils.canCombine(r.getOutput2(), getInv().getStackInSlot(2), getInv().getInventoryStackLimit()))
                return false;
        if (getInv().getStackInSlot(3) != null)
            if (!InventoryUtils.canCombine(r.getOutput3(), getInv().getStackInSlot(3), getInv().getInventoryStackLimit()))
                return false;
        return true;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        active_w = nbt.getBoolean("Act");
        cond.load(nbt);
        getInv().readFromNBT(nbt);
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("Act", active_w);
        cond.save(nbt);
        getInv().writeToNBT(nbt);
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) { //rework with custom packets
        //Log.info("Encoded " + Integer.toString(cond.getStorage()));
        //String t = String.format("%32s", Integer.toBinaryString(cond.getStorage())).replace(' ', '0'); //ensures leading zeros are present
        //int[] splitStorage = new int[]{Integer.parseInt(t.substring(16), 2), Integer.parseInt(t.substring(0, 16), 2)};
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        //craft.sendProgressBarUpdate(cont, 1, splitStorage[0]);
        //craft.sendProgressBarUpdate(cont, 2, splitStorage[1]);
        craft.sendProgressBarUpdate(cont, 1, (cond.getStorage() & 0xFFFF));
        craft.sendProgressBarUpdate(cont, 2, ((cond.getStorage() & 0xFFFF0000) >>> 16));
        craft.sendProgressBarUpdate(cont, 3, (int) progress);
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 0) {
            cond.setVoltage(value);
        }
        /*if (id == 1) {
            storageBuilder = value;
        }
        if (id == 2) {
            storageBuilder += value << 16;
            cond.setStorage(storageBuilder);
            Log.info("Decoded " + Integer.toString(storageBuilder));
            storageBuilder = 0;
        }*/
        if(id == 1)cond.setStorage(value & 0xFFFF);
        if(id == 2)cond.setStorage(cond.getStorage() | (value << 16));
        if (id == 3) {
            progress = value;
        }
    }

    @Override
    public void onNeigChange() {
        super.onNeigChange();
        cond.disconect();
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
        return INFINITE_EXTENT_AABB;
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
                return maxProgres;
            }

            @Override
            public float getLevel() {
                return progress;
            }
        };
    }
}
