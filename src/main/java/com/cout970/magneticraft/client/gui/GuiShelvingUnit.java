package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompButton;
import com.cout970.magneticraft.client.gui.component.CompScrollBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.container.ContainerShelvingUnit;
import com.cout970.magneticraft.container.SlotShelvingUnit;
import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

import static com.cout970.magneticraft.client.gui.component.CompButton.*;

public class GuiShelvingUnit extends GuiBasic {
    private static final ResourceLocation BG_DISABLED = new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/shelving_unit_disabled.png");
    private static final double upscale = 2;
    private static final double downscale = 0.5d;
    private CompScrollBar scrollBar;
    private ContainerShelvingUnit shelfContainer;
    List<CompButton> tabButtons;

    boolean shade = false;

    public GuiShelvingUnit(Container c, TileEntity tile) {
        super(c, tile);
        shelfContainer = (ContainerShelvingUnit) c;
        xTam = xSize = 195;
        yTam = ySize = 204;
    }

    @Override
    public void initComponents() {
        comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase() + ":textures/gui/shelving_unit.png")));
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
        comp.addAll(tabButtons);
        if (shelfContainer != null) {
            shelfContainer.curInv = 0;
        }
    }

    @Override
    public Slot getSlotAtPosition(int x, int y) {
        int inv = -1;
        if ((x >= (guiLeft + 8)) && (x <= (guiLeft + 187)) && (y >= (guiTop + 17)) && (y <= (guiTop + 106))) {
            inv = shelfContainer.curInv;
        }

        for (int k = 0; k < inventorySlots.inventorySlots.size(); k++) {
            Slot slot = (Slot) inventorySlots.inventorySlots.get(k);

            if (isMouseOverSlot(slot, x, y)) {
                int slotInv = (slot instanceof SlotShelvingUnit) ? ((SlotShelvingUnit) slot).invNum : -1;
                if (slotInv == inv) {
                    return slot;
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
        TileShelvingUnit shelf = shelfContainer.shelf;

        if (Mouse.isButtonDown(0)) {
            scrollBar.onClick(mx, my, 0, this);
        } else {
            scrollBar.setTracking(false);
        }
        scrollBar.render(mx, my, tile, this);

        if (allowed < 0) {
            inventorySlots.inventorySlots.stream().filter(s -> s instanceof SlotShelvingUnit).forEach(s -> {
                ((SlotShelvingUnit) s).lock();
                ((SlotShelvingUnit) s).hide();
            });

            mc.getTextureManager().bindTexture(BG_DISABLED);
            drawTexturedModalRect(xStart + 7, yStart + 17, 0, 0, 162, 90);
            if (!shade) {
                drawCenteredStringWithoutShadow(fontRendererObj, "This shelving unit has no crates!", xStart + 88, yStart + 57, 0xFFFFFF);
                drawCenteredStringWithoutShadow(fontRendererObj, "Right-click it with a chest to add one.", xStart + 88, yStart + 67, 0xFFFFFF);
            } else {
                drawCenteredString/*WithoutShadow*/(fontRendererObj, "This shelving unit has no crates!", xStart + 88, yStart + 57, 0xFFFFFF);
                drawCenteredString/*WithoutShadow*/(fontRendererObj, "Right-click it with a chest to add one.", xStart + 88, yStart + 67, 0xFFFFFF);
            }
        } else {
            for (int i = 0; i < inventorySlots.inventorySlots.size(); i++) {
                Object s = inventorySlots.inventorySlots.get(i);
                if (s instanceof SlotShelvingUnit) {
                    SlotShelvingUnit st = (SlotShelvingUnit) s;
                    st.show();
                    st.unlock();
                    st.yDisplayPosition = st.baseY - 18 * scrollBar.getScroll();
                    if ((st.yDisplayPosition < 17) || (st.yDisplayPosition > 106) || (st.invNum != shelfContainer.curInv)) {
                        st.hide();
                    }
                    if (i >= shelf.getCrateCount() * TileShelvingUnit.CRATE_SIZE) {
                        st.lock();
                    }
                    if (st.locked && !st.hidden) {
                        mc.getTextureManager().bindTexture(SlotShelvingUnit.BG_LOCKED);
                        GL11.glEnable(GL11.GL_BLEND);
                        GL11.glDisable(GL11.GL_LIGHTING);
                        //drawTexturedModelRectFromIcon(st.xDisplayPosition + xStart, st.yDisplayPosition + yStart, st.getBackgroundIconIndex(), 16, 16); // Rescale problems

                        GL11.glColor4f(1F, 1F, 1F, 1F);
                        drawTexturedModalRect(st.xDisplayPosition + guiLeft, st.yDisplayPosition + guiTop, 0, 0, 16, 16);

                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glEnable(GL11.GL_LIGHTING);
                    }
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

    public boolean apply(int tab, int mouseButton) {
        if (mouseButton == 0) {
            mc.playerController.sendEnchantPacket(shelfContainer.windowId, (shelfContainer.curInv = tab));
        }
        return true;
    }

    public void assertTabStates(int allowed) {
        if (allowed >= 0) {
            tabButtons.stream().forEach(n -> n.setCurrentState(ButtonState.NORMAL));
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
    protected void keyTyped(char letter, int num) {
        super.keyTyped(letter, num);
        if (letter == 'r') {
            shade = !shade;
        }
    }
}
