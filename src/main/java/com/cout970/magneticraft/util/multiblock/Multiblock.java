package com.cout970.magneticraft.util.multiblock;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public abstract class Multiblock {

    public int x, y, z;//lengths
    public BlockPos tran;
    public Mg_Component[][][] matrix;

    public abstract void init();


    public int[] getDimensions(EnumFacing e) {
        return new int[]{x, y, z};
    }

    public abstract int getID();
}
