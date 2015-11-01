package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.computer.*;
import com.cout970.magneticraft.api.computer.prefab.*;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.BufferedConductor;
import com.cout970.magneticraft.api.util.*;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IClientInformer;
import com.cout970.magneticraft.util.IGuiListener;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.InventoryUtils;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

public class TileDroidRED extends TileConductorLow implements IComputer, IGuiSync, IClientInformer, IGuiListener, IBusWire {

    private IModuleMemoryController memory;
    private IModuleDiskDrive floppyDisk;
    private IModuleCPU cpu;
    private IModuleROM rom;
    private InventoryComponent inv = new InventoryComponent(this, 16, "R.E.D.");
    public int droidAction = -1;//0 move front, 1 move back
    public int droidProgress = -1;
    public boolean activate = true;
    private long time;
    public int drillAnim;

    public InventoryComponent extras = new InventoryComponent(this, 2, "R.E.D.") {

        @Override
        public void setInventorySlotContents(int slot, ItemStack itemStack) {
            inventory[slot] = itemStack;

            if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
                itemStack.stackSize = this.getInventoryStackLimit();
            }
            markDirty();
            if (slot == 1 && !worldObj.isRemote) floppyDisk.insertDisk(itemStack);
        }

        @Override
        public int getInventoryStackLimit() {
            return 1;
        }
    };

    public IPeripheral droid = new IPeripheral() {

        public int address = 0xa;

        @Override
        public int getAddress() {
            return address;
        }

        @Override
        public void setAddress(int address) {
            this.address = address;
        }

        @Override
        public boolean isActive() {
            return false;
        }

        @Override
        public String getName() {
            return "RED";
        }

        @Override
        public int readByte(int pointer) {
            return 0;
        }

        @Override
        public void writeByte(int pointer, int data) {
//			Log.debug("Droid message at "+pointer+" with "+data);
            if (pointer == 1) {
                if (data == 1) move(true);
                else if (data == 2) move(false);
            } else if (pointer == 2) {
                rotate(true, data != 0);
            } else if (pointer == 3) {
                rotate(false, data != 0);
            } else if (pointer == 4) {
                mine();
            }
        }

        @Override
        public TileEntity getParent() {
            return TileDroidRED.this;
        }
    };

    public MonitorPeripheral monitor = new MonitorPeripheral(this);


    public void create() {
        memory = new ModuleMemoryController(0x10000, false, 8);
        cpu = new ModuleCPU_MIPS();
        floppyDisk = new ModuleDiskDrive(this);
        rom = new ModuleROM();
        cpu.connectMemory(memory);
        memory.setComputer(this);

    }

    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote && cpu == null) {
            create();
        }
        if (droidProgress > 0) {
            droidProgress--;
        }
        if (droidProgress == 0) {
            if (droidAction == 0) {
                MgDirection dir = getDirection();
                Block b = worldObj.getBlock(xCoord + dir.getOffsetX(), yCoord + dir.getOffsetY(), zCoord + dir.getOffsetZ());
                if (b == getBlockType()) {
                    Orientation ori = getOrientation();
                    if (!worldObj.isRemote)
                        worldObj.setBlockToAir(xCoord, yCoord, zCoord);
                    xCoord = xCoord + dir.getOffsetX();
                    yCoord = yCoord + dir.getOffsetY();
                    zCoord = zCoord + dir.getOffsetZ();
                    validate();
                    worldObj.setTileEntity(xCoord, yCoord, zCoord, this);
                    setOrientation(ori);
                    cond.drainPower(EnergyConverter.RFtoW(10));
                    markDirty();
                    onNeigChange();
                    sendUpdateToClient();
                }
                droidProgress = -1;
                return;
            } else if (droidAction == 1) {
                MgDirection dir = getDirection().opposite();
                Block b = worldObj.getBlock(xCoord + dir.getOffsetX(), yCoord + dir.getOffsetY(), zCoord + dir.getOffsetZ());
                if (b == getBlockType()) {
                    Orientation ori = getOrientation();
                    if (!worldObj.isRemote)
                        worldObj.setBlockToAir(xCoord, yCoord, zCoord);
                    xCoord = xCoord + dir.getOffsetX();
                    yCoord = yCoord + dir.getOffsetY();
                    zCoord = zCoord + dir.getOffsetZ();
                    validate();
                    worldObj.setTileEntity(xCoord, yCoord, zCoord, this);
                    setOrientation(ori);
                    cond.drainPower(EnergyConverter.RFtoW(10));
                    markDirty();
                    onNeigChange();
                    sendUpdateToClient();
                }
                droidProgress = -1;
                return;
            } else if (droidAction == 2) {
                if (worldObj.isRemote) return;
                Orientation or = getOrientation().rotateY(true);
                setOrientation(or);
            } else if (droidAction == 3) {
                if (worldObj.isRemote) return;
                Orientation or = getOrientation().rotateY(false);
                setOrientation(or);
            } else if (droidAction == 4) {
                if (worldObj.isRemote) return;
                Orientation or = getOrientation();
                or = Orientation.find(or.getLevel() + 1, or.getDirection());
                if (or != null) setOrientation(or);
            } else if (droidAction == 5) {
                if (worldObj.isRemote) return;
                Orientation or = getOrientation();
                or = Orientation.find(or.getLevel() - 1, or.getDirection());
                if (or != null) setOrientation(or);
            }
            droidProgress = -1;
        }
        if (worldObj.isRemote) return;
        cpu.iterate();
        floppyDisk.iterate();
        cond.drainPower(EnergyConverter.RFtoW(0.5D));
    }

    //axis == true rotate from y, else from x, dir == true, left or top, else right or bottom
    public void rotate(boolean axis, boolean dir) {
        if (axis) {
            if (dir) {
                droidProgress = 5;
                droidAction = 2;//left
                sendUpdateToClient();
            } else {
                droidProgress = 5;
                droidAction = 3;//right
                sendUpdateToClient();
            }
        } else {
            if (dir && getOrientation().getLevel() != 1) {
                droidProgress = 5;
                droidAction = 4;//top
                sendUpdateToClient();
            } else if (!dir && getOrientation().getLevel() != -1) {
                droidProgress = 5;
                droidAction = 5;//bottom
                sendUpdateToClient();
            }
        }
    }

    private void setOrientation(Orientation o) {
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, o.ordinal(), 3);
    }

    public Orientation getOrientation() {
        return Orientation.fromMeta(getBlockMetadata());
    }

    public void move(boolean front) {
        MgDirection dir = front ? getDirection() : getDirection().opposite();
        if (getBlockType().canPlaceBlockAt(worldObj, xCoord + dir.getOffsetX(), yCoord + dir.getOffsetY(), zCoord + dir.getOffsetZ())) {
            worldObj.setBlock(xCoord + dir.getOffsetX(), yCoord + dir.getOffsetY(), zCoord + dir.getOffsetZ(), ManagerBlocks.droid_red, 15, 2);
            if (front) {
                droidProgress = 5;
                droidAction = 0;
            } else {
                droidProgress = 10;
                droidAction = 1;
            }
            activate = true;
            sendUpdateToClient();
            markDirty();
        }
    }

    public void mine() {
        MgDirection dire = getDirection();
        Block b = worldObj.getBlock(xCoord + dire.getOffsetX(), yCoord + dire.getOffsetY(), zCoord + dire.getOffsetZ());
        int metadata = worldObj.getBlockMetadata(xCoord + dire.getOffsetX(), yCoord + dire.getOffsetY(), zCoord + dire.getOffsetZ());
        if (MgUtils.isMineableBlock(worldObj, new BlockInfo(b, metadata))) {

            ArrayList<ItemStack> items;
            items = b.getDrops(worldObj, xCoord + dire.getOffsetX(), yCoord + dire.getOffsetY(), zCoord + dire.getOffsetZ(), metadata, 0);
            worldObj.func_147480_a(xCoord + dire.getOffsetX(), yCoord + dire.getOffsetY(), zCoord + dire.getOffsetZ(), false);

            items.stream().filter(itemStack -> !InventoryUtils.dropIntoInventory(itemStack, getInv())).forEach(itemStack -> BlockMg.dropItem(itemStack, worldObj.rand, xCoord + dire.getOffsetX(), yCoord + dire.getOffsetY(), zCoord + dire.getOffsetZ(), worldObj));
        }
    }

    public MgDirection getDirection() {
        if (getOrientation().getLevel() == 1) return MgDirection.UP;
        if (getOrientation().getLevel() == -1) return MgDirection.DOWN;
        return getOrientation().getDirection().opposite();
    }

    public InventoryComponent getInv() {
        return inv;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        activate = nbt.getBoolean("Active");
        droidProgress = nbt.getInteger("PROGRESS");
        droidAction = nbt.getInteger("ACTION");
        getInv().readFromNBT(nbt);
        monitor.load(nbt);
        extras.readFromNBT(nbt, "Extras");
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER && cpu != null) {
            cpu.loadRegisters(nbt);
            memory.loadMemory(nbt);
            floppyDisk.load(nbt);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("Active", activate);
        nbt.setInteger("PROGRESS", droidProgress);
        nbt.setInteger("ACTION", droidAction);
        getInv().writeToNBT(nbt);
        monitor.save(nbt);
        extras.writeToNBT(nbt, "Extras");
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            if (cpu != null) {
                cpu.saveRegisters(nbt);
                memory.saveMemory(nbt);
                floppyDisk.save(nbt);
            }
        }
    }

    @Override
    public void saveInServer(NBTTagCompound nbt) {
        nbt.setBoolean("Active", activate);
        nbt.setInteger("PROGRESS", droidProgress);
        nbt.setInteger("ACTION", droidAction);
        getInv().writeToNBT(nbt);
        monitor.save(nbt);
        extras.writeToNBT(nbt, "Extras");
    }

    @Override
    public void loadInClient(NBTTagCompound nbt) {
        activate = nbt.getBoolean("Active");
        droidProgress = nbt.getInteger("PROGRESS");
        droidAction = nbt.getInteger("ACTION");
        getInv().readFromNBT(nbt);
        monitor.load(nbt);
        extras.readFromNBT(nbt, "Extras");
    }

    public float getDelta() {
        long aux = time;
        time = System.nanoTime();
        return time - aux;
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, (cond.getStorage() & 0xFFFF));
        craft.sendProgressBarUpdate(cont, 2, ((cond.getStorage() & 0xFFFF0000) >>> 16));
        sendUpdateToClient();
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
        }
    }

    @Override
    public IModuleCPU getCPU() {
        return cpu;
    }

    @Override
    public IModuleMemoryController getMemory() {
        return memory;
    }

    @Override
    public TileEntity getParent() {
        return this;
    }

    @Override
    public IElectricConductor initConductor() {
        return new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW, (int) EnergyConverter.RFtoW(50000), ElectricConstants.MACHINE_DISCHARGE, ElectricConstants.MACHINE_CHARGE);
    }

    public boolean isRunning() {
        return true;//cpu.isRunning();
    }

    @Override
    public void onMessageReceive(int id, int data) {
        if (id == 0) {
            if (!cpu.isRunning()) {
                cpu.start();
                rom.loadToRAM(memory);
                sendUpdateToClient();
            }
        } else if (id == 1) {
            cpu.stop();
            cpu.start();
            rom.loadToRAM(memory);
            sendUpdateToClient();
        } else if (id == 2) {
            cpu.stop();
            sendUpdateToClient();
        }
    }

    public IPeripheral[] getPeripherals() {
        return new IPeripheral[]{droid, monitor};
    }

    @Override
    public void saveInfoToMessage(NBTTagCompound nbt) {
        monitor.saveInfoToMessage(nbt);
    }

    @Override
    public void loadInfoFromMessage(NBTTagCompound nbt) {
        monitor.loadInfoFromMessage(nbt);
    }

    @Override
    public VecInt[] getValidConnections() {
        return VecIntUtil.FORGE_DIRECTIONS;
    }

    @Override
    public void iterate() {
    }

    @Override
    public boolean isAbleToConnect(IConnectable cond, VecInt dir) {
        return true;
    }

    @Override
    public ConnectionClass getConnectionClass(VecInt v) {
        return ConnectionClass.FULL_BLOCK;
    }

    @Override
    public void save(NBTTagCompound nbt) {
    }

    @Override
    public void load(NBTTagCompound nbt) {
    }
}