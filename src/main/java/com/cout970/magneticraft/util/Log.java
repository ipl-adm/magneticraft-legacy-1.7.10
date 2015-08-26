package com.cout970.magneticraft.util;

import com.cout970.magneticraft.Magneticraft;
import cpw.mods.fml.common.FMLLog;
import org.apache.logging.log4j.Level;

public class Log {
    public static final String prefix = Magneticraft.DEBUG ? "[Magneticraft] " : "";
    public static void log(Object o) {
        FMLLog.log(Magneticraft.ID, Level.INFO, String.valueOf(o));
    }

    public static void info(String string) {
        FMLLog.log(Magneticraft.ID, Level.INFO, prefix + string);
    }

    public static void error(String string) {
        FMLLog.log(Magneticraft.ID, Level.ERROR, string);
    }

    public static void debug(Object i) {
        if (Magneticraft.DEBUG)
            info(String.valueOf(i));
    }
}
