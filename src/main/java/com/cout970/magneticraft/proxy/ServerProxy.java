package com.cout970.magneticraft.proxy;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.util.Log;
import org.apache.commons.lang3.JavaVersion;
import org.apache.commons.lang3.SystemUtils;

public class ServerProxy implements IProxy {

    @Override
    public void init() {
        if (!SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_8) || Magneticraft.DEBUG) {
            Log.info("Java is outdated, throwing exception");
            throw new RuntimeException("Magneticraft cannot be used with versions of Java older than " + Magneticraft.MIN_JAVA);
        }
    }

}
