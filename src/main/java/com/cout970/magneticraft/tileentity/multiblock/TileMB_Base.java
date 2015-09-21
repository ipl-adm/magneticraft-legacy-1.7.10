package com.cout970.magneticraft.tileentity.multiblock;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.util.multiblock.MB_Register;
import com.cout970.magneticraft.util.multiblock.MB_Tile;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class TileMB_Base extends TileBase implements MB_Tile {

    public VecInt control;
    public Multiblock multi;
    public MgDirection dire;

    @Override
    public void setControlPos(VecInt blockPosition) {
        control = blockPosition;
    }

    @Override
    public VecInt getControlPos() {
        return control;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        int[] i = nbt.getIntArray("core");
        if (i != null && i.length == 3)
            control = new VecInt(i);
        multi = MB_Register.getMBbyID(nbt.getInteger("multi"));
        dire = MgDirection.getDirection(nbt.getByte("Dire"));
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (control != null) {
            nbt.setIntArray("core", control.intArray());
        }
        if (multi != null) {
            nbt.setInteger("multi", multi.getID());
        }
        if (dire != null) {
            nbt.setByte("Dire", (byte) dire.ordinal());
        }
    }

    @Override
    public void onDestroy(World w, VecInt p, Multiblock c, MgDirection e) {
    }

    @Override
    public void onActivate(World w, VecInt p, Multiblock c, MgDirection e) {
    }

    @Override
    public Multiblock getMultiblock() {
        return multi;
    }

    @Override
    public void setMultiblock(Multiblock m) {
        multi = m;
    }

    @Override
    public void setDirection(MgDirection e) {
        dire = e;
    }

    @Override
    public MgDirection getDirection() {
        return dire;
    }
}
