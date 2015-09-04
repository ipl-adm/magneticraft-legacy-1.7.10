package com.cout970.magneticraft.parts.electric.wires;

import codechicken.lib.vec.Cuboid6;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.parts.electric.PartWireCopper;

import java.util.List;

public class PartWireCopper_Up extends PartWireCopper {

    @Override
    public MgDirection getDirection() {
        return MgDirection.UP;
    }

    @Override
    public List<Cuboid6> getBoxes() {
        return Up_Boxes;
    }

    @Override
    public int getBoxBySide(MgDirection dir) {
        if (dir == MgDirection.NORTH) return 1;
        if (dir == MgDirection.SOUTH) return 2;
        if (dir == MgDirection.WEST) return 3;
        if (dir == MgDirection.EAST) return 4;
        return 0;
    }

}