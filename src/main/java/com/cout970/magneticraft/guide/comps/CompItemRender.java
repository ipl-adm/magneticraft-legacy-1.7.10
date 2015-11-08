package com.cout970.magneticraft.guide.comps;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.client.gui.GuiGuideBook;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.guide.BookGuide;
import com.cout970.magneticraft.guide.BookPage;
import com.cout970.magneticraft.guide.Box2D;
import com.cout970.magneticraft.guide.IPageComp;
import com.cout970.magneticraft.guide.Stack;
import com.google.gson.annotations.Expose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;

public class CompItemRender implements IPageComp {

    @Expose
    public int x;
    @Expose
    public int y;
    @Expose
    public Stack item;

    public CompItemRender(ItemStack item, int x, int y) {
        this.item = new Stack(item);
        this.x = x;
        this.y = y;
    }

    public CompItemRender() {
    }

    @Override
    public void render(int mx, int my, GuiGuideBook gui, BookPage page, BookGuide guide) {
        if (item != null && item.getStack() != null) {
            GL11.glPushMatrix();
            GL11.glColor3f(1, 1, 1);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_COLOR_MATERIAL);
            GL11.glEnable(GL11.GL_LIGHTING);
            RenderItem.getInstance().renderItemAndEffectIntoGUI(gui.getFontRenderer(),
                    Minecraft.getMinecraft().renderEngine, item.getStack(), gui.xStart + x, gui.yStart + y);
            RenderItem.getInstance().renderItemOverlayIntoGUI(gui.getFontRenderer(),
                    Minecraft.getMinecraft().renderEngine, item.getStack(), gui.xStart + x, gui.yStart + y);
            GL11.glPopMatrix();
        }
    }

    @Override
    public void onClick(int mx, int my, int button, GuiGuideBook gui, BookPage page, BookGuide guide) {
    }

    @Override
    public boolean onKey(int n, char key, GuiGuideBook gui, BookPage page, BookGuide guide) {
        return false;
    }

    @Override
    public String getID() {
        return "item_render";
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void renderTop(int mx, int my, GuiGuideBook gui, BookPage page, BookGuide guide) {
        if (item != null && item.getStack() != null) {
            if (GuiBasic.isIn(mx, my, gui.xStart + x, gui.yStart + y, 16, 16)) {
                List data = item.getStack().getTooltip(gui.mc.thePlayer, false);
                gui.drawHoveringText2(data, mx - gui.xStart, my - gui.yStart);
                RenderHelper.enableGUIStandardItemLighting();
            }
        }
    }

	@Override
	public Box2D getBox() {
		return new Box2D(x, y, x+16, y+16);
	}

    @Override
    public GuiPoint getPosition() {
        return new GuiPoint(x,y);
    }

    @Override
    public void setPosition(GuiPoint pos) {
        x = pos.x;
        y = pos.y;
    }
}
