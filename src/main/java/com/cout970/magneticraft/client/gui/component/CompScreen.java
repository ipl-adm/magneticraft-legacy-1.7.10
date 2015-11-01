package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.api.computer.prefab.MonitorPeripheral;
import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.messages.MessageClientStream;
import com.cout970.magneticraft.util.IClientInformer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;


public class CompScreen implements IGuiComp {

    private double zLevel;
    public MonitorPeripheral mon;

    public CompScreen(MonitorPeripheral m) {
        mon = m;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        gui.mc.renderEngine.bindTexture(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/monitor.png"));
        GL11.glColor4f(0.0F, 1.0F, 0.0F, 1.0F);
        int cursor = (mon.readByte(0x8) & 255) | (mon.readByte(0x9) << 8) | (mon.readByte(0xa) << 16) | (mon.readByte(0xb) << 24);
        for (int line = 0; line < 50; line++) {
            for (int desp = 0; desp < 80; desp++) {
                int character = mon.getText(line * 80 + desp) & 255;
                if (line * 80 + desp == cursor && tile.getWorldObj().getTotalWorldTime() % 20 >= 10) {
                    character ^= 128;
                }
                if (character != 32 && character != 0)
                    drawDoubledRect(gui.xStart + 15 + desp * 4, gui.yStart + 15 + line * 4, 4, 4, 350 + (character & 15) * 8, (character >> 4) * 8, 8, 8);
            }
        }
        GL11.glColor3f(1, 1, 1);
    }

    public void drawDoubledRect(int x, int y, int tamX, int tamY, int u, int v, int width, int height) {
        float aux = 0.001953125F;
        float aux2 = 0.00390625F;
        Tessellator tes = Tessellator.instance;
        tes.startDrawingQuads();
        tes.addVertexWithUV(x, y + tamY, this.zLevel, u * aux, (v + height) * aux2);
        tes.addVertexWithUV(x + tamX, y + tamY, this.zLevel, (u + width) * aux, (v + height) * aux2);
        tes.addVertexWithUV(x + tamX, y, this.zLevel, (u + width) * aux, v * aux2);
        tes.addVertexWithUV(x, y, this.zLevel, u * aux, v * aux2);
        tes.draw();
    }

    @Override
    public void onClick(int mx, int my, int button, GuiBasic gui) {
    }

    @Override
    public boolean onKey(int num, char key, GuiBasic gui) {
        if (key == 27) return false;
        int shift = 0;
        if (GuiScreen.isShiftKeyDown()) shift |= 64;
        if (GuiScreen.isCtrlKeyDown()) shift |= 32;
        switch (key) {
            case 199:
                this.sendKey(132 | shift, gui);
                break;
            case 200:
                this.sendKey(128 | shift, gui);
                break;
            case 201:
            case 202:
            case 204:
            case 206:
            case 209:
            default:
                if (key > 0 && key <= 127) {
                    this.sendKey(key, gui);
                }
                break;

            case 203:
                this.sendKey(130 | shift, gui);
                break;
            case 205:
                this.sendKey(131 | shift, gui);
                break;
            case 207:
                this.sendKey(133 | shift, gui);
                break;
            case 208:
                this.sendKey(129 | shift, gui);
                break;
            case 210:
                this.sendKey(134 | shift, gui);
                break;
            case 0:
                if (num != 54 && num != 42 && num != 56 && num != 184 && num != 29 && num != 221 && num != 157 && (num < 59 || num > 70) && num != 87 && num != 88 && num != 197 && num != 183 && num != 0) {
                    this.sendKey(num, gui);
                }
                break;
        }
        return true;
    }

    private void sendKey(int key, GuiBasic gui) {
        mon.keyPresed(key);
        MessageClientStream msg = new MessageClientStream((IClientInformer) gui.tile);
        ManagerNetwork.INSTANCE.sendToServer(msg);
    }

    @Override
    public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
    }

}