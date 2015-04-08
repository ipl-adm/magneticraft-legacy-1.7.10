package com.cout970.magneticraft.util.multiblock;

import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;

public interface MB_ControlBlock {

	public MgDirection getDirection(World w,BlockPosition p);
}
