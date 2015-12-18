package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.tileentity.multiblock.controllers.TileSteamTurbineControl;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.ArrayList;
import java.util.List;

public class GuiSteamTurbine extends GuiBasic {

    public GuiSteamTurbine(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/turbine.png")));
        comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/efficiencybar.png"), new GuiPoint(29, 20), ((TileSteamTurbineControl) tile).getProductionBar()));
        comp.add(new CompGenericBar(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/consumptionbar.png"), new GuiPoint(39, 20), ((TileSteamTurbineControl) tile).getSteamConsumptionBar()));
        comp.add(new CompFluidRender_Turbine((TileSteamTurbineControl) tile, new GuiPoint(50, 25), new GuiPoint(68, 64), new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/tank.png")));
        comp.add(new CompEnergyBarMediumVoltage(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/energybar2.png"), new GuiPoint(19, 16), ((TileSteamTurbineControl) tile).cond));
    }

    public class CompFluidRender_Turbine implements IGuiComp {

        public TileSteamTurbineControl turbine;
        public GuiPoint posA;
        public GuiPoint posB;
        private ResourceLocation background = null;

        public CompFluidRender_Turbine(TileSteamTurbineControl t, GuiPoint a, GuiPoint b, ResourceLocation back) {
            turbine = t;
            posA = a;
            posB = b;
            background = back;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (turbine != null && turbine.getFluidAmount() > 0) {
                IIcon i = FluidRegistry.getFluid("steam").getStillIcon();
                if (i == null) {
                    i = FluidRegistry.WATER.getIcon();
                }
                int scale = turbine.getFluidAmount() * Math.abs(posA.y - posB.y) / turbine.getCapacity();

                RenderUtil.bindTexture(TextureMap.locationBlocksTexture);
                gui.drawTexturedModelRectFromIcon(gui.xStart + posA.x, gui.yStart + posA.y + (Math.abs(posA.y - posB.y) - scale), i, Math.abs(posA.x - posB.x), scale);
                if (background == null) return;
                RenderUtil.bindTexture(background);
                RenderUtil.drawTexturedModalRectScaled(gui.xStart + posA.x - 1, gui.yStart + posA.y - 1, 0, 0, Math.abs(posA.x - posB.x), Math.abs(posA.y - posB.y), 20, 41);
            }
        }

        @Override
        public void onClick(int mx, int my, int button, GuiBasic gui) {
        }

        @Override
        public boolean onKey(int n, char key, GuiBasic gui) {
            return false;
        }

        @Override
        public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
            if (turbine != null && turbine.getFluidAmount() > 0) {
                if (isIn(mx, my, gui.xStart + posA.x, gui.yStart + posA.y, Math.abs(posA.x - posB.x), Math.abs(posA.y - posB.y))) {
                    List<String> data = new ArrayList<>();
                    data.add("Fluid: " + FluidRegistry.getFluidStack("steam", 1).getLocalizedName());
                    data.add("Amount: " + turbine.getFluidAmount());
                    gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                    RenderHelper.enableGUIStandardItemLighting();
                }
            }
        }
    }
}
