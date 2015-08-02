package com.cout970.magneticraft.util.energy;

import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.electricity.IEnergyInterfaceFactory;
import com.cout970.magneticraft.api.electricity.InteractionHelper;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

import cofh.api.energy.IEnergyHandler;
import ic2.api.energy.tile.IEnergySink;
import mods.railcraft.api.electricity.IElectricGrid;
import mods.railcraft.api.electricity.IElectricGrid.ChargeHandler.ConnectType;
import net.minecraft.tileentity.TileEntity;

public class EnergyInterfaceFactory implements IEnergyInterfaceFactory{
	
	public static void init(){
		InteractionHelper.registerEnergyInterfaceFactory(new EnergyInterfaceFactory());
	}

	@Override
	public boolean shouldHandleTile(TileEntity tile, VecInt f, int tier) {
		if(tier != 0)return false;
		return tile instanceof IElectricGrid || tile instanceof IEnergyHandler || tile instanceof IEnergySink;
	}

	@Override
	public IEnergyInterface getEnergyInterface(TileEntity tile, VecInt f,int tier) {
		if(tier == 0){
			if(tile instanceof IElectricGrid){
				if(((IElectricGrid) tile).getChargeHandler().getType() == ConnectType.BLOCK)
					return getElectricalGrid((IElectricGrid) tile);
			}
			if(tile instanceof IEnergyHandler && f.toMgDirection() != null){
				if(((IEnergyHandler) tile).canConnectEnergy(f.toMgDirection().toForgeDir())){
					return getEnergyHandler((IEnergyHandler)tile,f.toMgDirection());
				}
			}
			if(tile instanceof IEnergySink && f.toMgDirection() != null){
				if(((IEnergySink)tile).acceptsEnergyFrom(null, f.toMgDirection().toForgeDir())){
					return getEnergySink((IEnergySink) tile, f.toMgDirection());
				}
			}
		}
		return null;
	}

	public static IEnergyInterface getEnergySink(IEnergySink tile, MgDirection dir) {
		return new EU_EnergyInterfaceSink(tile, dir);
	}

	public static IEnergyInterface getElectricalGrid(IElectricGrid g){
		return new RailcraftChargeEnergyInteface(g);
	}

	public static IEnergyInterface getEnergyHandler(IEnergyHandler tile, MgDirection dir) {
		return new RF_EnergyInterface(tile, dir);
	}
}
