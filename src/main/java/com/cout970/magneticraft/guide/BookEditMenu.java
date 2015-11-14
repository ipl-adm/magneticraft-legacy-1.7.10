package com.cout970.magneticraft.guide;

import com.cout970.magneticraft.client.gui.GuiGuideBook;
import com.cout970.magneticraft.client.gui.component.CompButton;
import com.cout970.magneticraft.client.gui.component.CompEditText;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.guide.comps.CompText;
import net.minecraft.client.gui.Gui;

/**
 * Created by cout970 on 08/11/2015.
 */
public class BookEditMenu {

    private IPageComp comp;
    private CompButton save;
    private CompEditText textEdit;

    public BookEditMenu(IPageComp selectedComp, GuiGuideBook gui) {
        comp = selectedComp;
        save = new CompButton(new GuiPoint(-100, 0), 16, 16, new GuiPoint(0, 96), "textures/gui/buttons.png",
                (n) -> apply(0, n))
                .setUVForState(CompButton.ButtonState.HOVER, new GuiPoint(16, 96))
                .setUVForState(CompButton.ButtonState.DISABLED, new GuiPoint(32, 96))
                .setUVForState(CompButton.ButtonState.ACTIVE, new GuiPoint(48, 96))
                .setClickable(CompButton.ButtonState.ACTIVE, false);
        if(selectedComp instanceof CompText) {
            textEdit = new CompEditText(new GuiPoint(0, 0), ((CompText) selectedComp).text, new GuiPoint(80, 12));
        }
    }

    public boolean apply(int id, int b) {
        return false;
    }

    public void draw(int x, int y, GuiGuideBook gui) {
        Gui.drawRect(-120 + gui.xStart, 20 + gui.yStart, -20 + gui.xStart, 120 + gui.yStart, 0xFF777777);
        save.render(x, y, null, gui);
        if (comp instanceof CompText) {
            CompText t = (CompText) comp;
            gui.drawString(gui.getFontRenderer(), "Simple Text", -100+gui.xStart, 25+gui.yStart, 0xFFFFFF);
            textEdit.setPosition(new GuiPoint(-110, 40));
            textEdit.render(x,y,null, gui);
        }
    }

    public boolean onKey(char letra, int num, GuiGuideBook guiGuideBook) {
        save.onKey(num, letra, guiGuideBook);
        if (comp instanceof CompText) {
            return textEdit.onKey(num, letra, guiGuideBook);
        }
        return false;
    }

    public void onClick(int x, int y, int b, GuiGuideBook guiGuideBook) {
        save.onClick(x, y, b, guiGuideBook);
        if (comp instanceof CompText) {
            textEdit.onClick(x, y, b, guiGuideBook);
        }
    }
}
