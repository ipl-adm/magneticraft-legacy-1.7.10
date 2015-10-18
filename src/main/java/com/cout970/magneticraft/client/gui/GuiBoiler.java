package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.TileBoiler;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiBoiler extends GuiBasic {

    public GuiBoiler(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/boiler.png")));
        comp.add(new CompHeatBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/heatbar.png"), new GuiPoint(107, 20), ((TileBoiler) tile).heat));
        comp.add(new CompFluidRender(((TileBoiler) tile).water, new GuiPoint(54, 25), new GuiPoint(72, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
        comp.add(new CompFluidRender(((TileBoiler) tile).steam, new GuiPoint(81, 25), new GuiPoint(99, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
        comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/efficiencybar.png"), new GuiPoint(116, 20), ((TileBoiler) tile).getProductionBar()));
    }

}
