package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.messages.MessageTileUpdate;
import com.cout970.magneticraft.util.tile.RedstoneControl;

public class TileBase extends Tile1_8Updater{
	
	public boolean Powered;
	public RedstoneControl redstone = RedstoneControl.NORMAL;
	
	public void onNeigChange(){
		Powered = isPowered();
	}
	
	public void onBlockBreaks(){}
	
	public void sendUpdateToClient(){
		if(worldObj.isRemote)return;
		MessageTileUpdate message = new MessageTileUpdate(this);
		ManagerNetwork.INSTANCE.sendToAll(message);
	}
	
	public void setRedstoneControl(RedstoneControl newState){
		redstone = newState;
		onNeigChange();
		sendUpdateToClient();
	}
	
	public boolean isControled(){
		if(redstone == RedstoneControl.NORMAL)return !Powered;
		if(redstone == RedstoneControl.INVERSE)return Powered;
		return true;
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		redstone = RedstoneControl.values()[nbt.getByte("RedstoneControl")];
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setByte("RedstoneControl", (byte) redstone.ordinal());
	}
	
	public Packet getDescriptionPacket(){
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		S35PacketUpdateTileEntity p = new S35PacketUpdateTileEntity(xCoord,yCoord,zCoord,0,nbt);
		return p;
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}

	public static RedstoneControl step(RedstoneControl state) {
		return state == RedstoneControl.NORMAL ? RedstoneControl.INVERSE : state == RedstoneControl.INVERSE ? RedstoneControl.DISBLE : RedstoneControl.NORMAL;
	}

	
}
