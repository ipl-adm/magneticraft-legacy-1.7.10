package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.client.gui.component.*;
import com.cout970.magneticraft.container.ContainerShelvingUnit;
import com.cout970.magneticraft.container.SlotShelvingUnit;
import com.cout970.magneticraft.messages.MessageShelfSlotUpdate;
import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import com.cout970.magneticraft.util.MultilineString;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.cout970.magneticraft.client.gui.component.CompButton.*;

public class GuiShelvingUnit extends GuiBasic {
    private static final ResourceLocation BG_DISABLED = new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/shelving_unit_disabled.png");
    private static final MultilineString CRATE_MESSAGE = new MultilineString("{{0:-20}}This shelving unit{{0:-10}}has no crates!{{0:0}}Right-click it with a chest{{0:10}}to add one.");
    private CompScrollBar scrollBar;
    private ContainerShelvingUnit shelfContainer;
    private int lastCrateCount;
    private int slotRenewal;
    private TileShelvingUnit shelf;
    public List<CompButton> tabButtons;
    private CompTextField textField;

    public GuiShelvingUnit(Container c, TileEntity tile) {
        super(c, tile);
        xTam = xSize = 195;
        yTam = ySize = 204;
    }

    @Override
    public void initComponents() {
        Keyboard.enableRepeatEvents(true);
        shelfContainer = (ContainerShelvingUnit) inventorySlots;
        shelf = shelfContainer.shelf;
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/shelving_unit.png")));
        textField = new CompTextField(Minecraft.getMinecraft().fontRenderer, 9, 6, 87, 7);
        scrollBar = new CompScrollBar(new GuiPoint(175, 18), new GuiPoint(187, 106), 19);
        tabButtons = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            final int tab = i;
            CompButton button = new CompButton(new GuiPoint(173, 158 - 18 * i), 16, 16, new GuiPoint(0, 32 - 16 * i), "textures/gui/buttons.png", (n) -> apply(tab, n))
                    .setUVForState(ButtonState.HOVER, new GuiPoint(16, 32 - 16 * i))
                    .setUVForState(ButtonState.DISABLED, new GuiPoint(32, 32 - 16 * i))
                    .setUVForState(ButtonState.ACTIVE, new GuiPoint(48, 32 - 16 * i))
                    .setClickable(ButtonState.ACTIVE, false);
            tabButtons.add(button);
        }
        slotRenewal = 2;
        lastCrateCount = shelf.getCrateCount();
        comp.addAll(tabButtons);
        shelfContainer.curInv = 0;
    }

    @Override
    public Slot getSlotAtPosition(int x, int y) {
        int inv = -1;
        if ((x >= (guiLeft + 8)) && (x <= (guiLeft + 187)) && (y >= (guiTop + 17)) && (y <= (guiTop + 106))) {
            inv = shelfContainer.curInv;
        }

        if (inv != -1) {
            for (SlotShelvingUnit slot : shelfContainer.currentSlots) {
                if (isMouseOverSlot(slot, x, y)) {
                    return slot;
                }
            }
        } else {
            for (int k = 0; k < inventorySlots.inventorySlots.size(); k++) {
                Slot slot = (Slot) inventorySlots.inventorySlots.get(k);

                if (isMouseOverSlot(slot, x, y)) {
                    if (!(slot instanceof SlotShelvingUnit)) {
                        return slot;
                    }
                }
            }
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void drawGuiContainerBackgroundLayer(float fps, int mx, int my) {
        int allowed = (int) Math.ceil(((float) shelfContainer.shelf.getCrateCount()) / TileShelvingUnit.SHELF_CRATES) - 1;
        assertTabStates(allowed);
        super.drawGuiContainerBackgroundLayer(fps, mx, my);

        textField.render(mx, my, tile, this);

        if (shelf.getCrateCount() != lastCrateCount) {
            slotRenewal = 2;
        }
        lastCrateCount = shelf.getCrateCount();
        shelfContainer.adjustSlots(scrollBar.getScroll(), textField.getText(), slotRenewal);

        if (slotRenewal != 0) {
            ManagerNetwork.INSTANCE.sendToServer(new MessageShelfSlotUpdate(shelfContainer.curInv, scrollBar.getScroll(), textField.getText(), slotRenewal));
        }

        if (Mouse.isButtonDown(0)) {
            scrollBar.onClick(mx, my, 0, this);
        } else {
            scrollBar.setTracking(false);
        }
        scrollBar.render(mx, my, tile, this);

        if (allowed < 0) {
            mc.getTextureManager().bindTexture(BG_DISABLED);
            drawTexturedModalRect(xStart + 7, yStart + 17, 0, 0, 162, 90);
            CRATE_MESSAGE.drawCentered(this, fontRendererObj, xStart + 88, yStart + 62, 0xFFFFFF);
        } else {
            if (textField.getText().isEmpty()) {
                for (int i = 0; i < inventorySlots.inventorySlots.size(); i++) {
                    Object s = inventorySlots.inventorySlots.get(i);
                    if (s instanceof SlotShelvingUnit) {
                        SlotShelvingUnit st = (SlotShelvingUnit) s;
                        if (st.locked && !st.hidden) {
                            mc.getTextureManager().bindTexture(SlotShelvingUnit.BG_LOCKED);
                            GL11.glEnable(GL11.GL_BLEND);
                            GL11.glDisable(GL11.GL_LIGHTING);

                            GL11.glColor4f(1F, 1F, 1F, 1F);
                            drawTexturedModalRect(st.xDisplayPosition + guiLeft, st.yDisplayPosition + guiTop, 0, 0, 16, 16);

                            GL11.glDisable(GL11.GL_BLEND);
                            GL11.glEnable(GL11.GL_LIGHTING);
                        }
                    }
                }
            } else {
                for (int i = shelfContainer.currentSlots.size(); i < 45; i++) {
                    mc.getTextureManager().bindTexture(SlotShelvingUnit.BG_LOCKED);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glDisable(GL11.GL_LIGHTING);

                    GL11.glColor4f(1F, 1F, 1F, 1F);
                    drawTexturedModalRect(xStart + 8 + 18 * (i % 9), yStart + 18 + 18 * (i / 9), 0, 0, 16, 16);

                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_LIGHTING);
                }
            }
        }
    }


    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int dw = Mouse.getEventDWheel();
        if (dw != 0) {
            scrollBar.onWheel(dw / Math.abs(dw));
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int b) {
        textField.onClick(x, y, b, this);
        super.mouseClicked(x, y, b);
    }

    public boolean apply(int tab, int mouseButton) {
        if (mouseButton == 0) {
            mc.playerController.sendEnchantPacket(shelfContainer.windowId, (shelfContainer.curInv = tab));
        }
        slotRenewal = 2;
        return true;
    }

    public void assertTabStates(int allowed) {
        if (allowed >= 0) {
            tabButtons.forEach(n -> n.setCurrentState(ButtonState.NORMAL));
            if (shelfContainer.curInv > allowed) {
                mc.playerController.sendEnchantPacket(shelfContainer.windowId, (shelfContainer.curInv = allowed));
            }
            tabButtons.get(shelfContainer.curInv).setCurrentState(ButtonState.ACTIVE);
        }
        for (int i = allowed + 1; i < tabButtons.size(); i++) {
            tabButtons.get(i).setCurrentState(ButtonState.DISABLED);
        }
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void keyTyped(char letter, int num) {
        int oldLength = textField.getText().length();
        if (textField.textboxKeyTyped(letter, num)) {
            if (textField.getText().length() > oldLength) {
                slotRenewal = Math.max(slotRenewal, 1);
            } else {
                slotRenewal = 2;
            }
        } else {
            super.keyTyped(letter, num);
        }
    }
}
