package com.cout970.magneticraft.tileentity;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.computer.IComputerBus;
import com.cout970.magneticraft.api.computer.IPeripheralBus;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.tile.RedstoneControl;

public class TileMonitor extends TileBase implements IPeripheralBus{
	
	public IComputerBus cpu;
	public int address;
	private boolean update;
	public static final int KEYBOARD_MASK = 0xffff0000;
	public static final int TEXT_MASK = 0xfffe0000;
	
	public int getText(int pos){
		if(cpu == null)return 32;
		return cpu.isOnline() ? (int)cpu.readByte(pos) : 32;
	}
	
	public TileMonitor(){}

	public void keyPresed(int key) {
		if(cpu != null){
			byte b = cpu.readByte(KEYBOARD_MASK);
			b |= 1;
			cpu.writeByte(b,KEYBOARD_MASK);
			cpu.writeWord(key,KEYBOARD_MASK+4);
		}
	}
	
	public void onNeigChange(){
		super.onNeigChange();
		update = false;
	}
	
	public void updateEntity(){
		if((cpu == null && !update) || worldObj.getWorldTime() % 200 == 0){
			search();
			update = true;
		}
	}
	
	private void search() {
		for(MgDirection dir : MgDirection.values()){
			TileEntity t = MgUtils.getTileEntity(this, dir);
			if(t instanceof IComputerBus){
				cpu = (IComputerBus) t;
				return;
			}
		}
	}

	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		address = nbt.getInteger("Address");
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setInteger("Address", address);
	}

	@Override
	public void setAddress(int address,MgDirection side) {
		this.address = address;
	}
	
	@Override
	public int getAddress(MgDirection side) {
		return address;
	}

	@Override
	public boolean isOnline() {
		return true;
	}
}
