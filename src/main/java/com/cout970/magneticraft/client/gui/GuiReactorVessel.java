package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompHeatBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.tileentity.TileReactorVessel;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiReactorVessel extends GuiBasic {

    public GuiReactorVessel(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/reactor_vessel.png")));
        comp.add(new CompHeatBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/heatbar.png"), new GuiPoint(107, 20), ((TileReactorVessel) tile).heat));
//		comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/efficiencybar.png"),new GuiPoint(116,20)));
    }

}
