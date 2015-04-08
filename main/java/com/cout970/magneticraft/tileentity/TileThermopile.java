package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;

import com.cout970.magneticraft.api.acces.IThermopileDecay;
import com.cout970.magneticraft.api.acces.MgRegister;
import com.cout970.magneticraft.api.electricity.Conductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.ThermopileFuel;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileThermopile extends TileConductorLow{

	private int ticks;
	private double diff;

	@Override
	public IElectricConductor initConductor() {
		return new Conductor(this);
	}

	public void updateEntity(){
		super.updateEntity();
		
		if(worldObj.isRemote)return;
		if (this.cond.getVoltage() <= ElectricConstants.MAX_VOLTAGE){

			++this.ticks;
			if (this.ticks > 20)
			{
				this.ticks = 0;
				this.updateTemps();
			}
			this.cond.applyPower(diff);
		}
	}

	private void updateTemps() {
		double tempHot = 0,tempCold = 0;
		List<BlockInfo> list = new ArrayList<BlockInfo>();
		for(MgDirection d : MgDirection.VALID_DIRECTIONS){
			Block bl = worldObj.getBlock(xCoord+d.getOffsetX(), yCoord+d.getOffsetY(), zCoord+d.getOffsetZ());
			int meta = worldObj.getBlockMetadata(xCoord+d.getOffsetX(), yCoord+d.getOffsetY(), zCoord+d.getOffsetZ());
			BlockInfo b = new BlockInfo(bl, meta, xCoord+d.getOffsetX(), yCoord+d.getOffsetY(), zCoord+d.getOffsetZ());
			list.add(b);
			tempHot += getHeat(b);
			tempCold += getCold(b);		
		}
		diff = Math.min(tempHot, tempCold);
		for(IThermopileDecay t : MgRegister.ThermopileDecais){
			t.onCheck(worldObj, list, tempHot, tempCold);
		}
	}

	public double getHeat(BlockInfo b){
		for(ThermopileFuel f : MgRegister.ThermopileSources)
			if(f.heat && f.source.equals(b))return f.temp;
		return 0;
	}

	public double getCold(BlockInfo b){
		for(ThermopileFuel f : MgRegister.ThermopileSources)
			if(!f.heat && f.source.equals(b))return f.temp;
		return 0;
	}
}
