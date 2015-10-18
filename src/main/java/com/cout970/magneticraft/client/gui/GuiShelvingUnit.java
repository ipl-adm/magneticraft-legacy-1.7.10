package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompScrollBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.container.ContainerShelvingUnit;
import com.cout970.magneticraft.container.SlotShelvingUnit;
import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiShelvingUnit extends GuiBasic {
    private CompScrollBar scrollBar;
    private ContainerShelvingUnit shelfContainer;

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

    @Override
    protected void drawGuiContainerBackgroundLayer(float fps, int mx, int my) {
        super.drawGuiContainerBackgroundLayer(fps, mx, my);
        TileShelvingUnit shelf = shelfContainer.shelf;

        if (Mouse.isButtonDown(0)) {
            scrollBar.onClick(mx, my, 0, this);
        } else {
            scrollBar.setTracking(false);
        }
        scrollBar.render(mx, my, tile, this);

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
        super.mouseClicked(x, y, b);
        if (isIn(x, y, xStart + 175, yStart + 122, 16, 16) && (b == 0)) {
            mc.playerController.sendEnchantPacket(shelfContainer.windowId, (shelfContainer.curInv = (shelfContainer.curInv + 1) % 3));
        }
    }
}
