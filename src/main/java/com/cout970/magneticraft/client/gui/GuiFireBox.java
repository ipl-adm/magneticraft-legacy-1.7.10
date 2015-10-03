package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.TileFireBox;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiFireBox extends GuiBasic {

    public GuiFireBox(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/firebox.png")));
        comp.add(new CompBurningTime(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/fire.png"), new GuiPoint(80, 28), ((TileFireBox) tile).getBurningTimeBar()));
        comp.add(new CompHeatBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/heatbar.png"), new GuiPoint(107, 20), ((TileFireBox) tile).heat));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
    }
}
