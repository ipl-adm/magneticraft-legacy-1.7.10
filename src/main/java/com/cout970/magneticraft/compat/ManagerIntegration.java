package com.cout970.magneticraft.compat;

import cpw.mods.fml.common.Loader;

public class ManagerIntegration {

    public static void searchCompatibilities() {
        if (Loader.isModLoaded("Waila")) {
            CompatWaila.init();
        }
    }
}
