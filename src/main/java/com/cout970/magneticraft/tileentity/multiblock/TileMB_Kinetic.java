package com.cout970.magneticraft.tileentity.multiblock;

import com.cout970.magneticraft.api.kinetic.IKineticConductor;
import com.cout970.magneticraft.api.kinetic.IKineticTile;
import com.cout970.magneticraft.api.kinetic.KineticConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;

public class TileMB_Kinetic extends TileMB_Base implements IKineticTile {

    public IKineticConductor kinetic = initKineticCond();

    public IKineticConductor initKineticCond() {
        return new KineticConductor(this, 10, 10);
    }

    @Override
    public IKineticConductor getKineticConductor(MgDirection dir) {
        return MgUtils.contains(getValidSides(), dir) ? kinetic : null;
    }

    public void updateEntity() {
        super.updateEntity();
        kinetic.iterate();
        if (worldObj.isRemote) {
            float f = (float) (this.kinetic.getRotation() + (this.kinetic.getSpeed() / 60) * kinetic.getDelta() / 1E6);
            if (f > 1000) f %= 1000;
            this.kinetic.setRotation(f);
        }
    }

    @Override
    public MgDirection[] getValidSides() {
        return MgDirection.values();
    }

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }
}
