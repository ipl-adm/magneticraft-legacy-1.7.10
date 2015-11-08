package com.cout970.magneticraft.guide;

import com.cout970.magneticraft.client.gui.GuiGuideBook;
import com.cout970.magneticraft.client.gui.component.GuiPoint;

public interface IPageComp {

    void render(int mx, int my, GuiGuideBook gui, BookPage page, BookGuide guide);

    void onClick(int mx, int my, int button, GuiGuideBook gui, BookPage page, BookGuide guide);

    boolean onKey(int n, char key, GuiGuideBook gui, BookPage page, BookGuide guide);

    void renderTop(int mx, int my, GuiGuideBook gui, BookPage page, BookGuide guide);

    String getID();
    
    Box2D getBox();

    GuiPoint getPosition();

    void setPosition(GuiPoint newPos);
}
