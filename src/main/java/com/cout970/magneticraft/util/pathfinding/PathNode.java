package com.cout970.magneticraft.util.pathfinding;

import com.cout970.magneticraft.api.util.VecInt;

/**
 * Created by cout970 on 13/11/2015.
 */
public class PathNode {

    private VecInt position;
    private PathNode before;

    public PathNode(VecInt position, PathNode node) {
        this.position = position;
        before = node;
    }

    public boolean isStart(){
        return before == null;
    }

    public PathNode getBefore() {
        return before;
    }

    public void setBefore(PathNode before) {
        this.before = before;
    }

    public VecInt getPosition() {
        return position;
    }

    public void setPosition(VecInt position) {
        this.position = position;
    }
}
