package com.cout970.magneticraft.util;

import com.cout970.magneticraft.Magneticraft;
import cpw.mods.fml.client.CustomModLoadingErrorDisplayException;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiErrorScreen;
import org.lwjgl.input.Mouse;

import java.awt.*;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.List;

@SuppressWarnings("unchecked")
@SideOnly(Side.CLIENT)
public class OutdatedJavaException extends CustomModLoadingErrorDisplayException {
    private static final String[] errorMessages = {
            "Current Java version is outdated",
            "Magneticraft cannot be used with versions of Java older than " + Magneticraft.MIN_JAVA,
            "Please download newest version at http://java.com"
    };

    GuiButton button;

    @Override
    public void initGui(GuiErrorScreen errorScreen, FontRenderer fontRenderer) {
        button = new GuiButton(100, errorScreen.width / 2, errorScreen.height / 2 + errorMessages.length * 10, "Go to Java Website");
        button.xPosition -= button.width / 2;
    }

    @Override
    public void drawScreen(GuiErrorScreen errorScreen, FontRenderer fontRenderer, int mouseRelX, int mouseRelY, float tickTime) {
        int y = errorScreen.height / 2 - errorMessages.length * 10;
        for (String msg : errorMessages) {
            errorScreen.drawCenteredString(fontRenderer, msg, errorScreen.width / 2, y, 0xFFFFFF);
            y += 20;
        }
        button.drawButton(errorScreen.mc, mouseRelX, mouseRelY);
        if (Mouse.isButtonDown(0) && button.mousePressed(errorScreen.mc, mouseRelX, mouseRelY)) {
            button.func_146113_a(errorScreen.mc.getSoundHandler());
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(new URI("http://java.com"));
                } catch (Exception ignored) {

                }
            }
        }
    }
}
