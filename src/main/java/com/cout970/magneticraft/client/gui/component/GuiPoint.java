package com.cout970.magneticraft.client.gui.component;

public class GuiPoint {
    public int x, y;

    public GuiPoint(int i, int j) {
        x = i;
        y = j;
    }

	public GuiPoint copy() {
		return new GuiPoint(x, y);
	}
}
