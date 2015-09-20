package com.cout970.magneticraft.api.conveyor;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.List;

public interface IConveyorBeltLane {

    IConveyorBelt getConveyorBelt();

    List<IItemBox> getItemBoxes();

    IHitBoxArray getHitBoxes();

    boolean isOnLeft();

    void setHitBoxSpace(int pos, boolean value);

    void setHitBoxSpaceExtern(TileEntity tile, int pos, boolean value);

    boolean hasHitBoxSpace(int pos);

    boolean hasHitBoxSpaceExtern(TileEntity tile, int pos);

    void save(NBTTagCompound nbt);

    void load(NBTTagCompound nbt);
}
