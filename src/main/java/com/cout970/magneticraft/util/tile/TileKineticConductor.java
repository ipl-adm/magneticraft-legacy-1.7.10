package com.cout970.magneticraft.util.tile;

import com.cout970.magneticraft.api.kinetic.IKineticConductor;
import com.cout970.magneticraft.api.kinetic.IKineticTile;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.tileentity.TileBase;

public abstract class TileKineticConductor extends TileBase implements IKineticTile {

    public IKineticConductor kinetic = initKineticCond();

    @Override
    public IKineticConductor getKineticConductor(MgDirection dir) {
        return MgUtils.contains(getValidSides(), dir) ? kinetic : null;
    }

    public abstract IKineticConductor initKineticCond();

    @Override
    public abstract MgDirection[] getValidSides();

    public void updateEntity() {
        super.updateEntity();
        kinetic.iterate();
        if (worldObj.isRemote) {
            float f = (float) (this.kinetic.getRotation() + (this.kinetic.getSpeed() / 60) * kinetic.getDelta() / 1E6);
            if (f > 1000) f %= 1000;
            this.kinetic.setRotation(f);
        }
    }
}
