package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class CompEnergyTrackerBar implements IGuiComp {

    public ResourceLocation texture;
    public GuiPoint pos;
    public IEnergyTracker track;

    public CompEnergyTrackerBar(ResourceLocation tex, GuiPoint p, IEnergyTracker track) {
        texture = tex;
        pos = p;
        this.track = track;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        if (track != null) {
            float prod = track.getChangeInTheLastSecond() <= 0 ? track.getChangeInTheLastTick() / track.getMaxChange() : track.getChangeInTheLastSecond() / (track.getMaxChange() * 20);
            if (prod > 1) prod = 1;
            int scale = (int) (44 * prod);
            gui.mc.renderEngine.bindTexture(texture);
            RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y + (44 - scale), 0, 44 - scale, 6, scale, 12, 45);
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
        if (track != null) {
            if (GuiBasic.isIn(mx, my, gui.xStart + pos.x, gui.yStart + pos.y, 6, 44)) {
                List<String> data = new ArrayList<>();
                float prod = track.getChangeInTheLastSecond() <= 0 ? track.getChangeInTheLastTick() : track.getChangeInTheLastSecond() / 20;
                String s = track.isConsume() ? "Consumption " : "Production ";
                data.add(String.format(s + "%.3f kW", ((int) prod) / 1000f));
                gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                RenderHelper.enableGUIStandardItemLighting();
            }
        }
    }
}