package com.cout970.magneticraft.api.conveyor;

import com.cout970.magneticraft.api.util.MgDirection;

public interface IConveyor {

	public boolean addItem(MgDirection in, int pos, ItemBox it, boolean simulated);
	
	public ItemBox[] getContent(boolean onLeft);

	public MgDirection getDir();

	public boolean extract(int pos, ItemBox box, boolean isOnLeft, boolean simulated);
	public boolean inject(int pos, ItemBox box, boolean isOnLeft, boolean simulated);
}
