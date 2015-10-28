package com.cout970.magneticraft.util;

public class MultilineStringTest {
    public static void main(String[] args) {
        MultilineString mls = new MultilineString("{{-5:-10}}String 1{{-5:10}}String 2{{100:-1000}}");
        for (MultilineString.OffsetString s : mls.strings) {
            System.out.println(s.getXOffset() + " " + s.getYOffset() + " " + s.getBase());
        }
    }

}
