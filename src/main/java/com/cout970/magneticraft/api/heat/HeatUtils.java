package com.cout970.magneticraft.api.heat;

import codechicken.multipart.TileMultipart;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.tileentity.TileEntity;

import java.util.List;
import java.util.stream.Collectors;

public class HeatUtils {

    /**
     * Created to implement ForgeMultipart HeatConductors in the future
     *
     * @param tile tile entity to get the conductor
     * @param d    vector from the method caller
     * @return the coductor is exist
     */
    public static IHeatConductor[] getHeatCond(TileEntity tile, VecInt d) {
        if (tile instanceof IHeatTile) return ((IHeatTile) tile).getHeatCond(d.getOpposite());
        if (tile instanceof TileMultipart) {
            List<IHeatConductor> comp = ((TileMultipart) tile).jPartList().stream().filter(m -> m instanceof IHeatMultipart).map(m -> ((IHeatMultipart) m).getHeatConductor()).collect(Collectors.toList());
            return comp.toArray(new IHeatConductor[comp.size()]);
        }
        return null;
    }

}
