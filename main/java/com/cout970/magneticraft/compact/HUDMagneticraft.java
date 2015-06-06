package com.cout970.magneticraft.compact;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.item.ItemStack;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.IHeatTile;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tileentity.TileCopperTank;
import com.cout970.magneticraft.tileentity.TileRefineryTank;

public class HUDMagneticraft implements IWailaDataProvider{

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack,List<String> currenttip, IWailaDataAccessor accessor,IWailaConfigHandler config){
		if(accessor.getTileEntity() instanceof IHeatTile){
			IHeatConductor c = ((IHeatTile)accessor.getTileEntity()).getHeatCond(VecInt.NULL_VECTOR);
			int h = 0;
			if(c != null){
				h = (int) c.getTemperature();
			}
			
			String s = "Heat: "+h;
			currenttip.add(s);
		}
		
		if(accessor.getTileEntity() instanceof TileCopperTank){
			String s = "Tank empty";
			TileCopperTank t = (TileCopperTank) accessor.getTileEntity();
			if(t.getTank().getFluidAmount() > 0){
				s = "Fluid: "+t.getTank().getFluid().getLocalizedName()+" Amount: "+t.getTank().getFluid().amount;
			}
			currenttip.add(s);
		}
		if(accessor.getTileEntity() instanceof TileRefineryTank){
			String s = "Tank empty";
			TileRefineryTank t = (TileRefineryTank) accessor.getTileEntity();
			if(t.getTank().getFluidAmount() > 0){
				s = "Fluid: "+t.getTank().getFluid().getLocalizedName()+" Amount: "+t.getTank().getFluid().amount;
			}
			currenttip.add(s);
		}
		return currenttip;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack,List<String> currenttip, IWailaDataAccessor accessor,IWailaConfigHandler config) {
		return currenttip;
	}
	
}