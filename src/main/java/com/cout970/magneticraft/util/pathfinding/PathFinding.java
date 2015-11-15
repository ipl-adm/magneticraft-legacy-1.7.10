package com.cout970.magneticraft.util.pathfinding;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by cout970 on 13/11/2015.
 */
public abstract class PathFinding {

    protected HashSet<VecInt> scanned;
    protected Queue<PathNode> toScan;
    protected VecInt start;
    protected VecInt end;

    public PathFinding() {
        scanned = new HashSet<>();
        toScan = new LinkedList<>();
    }

    public void setStart(VecInt vec) {
        start = vec.copy();
    }

    public void setEnd(VecInt vec) {
        end = vec.copy();
    }

    public void addAdjacentNodes(PathNode node) {
        for(MgDirection d : MgDirection.values())
            addNode(node, d.toVecInt());
    }

    public PathNode scan(PathNode node) {

        scanned.add(node.getPosition());
        if (isEnd(node)) {
            return node;
        }
        addAdjacentNodes(node);
        return null;
    }

    public abstract void addNode(PathNode node, VecInt dir);

    protected abstract boolean isEnd(PathNode node);

    public LinkedList<VecInt> getPath(){
        PathNode node = getPathEnd();

        if (node != null) {
            LinkedList<VecInt> path = new LinkedList<>();
            for (PathNode current = node; current.getBefore() != null; current = current.getBefore()) {
                path.addFirst(current.getPosition());
            }
            return path;
        }

        return null;
    }

    public PathNode getPathEnd(){
        toScan.clear();
        scanned.clear();
        addNode(new PathNode(start, null), VecInt.NULL_VECTOR);
        PathNode node = null;

        while (!toScan.isEmpty()) {
            node = scan(toScan.poll());
            if (node != null) {
                break;
            }
        }

        return node;
    }
}
