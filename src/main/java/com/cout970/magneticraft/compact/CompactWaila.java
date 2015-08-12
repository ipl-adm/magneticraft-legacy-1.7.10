package com.cout970.magneticraft.compact;

import com.cout970.magneticraft.block.BlockMg;

import codechicken.multipart.BlockMultipart;
import mcp.mobius.waila.api.impl.ModuleRegistrar;

public class CompactWaila {

	
	public static void init(){
		ModuleRegistrar.instance().registerBodyProvider(new HUDMagneticraft(), BlockMg.class);
		ModuleRegistrar.instance().registerBodyProvider(new HUDMagneticraft(), BlockMultipart.class);
	}
}
