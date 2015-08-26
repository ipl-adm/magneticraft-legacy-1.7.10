package com.cout970.magneticraft.util.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTank;

public class TankMg extends FluidTank {

    public TankMg(TileEntity t, int capacity) {
        super(null, capacity);
        tile = t;
    }

    public void readFromNBT(NBTTagCompound main, String string) {
        NBTTagCompound nbt = (NBTTagCompound) main.getTag(string);
        this.readFromNBT(nbt);
    }

    public void writeToNBT(NBTTagCompound main, String string) {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        main.setTag(string, nbt);
    }

    public int getSpace() {
        return getCapacity() - getFluidAmount();
    }

    public TileEntity getParent() {
        return tile;
    }
}
