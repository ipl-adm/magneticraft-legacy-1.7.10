package com.cout970.magneticraft.api.electricity;

import codechicken.multipart.TileMultipart;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

import java.util.List;
import java.util.stream.Collectors;

public class ElectricUtils {

    /**
     * Checks if a connection is already formed and should not repeat, used for Electric conductors connections
     *
     * @param con
     * @param opp
     * @return
     */
    public static boolean alreadyContains(IIndexedConnection[] con, EnumFacing opp) {
        if (con == null) return false;
        if (opp == null) return false;
        for (IIndexedConnection i : con)
            if (opp.equals(i.getOffset())) return true;
        return false;
    }

    /**
     * Return the CableCompound in a Block, allowing multipart detection
     *
     * @param tile
     * @param f
     * @param tier
     * @return
     */
    public static IElectricConductor[] getElectricCond(TileEntity tile, EnumFacing f, int tier) {
        if (tile instanceof TileMultipart) {
            List<IElectricConductor> list = ((TileMultipart) tile).jPartList().stream().filter(m -> m instanceof IElectricMultiPart && ((IElectricMultiPart) m).getElectricConductor(tier) != null).map(m -> ((IElectricMultiPart) m).getElectricConductor(tier)).collect(Collectors.toList());
            return list.toArray(new IElectricConductor[list.size()]);
        }
        if (tile instanceof IElectricTile) return ((IElectricTile) tile).getConds(f, tier);
        return null;
    }

    /**
     * Find a Interface between to energy systems like railcraft change or RF
     *
     * @param t
     * @param i
     * @param tier
     * @return
     */
    public static IEnergyInterface getInterface(TileEntity t, EnumFacing i, int tier) {
        return InteractionHelper.processTile(t, i, tier);
    }

    /**
     * checks if the tileEntity is a Conductor
     *
     * @param tile
     * @param tier
     * @return
     */
    public static boolean isConductor(TileEntity tile, int tier) {
        return getElectricCond(tile, null, tier) != null;
    }

    public static IElectricPole getElectricPole(TileEntity tile) {
        if (tile instanceof ITileElectricPole) {
            if (((ITileElectricPole) tile).getMainTile() == null) return null;
            if (((ITileElectricPole) tile).getMainTile() == tile) {
                return ((ITileElectricPole) tile).getPoleConnection();
            } else {
                return ((ITileElectricPole) tile).getMainTile().getPoleConnection();
            }
        }
        return null;
    }

}
