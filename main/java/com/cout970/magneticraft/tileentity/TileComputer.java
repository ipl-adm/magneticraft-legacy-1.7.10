package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.computer.IBusConnectable;
import com.cout970.magneticraft.api.computer.IComputer;
import com.cout970.magneticraft.api.computer.IHardwareProvider;
import com.cout970.magneticraft.api.computer.IHardwareProvider.ModuleType;
import com.cout970.magneticraft.api.computer.IModuleCPU;
import com.cout970.magneticraft.api.computer.IModuleDiskDrive;
import com.cout970.magneticraft.api.computer.IModuleMemoryController;
import com.cout970.magneticraft.api.computer.IModuleROM;
import com.cout970.magneticraft.api.computer.impl.ModuleDisckDrive;
import com.cout970.magneticraft.api.computer.impl.ModuleHardDrive;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IGuiListener;
import com.cout970.magneticraft.util.InventoryComponent;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class TileComputer extends TileBase implements IGuiListener,IGuiSync, IComputer, IBusConnectable{
	
	private IModuleCPU procesor;
	private IModuleMemoryController memory;
	private IModuleROM rom;
	private ModuleHardDrive hardDrive;
	private ModuleDisckDrive floppyDrive;
	private boolean isRuning;
	public int addres = 0;
	
	private InventoryComponent inv = new InventoryComponent(this, 5, "Computer"){
		@Override
		public void setInventorySlotContents(int slot, ItemStack itemStack) {
			inventory[slot] = itemStack;

			if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
				itemStack.stackSize = this.getInventoryStackLimit();
			}
			markDirty();
			if(slot == 3)hardDrive.insertDisk(itemStack);
			if(slot == 4)floppyDrive.insertDisk(itemStack);
		}
		
		@Override
		public int getInventoryStackLimit() {
			return 1;
		}
	};
	
	public TileComputer(){
		hardDrive = new ModuleHardDrive(this);
		floppyDrive = new ModuleDisckDrive(this);
	}
	
	public void onBlockBreaks(){
		for(int i = 0; i<getInv().getSizeInventory();i++){
			if(getInv().getStackInSlot(i) != null)
			BlockMg.dropItem(getInv().getStackInSlot(i), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
		}
	}

	public void updateEntity(){
		if(worldObj.isRemote)return;
		if(isActive()){
			memory.writeWord(4, (int)worldObj.getWorldTime());
			procesor.iterate();
			hardDrive.iterate();
			floppyDrive.iterate();
			if(worldObj.getWorldTime() % 200 == 0){
				chechHardware();
				sendUpdateToClient();
			}
		}else{
			chechHardware();
		}
	}

	private void chechHardware() {
		ItemStack cpu = getInv().getStackInSlot(0);
		ItemStack ram = getInv().getStackInSlot(1);
		ItemStack rom = getInv().getStackInSlot(2);
		boolean hasCpu = false, hasRam = false, hasRom = false;
		if(cpu == null || ram == null || rom == null){
			procesor = null;
			memory = null;
			this.rom = null;
			return;
		}
		ModuleType m = null;
		if(cpu.getItem() instanceof IHardwareProvider){
			m = ((IHardwareProvider) cpu.getItem()).getModuleType(cpu);
			if(m == ModuleType.CPU){
				hasCpu = true;
			}
		}
		if(ram.getItem() instanceof IHardwareProvider){
			m = ((IHardwareProvider) ram.getItem()).getModuleType(ram);
			if(m == ModuleType.RAM){
				hasRam = true;
			}
		}
		if(rom.getItem() instanceof IHardwareProvider){
			m = ((IHardwareProvider) rom.getItem()).getModuleType(rom);
			if(m == ModuleType.ROM){
				hasRom = true;
			}
		}
		if(hasCpu && hasRam && hasRom)weldHardware();
		else{
			procesor = null;
			memory = null;
			this.rom = null;
		}
	}

	public void weldHardware(){
		if(!isActive()){
			procesor = (IModuleCPU) ((IHardwareProvider)getInv().getStackInSlot(0).getItem()).getHardware(getInv().getStackInSlot(0));
			memory = (IModuleMemoryController) ((IHardwareProvider)getInv().getStackInSlot(1).getItem()).getHardware(getInv().getStackInSlot(1));
			rom = (IModuleROM) ((IHardwareProvider)getInv().getStackInSlot(2).getItem()).getHardware(getInv().getStackInSlot(2));
			procesor.connectMemory(memory);
			memory.setComputer(this);
		}
	}
	
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		isRuning = nbt.getBoolean("ON");
		addres = nbt.getInteger("Address");
		floppyDrive.load(nbt);
		hardDrive.load(nbt);
		getInv().readFromNBT(nbt);
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
			if(!isActive())chechHardware();
			if(isActive()){
				procesor.loadRegisters(nbt);
				memory.loadMemory(nbt);
			}
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setBoolean("ON", isRunning());
		nbt.setInteger("Address", addres);
		floppyDrive.save(nbt);
		hardDrive.save(nbt);
		getInv().writeToNBT(nbt);
		if(isActive()){
			procesor.saveRegisters(nbt);
			memory.saveMemory(nbt);
		}
	}

	@Override
	public void onMessageReceive(int id, int dato) {
		if(isActive()){
			if(id == 0){
				if(!procesor.isRunning()){
					procesor.start();
					rom.loadToRAM(memory);
					sendUpdateToClient();
				}
			}else if(id == 1){
				procesor.stop();
				procesor.start();
				rom.loadToRAM(memory);
				sendUpdateToClient();
			}else if(id == 2){
				procesor.stop();
				sendUpdateToClient();
			}
		}
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, isRunning() ? 1 : 0);
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)isRuning = value == 1;
	}

	@Override
	public IModuleCPU getCPU() {
		return procesor;
	}

	@Override
	public IModuleMemoryController getMemory() {
		return memory;
	}

	@Override
	public int getAddress() {
		return addres;
	}

	@Override
	public void setAddress(int address) {
		addres = address;
	}

	@Override
	public boolean isActive() {
		return procesor != null && memory != null && rom != null;
	}
	
	public InventoryComponent getInv() {
		return inv;
	}

	public boolean isRunning() {
		if(!worldObj.isRemote)isRuning = isActive() && procesor.isRunning();
		return isRuning;
	}

	@Override
	public IModuleDiskDrive getDrive(int n) {
		if(n == 0)return hardDrive;
		if(n == 1)return floppyDrive;
		return null;
	}

	@Override
	public int readByte(int pointer) {
		return 0;
	}

	@Override
	public void writeByte(int pointer, int data) {
	}


	@Override
	public TileEntity getParent() {
		return this;
	}

	@Override
	public String getName() {
		return "Computer";
	}
}
