package com.cout970.magneticraft.util.concurrency;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class PathFindingExecutor {
    public static final int MAX_THREADS = 4;
    public static final ScheduledExecutorService INSTANCE = Executors.newScheduledThreadPool(MAX_THREADS);
}
