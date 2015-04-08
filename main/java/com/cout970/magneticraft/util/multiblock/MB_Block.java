package com.cout970.magneticraft.util.multiblock;

import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;

public interface MB_Block{

	public void mutates(World w, BlockPosition blockPosition, Multiblock c, MgDirection e);

	public void destroy(World w, BlockPosition blockPosition, Multiblock c, MgDirection e);
	
}
