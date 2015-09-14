package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.conveyor.IConveyorBelt;
import com.cout970.magneticraft.api.conveyor.IConveyorBeltLane;
import com.cout970.magneticraft.api.conveyor.IItemBox;
import com.cout970.magneticraft.api.conveyor.prefab.ItemBox;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecDouble;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.util.IGuiListener;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.MgBeltUtils;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class TileInserter extends TileBase implements IGuiListener {

    public InventoryComponent inv = new InventoryComponent(this, 1, "Inserter");
    public InventoryComponent filter = new InventoryComponent(this, 9, "Filter");
    public InventoryComponent upgrades = new InventoryComponent(this, 4, "Upgrades") {
        @Override
        public int getInventoryStackLimit() {
            return 1;
        }
    };
    public boolean whiteList;
    public boolean ignoreNBT;
    public boolean ignoreMeta;
    public boolean ignoreDict;
    public byte valid_dirs = 0xF;
    public int counter = 0;
    public InserterAnimation anim = null;
    private int cooldown;

    public MgDirection getDir() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    public void onBlockBreaks() {
        if (worldObj.isRemote) return;
        BlockMg.dropItem(getInv().getStackInSlot(0), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
        for (int i = 0; i < upgrades.getSizeInventory(); i++) {
            BlockMg.dropItem(upgrades.getStackInSlot(i), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
        }
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            return;
        }
        if (anim == null) {
            anim = InserterAnimation.Extending_Short;
            counter = 0;
            return;
        }
        if ((getInv().getStackInSlot(0) != null) && (getInv().getStackInSlot(0).stackSize == 0)) {
            getInv().setInventorySlotContents(0, null);
            anim = InserterAnimation.Extending_Short;
            counter = 0;
            return;
        }
        if (cooldown > 0) cooldown--;
        if (anim == InserterAnimation.DropItem || anim == InserterAnimation.DropItem_Large) {
            if (getInv().getStackInSlot(0) != null) {
                TileEntity o = MgUtils.getTileEntity(this, getDir().opposite());
                if (o instanceof IInventory)
                    dropToInv((IInventory) o);
                else if (o instanceof IConveyorBelt && ((IConveyorBelt) o).getOrientation().getLevel() == 0) {
                    dropToBelt((IConveyorBelt) o);
                } else if (canDropItems() && cooldown == 0) {
                    dropItem();
                    cooldown = 10;
                }
            }
            if (getInv().getStackInSlot(0) == null) {
                if (anim == InserterAnimation.DropItem) {
                    anim = InserterAnimation.Retracting_INV_Short;
                } else {
                    anim = InserterAnimation.Retracting_INV_Large;
                }
                counter = 0;
            }
            sendUpdateToClient();

        } else if (anim == InserterAnimation.SuckItem || anim == InserterAnimation.SuckItem_Large) {
            if (isControlled()) {
                if (getInv().getStackInSlot(0) == null) {
                    TileEntity t = MgUtils.getTileEntity(this, getDir()), o = MgUtils.getTileEntity(this, getDir().opposite());
                    if (t instanceof IInventory) {
                        suckFromInv((IInventory) t, o);
                    } else if (t instanceof IConveyorBelt && ((IConveyorBelt) t).getOrientation().getLevel() == 0) {
                        suckFromBelt((IConveyorBelt) t, o);
                    } else if (canSuckDroppedItems() && cooldown == 0) {
                        suckFromGround();
                        cooldown = 10;
                    }
                }
                if (getInv().getStackInSlot(0) != null) {
                    if (anim == InserterAnimation.SuckItem) {
                        anim = InserterAnimation.Retracting_Short;
                    } else {
                        anim = InserterAnimation.Retracting_Large;
                    }
                    counter = 0;
                    sendUpdateToClient();

                }
            }
        } else {
            if (counter >= 180) {
                anim = getNextAnimation();
                counter = 0;
            } else {
                counter += getSpeed();
            }
        }
        sendUpdateToClient();
    }

    private InserterAnimation getNextAnimation() {
        if (anim == InserterAnimation.Retracting_Short || anim == InserterAnimation.Retracting_Large)
            return InserterAnimation.Rotating;
        if (anim == InserterAnimation.Retracting_INV_Short || anim == InserterAnimation.Retracting_INV_Large)
            return InserterAnimation.Rotating_INV;
        if (anim == InserterAnimation.Rotating) {
            TileEntity o = MgUtils.getTileEntity(this, getDir().opposite());
            if (o instanceof IConveyorBelt && ((IConveyorBelt) o).getOrientation().getLevel() == 0 && ((IConveyorBelt) o).getDir().isPerpendicular(getDir())) {
                return InserterAnimation.Extending_INV_Large;
            } else {
                return InserterAnimation.Extending_INV_Short;
            }
        }
        if (anim == InserterAnimation.Rotating_INV) return InserterAnimation.Extending_Short;
        if (anim == InserterAnimation.Extending_INV_Short) {//drop
            return InserterAnimation.DropItem;
        }
        if (anim == InserterAnimation.Extending_INV_Large) {//suck
            return InserterAnimation.DropItem_Large;
        }
        if (anim == InserterAnimation.Extending_Short) {
            return InserterAnimation.SuckItem;
        }
        if (anim == InserterAnimation.Extending_Large) {
            return InserterAnimation.SuckItem_Large;
        }
        return anim;
    }

    @SuppressWarnings("rawtypes")
    private void suckFromGround() {
        if (worldObj.isRemote) return;
        VecInt vec1 = new VecInt(this).add(getDir().toVecInt());
        Block b = worldObj.getBlock(vec1.getX(), vec1.getY(), vec1.getZ());
        if (b.isAir(worldObj, vec1.getX(), vec1.getY(), vec1.getZ())) {
            List l = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(vec1.getX(), vec1.getY(), vec1.getZ(), vec1.getX() + 1, vec1.getY() + 1, vec1.getZ() + 1));
            if (!l.isEmpty()) {
                for (Object aL : l) {
                    if (aL instanceof EntityItem) {
                        EntityItem entity = (EntityItem) aL;
                        if (entity.getEntityItem() != null) {
                            getInv().setInventorySlotContents(0, entity.getEntityItem());
                            entity.setDead();
                            break;
                        }
                    }
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private void dropItem() {
        if (worldObj.isRemote) return;
        VecInt vec1 = new VecInt(this).add(getDir().opposite().toVecInt());
        Block b = worldObj.getBlock(vec1.getX(), vec1.getY(), vec1.getZ());
        if (b.isAir(worldObj, vec1.getX(), vec1.getY(), vec1.getZ())) {
            List l = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(vec1.getX(), vec1.getY(), vec1.getZ(), vec1.getX() + 1, vec1.getY() + 1, vec1.getZ() + 1));
            if (l.isEmpty()) {
                ItemStack item = getInv().getStackInSlot(0);
                if (item == null) return;
                VecDouble vec2 = new VecDouble(vec1);
                vec2.add(0.5, 0.5, 0.5);
                EntityItem entityItem = new EntityItem(worldObj, vec2.getX(), vec2.getY(), vec2.getZ(), new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
                if (item.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                }
                entityItem.motionX = 0;
                entityItem.motionY = 0;
                entityItem.motionZ = 0;
                worldObj.spawnEntityInWorld(entityItem);
                getInv().setInventorySlotContents(0, null);
            }
        }
    }

    private boolean canDropItems() {
        for (int i = 0; i < upgrades.getSizeInventory(); i++) {
            ItemStack item = upgrades.getStackInSlot(i);
            if (item != null) {
                if (item.getItem() == ManagerItems.upgrade_drop) {
                    return true;
                }
            }
        }
        return false;
    }

    public int getSpeed() {
        int speed = 20;
        for (int i = 0; i < upgrades.getSizeInventory(); i++) {
            ItemStack item = upgrades.getStackInSlot(i);
            if (item != null) {
                if (item.getItem() == ManagerItems.upgrade_speed) {
                    speed += 10;
                } else if (item.getItem() == ManagerItems.upgrade_slow) {
                    speed -= 10;
                }
            }
        }
        return Math.min(40, (Math.max(10, speed)));
    }

    private boolean canSuckDroppedItems() {
        for (int i = 0; i < upgrades.getSizeInventory(); i++) {
            ItemStack item = upgrades.getStackInSlot(i);
            if (item != null) {
                if (item.getItem() == ManagerItems.upgrade_suck) {
                    return true;
                }
            }
        }
        return false;
    }

    private void dropToBelt(IConveyorBelt t) {
        ItemStack s = getInv().getStackInSlot(0);
        if (t.addItem(getDir(), 2, new ItemBox(s), true)) {
            getInv().setInventorySlotContents(0, null);
            t.addItem(getDir(), 2, new ItemBox(s), false);
            t.onChange();
        }
    }

    private void dropToInv(IInventory t) {
        ItemStack s = getInv().getStackInSlot(0);
        if (MgBeltUtils.dropItemStackIntoInventory(t, s, getDir(), true) == 0) {
            getInv().setInventorySlotContents(0, null);
            MgBeltUtils.dropItemStackIntoInventory(t, s, getDir(), false);
        }
    }

    private void suckFromBelt(IConveyorBelt t, Object obj) {
        IConveyorBeltLane side = t.getSideLane(true);
        if (extractFromBelt(side, t, true, obj)) {
            return;
        }
        side = t.getSideLane(false);
        extractFromBelt(side, t, false, obj);
    }

    public boolean extractFromBelt(IConveyorBeltLane side, IConveyorBelt t, boolean left, Object obj) {
        if (side.getItemBoxes().isEmpty()) return false;
        IItemBox b = side.getItemBoxes().get(0);

        if (t.removeItem(b, left, true)) {
            ItemStack x = b.getContent();
            if (canInject(obj, x) && canExtract(t, x)) {
                getInv().setInventorySlotContents(0, b.getContent());
                t.removeItem(b, left, false);
                t.onChange();
                for (TileEntity tile : MgUtils.getNeig(t.getParent()))
                    if (tile instanceof IConveyorBelt) ((IConveyorBelt) tile).onChange();
                return true;
            }
        }
        return false;
    }

    private void suckFromInv(IInventory t, Object obj) {
        if (t instanceof ISidedInventory) {
            ISidedInventory ts = (ISidedInventory) t;
            for (MgDirection d : getValidDirections()) {
                if (ts.getAccessibleSlotsFromSide(d.ordinal()) == null) {
                    for (int i : ts.getAccessibleSlotsFromSide(d.ordinal())) {
                        ItemStack s = t.getStackInSlot(i);
                        if ((s != null) && (s.stackSize > 0) && ts.canExtractItem(i, s, d.ordinal()) && canInject(obj, s) && canExtract(t, s)) {
                            getInv().setInventorySlotContents(0, s);
                            t.setInventorySlotContents(i, null);
                            return;
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < t.getSizeInventory(); i++) {
                if (t.getStackInSlot(i) != null) {
                    ItemStack s = t.getStackInSlot(i);
                    if (canInject(obj, s) && canExtract(t, s)) {
                        getInv().setInventorySlotContents(0, s);
                        t.setInventorySlotContents(i, null);
                        return;
                    }
                }
            }
        }
    }

    public boolean canExtract(Object obj, ItemStack s) {
        if (s == null) return false;
        if (whiteList) {
            for (int i = 0; i < filter.getSizeInventory(); i++) {
                if (checkFilter(i, s)) return true;
            }
            return false;
        } else {
            for (int i = 0; i < filter.getSizeInventory(); i++) {
                if (checkFilter(i, s)) return false;
            }
            return true;
        }
    }

    public int getSlotWithItemStack(IInventory i) {
        for (MgDirection d : getValidDirections()) {
            int s = MgBeltUtils.getSlotWithItemStack(i, d, false);
            if (s != -1) return s;
        }
        return -1;
    }

    private List<MgDirection> getValidDirections() {
        List<MgDirection> list = new ArrayList<MgDirection>();
        for (MgDirection d : MgDirection.values())
            if ((valid_dirs & (1 << d.ordinal())) > 0) list.add(d);
        return list;
    }

    public boolean checkFilter(int slot, ItemStack i) {
        ItemStack f = filter.getStackInSlot(slot);
        if (f == null) return false;
        if (!ignoreDict) {
            int[] c = OreDictionary.getOreIDs(i);
            int[] d = OreDictionary.getOreIDs(f);
            if (c.length > 0 && d.length > 0) {
                for (int k : c) {
                    for (int j : d)
                        if (k == j) return true;
                }
            }
        }
        if (f.getItem() != i.getItem()) return false;
        if (!ignoreMeta)
            if (f.getItemDamage() != i.getItemDamage()) return false;
        if (!ignoreNBT)
            if (f.getTagCompound() != i.getTagCompound()) return false;
        return true;
    }

    public boolean canInject(Object obj, ItemStack s) {
        if (obj instanceof IInventory) {
            return MgBeltUtils.dropItemStackIntoInventory((IInventory) obj, s, getDir(), true) == 0;
        } else if (obj instanceof IConveyorBelt) {
            return ((IConveyorBelt) obj).addItem(getDir(), 2, new ItemBox(s), true);
        } else {
            return canDropItems();
        }
    }

    public InventoryComponent getInv() {
        return inv;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        getInv().readFromNBT(nbt);
        counter = nbt.getInteger("Stage");
        anim = InserterAnimation.values()[nbt.getInteger("Animation") % InserterAnimation.values().length];
        whiteList = nbt.getBoolean("WhiteList");
        ignoreMeta = nbt.getBoolean("IgnoreMeta");
        ignoreNBT = nbt.getBoolean("IgnoreNBT");
        ignoreDict = nbt.getBoolean("IgnoreDict");
        valid_dirs = nbt.getByte("ValidDirs");
        filter.readFromNBT(nbt, "Filter");
        upgrades.readFromNBT(nbt, "Upgrades");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        getInv().writeToNBT(nbt);
        nbt.setInteger("Stage", counter);
        nbt.setInteger("Animation", anim == null ? InserterAnimation.Extending_Short.ordinal() : anim.ordinal());
        nbt.setBoolean("WhiteList", whiteList);
        nbt.setBoolean("IgnoreMeta", ignoreMeta);
        nbt.setBoolean("IgnoreNBT", ignoreNBT);
        nbt.setBoolean("IgnoreDict", ignoreDict);
        nbt.setByte("ValidDirs", valid_dirs);
        filter.writeToNBT(nbt, "Filter");
        upgrades.writeToNBT(nbt, "Upgrades");
    }

    public enum InserterAnimation {
        Rotating, Rotating_INV, Retracting_Short, Extending_Short, Retracting_INV_Short, Extending_INV_Short, Retracting_Large, Extending_Large, Retracting_INV_Large, Extending_INV_Large, DropItem, SuckItem, DropItem_Large, SuckItem_Large
    }

    @Override
    public void onMessageReceive(int id, int data) {
        if (id == 0) {
            whiteList = data == 1;
            sendUpdateToClient();
        } else if (id == 1) {
            ignoreMeta = data == 1;
            sendUpdateToClient();
        } else if (id == 2) {
            ignoreNBT = data == 1;
            sendUpdateToClient();
        } else if (id == 3) {
            ignoreDict = data == 1;
            sendUpdateToClient();
        }
    }
}
