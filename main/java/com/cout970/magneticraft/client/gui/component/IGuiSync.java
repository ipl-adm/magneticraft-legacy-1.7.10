package com.cout970.magneticraft.client.gui.component;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

public interface IGuiSync {

	public void sendGUINetworkData(Container cont, ICrafting craft);

	public void getGUINetworkData(int id, int value);
}
