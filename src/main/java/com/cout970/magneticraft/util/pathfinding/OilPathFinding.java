package com.cout970.magneticraft.util.pathfinding;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tileentity.TilePumpJack;
import net.minecraft.block.Block;
import net.minecraft.world.IBlockAccess;

public class OilPathFinding extends PathFinding {

    public OilPathFinding(IBlockAccess ba, VecInt start) {
        super(ba, start);
    }

    @Override
    protected boolean hasFailed() {
        return (scanned.size() > 4000) || (toScan.size() > 10000);
    }

    @Override
    public boolean hasGoal() {
        return false;
    }

    @Override
    public boolean isGoal(PathNode node) {
        return false;
    }

    @Override
    public boolean isPath(PathNode node) {
        Block b = node.getPosition().getBlock(field);

        return b == ManagerBlocks.oilSource || b == ManagerBlocks.oilSourceDrained || b == TilePumpJack.fluidOil;
    }
}
