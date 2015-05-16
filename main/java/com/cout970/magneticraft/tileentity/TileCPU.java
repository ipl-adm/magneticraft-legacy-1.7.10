package com.cout970.magneticraft.tileentity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.computer.ComponentCPU;
import com.cout970.magneticraft.api.computer.IComputerBus;
import com.cout970.magneticraft.api.computer.IPeripheralBus;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IGuiListener;

public class TileCPU extends TileBase implements IGuiListener,IGuiSync, IComputerBus{

	private ComponentCPU procesor = new ComponentCPU();
	public int addres = 0;
	
	public TileCPU(){}
	
	public void updateEntity(){
		if(worldObj.isRemote)return;
		procesor.iterate();
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		procesor.loadMemory(nbt);
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		procesor.saveMemory(nbt);
	}
	
	public ComponentCPU getProcesor(){
		return procesor;
	}

	@Override
	public void onMessageReceive(int id, int dato) {
		if(id == 0){
			if(!procesor.isRunning())procesor.startPC();
		}else if(id == 1){
			if(!procesor.isRunning())procesor.stopPC();
			procesor.startPC();
		}else if(id == 2){
			procesor.stopPC();
		}
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, getProcesor().isRunning()?1:0);
		for(int i = 0; i< 32;i++){
			craft.sendProgressBarUpdate(cont, i+1, getProcesor().getRegister(i));
		}
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)if(value == 1)getProcesor().cpuCicles = 1; else getProcesor().cpuCicles = -1;
		if(id >=1 && id <= 33){
			getProcesor().setRegister(id-1, value);
		}
	}

	@Override
	public int getAddress(MgDirection side) {
		return addres;
	}

	@Override
	public void setAddress(int address, MgDirection side) {
		addres = address;
	}

	@Override
	public int readWord(int pointer) {
		return procesor.readWord(pointer);
	}

	@Override
	public void writeWord(int b, int pointer) {
		procesor.writeWord(pointer, b);
	}

	@Override
	public boolean isOnline() {
		return procesor.isRunning();
	}

	@Override
	public byte readByte(int pointer) {
		return procesor.readByte(pointer);
	}

	@Override
	public void writeByte(byte b, int pointer) {
		procesor.writeByte(pointer, b);
	}
}
