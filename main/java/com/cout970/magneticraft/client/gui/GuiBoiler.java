package com.cout970.magneticraft.client.gui;

import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompFluidRender;
import com.cout970.magneticraft.client.gui.component.CompGenericBar;
import com.cout970.magneticraft.client.gui.component.CompHeatBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.tileentity.TileBoiler;

public class GuiBoiler extends GuiBasic{

	public GuiBoiler(Container c, TileEntity tile) {
		super(c, tile);
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/boiler.png")));
		comp.add(new CompHeatBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/heatbar.png"), new GuiPoint(107, 20)));
		comp.add(new CompFluidRender(((TileBoiler)tile).water, new GuiPoint(54, 25), new GuiPoint(72, 64),new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/tank.png")));
		comp.add(new CompFluidRender(((TileBoiler)tile).steam, new GuiPoint(81, 25), new GuiPoint(99, 64),new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/tank.png")));
		comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/efficiencybar.png"),new GuiPoint(116,20)));
	}

}
