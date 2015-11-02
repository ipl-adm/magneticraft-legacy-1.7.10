package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

import net.minecraft.tileentity.TileEntity;

public class TilePumpjackEnergyLink extends TileBase implements IElectricTile {

	@Override
	public IElectricConductor[] getConds(VecInt dir2, int Vtier) {
		VecInt pos = getPosition();
		MgDirection dir = getOrientation();
		if (dir.opposite().toVecInt().equals(dir2)) {
			pos.add(dir);
			pos.add(dir);
			TileEntity t = pos.getTileEntity(worldObj);
			if (t instanceof TilePumpJack) {
				return ((TilePumpJack) t).getConds(VecInt.NULL_VECTOR, 0);
			}
		}
		return null;
	}

	public MgDirection getOrientation() {
		if (getBlockMetadata() < 8)
			return MgDirection.DOWN;
		return MgDirection.AXIX_Y[(getBlockMetadata() - 8) % MgDirection.AXIX_Y.length];
	}
}
