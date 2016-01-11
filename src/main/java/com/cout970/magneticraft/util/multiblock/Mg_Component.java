package com.cout970.magneticraft.util.multiblock;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class Mg_Component {

    public abstract boolean isCorrect(World w, BlockPos p, int x, int y, int z, Multiblock c, EnumFacing e, int meta);

    public abstract void establish(World w, BlockPos p, int x, int y, int z, Multiblock c, EnumFacing e, int meta);

    public abstract void destroy(World w, BlockPos p, int x, int y, int z, Multiblock c, EnumFacing e, int meta);

    public abstract String getErrorMessage(World w, BlockPos p, int x, int y, int z, Multiblock c, EnumFacing e, int meta);
}
