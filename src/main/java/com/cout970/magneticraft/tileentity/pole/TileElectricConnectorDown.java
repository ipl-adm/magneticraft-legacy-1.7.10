package com.cout970.magneticraft.tileentity.pole;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricPole;
import com.cout970.magneticraft.api.electricity.ITileElectricPole;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.ConnectionClass;
import com.cout970.magneticraft.api.util.IConnectable;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import net.minecraft.tileentity.TileEntity;

public class TileElectricConnectorDown extends TileConductorLow implements ITileElectricPole {

    public int mask = -1;

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this) {
            @Override
            public VecInt[] getValidConnections() {
                return new VecInt[]{MgDirection.EAST.toVecInt(), MgDirection.WEST.toVecInt()};
            }

            @Override
            public boolean isAbleToConnect(IConnectable c, VecInt d) {
                return c.getConnectionClass(d.getOpposite()) == ConnectionClass.CABLE_LOW
                        || c.getConnectionClass(d.getOpposite()) == ConnectionClass.SLAB_BOTTOM
                        || c.getConnectionClass(d.getOpposite()) == ConnectionClass.FULL_BLOCK;
            }
        };
    }

    @Override
    public IElectricPole getPoleConnection() {
        return null;
    }

    @Override
    public ITileElectricPole getMainTile() {
        TileEntity pole = new VecInt(this).add(0, 1, 0).getTileEntity(getWorld());
        if (pole instanceof ITileElectricPole) return (ITileElectricPole) pole;
        return null;
    }
}
