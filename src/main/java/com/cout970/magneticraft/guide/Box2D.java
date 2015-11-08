package com.cout970.magneticraft.guide;

import com.cout970.magneticraft.client.gui.component.GuiPoint;
import net.minecraft.client.gui.Gui;

public class Box2D {

    private GuiPoint min;
    private GuiPoint max;

    public Box2D(int x1, int y1, int x2, int y2) {
        min = new GuiPoint(Math.min(x1, x2), Math.min(y1, y2));
        max = new GuiPoint(Math.max(x1, x2), Math.max(y1, y2));
    }

    public Box2D(GuiPoint a, GuiPoint b) {
        this(a.x, a.y, b.x, b.y);
    }

    public int getMinX() {
        return min.x;
    }

    public int getMinY() {
        return min.y;
    }

    public int getMaxX() {
        return max.x;
    }

    public int getMaxY() {
        return max.y;
    }

    public int sizeX() {
        return max.x - min.x;
    }

    public int sizeY() {
        return max.y - min.y;
    }

    public GuiPoint min() {
        return min.copy();
    }

    public GuiPoint max() {
        return max.copy();
    }

    public Box2D copy() {
        return new Box2D(max, min);
    }

    public Box2D translate(int x, int y) {
        min.x += x;
        max.x += x;

        min.y += y;
        max.y += y;
        return this;
    }

    public boolean isIn(int x, int y) {
        if (x >= getMinX() && x <= getMaxX()) {
            if (y >= getMinY() && y <= getMaxY()) {
                return true;
            }
        }
        return false;
    }

    public void draw(int color){
        Gui.drawRect(getMinX(), getMinY(), getMinX() + 1, getMaxY(), color);
        Gui.drawRect(getMinX(), getMaxY(), getMaxX(), getMaxY() + 1, color);
        Gui.drawRect(getMinX(), getMinY(), getMaxX(), getMinY() + 1, color);
        Gui.drawRect(getMaxX(), getMinY(), getMaxX() + 1, getMaxY()+1, color);
    }

    @Override
    public String toString() {
        return "Box2D{" +
                "min=" + min +
                ", max=" + max +
                '}';
    }
}
