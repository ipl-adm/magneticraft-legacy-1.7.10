package com.cout970.magneticraft.parts.micro.wires;

import java.util.List;

import codechicken.lib.vec.Cuboid6;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.parts.micro.PartWireCopper;

public class PartWireCopper_South extends PartWireCopper{

	@Override
	public MgDirection getDirection() {
		return MgDirection.SOUTH;
	}

	@Override
	public List<Cuboid6> getBoxes() {
		return South_Boxes;
	}

	@Override
	public int getBoxBySide(MgDirection dir) {
		if(dir == MgDirection.DOWN)return 1;
		if(dir == MgDirection.UP)return 2;
		if(dir == MgDirection.WEST)return 3;
		if(dir == MgDirection.EAST)return 4;
		return 0;
	}
}