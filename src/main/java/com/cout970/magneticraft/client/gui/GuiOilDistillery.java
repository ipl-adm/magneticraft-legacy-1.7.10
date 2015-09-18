package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileOilDistillery;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiOilDistillery extends GuiBasic {

    public GuiOilDistillery(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponenets() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/distillery.png")));
        comp.add(new CompFluidRender(((TileOilDistillery) tile).getInput(), new GuiPoint(63, 25), new GuiPoint(81, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
        comp.add(new CompFluidRender(((TileOilDistillery) tile).getOutput(), new GuiPoint(96, 25), new GuiPoint(114, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
        comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(23, 16), ((TileOilDistillery) tile).own));
        comp.add(new CompStorageBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(31, 16), ((TileOilDistillery) tile).own));
    }
}
