package com.cout970.magneticraft.util.multiblock;

import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;

public interface MB_Tile {

	public void setControlPos(BlockPosition blockPosition);
	public BlockPosition getControlPos();
	public void onDestroy(World w, BlockPosition p, Multiblock c, MgDirection e);
	public void onActivate(World w, BlockPosition p, Multiblock c, MgDirection e);
	public Multiblock getMultiblock();
	public void setMultiblock(Multiblock m);
	public void setDirection(MgDirection e);
	public MgDirection getDirection();
}
