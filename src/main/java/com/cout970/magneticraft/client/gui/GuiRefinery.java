package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompFluidRender;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileRefinery;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiRefinery extends GuiBasic {

    public GuiRefinery(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/refinery.png")));
        comp.add(new CompFluidRender(((TileRefinery) tile).input, new GuiPoint(41, 25), new GuiPoint(59, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
        comp.add(new CompFluidRender(((TileRefinery) tile).output0, new GuiPoint(74, 25), new GuiPoint(92, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
        comp.add(new CompFluidRender(((TileRefinery) tile).output1, new GuiPoint(98, 25), new GuiPoint(116, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
        comp.add(new CompFluidRender(((TileRefinery) tile).output2, new GuiPoint(122, 25), new GuiPoint(140, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
    }
}
