package com.cout970.magneticraft.util;

import com.cout970.magneticraft.Magneticraft;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;


public class Log {
    public static final String prefix = Magneticraft.DEBUG ? "[Magneticraft] " : "";

    private static Logger logger;

    public static void init(Logger l) {
        logger = l;
    }

    public static void log(Object o) {
        logger.log(Level.INFO, String.valueOf(o));
    }

    public static void info(String string) {
        logger.log(Level.INFO, prefix + string);
    }

    public static void error(String string) {
        logger.log(Level.ERROR, string);
    }

    public static void debug(Object i) {
        if (Magneticraft.DEBUG)
            info(String.valueOf(i));
    }
}
