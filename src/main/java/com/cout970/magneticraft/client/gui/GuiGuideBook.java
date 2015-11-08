package com.cout970.magneticraft.client.gui;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.client.gui.component.CompButton;
import com.cout970.magneticraft.client.gui.component.CompButton.ButtonState;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.guide.*;
import com.cout970.magneticraft.guide.comps.CompCraftingRecipe;
import com.cout970.magneticraft.guide.comps.CompItemRender;
import com.cout970.magneticraft.guide.comps.CompLargeText;
import com.cout970.magneticraft.guide.comps.CompText;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Stack;

public class GuiGuideBook extends GuiBasic {

    public static ResourceLocation background = new ResourceLocation(
            Magneticraft.ID + ":textures/gui/guide_book.png");
    public static ResourceLocation arrows = new ResourceLocation(
            Magneticraft.ID + ":textures/gui/arrows.png");
    public static ResourceLocation craftingGrid = new ResourceLocation(
            Magneticraft.ID + ":textures/gui/crafting_grid.png");

    public Stack<BookPage> oldPages;
    public BookGuide book;
    public BookPage currentPage;
    public int xStart;
    public int yStart;
    public int xTam, yTam;
    public float scale = 2.5f;
    // editor stuff
    public IPageComp selectedComp;
    public boolean pressed;
    public BookEditMenu menuEdit;

    public GuiGuideBook(Container c) {
        super(c, null);
        xSize = (int) (140 * scale);
        ySize = (int) (93 * scale);
        xTam = xSize;
        yTam = ySize;
        book = GuideBookIO.getBook();
        currentPage = book.getMainPage();
        oldPages = new Stack<>();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float fps, int mx, int my) {
        super.drawGuiContainerBackgroundLayer(fps, mx, my);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        xStart = (width - xSize) / 2;
        yStart = (height - ySize) / 2;
        RenderUtil.bindTexture(background);
        RenderUtil.drawTexturedModalRectScaled(xStart, yStart, 0, 0, (int) (140 * scale), (int) (93 * scale),
                (int) (180 * scale), (int) (132 * scale));
        if (currentPage != null) {
            currentPage.gadgets.stream().filter(holder -> holder != null).forEach(holder -> {
                IPageComp comp = holder.getComponent();
                if (comp != null) {
                    comp.render(mx, my, this, currentPage, book);

                }
            });
        }
        if (menuEdit != null) {
            menuEdit.draw(mx, my, this);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        super.drawGuiContainerForegroundLayer(x, y);
        if (currentPage != null) {
            currentPage.gadgets.stream().filter(holder -> holder != null).forEach(holder -> {
                IPageComp comp = holder.getComponent();
                if (comp != null) {
                    comp.renderTop(x, y, this, currentPage, book);
                }
            });
            if (Magneticraft.DEBUG) {

                currentPage.gadgets.stream().filter(holder -> holder != null).forEach(holder -> {
                    IPageComp comp = holder.getComponent();
                    if (comp != null) {
                        Box2D box = comp.getBox();
                        int color = selectedComp == comp ? 0xFFFF0000 : 0xFFFFFFFF;
                        box.draw(color);
                    }
                });

                if (selectedComp != null) {
                    if (Mouse.isButtonDown(0)) {
                        if (!pressed) {
                            pressed = isIn(selectedComp.getBox().copy().translate(xStart, yStart), x, y);
                        }
                        if (pressed) {
                            GuiPoint newPos = selectedComp.getPosition();
                            float dx = Math.round(Mouse.getDX() * 0.35F);
                            float dy = Math.round(Mouse.getDY() * 0.35F);

                            newPos.x += dx;
                            newPos.y -= dy;
                            selectedComp.setPosition(newPos);
                        }
                    } else pressed = false;
                }
            }
        }
    }

    /**
     * b == 0 => left b == 1 => right
     */
    protected void mouseClicked(int x, int y, int b) {
        super.mouseClicked(x, y, b);

        if (menuEdit != null) {
            menuEdit.onClick(x, y, b, this);
        }

        if (currentPage != null) {
            currentPage.gadgets.stream().filter(holder -> holder != null).forEach(holder -> {
                IPageComp comp = holder.getComponent();
                if (comp != null) {
                    comp.onClick(x, y, b, this, currentPage, book);
                    if (b == 0 && isIn(comp.getBox().copy().translate(xStart, yStart), x, y)) {
                        selectedComp = comp;
                    } else if (b == 1) {
                        selectedComp = null;
                        menuEdit = null;
                    }
                }
            });
        }
    }

    public static boolean isIn(Box2D box, int mx, int my) {
        return GuiBasic.isIn(mx, my, box.getMinX(), box.getMinY(), box.getMaxX() - box.getMinX(), box.getMaxY() - box.getMinY());
    }

    protected void keyTyped(char letra, int num) {
        boolean block = false;
        // TODO remove this later
        // {
        if (num == 19) {
            GuideBookIO.loadBook();
            book = GuideBookIO.book;
            currentPage = book.getMainPage();
        }
        if (num == 20) {
            GuideBookIO.saveBook();
            book = GuideBookIO.book;
            currentPage = book.getMainPage();
        }
        // }
        if (num == 14) {
            if (!oldPages.empty()) {
                currentPage = oldPages.pop();
            }
        }

        if (menuEdit != null) {

            if(menuEdit.onKey(letra, num, this)){
                block = true;
            }
        }

        if (currentPage != null) {
            for (CompHolder holder : currentPage.gadgets) {
                if (holder != null) {
                    IPageComp comp = holder.getComponent();
                    if (comp != null) {
                        if (comp.onKey(num, letra, this, currentPage, book)) {
                            block = true;
                            break;
                        }
                    }
                }
            }
        }
        if (!block)
            super.keyTyped(letra, num);
    }

    public FontRenderer getFontRenderer() {
        return this.fontRendererObj;
    }

    public void drawHoveringText2(List<String> data, int x, int y) {
        this.drawHoveringText(data, x, y, fontRendererObj);
    }

    public void changePage(String page) {
        BookPage p = book.getPage(page);
        if (p != null) {
            oldPages.push(currentPage);
            currentPage = p;
        }
    }

    @Override
    public void initComponents() {
        if (Magneticraft.DEBUG) {
            Log.debug("Starting guide book edit mode");
            comp.add(new CompButton(new GuiPoint(-18, 18), 16, 16,
                    new GuiPoint(0, 96), "textures/gui/buttons.png", (n) -> apply(0, n))
                    .setUVForState(ButtonState.HOVER, new GuiPoint(16, 96))
                    .setUVForState(ButtonState.DISABLED, new GuiPoint(32, 96))
                    .setUVForState(ButtonState.ACTIVE, new GuiPoint(48, 96))
                    .setClickable(ButtonState.ACTIVE, false));
            comp.add(new CompButton(new GuiPoint(-18, 18 * 2), 16, 16,
                    new GuiPoint(0, 112), "textures/gui/buttons.png", (n) -> apply(1, n))
                    .setUVForState(ButtonState.HOVER, new GuiPoint(16, 112))
                    .setUVForState(ButtonState.DISABLED, new GuiPoint(32, 112))
                    .setUVForState(ButtonState.ACTIVE, new GuiPoint(48, 112))
                    .setClickable(ButtonState.ACTIVE, false));
            comp.add(new CompButton(new GuiPoint(-18, 18 * 3), 16, 16,
                    new GuiPoint(0, 128), "textures/gui/buttons.png", (n) -> apply(2, n))
                    .setUVForState(ButtonState.HOVER, new GuiPoint(16, 128))
                    .setUVForState(ButtonState.DISABLED, new GuiPoint(32, 128))
                    .setUVForState(ButtonState.ACTIVE, new GuiPoint(48, 128))
                    .setClickable(ButtonState.ACTIVE, false));
            comp.add(new CompButton(new GuiPoint(-18, 18 * 4), 16, 16,
                    new GuiPoint(0, 144), "textures/gui/buttons.png", (n) -> apply(3, n))
                    .setUVForState(ButtonState.HOVER, new GuiPoint(16, 144))
                    .setUVForState(ButtonState.DISABLED, new GuiPoint(32, 144))
                    .setUVForState(ButtonState.ACTIVE, new GuiPoint(48, 144))
                    .setClickable(ButtonState.ACTIVE, false));
            comp.add(new CompButton(new GuiPoint(-18, 18 * 5), 16, 16,
                    new GuiPoint(0, 160), "textures/gui/buttons.png", (n) -> apply(4, n))
                    .setUVForState(ButtonState.HOVER, new GuiPoint(16, 160))
                    .setUVForState(ButtonState.DISABLED, new GuiPoint(32, 160))
                    .setUVForState(ButtonState.ACTIVE, new GuiPoint(48, 160))
                    .setClickable(ButtonState.ACTIVE, false));
            comp.add(new CompButton(new GuiPoint(-18, 18 * 6), 16, 16,
                    new GuiPoint(0, 176), "textures/gui/buttons.png", (n) -> apply(5, n))
                    .setUVForState(ButtonState.HOVER, new GuiPoint(16, 176))
                    .setUVForState(ButtonState.DISABLED, new GuiPoint(32, 176))
                    .setUVForState(ButtonState.ACTIVE, new GuiPoint(48, 176))
                    .setClickable(ButtonState.ACTIVE, false));
            comp.add(new CompButton(new GuiPoint(-18, 18 * 7), 16, 16,
                    new GuiPoint(0, 192), "textures/gui/buttons.png", (n) -> apply(6, n))
                    .setUVForState(ButtonState.HOVER, new GuiPoint(16, 192))
                    .setUVForState(ButtonState.DISABLED, new GuiPoint(32, 192))
                    .setUVForState(ButtonState.ACTIVE, new GuiPoint(48, 192))
                    .setClickable(ButtonState.ACTIVE, false));
            comp.add(new CompButton(new GuiPoint(-18, 18 * 8), 16, 16,
                    new GuiPoint(0, 208), "textures/gui/buttons.png", (n) -> apply(7, n))
                    .setUVForState(ButtonState.HOVER, new GuiPoint(16, 208))
                    .setUVForState(ButtonState.DISABLED, new GuiPoint(32, 208))
                    .setUVForState(ButtonState.ACTIVE, new GuiPoint(48, 208))
                    .setClickable(ButtonState.ACTIVE, false));
        }
    }

    public Boolean apply(Integer type, Integer input) {
        if (type == 0) {//save
            GuideBookIO.saveBook();
        } else if (type == 1) {//single text
            selectedComp = new CompText(80, -20, "sample text", new Color(1, 1, 1), false);
            currentPage.addComponent(selectedComp);
        } else if (type == 2) {//crafting
            selectedComp = new CompCraftingRecipe(new ItemStack[10], 20, -50);
            currentPage.addComponent(selectedComp);
        } else if (type == 3) {//large text
            selectedComp = new CompLargeText(80, -20, new String[]{"sample text"}, new Color(1, 1, 1), false);
            currentPage.addComponent(selectedComp);
        } else if (type == 4) {//single item
            selectedComp = new CompItemRender(new ItemStack(Items.apple), 80, -20);
            currentPage.addComponent(selectedComp);
        } else if (type == 5) {//link

        } else if (type == 6) {//image

        } else if (type == 7) {//edit menu
            if (selectedComp != null)
                menuEdit = new BookEditMenu(selectedComp, this);
        } else {
            return false;
        }
        return true;
    }
}
