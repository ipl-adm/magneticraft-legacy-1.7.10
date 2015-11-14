package com.cout970.magneticraft.util.fluid;


import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.ArrayList;
import java.util.List;

public class FluidUtils {

    public static void onBlockAdded(World w, int x, int y, int z) {
        if (w.isRemote) return;
        if (!(w.getTileEntity(x, y, z) instanceof IFluidTransport)) return;
        IFluidTransport te = (IFluidTransport) w.getTileEntity(x, y, z);
        boolean hasNetwork = false;
        for (MgDirection dir : MgDirection.values()) {
            TileEntity e = MgUtils.getTileEntity(te.getTileEntity(), dir);
            if (e instanceof IFluidTransport) {
                if (((IFluidTransport) e).getNetwork() != null) {
                    if (!hasNetwork) {
                        te.setNetwork(((IFluidTransport) e).getNetwork());
                        te.getNetwork().getPipes().add(te);
                        hasNetwork = true;
                    } else {
                        te.getNetwork().mergeWith(((IFluidTransport) e).getNetwork());
                    }
                }

            }
        }
        if (!hasNetwork) {
            te.setNetwork(FluidNetwork.create(te, w.getTileEntity(x, y, z)));
        }
        te.getNetwork().refresh();
    }

    public static void onBlockPreDestroy(World w, int x, int y, int z, int meta) {
        if (w.isRemote) return;
        TileEntity te = w.getTileEntity(x, y, z);
        if (te instanceof IFluidTransport) {
            if (((IFluidTransport) te).getNetwork() != null) {
                ((IFluidTransport) te).getNetwork().excludeAndRecalculate((IFluidTransport) te);
            }
        }
    }

    private static boolean isConnectable(TileEntity a) {
        return a instanceof IFluidTransport || a instanceof IFluidHandler;
    }

    public static boolean canPassFluid(TileEntity a, TileEntity b) {

        return isConnectable(b) && isConnectable(a);
    }

    public static List<TankConnection> getTankConnections(TileEntity tile) {
        List<TankConnection> t = new ArrayList<>();
        for (MgDirection d : MgDirection.values()) {
            TileEntity y = MgUtils.getTileEntity(tile, d);
            if (y instanceof IFluidHandler) {
                t.add(new TankConnection((IFluidHandler) y, d.opposite()));
            }
        }
        return t;
    }

    public static boolean isPipe(TileEntity g) {
        if (g instanceof TileMultipart) {
            TileMultipart mp = (TileMultipart) g;
            for (TMultiPart p : mp.jPartList()) {
                if (p instanceof IFluidTransport) return true;
            }
        }
        return g instanceof IFluidTransport;
    }

    public static IFluidTransport getFluidTransport(TileEntity e) {
        if (e instanceof IFluidTransport) return (IFluidTransport) e;
        if (e instanceof TileMultipart) {
            TileMultipart mp = (TileMultipart) e;
            for (TMultiPart p : mp.jPartList()) {
                if (p instanceof IFluidTransport) return (IFluidTransport) p;
            }
        }
        return null;
    }
}
