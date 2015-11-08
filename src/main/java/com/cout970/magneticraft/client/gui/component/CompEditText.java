package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.guide.Box2D;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatAllowedCharacters;

/**
 * Created by cout970 on 08/11/2015.
 */
public class CompEditText implements IGuiComp {

    private GuiPoint pos;
    private String buffer;
    private boolean selected;
    private GuiPoint size;
    private int index;

    public CompEditText(GuiPoint pos, String defaul, GuiPoint size){
        this.pos = pos;
        if(defaul == null){
            buffer = "";
        }else{
            buffer = defaul;
        }
        this.size = size;
    }

    public Box2D getBox(GuiBasic gui){
        return new Box2D(gui.xStart+pos.x, gui.yStart+pos.y, gui.xStart+pos.x+size.x, gui.yStart+pos.y+size.y);
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        if(buffer == null)
            buffer = "";
        getBox(gui).draw(selected ? 0xFFFF0000 : 0xFFFFFFFF);
        if(!buffer.equals("")) {
            index = buffer.length();
            int maxLenght = size.x/6;
            int start = Math.max(0, index - maxLenght);
            int end = Math.min(buffer.length(), index);
            String displayText = buffer.substring(start, end);
            gui.drawString(gui.getFontRenderer(), displayText, gui.xStart + pos.x + 5, gui.yStart + pos.y + 2, 0xFFFFFFFF);
        }
    }

    @Override
    public void onClick(int mx, int my, int button, GuiBasic gui) {
        if(getBox(gui).isIn(mx, my)){
            selected = true;
        }else{
            selected = false;
        }
    }

    @Override
    public boolean onKey(int num, char key, GuiBasic gui) {
        if(selected){
            if(num == 14){//remove key

                if(buffer.length() >= 1){
                    buffer = buffer.substring(0, buffer.length()-1);
                }
                return true;
            }else if(ChatAllowedCharacters.isAllowedCharacter(key)){
                buffer += key;
                return true;
            }
        }
        return false;
    }

    @Override
    public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {

    }

    public void setPosition(GuiPoint point){
        pos = point;
    }
}
