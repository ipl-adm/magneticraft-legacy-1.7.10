package com.cout970.magneticraft.tileentity.pole;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricPole;
import com.cout970.magneticraft.api.electricity.ITileElectricPole;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.ConnectionClass;
import com.cout970.magneticraft.api.util.IConnectable;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public class TileElectricPoleCableWireDown extends TileConductorLow implements ITileElectricPole {

    public int mask = -1;

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this) {
            @Override
            public EnumFacing[] getValidConnections() {
                return EnumFacing.HORIZONTALS;
            }

            @Override
            public boolean isAbleToConnect(IConnectable c, EnumFacing d) {
                return (d.ordinal() > 1)
                        && (c.getConnectionClass(d.getOpposite()) == ConnectionClass.CABLE_LOW
                        || c.getConnectionClass(d.getOpposite()) == ConnectionClass.SLAB_BOTTOM
                        || c.getConnectionClass(d.getOpposite()) == ConnectionClass.FULL_BLOCK);
            }
        };
    }

    @Override
    public IElectricPole getPoleConnection() {
        return null;
    }

    @Override
    public ITileElectricPole getMainTile() {
        TileEntity pole = worldObj.getTileEntity(pos.add(0, 4, 0));
        if (pole instanceof ITileElectricPole) return (ITileElectricPole) pole;
        return null;
    }
}
