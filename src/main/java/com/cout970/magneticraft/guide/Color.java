package com.cout970.magneticraft.guide;

import com.cout970.magneticraft.util.RenderUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.LinkedTreeMap;

public class Color {

    @Expose
    public int r;
    @Expose
    public int g;
    @Expose
    public int b;

    public Color(float r, float g, float b) {
        this.r = (int) (r * 255);
        this.g = (int) (g * 255);
        this.b = (int) (b * 255);
    }

    public Color(LinkedTreeMap<String, Object> map) {
        r = toInt(map, "r");
        g = toInt(map, "g");
        b = toInt(map, "b");
    }

    public int toInteger() {
        return RenderUtil.fromRGB(r, g, b);
    }

    private int toInt(LinkedTreeMap<String, Object> map, String string) {
        Double d = (Double) map.get(string);
        return d.intValue();
    }
}
