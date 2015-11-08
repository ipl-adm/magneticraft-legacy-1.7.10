package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.messages.MessageRedstoneControlUpdate;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.util.RenderUtil;
import com.cout970.magneticraft.util.tile.RedstoneControl;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class CompButtonRedstoneControl implements IGuiComp {

    public GuiPoint pos;

    public CompButtonRedstoneControl(GuiPoint pos) {
        this.pos = pos;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        if (tile instanceof TileBase) {
            GL11.glColor4f(1, 1, 1, 1);
            int i = ((TileBase) tile).redstone == RedstoneControl.DISABLE ? 0 : ((TileBase) tile).redstone == RedstoneControl.INVERSE ? 18 : 36;
            RenderUtil.bindTexture(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/button_rs.png"));
            RenderUtil.drawTexturedModalRectScaled(gui.xStart + pos.x, gui.yStart + pos.y, 0, i, 18, 18, 18, 54);
        }
    }

    @Override
    public void onClick(int mx, int my, int button, GuiBasic gui) {
        if (GuiBasic.isIn(mx, my, pos.x + gui.xStart, pos.y + gui.yStart, 18, 18)) {
            if (gui.tile instanceof TileBase) {
                RedstoneControl state = ((TileBase) (gui.tile)).redstone;
                state = TileBase.step(state);
                ManagerNetwork.INSTANCE.sendToServer(new MessageRedstoneControlUpdate(gui.tile, state));
            }
        }
    }

    @Override
    public boolean onKey(int n, char key, GuiBasic gui) {
        return false;
    }

    @Override
    public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
        if (GuiBasic.isIn(mx, my, pos.x + gui.xStart, pos.y + gui.yStart, 18, 18)) {
            if (gui.tile instanceof TileBase) {
                RedstoneControl state = ((TileBase) (gui.tile)).redstone;
                List<String> data = new ArrayList<>();
                data.add(state == RedstoneControl.NORMAL ? "Signal off" : state == RedstoneControl.INVERSE ? "Signal on" : "Ignore");
                gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                RenderHelper.enableGUIStandardItemLighting();
            }
        }
    }

}
