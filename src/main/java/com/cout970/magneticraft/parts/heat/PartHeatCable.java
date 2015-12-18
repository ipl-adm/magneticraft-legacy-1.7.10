package com.cout970.magneticraft.parts.heat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.heat.HeatUtils;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.api.util.IConnectable;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.tilerender.TileRenderHeatCable;

import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.ISidedHollowConnect;
import codechicken.multipart.NormallyOccludedPart;
import codechicken.multipart.TileMultipart;
import net.minecraft.tileentity.TileEntity;

public class PartHeatCable extends PartHeat implements ISidedHollowConnect {

    public byte conMask = -1;

    public PartHeatCable() {
        super(ManagerItems.partheatcable);
    }

    @Override
    public void create() {
        heat = new HeatConductor(getTile(), 1500, 1000) {
            @Override
            public boolean isAbleToConnect(IConnectable cond, VecInt d) {
                return d.equals(VecInt.NULL_VECTOR)
                        || ((d.toMgDirection() != null)
                        && ((TileMultipart) getTile()).canAddPart(new NormallyOccludedPart(boxes.get(d.toMgDirection().ordinal()))));
            }
        };
    }

    @Override
    public void updateConnections() {
        conMask = 0;
        for (MgDirection d : MgDirection.values()) {
            TileEntity t = MgUtils.getTileEntity(tile(), d);
            IHeatConductor[] c = HeatUtils.getHeatCond(t, d.toVecInt().getOpposite());
            if (c != null) {
                for (IHeatConductor cond : c) {
                    if (cond != null) {
                        if (cond.isAbleToConnect(heat, d.opposite().toVecInt()) && heat.isAbleToConnect(cond, d.toVecInt())) {
                            conMask |= (1 << d.ordinal());
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onNeighborChanged() {
        super.onNeighborChanged();
        conMask = -1;
    }

    public static List<Cuboid6> boxes = new ArrayList<>();

    static {
        double w = 6 / 16d;
        boxes.clear();
        boxes.add(new Cuboid6(0.5 - w, 0, 0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w));//up
        boxes.add(new Cuboid6(0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w, 1, 0.5 + w));//down
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0, 0.5 + w, 0.5 + w, 0.5 - w));//north
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w, 1));//south
        boxes.add(new Cuboid6(0, 0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w));//west
        boxes.add(new Cuboid6(0.5 + w, 0.5 - w, 0.5 - w, 1, 0.5 + w, 0.5 + w));//east
        boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w));//base
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
            if ((conMask & (1 << i)) > 0) {
                t2.add(boxes.get(i));
            }
        }
        return t2;
    }

    private TileRenderHeatCable render;

    @Override
    public void renderPart(Vector3 pos) {
        if (render == null) render = new TileRenderHeatCable();
        render.render(this, pos);
    }

    @Override
    public int getHollowSize(int side) {
        return 12;
    }
}
