package com.cout970.magneticraft.api.kinetic;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.LinkedList;

public class KineticPathFinder {

    public LinkedList<IKineticConductor> conds;
    public World w;
    public LinkedList<ExtendedPos> scanPosition;
    public HashSet<BlockPos> scanMap;

    public KineticPathFinder(World w, IKineticConductor cond) {
        this.w = w;
        conds = new LinkedList<>();
        scanPosition = new LinkedList<>();
        scanMap = new HashSet<>();
        conds.addLast(cond);
        scanMap.add(cond.getParent().getPos());
        for (EnumFacing dir : cond.getValidSides()) {
            addBlock(new ExtendedPos(cond.getParent().getPos().add(dir.getDirectionVec()), dir));
        }
    }

    public void addBlock(ExtendedPos v) {
        if (!scanMap.contains(v.pos)) {
            scanPosition.addLast(v);
            scanMap.add(v.pos);
        }
    }

    public boolean iterate() {
        if (scanPosition.size() == 0) {
            return false;
        } else {
            ExtendedPos vec = scanPosition.removeFirst();
            return step(vec);
        }
    }

    public boolean step(ExtendedPos vec) {
        TileEntity tile = w.getTileEntity(vec.pos);
        if (tile instanceof IKineticTile) {
            IKineticTile k = (IKineticTile) tile;
            IKineticConductor cond = k.getKineticConductor(vec.dir);
            if (cond != null) {
                conds.add(cond);
                for (EnumFacing d : k.getValidSides()) {
                    addBlock(new ExtendedPos(new BlockPos(vec.pos).add(d.getDirectionVec()), d.getOpposite()));
                }
            }
        }
        return true;
    }

    public class ExtendedPos {

        public BlockPos pos;
        public EnumFacing dir;

        public ExtendedPos(BlockPos pos, EnumFacing d) {
            this.pos = pos;
            dir = d;
        }
    }
}
