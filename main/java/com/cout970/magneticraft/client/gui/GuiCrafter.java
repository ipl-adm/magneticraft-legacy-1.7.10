package com.cout970.magneticraft.client.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.client.gui.component.CompBackground;
import com.cout970.magneticraft.client.gui.component.GuiPoint;
import com.cout970.magneticraft.client.gui.component.IGuiComp;
import com.cout970.magneticraft.messages.MessageRedstoneStateUpdate;
import com.cout970.magneticraft.tileentity.TileCrafter;
import com.cout970.magneticraft.tileentity.TileCrafter.RedstoneState;
import com.cout970.magneticraft.util.RenderUtil;

public class GuiCrafter extends GuiBasic{

	public GuiCrafter(Container c, TileEntity tile) {
		super(c, tile);
	}

	@Override
	public void initComponenets() {
		comp.add(new CompBackground(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/crafter.png")));
		comp.add(new CompButtonRedstoneState(new GuiPoint(70, 10)));
	}
	
	public class CompButtonRedstoneState implements IGuiComp{

		public GuiPoint pos;
		
		public CompButtonRedstoneState(GuiPoint pos){
			this.pos = pos;
		}
		
		@Override
		public void render(int mx, int my, TileEntity tile, GuiBasic gui) {
			if(tile instanceof TileCrafter){
				int i = ((TileCrafter) tile).state == RedstoneState.NORMAL ? 0 : ((TileCrafter) tile).state == RedstoneState.INVERTED ? 18 : 36;
				RenderUtil.bindTexture(new ResourceLocation(Magneticraft.NAME.toLowerCase()+":textures/gui/button_states.png"));
				RenderUtil.drawTexturedModalRectScaled(gui.xStart+pos.x, gui.yStart+pos.y, 0, i, 18, 18, 18, 54);
			}
		}

		@Override
		public void onClick(int mx, int my, int buttom, GuiBasic gui) {
			if(gui.isIn(mx, my, pos.x+gui.xStart, pos.y+gui.yStart, 18, 18)){
				if(gui.tile instanceof TileCrafter){
					 RedstoneState state = ((TileCrafter)(gui.tile)).state;
					 state = TileCrafter.step(state);
					 ManagerNetwork.INSTANCE.sendToServer(new MessageRedstoneStateUpdate(gui.tile, state));
				}
			}
		}

		@Override
		public boolean onKey(int n, char key, GuiBasic gui) {
			return false;
		}

		@Override
		public void renderTop(int mx, int my, TileEntity tile, GuiBasic gui) {
			if(gui.isIn(mx, my, pos.x+gui.xStart, pos.y+gui.yStart, 18, 18)){
				if(gui.tile instanceof TileCrafter){
					RedstoneState state = ((TileCrafter)(gui.tile)).state;
					 List<String> data = new ArrayList<String>();
					data.add(state == RedstoneState.NORMAL ? "Signal off" : state == RedstoneState.INVERTED ? "Signal on" : "Pulse");
					gui.drawHoveringText2(data, mx-gui.xStart, my-gui.yStart);
					RenderHelper.enableGUIStandardItemLighting();
				}
			}
		}

	}

}
