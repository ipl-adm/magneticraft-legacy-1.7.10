package com.cout970.magneticraft.util.concurrency;

import com.cout970.magneticraft.util.pathfinding.PathFinding;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

public class PathFindingCallable implements Callable<PathFinding> {
    private PathFinding current;
    private ExecutorService parent;
    public static final int MAX_ITERATIONS = 100;

    public PathFindingCallable(PathFinding alg, ExecutorService parent) {
        current = alg;
        this.parent = parent;
    }

    @Override
    public PathFinding call() throws Exception {
        current.iterate(MAX_ITERATIONS);
        return current;
    }
}