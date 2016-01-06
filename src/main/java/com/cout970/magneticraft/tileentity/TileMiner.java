package com.cout970.magneticraft.tileentity;

import buildcraft.api.core.EnumColor;
import buildcraft.api.transport.IPipeTile;
import buildcraft.api.transport.IPipeTile.PipeType;
import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerConfig;
import com.cout970.magneticraft.api.conveyor.IConveyorBelt;
import com.cout970.magneticraft.api.conveyor.prefab.ItemBox;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.*;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IEnergyTracker;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.util.*;
import com.cout970.magneticraft.util.tile.TileConductorMedium;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TileMiner extends TileConductorMedium implements IInventoryManaged, IGuiSync, IGuiListener {

    public static final int MINING_COST_PER_BLOCK = 1000;//1000 RF
    private static final int MAX_DIMENSION = 80;
    private static final int MIN_DIMENSION = 10;
    public InventoryComponent inv = new InventoryComponent(this, 1, "Miner");
    public WorkState state = WorkState.UNREADY;
    public List<BlockInfo> well = new ArrayList<>();
    public ArrayList<ItemStack> items = new ArrayList<>();
    public float coolDown = MINING_COST_PER_BLOCK;
    public float consumptionCounter;
    public float consume;
    public int minedLastSecond;
    public int hole = 0;
    public int dim = 11;
    public int mined;
    public boolean removeWater = false;
    public boolean replaceWithDirt = true;
    public boolean scheduleUpdate = false;
    private boolean isFirstTime = false;
    private Ticket chunkTicket;

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this, 1, ElectricConstants.RESISTANCE_COPPER_MED){
            @Override
            public double getVoltageCapacity() {
                return ElectricConstants.MACHINE_CAPACITY*10;
            }
        };
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        if (!isFirstTime) loadChunk();
        if (scheduleUpdate && worldObj.getTotalWorldTime() % 80 == 0) {
            updateTicket();
            scheduleUpdate = false;
        }



        if (state == WorkState.UNREADY) {
            scanWell();
            hole++;
            if (hole > dim * dim) {
                state = WorkState.FINISHED;
                hole = 0;
            } else
                state = WorkState.WORKING;
        }
        if (state == WorkState.WORKING && isControlled()) {

            if (items.isEmpty()) {
                double work = ElectricConstants.MAX_VOLTAGE*0.5;
                double eff = cond.getVoltage()-work * cond.getVoltageMultiplier();
                double p = eff*3;
                p = p*p;

                if (coolDown > 0) {
                    if (cond.getVoltage() > work * cond.getVoltageMultiplier()) {
                        coolDown -= EnergyConverter.WtoRF(p);
                        consumptionCounter += p;
                        cond.drainPower(p);
                    }
                }
                while (coolDown <= 0) {
                    if (mineOneBlock()) {
                        coolDown += MINING_COST_PER_BLOCK;
                    }
                    if (state != WorkState.WORKING) break;
                }
            }

            while (items.iterator().hasNext()) {
                if (ejectItems(items.get(0))) {
                    items.remove(0);
                } else break;
            }
        }
        if (worldObj.getTotalWorldTime() % 20 == 0) {
            consume = consumptionCounter;
            minedLastSecond = mined;
            consumptionCounter = 0;
            mined = 0;
        }
    }

    private boolean mineOneBlock() {
        if (well.size() > 0) {
            BlockInfo f = well.get(0);
            well.remove(f);
            Block b = worldObj.getBlock(f.getX(), f.getY(), f.getZ());
            int meta = worldObj.getBlockMetadata(f.getX(), f.getY(), f.getZ());
            BlockInfo f0 = new BlockInfo(b, meta);
            if (f0.getBlock() != Blocks.air && (MgUtils.isMineableBlock(getWorldObj(), f0)) || (Block.isEqualTo(b, Blocks.water) || Block.isEqualTo(b, Blocks.lava) || Block.isEqualTo(b, Blocks.flowing_lava) || Block.isEqualTo(b, Blocks.flowing_water))) {
                //break block
                items.addAll(f.getBlock().getDrops(worldObj, f.getX(), f.getY(), f.getZ(), f.getMeta(), 0));
                if (replaceWithDirt) {
                    worldObj.setBlock(f.getX(), f.getY(), f.getZ(), Blocks.dirt);
                } else {
                    worldObj.setBlockToAir(f.getX(), f.getY(), f.getZ());
                }
                if (removeWater && !replaceWithDirt) {
                    for (MgDirection dir : MgDirection.values()) {
                        Block ba = worldObj.getBlock(f.getX() + dir.getOffsetX(), f.getY() + dir.getOffsetY(), f.getZ() + dir.getOffsetZ());
                        if (Block.isEqualTo(ba, Blocks.water) || Block.isEqualTo(ba, Blocks.lava) || Block.isEqualTo(ba, Blocks.flowing_lava) || Block.isEqualTo(ba, Blocks.flowing_water)) {
                            worldObj.setBlock(f.getX() + dir.getOffsetX(), f.getY() + dir.getOffsetY(), f.getZ() + dir.getOffsetZ(), Blocks.dirt);
                        }
                    }
                }
                mined++;
                return true;
            } else {
                return false;
            }
        } else {
            state = WorkState.UNREADY;
        }
        return true;
    }

    private void scanWell() {
        MgDirection d = getDirection();
        int x, z;
        if (dim < MIN_DIMENSION) dim = MIN_DIMENSION;
        if (d == MgDirection.NORTH) {
            x = (hole / dim) - dim / 2;
            z = -((hole % dim) + 1);
        } else if (d == MgDirection.SOUTH) {
            x = (hole / dim) - dim / 2;
            z = (hole % dim) + 1;
        } else if (d == MgDirection.EAST) {
            x = (hole % dim) + 1;
            z = (hole / dim) - dim / 2;
        } else {
            x = -((hole % dim) + 1);
            z = (hole / dim) - dim / 2;
        }
        VecInt pos = new VecInt(xCoord + x, 0, zCoord + z);
        well.clear();
        for (int i = yCoord + 5; i >= 0; i--) {
            Block b = worldObj.getBlock(pos.getX(), i, pos.getZ());
            int meta = worldObj.getBlockMetadata(pos.getX(), i, pos.getZ());
            BlockInfo info = new BlockInfo(b, meta, pos.copy().add(0, i, 0));
            if (replaceWithDirt && Block.isEqualTo(b, Blocks.dirt)) continue;
            if (MgUtils.isMineableBlock(worldObj, info) || (removeWater && (Block.isEqualTo(b, Blocks.water) || Block.isEqualTo(b, Blocks.lava) || Block.isEqualTo(b, Blocks.flowing_lava) || Block.isEqualTo(b, Blocks.flowing_water)))) {
                well.add(info);
            }
        }
    }

    public boolean ejectItems(ItemStack i) {
        if (i == null) return true;
        for (MgDirection d : MgDirection.values()) {
            TileEntity t = MgUtils.getTileEntity(this, d);
            if (t instanceof IInventory) {
                if (t instanceof ISidedInventory) {
                    ISidedInventory s = (ISidedInventory) t;
                    for (int j : s.getAccessibleSlotsFromSide(d.opposite().ordinal())) {
                        if (s.canInsertItem(j, i, d.opposite().ordinal())) {
                            s.setInventorySlotContents(j, InventoryUtils.addition(i, s.getStackInSlot(j)));
                            return true;
                        }
                    }
                } else {
                    IInventory s = (IInventory) t;
                    for (int j = 0; j < s.getSizeInventory(); j++) {
                        if (s.getStackInSlot(j) == null) {
                            s.setInventorySlotContents(j, i);
                            return true;
                        } else if (s.getStackInSlot(j).isItemEqual(i) && s.getStackInSlot(j).stackSize + i.stackSize <= s.getInventoryStackLimit()) {
                            s.setInventorySlotContents(j, InventoryUtils.addition(i, s.getStackInSlot(j)));
                            return true;
                        }
                    }
                }
            } else if (ManagerIntegration.BUILDCRAFT && (t instanceof IPipeTile)) {
                IPipeTile a = (IPipeTile) t;
                if (a.getPipeType() == PipeType.ITEM) {
                    int r = a.injectItem(i, true, d.toForgeDir().getOpposite(), EnumColor.WHITE);
                    if (r > 0) return true;
                }
            } else if (t instanceof IConveyorBelt) {
                IConveyorBelt c = (IConveyorBelt) t;
                ItemBox box = new ItemBox(i);
                if (c.addItem(d.opposite(), 0, box, true)) {
                    c.addItem(d.opposite(), 0, box, false);
                    c.onChange();
                    return true;
                }
            }
        }
        return false;
    }

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata()).opposite();
    }

    public enum WorkState {
        UNREADY, FINISHED, WORKING, IDLE, BLOCKED
    }

    public InventoryComponent getInv() {
        return inv;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        inv.readFromNBT(nbt);

        dim = nbt.getInteger("Dimension");
        hole = nbt.getInteger("Hole");
        replaceWithDirt = nbt.getBoolean("Replace");
        removeWater = nbt.getBoolean("Remove");

        items.clear();
        int size = nbt.getInteger("BufferSize");
        NBTTagList list = nbt.getTagList("BufferData", 10);
        for (int i = 0; i < size; i++) {
            NBTTagCompound t = list.getCompoundTagAt(i);
            ItemStack it = ItemStack.loadItemStackFromNBT(t);
            items.add(it);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        inv.writeToNBT(nbt);

        nbt.setInteger("Dimension", dim);
        nbt.setInteger("Hole", hole);
        nbt.setBoolean("Replace", replaceWithDirt);
        nbt.setBoolean("Remove", removeWater);

        if (!items.isEmpty()) {
            nbt.setInteger("BufferSize", items.size());
            NBTTagList list = new NBTTagList();
            for (ItemStack item : items) {
                NBTTagCompound t = new NBTTagCompound();
                if (item != null) {
                    item.writeToNBT(t);
                }
                list.appendTag(t);
            }
            nbt.setTag("BufferData", list);
        }
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, (int) coolDown);
        craft.sendProgressBarUpdate(cont, 2, state.ordinal());
        craft.sendProgressBarUpdate(cont, 3, (int) consume);
        craft.sendProgressBarUpdate(cont, 4, minedLastSecond);
        craft.sendProgressBarUpdate(cont, 5, hole);
        craft.sendProgressBarUpdate(cont, 6, dim);
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 0) cond.setVoltage(value);
        if (id == 1) coolDown = value;
        if (id == 2) state = WorkState.values()[value % WorkState.values().length];
        if (id == 3) consume = value;
        if (id == 4) minedLastSecond = value;
        if (id == 5) hole = value;
        if (id == 6) dim = value;
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
    public void onMessageReceive(int id, int data) {
        if (id == 0) {
            int old = dim;
            dim = Math.min(MAX_DIMENSION, dim + data);
            if (old != dim) {
                hole = 0;
                state = WorkState.UNREADY;
                scheduleUpdate = true;
                sendUpdateToClient();
            }
        } else if (id == 1) {
            int old = dim;
            dim = Math.max(MIN_DIMENSION, dim - data);
            if (old != dim) {
                hole = 0;
                state = WorkState.UNREADY;
                scheduleUpdate = true;
                sendUpdateToClient();
            }
        } else if (id == 2) {
            replaceWithDirt = data == 1;
            sendUpdateToClient();
        } else if (id == 3) {
            removeWater = data == 1;
            sendUpdateToClient();
        }
    }

    public IEnergyTracker getEnergyTracker() {
        return new IEnergyTracker() {

            @Override
            public boolean isConsume() {
                return true;
            }

            @Override
            public float getMaxChange() {
                return 70000;
            }

            @Override
            public float getChangeInTheLastTick() {
                return consumptionCounter;
            }

            @Override
            public float getChangeInTheLastSecond() {
                return consume;
            }
        };
    }

    public IBarProvider getBlocksMinedLastSecondBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return "Blocks mined in the last second: " + minedLastSecond;
            }

            @Override
            public float getMaxLevel() {
                return 150;
            }

            @Override
            public float getLevel() {
                return Math.min(minedLastSecond, 150);
            }
        };
    }

    //chunk loading

    public void updateTicket() {
        if (chunkTicket == null) return;
        ForgeChunkManager.releaseTicket(chunkTicket);
        chunkTicket = null;
        loadChunk();
    }

    public void loadChunk() {
        isFirstTime = true;
        if (ManagerConfig.MINER_CHUNKLOADING) {
            if (chunkTicket == null) {
                chunkTicket = ForgeChunkManager.requestTicket(Magneticraft.INSTANCE, worldObj, Type.NORMAL);
            }
            chunkTicket.getModData().setInteger("quarryX", xCoord);
            chunkTicket.getModData().setInteger("quarryY", yCoord);
            chunkTicket.getModData().setInteger("quarryZ", zCoord);
            forceChunkLoading(chunkTicket);
        }
    }

    public void forceChunkLoading(Ticket ticket) {
        if (chunkTicket == null) {
            chunkTicket = ticket;
        }
        Set<ChunkCoordIntPair> chunks = Sets.newHashSet();
        ChunkCoordIntPair quarryChunk = new ChunkCoordIntPair(xCoord >> 4, zCoord >> 4);
        chunks.add(quarryChunk);
        ForgeChunkManager.forceChunk(ticket, quarryChunk);

        MgDirection d = getDirection();
        int xMax, xMin, zMax, zMin;
        if (d == MgDirection.NORTH) {
            xMin = -dim / 2;
            xMax = dim / 2;
            zMin = -dim;
            zMax = -1;
        } else if (d == MgDirection.SOUTH) {
            xMin = -dim / 2;
            xMax = dim / 2;
            zMin = 1;
            zMax = dim;
        } else if (d == MgDirection.EAST) {
            xMin = 1;
            xMax = dim;
            zMin = -dim / 2;
            zMax = dim / 2;
        } else {
            xMin = -dim;
            xMax = -1;
            zMin = -dim / 2;
            zMax = dim / 2;
        }

        for (int chunkX = (xMin + xCoord) >> 4; chunkX <= (xMax + xCoord) >> 4; chunkX++) {
            for (int chunkZ = (zMin + zCoord) >> 4; chunkZ <= (zMax + zCoord) >> 4; chunkZ++) {
                ChunkCoordIntPair chunk = new ChunkCoordIntPair(chunkX, chunkZ);
                ForgeChunkManager.forceChunk(ticket, chunk);
                chunks.add(chunk);
            }
        }
        Log.info("Miner at " + xCoord + " " + yCoord + " " + zCoord + " will keep " + chunks.size() + " chunks loaded");
    }
}
