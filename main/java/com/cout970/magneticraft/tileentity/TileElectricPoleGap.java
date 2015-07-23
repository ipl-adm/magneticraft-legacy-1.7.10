package com.cout970.magneticraft.tileentity;

import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.electricity.wires.IElectricPole;
import com.cout970.magneticraft.api.electricity.wires.ITileElectricPole;
import com.cout970.magneticraft.api.util.VecInt;

public class TileElectricPoleGap extends TileBase implements ITileElectricPole{

	@Override
	public IElectricPole getPoleConnection() {
		return null;
	}

	@Override
	public ITileElectricPole getMainTile() {
		TileEntity tile = new VecInt(this).add(0, 4-getBlockMetadata(), 0).getTileEntity(getWorldObj());
		if(tile instanceof ITileElectricPole)return (ITileElectricPole) tile;
		return null;
	}

}
