package com.cout970.magneticraft.guide.comps;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.client.gui.GuiGuideBook;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.guide.BookGuide;
import com.cout970.magneticraft.guide.BookPage;
import com.cout970.magneticraft.guide.Box2D;
import com.cout970.magneticraft.guide.IPageComp;
import com.cout970.magneticraft.util.RenderUtil;
import com.google.gson.annotations.Expose;

public class CompPageLink implements IPageComp {

    @Expose
    public boolean left;
    @Expose
    public String page;

    public CompPageLink() {
    }

    public CompPageLink(String link, boolean side) {
        page = link;
        this.left = side;
    }

    @Override
    public void render(int mx, int my, GuiGuideBook gui, BookPage page, BookGuide guide) {
        if (page == null)
            return;
        RenderUtil.bindTexture(GuiGuideBook.background);
        if (left) {
            RenderUtil.drawTexturedModalRectScaled(gui.xStart + 25, gui.yStart + 195, 0, 93, 18, 12, 180, 132);
        } else {
            RenderUtil.drawTexturedModalRectScaled(gui.xStart + 305, gui.yStart + 195, 0, 105, 18, 12, 180, 132);
        }
    }

    @Override
    public void onClick(int mx, int my, int button, GuiGuideBook gui, BookPage current, BookGuide guide) {
        if (page != null) {
            if (left) {
                if (GuiBasic.isIn(mx, my, gui.xStart + 25, gui.yStart + 195, 18, 12)) {
                    gui.changePage(page);
                }
            } else {
                if (GuiBasic.isIn(mx, my, gui.xStart + 305, gui.yStart + 195, 18, 12)) {
                    gui.changePage(page);
                }
            }
        }
    }

    @Override
    public boolean onKey(int n, char key, GuiGuideBook gui, BookPage page, BookGuide guide) {
        return false;
    }

    @Override
    public void renderTop(int mx, int my, GuiGuideBook gui, BookPage page, BookGuide guide) {
    }

    @Override
    public String getID() {
        return "page_link";
    }

	@Override
	public Box2D getBox() {
		return new Box2D(0, 0, 0, 0);
	}

    @Override
    public GuiPoint getPosition() {
        return new GuiPoint(0,0);
    }

    @Override
    public void setPosition(GuiPoint pos) {}
}
