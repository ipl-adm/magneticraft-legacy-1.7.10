package com.cout970.magneticraft.parts.electric;

import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.ISidedHollowConnect;
import codechicken.multipart.NormallyOccludedPart;
import codechicken.multipart.TileMultipart;
import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.electricity.*;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.*;
import com.cout970.magneticraft.client.tilerender.TileRenderCableMedium;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PartCableMedium extends PartElectric implements ISidedHollowConnect, IElectricMultiPart {

    public boolean[] connections = new boolean[6];
    public static List<Cuboid6> boxes = new ArrayList<>();

    static {
        double w = 3 / 16d;

        boxes.add(new Cuboid6(0.5 - w, 0, 0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w));//down
        boxes.add(new Cuboid6(0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w, 1, 0.5 + w));//up
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0, 0.5 + w, 0.5 + w, 0.5 - w));//north
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w, 1));//south
        boxes.add(new Cuboid6(0, 0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w));//west
        boxes.add(new Cuboid6(0.5 + w, 0.5 - w, 0.5 - w, 1, 0.5 + w, 0.5 + w));//east
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w));//base
    }

    public PartCableMedium() {
        super(ManagerItems.part_copper_cable_medium);
    }

    @Override
    public List<Cuboid6> getOcclusionCubes() {
        return Collections.singletonList(boxes.get(6));
    }

    @Override
    public List<Cuboid6> getCollisionCubes() {
        ArrayList<Cuboid6> t2 = new ArrayList<>();
        t2.add(boxes.get(6));
        for (int i = 0; i < 6; i++) {
            if (connections[i]) {
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
    public void update() {
        super.update();
        if (tile() == null) return;
        if (toUpdate) {
            if (cond == null)
                create();
            toUpdate = false;
            updateConnections();
        }
        if (tempNBT != null) {
            cond.load(tempNBT);
            tempNBT = null;
        }
        cond.recache();
        cond.iterate();
    }

    public void create() {
        cond = new ElectricConductor(getTile(), getTier(), ElectricConstants.RESISTANCE_COPPER_MED) {
            @Override
            public boolean isAbleToConnect(IConnectable c, VecInt d) {
                if (c.getConnectionClass(d.getOpposite()) == ConnectionClass.FULL_BLOCK || c.getConnectionClass(d.getOpposite()) == ConnectionClass.Cable_MEDIUM) {
                    if (d.toMgDirection() == null) return false;
                    if (((TileMultipart) getTile()).canAddPart(new NormallyOccludedPart(boxes.get(d.toMgDirection().ordinal()))))
                        return true;
                }
                return false;
            }

            @Override
            public ConnectionClass getConnectionClass(VecInt v) {
                return ConnectionClass.Cable_MEDIUM;
            }

            @Override
            public double getVoltageCapacity() {
                return ElectricConstants.CABLE_MEDIUM_CAPACITY;

            }
        };
    }

    public void updateConnections() {
        Arrays.fill(connections, false);
        for (MgDirection d : MgDirection.values()) {
            TileEntity t = MgUtils.getTileEntity(getTile(), d);
            IElectricConductor[] c = ElectricUtils.getElectricCond(t, VecInt.fromDirection(d).getOpposite(), getTier());
            if (c != null) {
                for (IElectricConductor e : c) {
                    if (e.isAbleToConnect(cond, VecInt.fromDirection(d.opposite())) && cond.isAbleToConnect(e, VecInt.fromDirection(d))) {
                        connections[d.ordinal()] = true;
                    }
                }
            }
            IEnergyInterface inter = ElectricUtils.getInterface(t, d.toVecInt().getOpposite(), getTier());
            if (inter != null && inter.canConnect(d.toVecInt())) {
                if (((TileMultipart) getTile()).canAddPart(new NormallyOccludedPart(boxes.get(d.ordinal()))))
                    connections[d.ordinal()] = true;
            }
        }
    }

    @Override
    public int getTier() {
        return 1;
    }

    //Render

    private TileRenderCableMedium render;

    @Override
    public void renderPart(Vector3 pos) {
        if (render == null) render = new TileRenderCableMedium();
        render.render(this, pos);
    }
}
