package com.cout970.magneticraft;

import com.cout970.magneticraft.messages.MessageRedstoneControlUpdate;
import com.cout970.magneticraft.messages.MessageTileUpdate;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class ManagerNetwork {

	public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("magneticraft");
	
	public static void registerMessages(){
		INSTANCE.registerMessage(MessageTileUpdate.class, MessageTileUpdate.class, 0, Side.CLIENT);
		INSTANCE.registerMessage(MessageRedstoneControlUpdate.class, MessageRedstoneControlUpdate.class, 1, Side.SERVER);
	}
}
