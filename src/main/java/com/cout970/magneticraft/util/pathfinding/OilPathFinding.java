package com.cout970.magneticraft.util.pathfinding;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.LinkedList;
import java.util.Set;

public class OilPathFinding extends PathFinding {

    private World world;
    private LinkedList<VecInt> oilBlocks;
    private LinkedList<VecInt> fluidOilBlocks;

    public OilPathFinding(World world) {
        this.world = world;
        oilBlocks = new LinkedList<>();
        fluidOilBlocks = new LinkedList<>();
    }

    @Override
    public void addNode(PathNode node, VecInt dir) {

        if (scanned.size() > 4000) return;
        if (toScan.size() > 10000) return;

        VecInt vec = node.getPosition().copy().add(dir);

        if (scanned.contains(vec) || toScan.stream().anyMatch(n -> (n.getPosition().equals(vec)))) return;

        Block b = world.getBlock(vec.getX(), vec.getY(), vec.getZ());

        if (b == ManagerBlocks.oilSource) {
            oilBlocks.add(vec);
            toScan.add(new PathNode(vec, node));
        } else if (b == ManagerBlocks.oilSourceDrained) {
            toScan.add(new PathNode(vec, node));
        } else if (b == FluidRegistry.getFluid("oil").getBlock()) {
            toScan.add(new PathNode(vec, node));
            fluidOilBlocks.add(vec);
        }
    }

    @Override
    protected boolean isEnd(PathNode node) {
        return false;
    }

    public Set<VecInt> getScannedBlocks() {
        return scanned;
    }

    public LinkedList<VecInt> getOilBlocks() {
        return oilBlocks;
    }

    public LinkedList<VecInt> getFluidOilBlocks() {
        return fluidOilBlocks;
    }
}
