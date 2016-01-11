package com.cout970.magneticraft.api.util;

import buildcraft.api.tools.IToolWrench;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import cofh.api.item.IToolHammer;
import com.cout970.magneticraft.api.computer.IOpticFiber;
import com.cout970.magneticraft.api.tool.IWrench;
import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.util.FakePlayerProvider;
import com.google.common.primitives.Doubles;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
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
    public static TileEntity getTileEntity(TileEntity tile, VecInt d) {
        if (!tile.getWorldObj().blockExists(tile.xCoord + d.getX(), tile.yCoord + d.getY(), tile.zCoord + d.getZ())) {
            return null;
        }
        return tile.getWorldObj().getTileEntity(tile.xCoord + d.getX(), tile.yCoord + d.getY(), tile.zCoord + d.getZ());
    }

    /**
     * Useful method to get an adjacent TileEntity
     *
     * @param tile
     * @param d
     * @return
     */
    public static TileEntity getTileEntity(TileEntity tile, MgDirection d) {
        if (!tile.getWorldObj().blockExists(tile.xCoord + d.getOffsetX(), tile.yCoord + d.getOffsetY(), tile.zCoord + d.getOffsetZ())) {
            return null;
        }
        return tile.getWorldObj().getTileEntity(tile.xCoord + d.getOffsetX(), tile.yCoord + d.getOffsetY(), tile.zCoord + d.getOffsetZ());
    }

    /**
     * Return the TileEntities adjacent to a Blocks
     *
     * @param t
     * @return
     */
    public static List<TileEntity> getNeig(TileEntity t) {
        List<TileEntity> list = new ArrayList<>();
        for (MgDirection d : MgDirection.values()) {
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
                && (w.canMineBlock(FakePlayerProvider.getFakePlayer((WorldServer) w), info.getX(), info.getY(), info.getZ()));
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

    public static boolean isWrench(ItemStack is) {
        return (is != null) && isWrench(is.getItem());
    }

    public static boolean isWrench(Item item) {
        return (item instanceof IWrench) || (ManagerIntegration.BUILDCRAFT && (item instanceof IToolWrench)) || (ManagerIntegration.COFH_TOOLS && (item instanceof IToolHammer));
    }

    public static boolean matchesPattern(ItemStack stack, String pattern) {
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
        if ((stack != null) && (stack.getDisplayName().matches(realPattern))) {
            return true;
        }
        if (IntStream.of(OreDictionary.getOreIDs(stack)).mapToObj(OreDictionary::getOreName).anyMatch(s -> s.matches(realPattern))) {
            return true;
        }
        return false;
    }

    //I feel like there's a better way, but fuck it.
    public static boolean isUnobstructed2D(World w, Pair<Integer, Integer> start, Pair<Integer, Integer> end, int height, boolean checkStart, boolean checkEnd, int precision) {
        if (Objects.equals(start.getLeft(), end.getLeft())) {
            if (Objects.equals(start.getRight(), end.getRight())) {
                return !((checkEnd || checkStart) && (w.getBlock(start.getLeft(), height, start.getRight()) != null && !w.isAirBlock(start.getLeft(), height, start.getRight()))); //oh god
            }

            int x = start.getLeft();
            int startZ = Math.min(start.getRight(), end.getRight());
            int endZ = Math.max(start.getRight(), end.getRight());
            for (int z = startZ; z <= endZ; z++) {

                if (!checkStart && ((x == start.getLeft()) && (z == start.getRight()))) {
                    continue;
                }

                if (!checkEnd && ((x == end.getLeft()) && (z == end.getRight()))) {
                    continue;
                }

                Block b = w.getBlock(x, height, z);
                if (b != null && !w.isAirBlock(x, height, z)) {
                    return false;
                }
            }
            return true;
        }

        final double x1 = start.getLeft(),
                z1 = start.getRight(),
                x2 = end.getLeft(),
                z2 = end.getRight();
        Function<Double, Double> lineFunction = (x -> ((z2 - z1) / (x2 - x1) * (x - x1) + z1));
        boolean isPositive = ((z2 - z1) / (x2 - x1)) > 0;

        int startX = Math.min(start.getLeft(), end.getLeft());
        int endX = Math.max(start.getLeft(), end.getLeft());

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

            if (!checkStart && ((cx == start.getLeft()) && (cz == start.getRight()))) {
                continue;
            }

            if (!checkEnd && ((cx == end.getLeft()) && (cz == end.getRight()))) {
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

}
