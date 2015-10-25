package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.DefaultEnumMap;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;


public class CompButton implements IGuiComp {
    public ResourceLocation texture;
    Consumer<Integer> function;
    GuiPoint pos;
    private ButtonState curState;
    DefaultEnumMap<ButtonState, GuiPoint> uvMap;
    private int width, height;
    private boolean isDown;

    public CompButton(GuiPoint start, int width, int height, GuiPoint uv, String texture, Consumer<Integer> func) {
        this.texture = new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":" + texture);
        function = func;
        pos = start;
        curState = ButtonState.NORMAL;
        uvMap = new DefaultEnumMap<>(ButtonState.class, uv);
        this.width = width;
        this.height = height;
        isDown = false;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        ButtonState drawState = getState(mx, my, tile, gui);
        GuiPoint curUV;
        if ((!uvMap.containsKey(drawState) || uvMap.get(drawState) == null) && (drawState.parent != null)) {
            curUV = uvMap.getOrDefault(drawState.parent);
        } else {
            curUV = uvMap.getOrDefault(drawState);
        }

        gui.mc.renderEngine.bindTexture(texture);
        gui.drawTexturedModalRect(gui.xStart + pos.x, gui.yStart + pos.y, curUV.x, curUV.y, width, height);
    }

    @Override
    public void onClick(int mx, int my, int button, GuiBasic gui) {
        if (GuiBasic.isIn(mx, my, pos.x + gui.xStart, pos.y + gui.yStart, width, height)) {
            function.accept(button);
        }
    }

    public void setUVForState(ButtonState state, GuiPoint uv) {
        uvMap.put(state, uv);
    }

    public void resetUVForState(ButtonState state) {
        uvMap.remove(state);
    }

    public ButtonState getState(int mx, int my, TileEntity tile, GuiBasic gui) {
        if (curState == ButtonState.DISABLED) {
            return ButtonState.DISABLED;
        }
        if (isDown) {
            return (curState == ButtonState.ACTIVE) ? ButtonState.ACTIVE_DOWN : ButtonState.DOWN;
        }
        if (GuiBasic.isIn(mx, my, pos.x + gui.xStart, pos.y + gui.yStart, width, height)) {
            return (curState == ButtonState.ACTIVE) ? ButtonState.ACTIVE_HOVER : ButtonState.HOVER;
        }
        return curState;
    }

    public void setCurrentState(ButtonState state) {
        curState = state;
    }

    @Override
    public boolean onKey(int n, char key, GuiBasic gui) {
        return false;
    }


    @Override
    public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {

    }

    public enum ButtonState {
        NORMAL(null),
        HOVER(NORMAL),
        DOWN(NORMAL),
        DISABLED(null),
        ACTIVE(null),
        ACTIVE_HOVER(ACTIVE),
        ACTIVE_DOWN(ACTIVE),
        HIGHLIGHT(null);

        ButtonState parent;

        ButtonState(ButtonState parent) {
            this.parent = parent;
        }
    }
}
