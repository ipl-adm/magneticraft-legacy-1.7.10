package com.cout970.magneticraft.util.multiblock;

import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

public interface MB_Block{

	public void mutates(World w, VecInt blockPosition, Multiblock c, MgDirection e);

	public void destroy(World w, VecInt blockPosition, Multiblock c, MgDirection e);
	
}
