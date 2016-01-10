package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.*;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.concurrency.PathFindingCallable;
import com.cout970.magneticraft.util.concurrency.PathFindingExecutor;
import com.cout970.magneticraft.util.concurrency.ThreadSafeBlockAccess;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.pathfinding.OilPathFinding;
import com.cout970.magneticraft.util.pathfinding.PathFinding;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.*;
import java.util.concurrent.Future;

public class TilePumpJack extends TileConductorLow implements IFluidHandler1_8 {

    private static final int speed = 50;
    public static Block fluidOil;

    // client
    public float m;
    public boolean active;
    public float time;
    // server
    public TankMg tank = new TankMg(this, 4000);
    private List<VecInt> pipes = new LinkedList<>();
    private Set<VecInt> oilBlocks = new HashSet<>();
    private Set<VecInt> fluid = new HashSet<>();

    private boolean pipesPlaced;
    private boolean foundOilDeposit;
    private int cooldown;
    private int buffer;
    private int lastY = -1;
    private Future<PathFinding> result;


    @Override
    public IElectricConductor[] getConds(VecInt dir, int tier) {
        if (tier != 0)
            return null;
        if (VecInt.NULL_VECTOR.equals(dir))
            return new IElectricConductor[]{cond};
        return null;
    }

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this) {
            @Override
            public VecInt[] getValidConnections() {
                return new VecInt[]{getOrientation().toVecInt()};
            }
        };
    }

    public MgDirection getOrientation() {
        return MgDirection.AXIX_Y[getBlockMetadata() % MgDirection.AXIX_Y.length];
    }

    public void updateEntity() {
        super.updateEntity();
        boolean working = cond.getVoltage() > ElectricConstants.MACHINE_WORK;


        if (worldObj.getTotalWorldTime() % 20 == 0) {
            if (working && !isActive()) {
                setActive(true);
            } else if (!working && isActive()) {
                setActive(false);
            }
        }

        if (worldObj.isRemote || !working)
            return;

        if (fluidOil == null)
            fluidOil = FluidRegistry.getFluid("oil").getBlock();

        if (!foundOilDeposit) {
            //trying to extract result from the thread
            if (result != null) {
                if (result.isDone()) {
                    PathFinding pf = null;
                    try {
                        pf = result.get();
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                    if (pf != null) {
                        //finished
                        if (pf.isDone()) {
                            PathFinding.Result pfResult = pf.getResult();
                            List<VecInt> all = pfResult.getAllScanned();
                            if (!all.isEmpty()) {
                                all.stream().filter(v -> v.getBlock(worldObj) == ManagerBlocks.oilSource).forEach(oilBlocks::add);
                                all.stream().filter(v -> v.getBlock(worldObj) == fluidOil).forEach(fluid::add);
                                lastY = -1;
                                foundOilDeposit = true;
                            } else {
                                lastY--;
                                foundOilDeposit = false;
                            }
                            result = null;
                        } else {
                            //not finished
                            result = PathFindingExecutor.INSTANCE.submit(new PathFindingCallable(pf, PathFindingExecutor.INSTANCE));
                        }
                    } else {
                        //errored
                        result = null;
                    }
                }
            } else if (worldObj.getTotalWorldTime() % 80 == 0) {
                updateOilDeposit();
            }

        } else {
            if (!pipesPlaced) {
                if (worldObj.getTotalWorldTime() % 40 == 0) {
                    placePipes();
                }
            } else {

                if (tank.getSpace() > 0 && buffer > 0) {//fill tank

                    int i = Math.min(speed, buffer);
                    buffer -= tank.fill(FluidRegistry.getFluidStack("oil", i), true);
                    cond.drainPower(EnergyConverter.RFtoW(i));

                } else if (buffer < 1) {//drain one block

                    if (!fluid.isEmpty()) {
                        VecInt b = fluid.iterator().next();
                        int m = worldObj.getBlockMetadata(b.getX(), b.getY(), b.getZ());
                        Block bl = worldObj.getBlock(b.getX(), b.getY(), b.getZ());
                        if (m == 0 && bl == fluidOil) {
                            worldObj.setBlock(b.getX(), b.getY(), b.getZ(), Blocks.air);
                            buffer = 1000;
                        }
                        fluid.remove(b);

                    } else if (!oilBlocks.isEmpty()) {

                        VecInt b = oilBlocks.iterator().next();
                        int m = worldObj.getBlockMetadata(b.getX(), b.getY(), b.getZ());
                        if (m > 0) {
                            worldObj.setBlockMetadataWithNotify(b.getX(), b.getY(), b.getZ(), m - 1, 2);
                        } else {
                            worldObj.setBlock(b.getX(), b.getY(), b.getZ(), ManagerBlocks.oilSourceDrained);
                            oilBlocks.remove(b);
                        }
                        buffer = 1000;
                    } else {
                        foundOilDeposit = false;
                    }
                }
            }
        }
        export();
    }

    private void updateOilDeposit() {
        fluid.clear();
        oilBlocks.clear();
        int start = lastY == -1 ? yCoord : lastY;
        for (int i = start; i > 0; i--) {
            VecInt pos = new VecInt(xCoord, i, zCoord);
            Block b = pos.getBlock(worldObj);

            if (b.equals(ManagerBlocks.oilSource) || b.equals(fluidOil) || b.equals(ManagerBlocks.oilSourceDrained)) {
                OilPathFinding pathFinding = new OilPathFinding(ThreadSafeBlockAccess.getAccess(worldObj), pos);
                result = PathFindingExecutor.INSTANCE.submit(new PathFindingCallable(pathFinding, PathFindingExecutor.INSTANCE));
                break;
            }
        }
    }

    private void placePipes() {

        if (pipes.isEmpty()) {
            findPipeSpots();
            pipesPlaced = pipes.isEmpty();
        } else {
            VecInt c = pipes.iterator().next();
            replaceBlock(c.getX(), c.getY(), c.getZ(), ManagerBlocks.concreted_pipe);
            cond.drainPower(EnergyConverter.RFtoW(80));
            pipes.remove(c);
        }
    }

    private void findPipeSpots() {
        pipes.clear();

        for (int i = 1; i < yCoord; i++) {
            Block b = worldObj.getBlock(xCoord, yCoord - i, zCoord);
            int meta = worldObj.getBlockMetadata(xCoord, yCoord - i, zCoord);

            if (b.equals(ManagerBlocks.oilSource) || b.equals(fluidOil)) {
                break;
            } else if (b == Blocks.air || MgUtils.isMineableBlock(worldObj, new BlockInfo(b, meta)) && b != ManagerBlocks.concreted_pipe && b != ManagerBlocks.oilSourceDrained) {
                pipes.add(new VecInt(xCoord, yCoord - i, zCoord));
            }
        }
    }

    public void replaceBlock(int x, int y, int z, Block replace) {
        if (worldObj.getBlock(x, y, z).isAir(worldObj, x, y, z) || MgUtils.isMineableBlock(worldObj,
                new BlockInfo(worldObj.getBlock(x, y, z), worldObj.getBlockMetadata(x, y, z), x, y, z))) {
            ArrayList<ItemStack> items;
            Block id = worldObj.getBlock(x, y, z);
            int metadata = worldObj.getBlockMetadata(x, y, z);
            items = id.getDrops(worldObj, x, y, z, metadata, 0);
            items.stream().filter(item -> item != null && item.stackSize > 0).forEach(item -> {
                MgDirection dir = getOrientation().opposite();
                float rx = dir.getOffsetX() + 0.5f;
                float ry = 0.5F;
                float rz = dir.getOffsetZ() + 0.5f;
                EntityItem entityItem = new EntityItem(worldObj,
                        xCoord + rx, yCoord + ry, zCoord + rz,
                        new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
                if (item.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                }
                entityItem.motionX = 0;
                entityItem.motionY = 0;
                entityItem.motionZ = 0;
                worldObj.spawnEntityInWorld(entityItem);
            });

            worldObj.setBlock(x, y, z, replace);
        }
    }

    private void setActive(boolean b) {
        active = b;
        sendUpdateToClient();
    }

    public boolean isActive() {
        return active;
    }

    private void export() {
        if (tank.getFluidAmount() > 0) {
            for (MgDirection d : MgDirection.VALID_DIRECTIONS) {
                TileEntity t = MgUtils.getTileEntity(this, d);
                if (t instanceof IFluidHandler) {
                    IFluidHandler f = (IFluidHandler) t;
                    if (f.canFill(d.toForgeDir(), FluidRegistry.getFluid("oil"))) {
                        int m = f.fill(d.toForgeDir(), tank.drain(100, false), true);
                        tank.drain(m, true);
                    }
                    if (tank.getFluidAmount() == 0)
                        break;
                }
            }
        }
    }

    @Override
    public int fillMg(MgDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drainMg_F(MgDirection from, FluidStack resource, boolean doDrain) {
        return drainMg(from, resource.amount, doDrain);
    }

    @Override
    public FluidStack drainMg(MgDirection from, int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFillMg(MgDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrainMg(MgDirection from, Fluid fluid) {
        return fluid.getName().equals("oil");
    }

    @Override
    public FluidTankInfo[] getTankInfoMg(MgDirection from) {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    public float getDelta(float partial) {
        float aux = time;
        time = (worldObj.getTotalWorldTime() + partial) * 50;
        return time - aux;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        tank.readFromNBT(nbt, "oil");
        buffer = nbt.getInteger("Buffer");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        tank.writeToNBT(nbt, "oil");
        nbt.setInteger("Buffer", buffer);
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        VecInt v1 = VecIntUtil.getRotatedOffset(getOrientation().opposite(), 0, 0, 1);
        VecInt v2 = VecIntUtil.getRotatedOffset(getOrientation(), 0, 3, 2);
        VecInt block = new VecInt(xCoord, yCoord, zCoord);

        return VecIntUtil.getAABBFromVectors(v1.add(block), v2.add(block));
    }

    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return this.fillMg(MgDirection.getDirection(from.ordinal()), resource, doFill);
    }

    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
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

    public int getConnections() {
        return -1;
    }
}
