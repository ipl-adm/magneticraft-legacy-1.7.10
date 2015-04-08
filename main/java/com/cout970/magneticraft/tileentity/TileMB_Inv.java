package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.util.IManagerInventory;
import com.cout970.magneticraft.util.InventoryComponent;

public class TileMB_Inv extends TileMB_Base implements IManagerInventory{

	public InventoryComponent inv = new InventoryComponent(this, 4, "Multiblock buffer");

	public InventoryComponent getInv(){
		return inv;
	}
}
