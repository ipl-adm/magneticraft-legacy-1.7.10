package com.cout970.magneticraft.api.computer;

public interface IComputerBus extends IPeripheralBus{

	public int readWord(int pointer);
	public void writeWord(int b,int pointer);
	
	public byte readByte(int pointer);
	public void writeByte(byte b,int pointer);
	
}
