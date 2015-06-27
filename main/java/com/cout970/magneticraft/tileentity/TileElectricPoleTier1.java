package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.cout970.magneticraft.api.electricity.CompoundElectricCables;
import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricPole;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;

import net.minecraft.util.AxisAlignedBB;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileElectricPoleTier1 extends TileBase implements IElectricPole{

	public List<IElectricConductor> connections = new ArrayList<IElectricConductor>();
	public IElectricConductor cond = new ElectricConductor(this);
	
	
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }

	@Override
	public boolean connectWire(MgDirection dir, int tier, IElectricConductor to) {
		if(tier == 0 && !connections.contains(to) && to != cond){
			connections.add(to);
			return true;
		}
		return false;
	}

	@Override
	public List<IElectricConductor> getConnectedConductors() {
		return connections;
	}

	@Override
	public CompoundElectricCables getConds(VecInt dir, int Vtier) {
		if(dir == VecInt.NULL_VECTOR)return new CompoundElectricCables(cond);
		return null;
	}
}
