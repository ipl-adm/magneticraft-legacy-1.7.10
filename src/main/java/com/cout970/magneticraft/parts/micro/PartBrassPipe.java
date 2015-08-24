package com.cout970.magneticraft.parts.micro;

import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.ISidedHollowConnect;
import codechicken.multipart.TileMultipart;
import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.client.tilerender.TileRenderBrassPipe;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PartBrassPipe extends MgPart implements ISidedHollowConnect {

    public byte connections;
    public int interactions;
    public static List<Cuboid6> boxes = new ArrayList<Cuboid6>();
    private static TileRenderBrassPipe render;

    static {
        double w = 2 / 16d;
        boxes.add(new Cuboid6(0.5 - w, 0, 0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w));//down
        boxes.add(new Cuboid6(0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w, 1, 0.5 + w));//up
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0, 0.5 + w, 0.5 + w, 0.5 - w));//north
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w, 1));//south
        boxes.add(new Cuboid6(0, 0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w));//west
        boxes.add(new Cuboid6(0.5 + w, 0.5 - w, 0.5 - w, 1, 0.5 + w, 0.5 + w));//east
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w));//base
    }

    public PartBrassPipe() {
        super(ManagerItems.part_brass_pipe);
    }

    public void update() {
        super.update();
        connections = 0;
        for (MgDirection dir : MgDirection.values()) {
            TileEntity t = MgUtils.getTileEntity(tile(), dir);
            if (t instanceof TileMultipart) {
                connections |= 1 << dir.ordinal();
            }
        }
    }

    @Override
    public List<Cuboid6> getOcclusionCubes() {
        return Arrays.asList(new Cuboid6[]{boxes.get(6)});
    }

    @Override
    public List<Cuboid6> getCollisionCubes() {
        ArrayList<Cuboid6> t2 = new ArrayList<Cuboid6>();
        t2.add(boxes.get(6));
        for (byte i = 0; i < 6; i++) {
            if ((connections & (1 << i)) > 0) {
                t2.add(boxes.get(i));
            }
        }
        return t2;
    }

    @Override
    public int getHollowSize(int arg0) {
        return 4;
    }

    @Override
    public void renderPart(Vector3 pos) {
        if (render == null) render = new TileRenderBrassPipe();
        render.render(this, pos);
    }
}
