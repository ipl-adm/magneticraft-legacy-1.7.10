package com.cout970.magneticraft.util.multiblock;

import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

public interface MB_ControlBlock extends MB_Block{

	public MgDirection getDirection(World w, VecInt p);
	public Multiblock getStructure();
}
