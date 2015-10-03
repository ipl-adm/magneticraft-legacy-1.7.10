package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.TileCombustionEngine;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class GuiCombustionEngine extends GuiBasic {

    public GuiCombustionEngine(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/combustion_engine.png")));
        comp.add(new CompFluidRender(((TileCombustionEngine) tile).getTank(), new GuiPoint(47, 25), new GuiPoint(65, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
        comp.add(new CompEnergyBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(23, 16), ((TileCombustionEngine) tile).cond));
        comp.add(new CompStorageBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar.png"), new GuiPoint(31, 16), ((TileCombustionEngine) tile).cond));
        comp.add(new CompHeatBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/heatbar.png"), new GuiPoint(69, 20), ((TileCombustionEngine) tile).heat));
        comp.add(new CompButtonRedstoneControl(new GuiPoint(150, 8)));
        comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/efficiencybar.png"), new GuiPoint(78, 20), ((TileCombustionEngine) tile).getProductionBar()));
    }
}
