package com.cout970.magneticraft.tileentity;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.computer.IPeripheralBus;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.util.tile.RedstoneControl;

public class TileMonitor extends TileBase implements IPeripheralBus{

	public byte[] text = new byte[4000];
	public int line;
	public int cursorX;
	public int cursorY;
	public int cursorMode;
	public int keyPosition;
	public int keyStart;
	public byte[] keyBuffer = new byte[16];
	public int address = 1;
	
	public TileMonitor(){
		Arrays.fill(text, (byte)32);
	}

	public void keyPresed(int key) {
		int pos = keyPosition + 1 & 15;
		if(pos != keyStart){
			keyBuffer[pos] = (byte) (key & 0xFF);
			keyPosition = pos;
			this.markDirty();
		}
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		text = nbt.getByteArray("Text");
		if(text.length != 4000) text = new byte[4000];
		cursorX = nbt.getInteger("CursorX") & 255;
		cursorY = nbt.getInteger("CursorY") & 255;
		cursorMode = nbt.getInteger("CursorMode") & 255;
		keyPosition = nbt.getInteger("KeyPosition") & 255;
		keyStart = nbt.getInteger("KeyStart") & 255;
		keyBuffer = nbt.getByteArray("KeyBuffer");
		if(keyBuffer.length != 16) keyBuffer = new byte[16];
		address = nbt.getInteger("Address");
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setByteArray("Text", text);
		nbt.setInteger("Line", line);
		nbt.setInteger("CursorX", cursorX);
		nbt.setInteger("CursorY", cursorY);
		nbt.setInteger("CursorMode", cursorMode);
		nbt.setInteger("KeyPosition", keyPosition);
		nbt.setInteger("KeyStart", keyStart);
		nbt.setByteArray("KeyBuffer", keyBuffer);
		nbt.setInteger("Address", address);
	}

	@Override
	public int read(int pointer) {
		if(pointer < 16){
			switch(pointer){
			case 0: return line;
			case 1: return cursorX;
			case 2: return cursorY;
			case 3: return cursorMode;
			case 4: return keyStart;
			case 5: return keyPosition;
			case 6: return keyBuffer[keyStart];
			case 7: return keyPosition;
			}
		}else if(pointer < 96){
			return text[line * 80 + pointer - 16];
		}
		return 0;
	}
	
	@Override
	public void write(int b, int pointer) {
		if(pointer < 16){
			switch(pointer){
			case 0: line = Math.min(b,49); break;
			case 1: cursorX = b & 0xFF; break;
			case 2: cursorY = b & 0xFF; break;
			case 3: cursorMode = b & 0xFF; break;
			case 4: keyStart = b & 0xFF; break;
			case 5: keyPosition = b & 0xFF; break;
			case 6: keyBuffer[keyStart] = (byte)b; break;
			case 7: keyPosition = b & 0xFF; break;
			}
		}else if(pointer < 96){
			text[line * 80 + pointer - 16] = (byte)b;
		}
	}

	@Override
	public void setAddress(int address,MgDirection side) {
		this.address = address;
	}
	
	@Override
	public int getAddress(MgDirection side) {
		return address;
	}
}
