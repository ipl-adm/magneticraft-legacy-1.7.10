package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class CompEnergyBar implements IGuiComp {

    public ResourceLocation texture;
    public GuiPoint pos;
    public IElectricConductor cond;

    public CompEnergyBar(ResourceLocation tex, GuiPoint p, IElectricConductor cond) {
        texture = tex;
        pos = p;
        this.cond = cond;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        if (cond != null) {
            int scale = (int) (cond.getVoltage() >= ElectricConstants.MAX_VOLTAGE ? 50 : 50 * (cond.getVoltage() / ElectricConstants.MAX_VOLTAGE));
            gui.mc.renderEngine.bindTexture(texture);
            RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y + (50 - scale), 25, 50 - scale, 5, scale, 70, 50);
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
        if (cond != null) {
            if (GuiBasic.isIn(mx, my, gui.xStart + pos.x, gui.yStart + pos.y, 6, 50)) {
                List<String> data = new ArrayList<>();
                data.add(((int) cond.getVoltage()) + "V");
                gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                RenderHelper.enableGUIStandardItemLighting();
            }
        }
    }
}
