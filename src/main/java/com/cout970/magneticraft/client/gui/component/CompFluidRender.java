package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.fluid.TankMg;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;

import java.util.ArrayList;
import java.util.List;

public class CompFluidRender implements IGuiComp {

    public TankMg tank;
    public GuiPoint posA;
    public GuiPoint posB;
    private ResourceLocation background = null;

    public CompFluidRender(TankMg t, GuiPoint a, GuiPoint b) {
        tank = t;
        posA = a;
        posB = b;
    }

    public CompFluidRender(TankMg t, GuiPoint a, GuiPoint b, ResourceLocation back) {
        tank = t;
        posA = a;
        posB = b;
        background = back;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        if (tank != null && tank.getFluid() != null) {
            IIcon i = FluidRegistry.getFluid(tank.getFluid().getFluid().getName()).getStillIcon();
            if (i == null) {
                i = FluidRegistry.WATER.getIcon();
            }
            int scale = tank.getFluidAmount() * Math.abs(posA.y - posB.y) / tank.getCapacity();

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
        if (tank != null && tank.getFluid() != null) {
            if (GuiBasic.isIn(mx, my, gui.xStart + posA.x, gui.yStart + posA.y, Math.abs(posA.x - posB.x), Math.abs(posA.y - posB.y))) {
                List<String> data = new ArrayList<>();
                data.add("Fluid: " + tank.getFluid().getLocalizedName());
                data.add("Amount: " + tank.getFluidAmount());
                gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                RenderHelper.enableGUIStandardItemLighting();
            }
        }
    }
}
