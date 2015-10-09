package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.CompScrollBar;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.container.ContainerShelvingUnit;
import com.cout970.magneticraft.container.SlotShelvingUnit;
import com.cout970.magneticraft.tileentity.shelf.TileShelvingUnit;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

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
        IInventory inv = mc.thePlayer.inventory;
        if ((x >= (guiLeft + 8)) && (x <= (guiLeft + 187)) && (y >= (guiTop + 17)) && (y <= (guiTop + 106))) {
            inv = ((ContainerShelvingUnit) inventorySlots).shelf.getInv(shelfContainer.curInv);
        }

        for (int k = 0; k < this.inventorySlots.inventorySlots.size(); k++) {
            Slot slot = (Slot) this.inventorySlots.inventorySlots.get(k);

            if (isMouseOverSlot(slot, x, y) && slot.inventory.equals(inv)) {
                return slot;
            }
        }

        return null;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float fps, int mx, int my) {
        super.drawGuiContainerBackgroundLayer(fps, mx, my);
        TileShelvingUnit shelf = shelfContainer.shelf;
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
            }
        }
        if (Mouse.isButtonDown(0)) {
            scrollBar.onClick(mx, my, 0, this);
        } else {
            scrollBar.setTracking(false);
        }
        scrollBar.render(mx, my, tile, this);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        int dw = Mouse.getEventDWheel();
        if (dw != 0) {
            scrollBar.onWheel(dw / Math.abs(dw));
        }
    }
}
