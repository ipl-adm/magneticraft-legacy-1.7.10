package com.cout970.magneticraft.parts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.computer.IOpticFiber;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.client.tilerender.TileRenderOpticFiber;

import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;

public class PartOpticFiber extends MgPart implements IOpticFiber {

    public TileRenderOpticFiber renderer;
    public static List<Cuboid6> boxes = new ArrayList<>();
    public int connections;

    static {
        double p = 1 / 16d;
        boxes.clear();
        boxes.add(new Cuboid6(1 - 5 * p, 0, 1 - 5 * p, 1 - 3 * p, 3 * p, 1 - 3 * p));//down
        boxes.add(new Cuboid6(1 - 5 * p, 5 * p, 1 - 5 * p, 1 - 3 * p, 1, 1 - 3 * p));//up
        boxes.add(new Cuboid6(11 * p, 3 * p, 0, 1 - 3 * p, 5 * p, 1 - 5 * p));//north
        boxes.add(new Cuboid6(11 * p, 3 * p, 1 - 3 * p, 1 - 3 * p, 5 * p, 1));//south
        boxes.add(new Cuboid6(0, 3 * p, 11 * p, 1 - 5 * p, 5 * p, 1 - 3 * p));//west
        boxes.add(new Cuboid6(1 - 3 * p, 3 * p, 11 * p, 1, 5 * p, 1 - 3 * p));//east
        boxes.add(new Cuboid6(11 * p, 3 * p, 11 * p, 1 - 3 * p, 5 * p, 1 - 3 * p));//base
    }

    public PartOpticFiber() {
        super(ManagerItems.part_optic_fiber);
    }

    public void update() {
        super.update();
        connections = 0;
        for (MgDirection dir : MgDirection.values()) {
            IOpticFiber f = MgUtils.getOpticFiber(MgUtils.getTileEntity(tile(), dir), dir.opposite());
            if (f != null) {
                connections |= (1 << dir.ordinal());
            }
        }
    }

    @Override
    public List<Cuboid6> getOcclusionCubes() {
        return Collections.singletonList(boxes.get(6));
    }

    @Override
    public List<Cuboid6> getCollisionCubes() {
        List<Cuboid6> list = new ArrayList<>();
        list.add(boxes.get(6));
        if ((connections & (1)) > 0) list.add(boxes.get(0));
        if ((connections & (1 << 1)) > 0) list.add(boxes.get(1));
        if ((connections & (1 << 2)) > 0) list.add(boxes.get(2));
        if ((connections & (1 << 3)) > 0) list.add(boxes.get(3));
        if ((connections & (1 << 4)) > 0) list.add(boxes.get(4));
        if ((connections & (1 << 5)) > 0) list.add(boxes.get(5));
        return list;
    }

    @Override
    public void renderPart(Vector3 pos) {
        if (renderer == null) {
            renderer = new TileRenderOpticFiber();
        }
        renderer.render(this, pos);
    }

}
