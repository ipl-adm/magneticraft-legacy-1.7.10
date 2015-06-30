package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.cout970.magneticraft.api.electricity.CompoundElectricCables;
import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricPole;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecDouble;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.Log;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileElectricPoleTier1 extends TileBase implements IElectricPole{

	public List<IElectricConductor> connections = new ArrayList<IElectricConductor>();
	public IElectricConductor cond = new ElectricConductor(this);
	public int glList = -1;
	
	
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
	
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.getWorldTime() % 50 == 0){
			findConnections();
		}
	}

	public void findConnections(){
		connections.clear();
		if(worldObj.isRemote){
			if(glList != -1){
				GL11.glDeleteLists(glList, 1);
				glList = -1;
			}
		}
		int rad = 16;
		for(int x = -rad; x <= rad; x++){
			for(int z = -rad; z <= rad; z++){
				if(x == 0 && z == 0)continue;
				TileEntity t = worldObj.getTileEntity(xCoord+x, yCoord, zCoord+z);
				if(t instanceof IElectricPole){
					CompoundElectricCables comp = ((IElectricPole) t).getConds(VecInt.NULL_VECTOR, 0);
					if(comp != null){
						connections.add(comp.getCond(0));
					}
				}
			}
		}
	}

	@Override
	public CompoundElectricCables getConds(VecInt dir, int Vtier) {
		if(dir == VecInt.NULL_VECTOR)return new CompoundElectricCables(cond);
		return null;
	}

	@Override
	public List<IElectricPole> getConnectedConductors() {
		List<IElectricPole> poles = new ArrayList<IElectricPole>();
		for(IElectricConductor c : connections){
			if(c.getParent() instanceof IElectricPole){
				poles.add((IElectricPole) c.getParent());
			}
		}
		return poles;
	}

	@Override
	public VecDouble[] getWireConnector() {
		switch(getBlockMetadata()-6){
		case 0: return new VecDouble[]{new VecDouble(1.845, 0.75, 0.5), new VecDouble(0.5, 1, 0.5), new VecDouble(-0.845, 0.75, 0.5)};
		case 4: return new VecDouble[]{new VecDouble(-0.845, 0.75, 0.5), new VecDouble(0.5, 1, 0.5), new VecDouble(1.845, 0.75, 0.5)};
		
		case 1: return new VecDouble[]{new VecDouble(1.45, 0.75, 1.45), new VecDouble(0.5, 1, 0.5), new VecDouble(-0.45, 0.75, -0.45)};
		case 5: return new VecDouble[]{new VecDouble(-0.45, 0.75, -0.45), new VecDouble(0.5, 1, 0.5), new VecDouble(1.45, 0.75, 1.45)};
		
		case 3: return new VecDouble[]{new VecDouble(-0.45, 0.75, 1.45), new VecDouble(0.5, 1, 0.5), new VecDouble(1.45, 0.75, -0.45)};
		case 7: return new VecDouble[]{new VecDouble(1.45, 0.75, -0.45), new VecDouble(0.5, 1, 0.5), new VecDouble(-0.45, 0.75, 1.45)};
		
		case 2: return new VecDouble[]{new VecDouble(0.5, 0.75, 1.845), new VecDouble(0.5, 1, 0.5), new VecDouble(0.5, 0.75, -0.845)};
		case 6: return new VecDouble[]{new VecDouble(0.5, 0.75, -0.845), new VecDouble(0.5, 1, 0.5), new VecDouble(0.5, 0.75, 1.845)};
		}
		return new VecDouble[]{};
	}
}
