package com.cout970.magneticraft.tileentity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.computer.ComponentCPU;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IGuiListener;

public class TileCPU extends TileBase implements IGuiListener,IGuiSync{

	private ComponentCPU procesor = new ComponentCPU();
	
	public TileCPU(){
//		File archive;
//		FileInputStream stream = null;
//		try{
//			archive = new File("I:/Development/test.bin");
//			stream = new FileInputStream(archive);
//			stream.read(procesor.memory, 0x00080000, 0x00020000);
//			stream.close();
//			archive = new File("I:/Development/test_data.bin");
//			stream = new FileInputStream(archive);
//			stream.read(procesor.memory, 0x00040001, 0x1FFFF);
//			stream.close();
//		}catch(IOException e){
//			e.printStackTrace();
//		}
	}
	
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
			if(!procesor.isRunning()) procesor.startPC();
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
}
