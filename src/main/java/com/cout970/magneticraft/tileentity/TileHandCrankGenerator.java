package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.kinetic.IKineticConductor;
import com.cout970.magneticraft.api.kinetic.KineticGenerator;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.util.tile.TileKineticConductor;

public class TileHandCrankGenerator extends TileKineticConductor {

    public int tickCounter;
    private long time;
    private boolean work;

    @Override
    public IKineticConductor initKineticCond() {
        return new KineticGenerator(this);
    }

    @Override
    public MgDirection[] getValidSides() {
        return new MgDirection[]{getDirection()};
    }

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    public void updateEntity() {
        super.updateEntity();
        if (tickCounter > 0) {
            tickCounter--;
            kinetic.getNetwork().applyForce(1000);
            work = true;
        } else {
            if (work) {
                work = false;
                kinetic.getNetwork().stop(kinetic);
            }
        }
        if (worldObj.isRemote) {
            float f = (float) (kinetic.getRotation() + (kinetic.getSpeed() / 60) * kinetic.getDelta() / 1E6);
            if (f > 1000) f %= 1000;
            kinetic.setRotation(f);
        }
    }

    public float getDelta() {
        long aux = time;
        time = System.nanoTime();
        return time - aux;
    }
}
