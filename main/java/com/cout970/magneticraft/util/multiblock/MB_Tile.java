package com.cout970.magneticraft.util.multiblock;

import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

public interface MB_Tile {

	public void setControlPos(VecInt blockPosition);
	public VecInt getControlPos();
	public void onDestroy(World w, VecInt p, Multiblock c, MgDirection e);
	public void onActivate(World w, VecInt p, Multiblock c, MgDirection e);
	public Multiblock getMultiblock();
	public void setMultiblock(Multiblock m);
	public void setDirection(MgDirection e);
	public MgDirection getDirection();
}
