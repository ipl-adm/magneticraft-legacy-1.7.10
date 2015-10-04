package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiShelvingUnit extends GuiBasic {
    public GuiShelvingUnit(Container c, TileEntity tile) {
        super(c, tile);
        xTam = xSize = 195;
        yTam = ySize = 204;
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/shelving_unit.png")));

    }
}
