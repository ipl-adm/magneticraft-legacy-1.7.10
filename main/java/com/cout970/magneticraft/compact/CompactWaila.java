package com.cout970.magneticraft.compact;

import mcp.mobius.waila.api.impl.ModuleRegistrar;
import codechicken.multipart.BlockMultipart;

import com.cout970.magneticraft.block.BlockMg;

public class CompactWaila {

	
	public static void init(){
		ModuleRegistrar.instance().registerBodyProvider(new HUDMagneticraft(), BlockMg.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDMagneticraft(), BlockMultipart.class);
	}
}
