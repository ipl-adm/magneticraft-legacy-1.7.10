package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.magnetic.IMagneticFieldProvider;
import com.cout970.magneticraft.api.util.VecInt;

public class TilePermanentMagnet extends TileBase implements IMagneticFieldProvider{

	@Override
	public double getFieldStreng(VecInt side) {
		return 25;
	}

}
