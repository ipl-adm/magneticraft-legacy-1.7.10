package com.cout970.magneticraft.util.multiblock;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

public abstract class Mg_Component {

	public abstract boolean isCorrect(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta);

	public abstract void establish(World w, VecInt p, int x, int y, int z,Multiblock c, MgDirection e, int meta);

	public abstract void destroy(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta);

	public abstract String getErrorMesage(World w, VecInt p, int x, int y, int z, Multiblock c, MgDirection e, int meta);
}
