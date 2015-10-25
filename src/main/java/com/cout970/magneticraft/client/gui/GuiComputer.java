package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.messages.MessageGuiClick;
import com.cout970.magneticraft.tileentity.TileComputer;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiComputer extends GuiBasic {

    public GuiComputer(Container c, TileEntity tile) {
        super(c, tile);
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/cpu.png")));
        comp.add(new CompCPU(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/cpu.png")));
    }

    public class CompCPU implements IGuiComp {

        public ResourceLocation texture;

        public CompCPU(ResourceLocation tex) {
            texture = tex;
        }

        @Override
        public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
            TileComputer t = (TileComputer) tile;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            if (t.isRunning()) {
                gui.drawTexturedModalRect(gui.xStart + 158, gui.yStart + 61, 0, 177, 9, 9);
            } else {
                gui.drawTexturedModalRect(gui.xStart + 158, gui.yStart + 61, 9, 177, 9, 9);
            }

            gui.drawString(getFontRenderer(), "Start", gui.xStart + 125, gui.yStart + 7, RenderUtil.fromRGB(255, 255, 255));
            gui.drawString(getFontRenderer(), "Restart", gui.xStart + 113, gui.yStart + 21, RenderUtil.fromRGB(255, 255, 255));
            gui.drawString(getFontRenderer(), "Shutdown", gui.xStart + 105, gui.yStart + 35, RenderUtil.fromRGB(255, 255, 255));
        }

//		private int getPos(char a) {
//			switch(a){
//			case '0': return 0;
//			case '1': return 7;
//			case '2': return 14;
//			case '3': return 21;
//			case '4': return 28;
//			case '5': return 35;
//			case '6': return 42;
//			case '7': return 49;
//			case '8': return 56;
//			case '9': return 63;
//			case 'A': return 70;
//			case 'B': return 77;
//			case 'C': return 84;
//			case 'D': return 91;
//			case 'E': return 98;
//			case 'F': return 105;
//			}
//			return 0;
//		}
//
//		private String fill(String s) {
//			while(s.length() < 8)s = "0"+s;
//			return s;
//		}

        @Override
        public void onClick(int mx, int my, int button, GuiBasic gui) {
            if (isIn(mx, my, gui.xStart + 158, gui.yStart + 9, 10, 10)) {
                MessageGuiClick msg = new MessageGuiClick(tile, 0, 1);
                ManagerNetwork.INSTANCE.sendToServer(msg);
            } else if (isIn(mx, my, gui.xStart + 158, gui.yStart + 23, 10, 10)) {
                MessageGuiClick msg = new MessageGuiClick(tile, 1, 1);
                ManagerNetwork.INSTANCE.sendToServer(msg);
            } else if (isIn(mx, my, gui.xStart + 158, gui.yStart + 37, 10, 10)) {
                MessageGuiClick msg = new MessageGuiClick(tile, 2, 1);
                ManagerNetwork.INSTANCE.sendToServer(msg);
            }
        }

        @Override
        public boolean onKey(int n, char key, GuiBasic gui) {
            return false;
        }

        @Override
        public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
        }

    }

}
