package com.cout970.magneticraft.api.computer.impl;

import java.io.IOException;
import java.io.InputStream;

import com.cout970.magneticraft.api.computer.IModuleMemoryController;
import com.cout970.magneticraft.api.computer.IModuleROM;

public class ModuleROM implements IModuleROM{

	@Override
	public void loadToRAM(IModuleMemoryController ram) {
		InputStream archive;
		try{
			archive = ComputerUtils.getInputStream("bios.bin");
			byte[] buffer = new byte[0x2000];
			archive.read(buffer, 0, buffer.length);//0x0700
			for(int i = 0; i < buffer.length; i++){
				ram.writeByte(0x700 + i, buffer[i]);
			}
			archive.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
