package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.kinetic.IKineticConductor;
import com.cout970.magneticraft.api.kinetic.KineticConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.util.tile.TileKineticConductor;

public class TileWoodenShaft extends TileKineticConductor {


    @Override
    public MgDirection[] getValidSides() {
        return new MgDirection[]{getDirection(), getDirection().opposite()};
    }

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    @Override
    public IKineticConductor initKineticCond() {
        return new KineticConductor(this);
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            float f = (float) (kinetic.getRotation() + (kinetic.getSpeed() / 60) * kinetic.getDelta() / 1E6);
            if (f > 1000) f %= 1000;
            kinetic.setRotation(f);
        }
    }
}
