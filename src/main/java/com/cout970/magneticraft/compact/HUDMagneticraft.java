package com.cout970.magneticraft.compact;

import java.util.List;

import com.cout970.magneticraft.api.heat.HeatUtils;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tileentity.TileCopperTank;
import com.cout970.magneticraft.tileentity.TileRefineryTank;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import net.minecraft.item.ItemStack;

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
		IHeatConductor[] comp = HeatUtils.getHeatCond(accessor.getTileEntity(), VecInt.NULL_VECTOR);
		if(comp != null){
			for(IHeatConductor c : comp){
				int h = 0;
				if(c != null){
					h = (int) c.getTemperature();
				}

				String s = "Heat: "+h;
				currenttip.add(s);
			}
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