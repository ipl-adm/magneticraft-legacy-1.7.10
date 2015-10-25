package com.cout970.magneticraft.compat;

import codechicken.multipart.BlockMultipart;
import com.cout970.magneticraft.block.BlockMg;

public class CompatWaila {


    public static void init() {
        ModuleRegistrar.instance().registerBodyProvider(new HUDMagneticraft(), BlockMg.class);
        ModuleRegistrar.instance().registerBodyProvider(new HUDMagneticraft(), BlockMultipart.class);
    }
}
