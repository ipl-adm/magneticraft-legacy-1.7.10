package com.cout970.magneticraft.api.electricity;

import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public interface IInterPoleWire {

    World getWorld();

    void setWorld(World w);

    void iterate();

    double getEnergyFlow();

    void setEnergyFlow(double energyFlow);

    IElectricPole getStart();

    IElectricPole getEnd();

    VecInt vecStart();

    VecInt vecEnd();

    double getDistance();

    void save(NBTTagCompound nbt);

    void load(NBTTagCompound nbt);
}
