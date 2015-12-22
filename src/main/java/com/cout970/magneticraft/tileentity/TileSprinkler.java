package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.block.BlockSprinkler;
import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.entity.LiquidSprayFX;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.fluid.TankMg;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.IGrowable;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

public class TileSprinkler extends TileBase implements IFluidHandler1_8 {
    public static Map<Fluid, FertilizerInfo> fertilizers = new HashMap<>();
    public static int
            MAX_RADIUS = 3,
            MAX_HEIGHT = 7,
            MAX_DIAMETER = 2 * MAX_RADIUS + 1,
            MAX_AREA = MAX_DIAMETER * MAX_DIAMETER,
            CONSUMPTION = 1,
            MAX_CONSUMPTION = MAX_AREA * CONSUMPTION,
            PRECISION = (MAX_RADIUS == 0) ? 1 : 1 << (int) (Math.ceil(Math.log(MAX_RADIUS) / Math.log(2) + 1)); //raytracing precision

    static {
        fertilizers.put(FluidRegistry.WATER, new FertilizerInfo(1e-4, true, -1, 0));
        if (ManagerIntegration.MFR) {
            fertilizers.put(FluidRegistry.getFluid("sewage"), new FertilizerInfo(1e-2, false, -1, 0));
        }
    }

    private Map<Pair<Integer, Integer>, Integer>
            heightmapPlants = new HashMap<>(),
            heightmapFarms = new HashMap<>();
    private TankMg tank = new TankMg(this, MAX_CONSUMPTION);

    private boolean shouldRegen = true;
    private Fluid lastFluid = null;
    private int lastAmount = 0;
    private float lastRotation = 0;
    private float lastRender = 0;

    public static int getRadius(int amount) {
        int ret = amount / CONSUMPTION;
        return (int) Math.floor(Math.sqrt(ret) / 2d - 0.5d);
    }

    public MgDirection getDir() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    @Override
    public void updateEntity() {
        super.updateEntity();

        if (worldObj.isRemote) {
            if ((lastAmount > 0) && (worldObj.getTotalWorldTime() % 2 == 0)) {
                int lvl = Minecraft.getMinecraft().gameSettings.particleSetting;
                for (int direction = 0; direction < 8; direction += (lvl > 0) ? 2 : 1) {
                    for (int power = (lvl == 2) ? Math.max(1, getRadius(lastAmount)) : 1; power <= getRadius(lastAmount); power++) {
                        double angle = lastRotation + Math.PI * direction / 4;
                        Vec3 v = Vec3.createVectorHelper((0.05 + 0.20 * power) * Math.cos(angle), (blockMetadata == 1) ? 0 : 0, (0.05 + 0.20 * power) * Math.sin(angle));
                        LiquidSprayFX fx = new LiquidSprayFX(worldObj, xCoord + 0.5, yCoord + 0.4 + 0.2 * blockMetadata, zCoord + 0.5, 0.5f, 1f, v, lastFluid);
                        Minecraft.getMinecraft().effectRenderer.addEffect(fx);
                    }
                }
            }
            return;
        }

        if (heightmapPlants.isEmpty() || heightmapFarms.isEmpty()) {
            genHeightMap();
        }
        if (tank.getFluidAmount() > 0) {
            sprinkle(tank.getFluid());

        }

        updateFluid(tank.getFluid());

        tank.setFluid(null);
        if (shouldRegen || ((worldObj.getTotalWorldTime() % 20) == 0)) {
            heightmapPlants.clear();
            heightmapFarms.clear();
            shouldRegen = false;
        }
    }

    private void updateFluid(FluidStack fluid) {
        Fluid f = (fluid == null) ? null : fluid.getFluid();
        int amount = (fluid == null) ? 0 : fluid.amount;
        boolean shouldUpdate = false;

        if (!Objects.equals(f, lastFluid)) {
            shouldUpdate = true;
            lastFluid = f;
        }
        if (lastAmount != amount) {
            shouldUpdate = true;
            lastAmount = amount;
        }

        if (shouldUpdate) {
            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public int getActualY() {
        return yCoord + 1 - getBlockMetadata();
    }

    private void genHeightMap() {
        Set<Pair<Integer, Integer>> toCheck = new HashSet<>();
        Pair<Integer, Integer> sprinkler = Pair.of(xCoord, zCoord);
        for (int x = xCoord - MAX_RADIUS; x <= xCoord + MAX_RADIUS; x++) {
            for (int z = zCoord - MAX_RADIUS; z <= zCoord + MAX_RADIUS; z++) {
                if (MgUtils.isUnobstructed2D(worldObj, sprinkler, Pair.of(x, z), getActualY(), getBlockMetadata() == 0, false, PRECISION)) {
                    toCheck.add(Pair.of(x, z));
                }
            }
        }

        for (Pair<Integer, Integer> p : toCheck) {
            int x = p.getLeft(), z = p.getRight();
            for (int y = getActualY(); y >= getActualY() - MAX_HEIGHT; y--) {
                Block b = worldObj.getBlock(x, y, z);
                if (b instanceof BlockSprinkler) {
                    continue;
                }
                if ((b instanceof IGrowable) && (b instanceof IPlantable)) {
                    heightmapPlants.put(Pair.of(x, z), y);
                } else if (b instanceof BlockFarmland) {
                    heightmapFarms.put(Pair.of(x, z), y);
                    break;
                } else if (b != null && !worldObj.isAirBlock(x, y, z)) {
                    break;
                }
            }
        }
    }

    private void sprinkle(FluidStack fluid) {
        int radius = getRadius(fluid.amount);
        FertilizerInfo info = fertilizers.get(fluid.getFluid());

        for (int x = xCoord - radius; x <= xCoord + radius; x++) {
            for (int z = zCoord - radius; z <= zCoord + radius; z++) {
                Pair<Integer, Integer> coords = Pair.of(x, z);
                if (heightmapFarms.containsKey(coords)) {
                    int y1 = heightmapFarms.get(coords);
                    if (worldObj.getBlock(x, y1, z) instanceof BlockFarmland && info.doesHydrate()) {
                        int old = worldObj.getBlockMetadata(x, y1, z);
                        worldObj.setBlockMetadataWithNotify(x, y1, z, 7, (old == 0) ? 6 : 2); //prevents constant rendering if it's already wet
                    } else {
                        shouldRegen = true;
                    }
                }


                if (heightmapPlants.containsKey(coords)) {
                    int y2 = heightmapPlants.get(coords);

                    Block crop = worldObj.getBlock(x, y2, z);
                    if ((crop instanceof IGrowable) && (crop instanceof IPlantable)) {
                        if (((IGrowable) crop).func_149851_a(worldObj, x, y2, z, worldObj.isRemote) || ((IGrowable) crop).func_149852_a(worldObj, worldObj.rand, x, y2, z)) {
                            if (worldObj.rand.nextDouble() < info.getChance()) { //if can bonemeal and random is good
                                ((IGrowable) crop).func_149853_b(worldObj, worldObj.rand, x, y2, z);
                            }
                        }
                    } else {
                        shouldRegen = true;
                    }
                }
            }
        }
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (!canFill(from, resource.getFluid())) {
            return 0;
        }
        return tank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return fertilizers.containsKey(fluid) && from.equals(getDir().toForgeDir());
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    public float rotate(float time) {
        float diff = time - lastRender;
        lastRender = time;
        lastRotation = (lastRotation + lastAmount * diff) % 1000;
        return lastRotation;
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("lastAmount", lastAmount);
        if (lastFluid != null) {
            nbt.setInteger("fluidID", lastFluid.getID());
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        lastAmount = nbt.getInteger("lastAmount");
        if (nbt.hasKey("fluidID")) {
            lastFluid = FluidRegistry.getFluid(nbt.getInteger("fluidID"));
        } else {
            lastFluid = null;
        }
    }

    public static class FertilizerInfo {
        private final double chance;
        private final boolean doesHydrate;
        private final int potionID;
        private final int potionLength;

        public FertilizerInfo(double bonemealChance, boolean doesHydrate, int potionID, int potionLength) {
            chance = bonemealChance;
            this.doesHydrate = doesHydrate;
            this.potionID = potionID;
            this.potionLength = potionLength;
        }

        public double getChance() {
            return chance;
        }

        public boolean doesHydrate() {
            return doesHydrate;
        }

        public int getPotionID() {
            return potionID;
        }

        public boolean hasPotionEffect() {
            return potionID != -1;
        }

        public int getPotionLength() {
            return potionLength;
        }
    }
}
