package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.computer.IBusConnectable;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IClientInformer;

public class TileTextMonitor extends TileBase implements IBusConnectable, IClientInformer, IGuiSync{
	
	public int address = 0x1;
	private boolean update;
	public static final int KEYBOARD_MASK = 0xff010000;
	public static final int TEXT_MASK = 0xff010014;
	public byte[] buffer;
	private int regReady;
	private int regChar;
	
	public int getText(int pos){
		return getBuffer()[pos+20];
	}
	
	public byte[] getBuffer(){
		if(buffer == null || buffer.length != 4020)buffer = new byte[4020];
		return buffer;
	}
	
	public TileTextMonitor(){}

	public void keyPresed(int key) {
		regReady |= 1;
		regChar = key;
	}

	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		address = nbt.getInteger("Address");
		buffer = nbt.getByteArray("TEXT");
		getBuffer();
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setInteger("Address", address);
		getBuffer();
		nbt.setByteArray("TEXT", buffer);
	}
	
	@Override
	public void setAddress(int address) {
		this.address = address;
	}
	
	@Override
	public int getAddress() {
		return address;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public void saveInfoToMessage(NBTTagCompound nbt) {
		nbt.setByte("Ready", (byte) regReady);
		nbt.setByte("Char", (byte) regChar);
	}

	@Override
	public void loadInfoFromMessage(NBTTagCompound nbt) {
		getBuffer()[0] = nbt.getByte("Ready");
		getBuffer()[4] = nbt.getByte("Char");
	}

	@Override
	public TileEntity getParent() {
		return this;
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		sendUpdateToClient();
	}

	@Override
	public void getGUINetworkData(int id, int value) {}

	
	@Override
	public int readByte(int pointer) {
		if(pointer >= getBuffer().length)return 0;
		return getBuffer()[pointer];
	}

	@Override
	public void writeByte(int pointer, int data) {
		if(pointer >= getBuffer().length)return;
		getBuffer()[pointer] = (byte) data;
	}

	@Override
	public String getName() {
		return "TextMonitor";
	}
}
