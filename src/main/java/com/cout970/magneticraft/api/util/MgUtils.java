package com.cout970.magneticraft.api.util;

import buildcraft.api.tools.IToolWrench;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import cofh.api.item.IToolHammer;
import com.cout970.magneticraft.api.tool.IWrench;
import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.util.FakePlayerProvider;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.IntStream;

/**
 * @author Cout970
 */
public class MgUtils {
    private MgUtils() {
    }

    /**
     * Useful method to get an adjacent TileEntity
     *
     * @param tile
     * @param d
     * @return
     */
    public static TileEntity getTileEntity(TileEntity tile, EnumFacing d) {
        if (tile.getWorld().getBlockState(tile.getPos().add(d.getDirectionVec())).getBlock() != null) {
            return tile.getWorld().getTileEntity(tile.getPos().add(d.getDirectionVec()));
        }
        return null;
    }

    /**
     * Return the TileEntities adjacent to a Blocks
     *
     * @param t
     * @return
     */
    public static List<TileEntity> getNeig(TileEntity t) {
        List<TileEntity> list = new ArrayList<>();
        for (EnumFacing d : EnumFacing.values()) {
            TileEntity f = getTileEntity(t, d);
            if (f != null) list.add(f);
        }
        return list;
    }

    /**
     * checks if specified Block can be mined by a miner or by a BlockBreaker
     *
     * @param w
     * @param info
     * @return
     */
    public static boolean isMineableBlock(World w, BlockInfo info) {
        return (info.getBlock() != Blocks.air)
                && !(info.getBlock() instanceof BlockLiquid)
                && !(info.getBlock() instanceof BlockFluidBase)
                && !Block.isEqualTo(info.getBlock(), Blocks.mob_spawner)
                && (info.getBlock() != Blocks.portal) && (info.getBlock() != Blocks.end_portal)
                && (info.getBlock() != Blocks.end_portal_frame)
                && (info.getBlock().getBlockHardness(w, info.getPosition()) >= 0)
                && (w.canMineBlockBody(FakePlayerProvider.getFakePlayer((WorldServer) w), info.getPosition()));
    }

    /**
     * Checks if two fluidStacks are equal
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean areEqual(FluidStack a, FluidStack b) {
        return ((a == null) && (b == null))
                || (!(a == null || b == null)
                && FluidRegistry.getFluidName(a).equalsIgnoreCase(FluidRegistry.getFluidName(b)));
    }

    /**
     * checks if two itemStacks are equal or have the same id in OreDictionary
     *
     * @param a
     * @param b
     * @param meta
     * @return
     */
    public static boolean areEqual(ItemStack a, ItemStack b, boolean meta) {
        if (a == null && b == null) return true;
        if (a != null && b != null && a.getItem() != null && b.getItem() != null) {
            if (OreDictionary.itemMatches(a, b, meta)) return true;
            int[] c = OreDictionary.getOreIDs(a);
            int[] d = OreDictionary.getOreIDs(b);
            if (c.length > 0 && d.length > 0) {
                for (int i : c) {
                    for (int j : d)
                        if (i == j) return true;
                }
            }
        }
        return false;
    }

    public static boolean contains(EnumFacing[] vec, EnumFacing d) {
        for (EnumFacing dir : vec) {
            if (dir == d) return true;
        }
        return false;
    }
/*
    public static IOpticFiber getOpticFiber(TileEntity tile, EnumFacing dir) {
        if (tile instanceof TileMultipart) {
            for (TMultiPart p : ((TileMultipart) tile).jPartList()) {
                if (p instanceof IOpticFiber) {
                    return (IOpticFiber) p;
                }
            }
        }
        return null;
    }
*/
    public static boolean isWrench(ItemStack is) {
        return (is != null) && isWrench(is.getItem());
    }

    public static boolean isWrench(Item item) {
        return (item instanceof IWrench) ||
                (ManagerIntegration.BUILDCRAFT && (item instanceof IToolWrench)) ||
                (ManagerIntegration.COFH_TOOLS && (item instanceof IToolHammer));
    }

    public static boolean matchesPattern(ItemStack stack, String pattern) {
        if (stack == null) {
            return pattern.isEmpty();
        }

        String realPattern = ".*(?i:" + pattern + ").*";
        try {
            //noinspection ResultOfMethodCallIgnored
            Pattern.compile(realPattern);
        } catch (PatternSyntaxException e) {
            return false;
        }

        if (StringUtils.isNullOrEmpty(pattern)) {
            return true;
        }
        if (stack.getDisplayName().matches(realPattern)) {
            return true;
        }
        if (IntStream.of(OreDictionary.getOreIDs(stack)).mapToObj(OreDictionary::getOreName).anyMatch(s -> s.matches(realPattern))) {
            return true;
        }
        return false;
    }

    //I feel like there's a better way, but fuck it.
    public static boolean isUnobstructed2D(World w, BlockPos start, BlockPos end, boolean checkStart, boolean checkEnd, int precision) {
        int height = start.getY();
        if (Objects.equals(start.getX(), end.getX())) {
            if (Objects.equals(start.getZ(), end.getZ())) {
                return !((checkEnd || checkStart) && (w.getBlockState(start).getBlock() != null && !w.isAirBlock(start))); //oh god
            }

            int x = start.getX();
            int startZ = Math.min(start.getZ(), end.getZ());
            int endZ = Math.max(start.getZ(), end.getZ());
            for (int z = startZ; z <= endZ; z++) {

                if (!checkStart && ((x == start.getX()) && (z == start.getZ()))) {
                    continue;
                }

                if (!checkEnd && ((x == end.getX()) && (z == end.getZ()))) {
                    continue;
                }

                BlockPos pos = new BlockPos(x, height, z);
                Block b = w.getBlockState(pos).getBlock();
                if (b != null && !w.isAirBlock(pos)) {
                    return false;
                }
            }
            return true;
        }

        final double x1 = start.getX(),
                z1 = start.getZ(),
                x2 = end.getX(),
                z2 = end.getZ();
        Function<Double, Double> lineFunction = (x -> ((z2 - z1) / (x2 - x1) * (x - x1) + z1));
        boolean isPositive = ((z2 - z1) / (x2 - x1)) > 0;

        int startX = Math.min(start.getX(), end.getX());
        int endX = Math.max(start.getX(), end.getX());

        double step = 1d / precision;

        for (double dx = startX; Math.round(dx) <= endX; dx += step) {
            double dz = lineFunction.apply(.0 + dx);
            int cz = (int) Math.round(dz);
            int cx = (int) Math.round(dx);

            //check if corner, shitcode incoming
            if ((Math.abs(dx - Math.floor(dx) - 0.5) < 1e-8) && (Math.abs(dz - Math.floor(dz) - 0.5) < 1e-8)) {
                BlockPos pos1 = new BlockPos((int) Math.floor(dx), height, (int) Math.floor(dz));
                BlockPos pos2 = new BlockPos((int) Math.floor(dx), height, (int) Math.ceil(dz));
                BlockPos pos3 = new BlockPos((int) Math.ceil(dx), height, (int) Math.floor(dz));
                BlockPos pos4 = new BlockPos((int) Math.ceil(dx), height, (int) Math.ceil(dz));
                Block b1 = w.getBlockState(pos1).getBlock();
                Block b2 = w.getBlockState(pos2).getBlock();
                Block b3 = w.getBlockState(pos3).getBlock();
                Block b4 = w.getBlockState(pos4).getBlock();
                if (isPositive) {
                    if (b2 != null && !w.isAirBlock(pos2) && (b3 != null && !w.isAirBlock(pos3))) {
                        return false;
                    }
                } else {
                    if (b1 != null && !w.isAirBlock(pos1) && (b4 != null && !w.isAirBlock(pos4))) {
                        return false;
                    }
                }
            }

            if (!checkStart && ((cx == start.getX()) && (cz == start.getZ()))) {
                continue;
            }

            if (!checkEnd && ((cx == end.getX()) && (cz == end.getZ()))) {
                continue;
            }

            BlockPos pos = new BlockPos(cx, height, cz);
            Block b = w.getBlockState(pos).getBlock();
            if (b != null && !w.isAirBlock(pos)) {
                return false;
            }
        }
        return true;
    }

    public static void writePos(NBTTagCompound nbt, String name, BlockPos pos) {
        nbt.setInteger(name + "_x", pos.getX());
        nbt.setInteger(name + "_y", pos.getY());
        nbt.setInteger(name + "_z", pos.getZ());
    }

    public static BlockPos readPos(NBTTagCompound nbt, String name) {
        return new BlockPos(nbt.getInteger(name + "_x"), nbt.getInteger(name + "_y"), nbt.getInteger(name + "_z"));
    }
}
