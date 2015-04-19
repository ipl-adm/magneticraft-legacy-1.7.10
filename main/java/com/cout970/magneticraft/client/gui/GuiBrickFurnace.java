package com.cout970.magneticraft.client.gui;

import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompButtonRedstoneControl;
import com.cout970.magneticraft.client.gui.component.CompHeatBar;
import com.cout970.magneticraft.client.gui.component.CompProgresBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;

public class GuiBrickFurnace extends GuiBasic{

	public GuiBrickFurnace(Container c, TileEntity tile) {
		super(c, tile);
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/brick_furnace.png")));
		comp.add(new CompProgresBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/progresbar1.png"), new GuiPoint(87, 31)));
		comp.add(new CompHeatBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/heatbar.png"), new GuiPoint(43, 16)));
		comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
	}
}
