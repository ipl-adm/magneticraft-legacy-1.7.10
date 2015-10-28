package com.cout970.magneticraft.util;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class MultilineString {
    public List<OffsetString> strings;
    public MultilineString(String origin) {
        String[] t = origin.split("\\{\\{");
        strings = new ArrayList<>(t.length);
        for (String s : t) {
            if (s.isEmpty()) {
                continue;
            }
            try {
                String[] split1 = s.split("\\}\\}", 2);
                String[] split2 = split1[0].split(":");
                strings.add(new OffsetString(Integer.parseInt(split2[0]), Integer.parseInt(split2[1]), split1[1]));
            } catch (Exception e) {
                throw new IllegalArgumentException("Malformed multi-line: " + origin, e);
            }
        }
    }

    public static class OffsetString {
        private final int xOffset, yOffset;
        private final String base;
        public OffsetString(int x, int y, String base) {
            xOffset = x;
            yOffset = y;
            this.base = base;
        }

        public int getYOffset() {
            return yOffset;
        }

        public int getXOffset() {
            return xOffset;
        }

        public String getBase() {
            return base;
        }
    }

    public void drawCentered(GuiScreen gui, FontRenderer fontRenderer, int x, int y, int color) {
        strings.forEach(n -> gui.drawCenteredString(fontRenderer, n.getBase(), x + n.getXOffset(), y + n.getYOffset(), color));
    }
}
