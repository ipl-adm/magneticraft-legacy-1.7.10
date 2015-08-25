package com.cout970.magneticraft.client.gui;

import java.util.List;
import java.util.Stack;

import org.lwjgl.opengl.GL11;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.guide.BookGuide;
import com.cout970.magneticraft.guide.BookPage;
import com.cout970.magneticraft.guide.CompHolder;
import com.cout970.magneticraft.guide.GuideBookIO;
import com.cout970.magneticraft.guide.IPageComp;
import com.cout970.magneticraft.util.RenderUtil;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiGuideBook extends GuiContainer {

	public static ResourceLocation background = new ResourceLocation(Magneticraft.ID + ":textures/gui/guide_book.png");
	public static ResourceLocation arrows = new ResourceLocation(Magneticraft.ID + ":textures/gui/arrows.png");

	public Stack<BookPage> oldPages;
	public BookGuide book;
	public BookPage currentPage;
	public int xStart;
	public int yStart;
	public int xTam, yTam;
	public float scale = 2.5f;

	public GuiGuideBook(Container c) {
		super(c);
		xSize = (int) (140 * scale);
		ySize = (int) (93 * scale);
		xTam = xSize;
		yTam = ySize;
		book = GuideBookIO.getBook();
		currentPage = book.getMainPage();
		oldPages = new Stack<BookPage>();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float fps, int mx, int my) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		xStart = (width - xSize) / 2;
		yStart = (height - ySize) / 2;
		RenderUtil.bindTexture(background);
		RenderUtil.drawTexturedModalRectScaled(xStart, yStart, 0, 0, (int) (140 * scale), (int) (93 * scale),
				(int) (180 * scale), (int) (132 * scale));
		if (currentPage != null) {
			for (CompHolder holder : currentPage.gadgets) {
				if (holder != null) {
					IPageComp comp = holder.getComponent();
					if (comp != null) {
						comp.render(mx, my, this, currentPage, book);
					}
				}
			}
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int x, int y) {
		if (currentPage != null) {
			for (CompHolder holder : currentPage.gadgets) {
				if (holder != null) {
					IPageComp comp = holder.getComponent();
					if (comp != null) {
						comp.renderTop(x, y, this, currentPage, book);
					}
				}
			}
		}
	}

	/**
	 * b == 0 => left b == 1 => right
	 */
	protected void mouseClicked(int x, int y, int b) {
		super.mouseClicked(x, y, b);
		if (currentPage != null) {
			for (CompHolder holder : currentPage.gadgets) {
				if (holder != null) {
					IPageComp comp = holder.getComponent();
					if (comp != null) {
						comp.onClick(x, y, b, this, currentPage, book);
					}
				}
			}
		}
	}

	public boolean isIn(int mx, int my, int x, int y, int w, int h) {
		if (mx > x && mx < x + w) {
			if (my > y && my < y + h) {
				return true;
			}
		}
		return false;
	}

	protected void keyTyped(char letra, int num) {
		boolean block = false;
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
		if (num == 14) {
			if (!oldPages.empty()) {
				currentPage = oldPages.pop();
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
}
