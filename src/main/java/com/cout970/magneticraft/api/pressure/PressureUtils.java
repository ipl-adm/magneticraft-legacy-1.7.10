package com.cout970.magneticraft.api.pressure;

import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PressureUtils {

    public static IExplodable getExplodable(World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileMultipart) {
            TileMultipart mp = (TileMultipart) tile;
            for (TMultiPart part : mp.jPartList()) {
                if (part instanceof IExplodable) {
                    return (IExplodable) part;
                }
            }
        }
        if (tile instanceof IExplodable) {
            return (IExplodable) tile;
        }
        Block b = world.getBlockState(pos).getBlock();
        if (b instanceof IExplodable) {
            return (IExplodable) b;
        }
        return null;
    }

    public static List<IPressureConductor> getPressureCond(TileEntity tile, EnumFacing f) {
        List<IPressureConductor> conds = new ArrayList<>();
        if (tile instanceof IPressurePipe) {
            for (IPressureConductor con : ((IPressurePipe) tile).getPressureConductor()) {
                if (con != null) {
                    conds.add(con);
                }
            }
        }
        if (tile instanceof TileMultipart) {
            conds.addAll(((TileMultipart) tile).jPartList().stream().filter(part -> part instanceof IPressureMultipart).filter(part -> ((IPressureMultipart) part).getPressureConductor() != null).map(part -> ((IPressureMultipart) part).getPressureConductor()).collect(Collectors.toList()));
        }
        return conds;
    }
}
