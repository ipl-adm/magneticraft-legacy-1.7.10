package com.cout970.magneticraft.api.electricity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public interface IInterPoleWire {

    void setWorld(World w);

    World getWorld();

    void iterate();

    double getEnergyFlow();

    void setEnergyFlow(double energyFlow);

    IElectricPole getStart();

    IElectricPole getEnd();

    BlockPos posStart();

    BlockPos posEnd();

    double getDistance();

    void save(NBTTagCompound nbt);

    void load(NBTTagCompound nbt);
}
