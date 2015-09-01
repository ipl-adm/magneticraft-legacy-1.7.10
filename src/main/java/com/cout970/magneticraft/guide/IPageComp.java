package com.cout970.magneticraft.guide;

import com.cout970.magneticraft.client.gui.GuiGuideBook;

public interface IPageComp {

    public void render(int mx, int my, GuiGuideBook gui, BookPage page, BookGuide guide);

    public void onClick(int mx, int my, int buttom, GuiGuideBook gui, BookPage page, BookGuide guide);

    public boolean onKey(int n, char key, GuiGuideBook gui, BookPage page, BookGuide guide);

    public void renderTop(int mx, int my, GuiGuideBook gui, BookPage page, BookGuide guide);

    public String getID();
}
