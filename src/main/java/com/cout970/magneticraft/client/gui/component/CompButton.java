package com.cout970.magneticraft.client.gui.component;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.GuiBasic;
import com.cout970.magneticraft.util.DefaultEnumMap;
import com.google.common.base.Function;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;


public class CompButton implements IGuiComp {
    public ResourceLocation texture;
    Function<Integer, Boolean> function;
    GuiPoint pos;
    protected ButtonState curState;
    DefaultEnumMap<ButtonState, GuiPoint> uvMap;
    protected Set<ButtonState> blockedStates;
    protected int width, height;
    protected boolean isDown;

    public CompButton(GuiPoint start, int width, int height, GuiPoint uv, String texture, Function<Integer, Boolean> func) {
        this.texture = new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":" + texture);
        function = func;
        pos = start;
        curState = ButtonState.NORMAL;

        uvMap = new DefaultEnumMap<>(ButtonState.class, uv);
        blockedStates = EnumSet.of(ButtonState.DISABLED);

        this.width = width;
        this.height = height;
        isDown = false;
    }

    @Override
    public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
        ButtonState drawState = getState(mx, my, tile, gui);
        GuiPoint curUV = getUV(drawState);

        gui.mc.renderEngine.bindTexture(texture);
        gui.drawTexturedModalRect(gui.xStart + pos.x, gui.yStart + pos.y, curUV.x, curUV.y, width, height);
    }

    private GuiPoint getUV(ButtonState drawState) {
        if ((!uvMap.containsKey(drawState) || uvMap.get(drawState) == null) && (drawState.parent != null)) {
            return getUV(drawState.parent);
        } else {
            return uvMap.getOrDefault(drawState);
        }
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onClick(int mx, int my, int button, GuiBasic gui) {
        if (GuiBasic.isIn(mx, my, pos.x + gui.xStart, pos.y + gui.yStart, width, height) && (!blockedStates.contains(curState))) {
            if (function.apply(button)) {
                gui.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
            }
        }
    }

    public CompButton setUVForState(ButtonState state, GuiPoint uv) {
        uvMap.put(state, uv);
        return this;
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

    public CompButton setCurrentState(ButtonState state) {
        curState = state;
        return this;
    }

    @Override
    public boolean onKey(int n, char key, GuiBasic gui) {
        return false;
    }


    @Override
    public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {

    }

    public CompButton setClickable(ButtonState state, boolean clickable) {
        if (clickable) {
            blockedStates.remove(state);
        } else {
            blockedStates.add(state);
        }
        return this;
    }

    public enum ButtonState {
        NORMAL(null),
        HOVER(NORMAL),
        DOWN(HOVER),
        DISABLED(null),
        ACTIVE(null),
        ACTIVE_HOVER(ACTIVE),
        ACTIVE_DOWN(ACTIVE_HOVER),
        HIGHLIGHT(null);

        ButtonState parent;

        ButtonState(ButtonState parent) {
            this.parent = parent;
        }
    }
}
