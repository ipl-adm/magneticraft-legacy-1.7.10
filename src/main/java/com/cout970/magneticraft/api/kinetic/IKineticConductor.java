package com.cout970.magneticraft.api.kinetic;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

public interface IKineticConductor {

    double getWork();

    double getLose();

    void setLose(double lose);

    double getMass();

    void setMass(double mass);

    double getSpeed();

    void setSpeed(double speed);

    float getRotation();

    void setRotation(float angle);

    double getDelta();

    void iterate();

    KineticNetwork getNetwork();

    void setNetwork(KineticNetwork net);

    TileEntity getParent();

    KineticType getFunction();

    EnumFacing[] getValidSides();
}
