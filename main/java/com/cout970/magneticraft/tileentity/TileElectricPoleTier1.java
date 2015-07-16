package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;

import com.cout970.magneticraft.api.electricity.CompoundElectricCables;
import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.wires.ElectricPoleTier1;
import com.cout970.magneticraft.api.electricity.wires.IElectricPole;
import com.cout970.magneticraft.api.electricity.wires.ITileElectricPole;
import com.cout970.magneticraft.api.electricity.wires.WireConnection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileConductorLow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

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
	public CompoundElectricCables getConds(VecInt dir, int Vtier) {
		if(dir == VecInt.NULL_VECTOR && Vtier == 0)return new CompoundElectricCables(cond);
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
		cond = new ElectricConductor(this);
		pole = new ElectricPoleTier1(this, cond){
			@Override
			public void onDisconnect(WireConnection conn) {
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
