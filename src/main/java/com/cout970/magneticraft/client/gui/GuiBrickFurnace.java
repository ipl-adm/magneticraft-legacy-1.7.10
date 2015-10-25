package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.TileBrickFurnace;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiBrickFurnace extends GuiBasic {

    public GuiBrickFurnace(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/brick_furnace.png")));
        comp.add(new CompProgressBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/progressbar1.png"), new GuiPoint(87, 31), ((TileBrickFurnace) tile).getProgresBar()));
        comp.add(new CompHeatBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/heatbar.png"), new GuiPoint(43, 16), ((TileBrickFurnace) tile).heat));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
    }
}
