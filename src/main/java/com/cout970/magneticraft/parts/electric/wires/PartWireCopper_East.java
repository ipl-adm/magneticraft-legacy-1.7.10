package com.cout970.magneticraft.parts.electric.wires;

import codechicken.lib.vec.Cuboid6;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.parts.electric.PartWireCopper;

import java.util.List;

public class PartWireCopper_East extends PartWireCopper {

    @Override
    public MgDirection getDirection() {
        return MgDirection.EAST;
    }

    @Override
    public List<Cuboid6> getBoxes() {
        return East_Boxes;
    }

    @Override
    public int getBoxBySide(MgDirection dir) {
        if (dir == MgDirection.DOWN) return 1;
        if (dir == MgDirection.UP) return 2;
        if (dir == MgDirection.NORTH) return 3;
        if (dir == MgDirection.SOUTH) return 4;
        return 0;
    }

}