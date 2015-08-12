package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricPole;
import com.cout970.magneticraft.api.electricity.IInterPoleWire;
import com.cout970.magneticraft.api.electricity.ITileElectricPole;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricPoleTier1;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileConductorLow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

public class TileElectricPoleTier1 extends TileConductorLow implements ITileElectricPole{
	
	public ElectricPoleTier1 pole;
	public boolean clientUpdate = false;
	public boolean updateCables = true;
	public boolean locked = false;
	
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox(){
        return INFINITE_EXTENT_AABB;
    }
	
	public void onBlockBreaks(){
		pole.disconectAll();
	}
	
	public void onNeigChange(){
		super.onNeigChange();
		updateCables = true;
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(updateCables && !locked){
			findConnections();
			updateCables = false;
		}
		pole.iterate();
		if(clientUpdate){
			clientUpdate = false;
			sendUpdateToClient();
		}
	}

	public void findConnections(){
		ElectricPoleTier1.findConnections(pole);
		clientUpdate = true;
	}

	@Override
	public IElectricConductor[] getConds(VecInt dir, int Vtier) {
		if(dir == VecInt.NULL_VECTOR && Vtier == 0)return new IElectricConductor[]{cond};
		return null;
	}

	@Override
	public IElectricPole getPoleConnection() {
		return pole;
	}

	@Override
	public ITileElectricPole getMainTile() {
		return this;
	}

	@Override
	public IElectricConductor initConductor() {
		cond = new ElectricConductor(this){
			@Override
			public double getInvCapacity(){
				return getVoltageMultiplier()*EnergyConversor.RFtoW(0.8D);
			}
		};
		pole = new ElectricPoleTier1(this, cond){
			@Override
			public void onDisconnect(IInterPoleWire conn) {
				super.onDisconnect(conn);
				clientUpdate = true;
			}
		};
		return cond;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		pole.load(nbt);
		locked = nbt.getBoolean("Locked");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		pole.save(nbt);
		nbt.setBoolean("Locked", locked);
	}
}