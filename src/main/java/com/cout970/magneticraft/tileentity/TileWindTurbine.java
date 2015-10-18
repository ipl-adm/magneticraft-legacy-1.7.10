package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.BufferedConductor;
import com.cout970.magneticraft.api.tool.IWindTurbine;
import com.cout970.magneticraft.api.util.*;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IEnergyTracker;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.FractalLib;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Random;

public class TileWindTurbine extends TileConductorLow implements IInventoryManaged, IGuiSync {

    public int turbine = -1;
    public MgDirection facing = null;
    public float rotation;
    public long time;
    public InventoryComponent inv = new InventoryComponent(this, 1, "Wind Turbine");
    public boolean isDisplayed;
    public IRenderable rend;
    public int oldTurbine = -2;

    private int tracer;
    private byte[] rayTrace;
    private int efficiency;
    private double wind;
    private int power;
    public float speed;
    private int height;
    private int length;
    private double turbinePotency;
    private float production;
    private float productionPerSecond;
    private float averageProdCalc;

    private void traceAir1() {
        int yHeight = this.tracer / 17;
        int var2 = this.tracer % 17;
        MgDirection rightHand = facing.step(MgDirection.UP);
        VecInt pos = new VecInt(this);
        pos.add(facing.toVecInt().multiply(2));
        pos.add(rightHand.toVecInt().multiply(var2 - 8));
        pos.add(0, yHeight, 0);
        int air;

        for (air = 0; air < 20 && Block.isEqualTo(worldObj.getBlock(pos.getX(), pos.getY(), pos.getZ()), Blocks.air); ++air) {
            pos.add(facing.toVecInt());
        }

        if (this.rayTrace == null) {
            this.rayTrace = new byte[289];
        }

        this.efficiency = this.efficiency - this.rayTrace[this.tracer] + air;
        this.rayTrace[this.tracer] = (byte) air;
        ++this.tracer;

        if (this.tracer >= 289) {
            this.tracer = 0;
        }
    }

    public TileWindTurbine() {
        super();
    }

    public void updateEntity() {
        super.updateEntity();
        if (facing == null) {
            facing = MgDirection.getDirection(getBlockMetadata());
        } else {
            if (worldObj.getTotalWorldTime() % 20 == 0) {
                wind = 10000.0d * getWindSpeed();
                if (turbine != -1) {
                    power = (int) (wind * efficiency / 5780d);
                    this.speed = (float) (Math.sqrt(power) * 0.001f);
                }
                traceAir1();
            }
            if (worldObj.isRemote) return;
            if (worldObj.getTotalWorldTime() % 20 == 0) {
                productionPerSecond = averageProdCalc;
                averageProdCalc = 0;
            }

            if (turbine == -1) {
                production = 0;
                productionPerSecond = 0;
            } else if (cond.getVoltage() <= ElectricConstants.MAX_VOLTAGE && isControlled()) {
                cond.applyPower(EnergyConverter.RFtoW(power * turbinePotency / 100));
                production = (float) EnergyConverter.RFtoW(power * turbinePotency / 100);
            } else {
                production = 0;
            }
            averageProdCalc += production;
        }
    }

    private double getWindSpeed() {
        if (worldObj.provider.isHellWorld) {
            return 0.5d;
        } else {
            double tot = FractalLib.noise1D(2576710L, (double) worldObj.getTotalWorldTime() * 1.0E-4D, 0.6F, 5);
            tot = Math.max(0.0D, 1.6D * (tot - 0.5D) + 0.5D);

            if (worldObj.getWorldInfo().getTerrainType() != WorldType.FLAT) {
                tot *= Math.sqrt((double) yCoord) / 16.0D;
            }

            BiomeGenBase biome = worldObj.getBiomeGenForCoords(xCoord, zCoord);

            if (biome.canSpawnLightningBolt()) {
                if (worldObj.isThundering()) {
                    return 4.0D * tot;
                }

                if (worldObj.isRaining()) {
                    return 0.5D + 0.5D * tot;
                }
            }
            return tot;
        }
    }

    public void setInventorySlotContents(int a, ItemStack b) {
        getInv().setInventorySlotContents(a, b);
        check();
    }

    public ItemStack decrStackSize(int a, int b) {
        ItemStack c = getInv().decrStackSize(a, b);
        check();
        return c;
    }

    public void onBlockBreaks() {
        if (isDisplayed)
            deactivateTurbine();
    }

    private void check() {
        ItemStack a = getInv().getStackInSlot(0);
        if (a != null) {
            if (a.getItem() instanceof IWindTurbine && turbine != ((IWindTurbine) a.getItem()).getID()) {
                if (hasSpace((IWindTurbine) a.getItem())) {
                    turbine = -1;
                    deactivateTurbine();
                    turbine = ((IWindTurbine) a.getItem()).getID();
                    activateTurbine((IWindTurbine) a.getItem());
                    sendUpdateToClient();
                }
            } else {
                if (isDisplayed)
                    deactivateTurbine();
                turbine = -1;
            }
        } else {
            if (isDisplayed)
                deactivateTurbine();
            turbine = -1;
        }
    }

    private boolean hasSpace(IWindTurbine item) {
        int h = item.getHeight();
        int l = item.getLength();
        for (int y = -h + 1; y < h; y++) {
            for (int v = -l + 1; v < l; v++) {
                int i, k;
                switch (facing) {
                    case NORTH:
                        k = -1;
                        i = v;
                        break;
                    case SOUTH:
                        k = 1;
                        i = v;
                        break;
                    case WEST:
                        k = v;
                        i = -1;
                        break;
                    case EAST:
                        k = v;
                        i = 1;
                        break;
                    default:
                        return false;
                }
                if (!worldObj.isAirBlock(xCoord + i, yCoord + y, zCoord + k)) {
                    if (worldObj.getBlock(xCoord + i, yCoord + y, zCoord + k) != this.getBlockType())
                        return false;
                }
            }
        }
        return true;
    }

    public void deactivateTurbine() {
        isDisplayed = false;
        turbinePotency = 0;
        for (int y = -height + 1; y < height; y++) {
            for (int v = -length + 1; v < length; v++) {
                int i, k;
                switch (facing) {
                    case NORTH:
                        k = -1;
                        i = v;
                        break;
                    case SOUTH:
                        k = 1;
                        i = v;
                        break;
                    case WEST:
                        k = v;
                        i = -1;
                        break;
                    case EAST:
                        k = v;
                        i = 1;
                        break;
                    default:
                        return;
                }
                worldObj.setBlock(xCoord + i, yCoord + y, zCoord + k, Blocks.air);
            }
        }
    }

    public void activateTurbine(IWindTurbine w) {
        isDisplayed = true;
        height = w.getHeight();
        length = w.getLength();
        turbinePotency = w.getPotency();
        for (int y = -height + 1; y < height; y++) {
            for (int v = -length + 1; v < length; v++) {
                int i, k;
                switch (facing) {
                    case NORTH:
                        k = -1;
                        i = v;
                        break;
                    case SOUTH:
                        k = 1;
                        i = v;
                        break;
                    case WEST:
                        k = v;
                        i = -1;
                        break;
                    case EAST:
                        k = v;
                        i = 1;
                        break;
                    default:
                        return;
                }
                worldObj.setBlock(xCoord + i, yCoord + y, zCoord + k, getBlockType(), 15, 2);
                TileEntity t = worldObj.getTileEntity(xCoord + i, yCoord + y, zCoord + k);
                if (t instanceof TileWindTurbineGap) {
                    ((TileWindTurbineGap) t).x = xCoord;
                    ((TileWindTurbineGap) t).y = yCoord;
                    ((TileWindTurbineGap) t).z = zCoord;
                }
            }
        }
    }

    public float getDelta() {
        long aux = time;
        time = System.nanoTime();
        return (float) ((time - aux) * 1E-6);
    }


    @Override
    public void readFromNBT(NBTTagCompound nbt) {

        super.readFromNBT(nbt);
        isDisplayed = nbt.getBoolean("Disp");
        turbine = nbt.getInteger("turbine");
        height = nbt.getInteger("H");
        length = nbt.getInteger("L");
        getInv().readFromNBT(nbt);
        tracer = nbt.getInteger("Tracer");
        efficiency = nbt.getInteger("Eff");
        rayTrace = nbt.getByteArray("rayTrace");
        if (rayTrace.length != 289) {
            rayTrace = null;
        }
        turbinePotency = nbt.getDouble("P");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setBoolean("Disp", isDisplayed);
        nbt.setInteger("turbine", turbine);
        getInv().writeToNBT(nbt);
        nbt.setInteger("Tracer", tracer);
        nbt.setInteger("Eff", efficiency);
        if (rayTrace != null)
            nbt.setByteArray("rayTrace", rayTrace);
        nbt.setInteger("H", height);
        nbt.setInteger("L", length);
        nbt.setDouble("P", turbinePotency);
    }

    @Override
    public IElectricConductor initConductor() {
        return new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW, 80000, ElectricConstants.GENERATOR_DISCHARGE, ElectricConstants.GENERATOR_CHARGE);
    }

    public InventoryComponent getInv() {
        return inv;
    }

    public int getWindScaled(int i) {
        return (int) Math.min(i, i * wind / 13333);
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, (cond.getStorage() & 0xFFFF));
        craft.sendProgressBarUpdate(cont, 2, ((cond.getStorage() & 0xFFFF0000) >>> 16));
        craft.sendProgressBarUpdate(cont, 3, (int) production);
        craft.sendProgressBarUpdate(cont, 4, (int) productionPerSecond);
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
            production = value;
        if (id == 4)
            productionPerSecond = value;
    }

    public float getTurbineScale() {
        ItemStack a = getInv().getStackInSlot(0);
        if (a != null) {
            if (a.getItem() instanceof IWindTurbine) {
                return ((IWindTurbine) a.getItem()).getScale();
            }
        }
        return 1;
    }

    public IEnergyTracker getEnergyTracker() {
        return new IEnergyTracker() {

            @Override
            public float getMaxChange() {
                if (turbinePotency != 0) return (float) (1333 * turbinePotency);
                return 1;
            }

            @Override
            public float getChangeInTheLastTick() {
                if (turbinePotency != 0) return production;
                return 0;
            }

            @Override
            public float getChangeInTheLastSecond() {
                if (turbinePotency != 0) return productionPerSecond;
                return 0;
            }

            @Override
            public boolean isConsume() {
                return false;
            }
        };
    }

    public void onTurbineBreaks() {
        deactivateTurbine();
        if (turbine == -1) return;
        Random rand = worldObj.rand;
        ItemStack i = getInv().getStackInSlot(0);
        getInv().setInventorySlotContents(0, null);
        turbine = -1;
        if (i != null && i.stackSize > 0) {
            float rx = rand.nextFloat() * 0.8F + 0.1F;
            float ry = rand.nextFloat() * 0.8F + 0.1F;
            float rz = rand.nextFloat() * 0.8F + 0.1F;
            EntityItem entityItem = new EntityItem(worldObj,
                    xCoord + rx, yCoord + ry, zCoord + rz,
                    new ItemStack(i.getItem(), i.stackSize, i.getItemDamage()));
            if (i.hasTagCompound()) {
                entityItem.getEntityItem().setTagCompound((NBTTagCompound) i.getTagCompound().copy());
            }
            float factor = 0.05F;
            entityItem.motionX = rand.nextGaussian() * factor;
            entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
            entityItem.motionZ = rand.nextGaussian() * factor;
            worldObj.spawnEntityInWorld(entityItem);
            sendUpdateToClient();
        }
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        VecInt v1 = VecIntUtil.getRotatedOffset(MgDirection.getDirection(getBlockMetadata()), length - 1, height - 1, 0);
        VecInt v2 = VecIntUtil.getRotatedOffset(MgDirection.getDirection(getBlockMetadata()), 1 - length, 1 - height, 1);
        VecInt block = new VecInt(xCoord, yCoord, zCoord);

        return VecIntUtil.getAABBFromVectors(v1.add(block), v2.add(block));
    }

    public int getSizeInventory() {
        return getInv().getSizeInventory();
    }

    public ItemStack getStackInSlot(int s) {
        return getInv().getStackInSlot(s);
    }

    public ItemStack getStackInSlotOnClosing(int a) {
        return getInv().getStackInSlotOnClosing(a);
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

    public IBarProvider getEfficiencyBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return null;
            }

            @Override
            public float getMaxLevel() {
                return 1;
            }

            @Override
            public float getLevel() {
                return efficiency / 5780f;
            }
        };
    }
}
