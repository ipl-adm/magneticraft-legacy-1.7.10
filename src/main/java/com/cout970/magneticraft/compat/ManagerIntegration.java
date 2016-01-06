package com.cout970.magneticraft.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModAPIManager;

public class ManagerIntegration {

    public static boolean RAILCRAFT = false;
    public static boolean BUILDCRAFT = false;
    public static boolean IC2 = false;
    public static boolean COFH_ENERGY = false;
    public static boolean COFH_TOOLS = false;
    public static boolean IE = false;
    public static boolean MFR = false;

    public static void searchCompatibilities() {
        if (Loader.isModLoaded("Waila")) {
            CompatWaila.init();
        }

        BUILDCRAFT = Loader.isModLoaded("BuildCraft|Core");

        IC2 = Loader.isModLoaded("IC2");

        RAILCRAFT = Loader.isModLoaded("Railcraft");

        IE = Loader.isModLoaded("ImmersiveEngineering");

        COFH_ENERGY = ModAPIManager.INSTANCE.hasAPI("CoFHAPI|energy");

        COFH_TOOLS = ModAPIManager.INSTANCE.hasAPI("CoFHAPI|item");

        MFR = Loader.isModLoaded("MineFactoryReloaded");
    }
}
