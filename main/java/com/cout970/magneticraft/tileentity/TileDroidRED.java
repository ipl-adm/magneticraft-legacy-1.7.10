package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.computer.IBusConnectable;
import com.cout970.magneticraft.api.computer.IComputer;
import com.cout970.magneticraft.api.computer.IModuleCPU;
import com.cout970.magneticraft.api.computer.IModuleDiskDrive;
import com.cout970.magneticraft.api.computer.IModuleMemoryController;
import com.cout970.magneticraft.api.computer.impl.ModuleCPU_MIPS;
import com.cout970.magneticraft.api.computer.impl.ModuleDisckDrive;
import com.cout970.magneticraft.api.computer.impl.ModuleMemoryController;
import com.cout970.magneticraft.api.electricity.BufferedConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.InventoryUtils;
import com.cout970.magneticraft.util.Orientation;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileDroidRED extends TileConductorLow implements IComputer, IGuiSync, IBusConnectable{

	private IModuleMemoryController memory;
	private IModuleDiskDrive floppyDisk;
	private InventoryComponent inv;
	private IModuleCPU cpu;
	private byte[] buffer;
	public int droidAction = -1;//0 move front, 1 move back
	public int droidProgress = -1;
	public boolean activate = true;
	private long time;
	public int drillAnim;
	private int address;
	
	public TileDroidRED(){
		inv = new InventoryComponent(this, 16, "R.E.D.");
		memory = new ModuleMemoryController(0x10000, false, 8);
		cpu = new ModuleCPU_MIPS();  
		floppyDisk = new ModuleDisckDrive(this);
		cpu.connectMemory(memory);
	}

	public void updateEntity(){
		super.updateEntity();
		if(droidProgress > 0){
			droidProgress--;
		}
		if(droidProgress == 0){
			if(droidAction == 0){
				
				MgDirection dir = getDirection().opposite();
				Block b = worldObj.getBlock(xCoord+dir.getOffsetX(), yCoord+dir.getOffsetY(), zCoord+dir.getOffsetZ());
				if(b == getBlockType()){
					Orientation ori = getOrientation();
					if(!worldObj.isRemote)
						worldObj.setBlockToAir(xCoord, yCoord, zCoord);
					xCoord = xCoord+dir.getOffsetX();
					yCoord = yCoord+dir.getOffsetY();
					zCoord = zCoord+dir.getOffsetZ();
					validate();
					worldObj.setTileEntity(xCoord, yCoord, zCoord, this);
					setOrientation(ori);
					markDirty();
					onNeigChange();
					sendUpdateToClient();
				}
				droidProgress = -1;
				return;
			}else if(droidAction == 1){
				MgDirection dir = getDirection();
				Block b = worldObj.getBlock(xCoord+dir.getOffsetX(), yCoord+dir.getOffsetY(), zCoord+dir.getOffsetZ());
				if(b == getBlockType()){
					Orientation ori = getOrientation();
					if(!worldObj.isRemote)
						worldObj.setBlockToAir(xCoord, yCoord, zCoord);
					xCoord = xCoord+dir.getOffsetX();
					yCoord = yCoord+dir.getOffsetY();
					zCoord = zCoord+dir.getOffsetZ();
					validate();
					worldObj.setTileEntity(xCoord, yCoord, zCoord, this);
					setOrientation(ori);
					markDirty();
					onNeigChange();
					sendUpdateToClient();
				}
				droidProgress = -1;
				return;
			}else if(droidAction == 2){
				if(worldObj.isRemote)return;
				Orientation or = getOrientation().rotateY(true);
				setOrientation(or);
			}else if(droidAction == 3){
				if(worldObj.isRemote)return;
				Orientation or = getOrientation().rotateY(false);
				setOrientation(or);
			}else if(droidAction == 4){
				if(worldObj.isRemote)return;
				Orientation or = getOrientation();
				or = Orientation.find(or.getLevel()+1, or.getDirection());
				if(or != null)setOrientation(or);
			}else if(droidAction == 5){
				if(worldObj.isRemote)return;
				Orientation or = getOrientation();
				or = Orientation.find(or.getLevel()-1, or.getDirection());
				if(or != null)setOrientation(or);
			}
			droidProgress = -1;
		}
		if(worldObj.isRemote)return;
		cond.drainPower(50);
	}
	
	//axis == true rotate from y, else from x, dir == true, left or top, else right or bottom 
	public void rotate(boolean axis, boolean dir){
		if(axis){
			if(dir){
				droidProgress = 5;
				droidAction = 2;//left
				sendUpdateToClient();
			}else{
				droidProgress = 5;
				droidAction = 3;//right
				sendUpdateToClient();
			}
		}else{
			if(dir && getOrientation().getLevel() != 1){
				droidProgress = 5;
				droidAction = 4;//top
				sendUpdateToClient();
			}else if(!dir && getOrientation().getLevel() != -1){
				droidProgress = 5;
				droidAction = 5;//bottom
				sendUpdateToClient();
			}
		}
	}
	
	private void setOrientation(Orientation o){
		worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, o.ordinal(), 3);
	}
	
	public Orientation getOrientation() {
		return Orientation.fromMeta(getBlockMetadata());
	}

	public void move(boolean front){
		MgDirection dir = front ? getDirection().opposite() : getDirection();
		if(getBlockType().canPlaceBlockAt(worldObj, xCoord+dir.getOffsetX(), yCoord+dir.getOffsetY(), zCoord+dir.getOffsetZ())){
			worldObj.setBlock(xCoord+dir.getOffsetX(), yCoord+dir.getOffsetY(), zCoord+dir.getOffsetZ(), ManagerBlocks.droid_red, 15, 2);
			if(front){
				droidProgress = 5;
				droidAction = 0;
			}else{
				droidProgress = 10;
				droidAction = 1;
			}
			activate = true;
			sendUpdateToClient();
			markDirty();
		}
	}
	
	public void mine(){
		MgDirection dire = getDirection().opposite();
		Block b = worldObj.getBlock(xCoord+dire.getOffsetX(), yCoord+dire.getOffsetY(), zCoord+dire.getOffsetZ());
		int metadata = worldObj.getBlockMetadata(xCoord+dire.getOffsetX(), yCoord+dire.getOffsetY(), zCoord+dire.getOffsetZ());
		if(MgUtils.isMineableBlock(worldObj, new BlockInfo(b, metadata))){
			
			ArrayList<ItemStack> items = new ArrayList<ItemStack>();
			items = b.getDrops(worldObj, xCoord+dire.getOffsetX(), yCoord+dire.getOffsetY(), zCoord+dire.getOffsetZ(), metadata, 0);
			worldObj.func_147480_a(xCoord+dire.getOffsetX(), yCoord+dire.getOffsetY(), zCoord+dire.getOffsetZ(), false);

			for(ItemStack itemStack : items){
				if(!InventoryUtils.dropIntoInventory(itemStack, getInv())){
					BlockMg.dropItem(itemStack, worldObj.rand, xCoord+dire.getOffsetX(), yCoord+dire.getOffsetY(), zCoord+dire.getOffsetZ(), worldObj);
				}
			}
		}
	}

	public MgDirection getDirection() {
		if(getOrientation().getLevel() == 1)return MgDirection.UP;
		if(getOrientation().getLevel() == -1)return MgDirection.DOWN;
		return getOrientation().getDirection();
	}
	
	public InventoryComponent getInv() {
		return inv;
	}
	
	public byte[] getBuffer(){
		if(buffer == null || buffer.length != 16)buffer = new byte[16];
		return buffer;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		activate = nbt.getBoolean("Active");
		droidProgress = nbt.getInteger("PROGRESS");
		droidAction = nbt.getInteger("ACTION");
		address = nbt.getInteger("Address");
		getInv().readFromNBT(nbt);
		buffer = nbt.getByteArray("Buffer");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setBoolean("Active", activate);
		nbt.setInteger("PROGRESS", droidProgress);
		nbt.setInteger("ACTION", droidAction);
		nbt.setInteger("Address", address);
		nbt.setByteArray("Buffer", getBuffer());
		getInv().writeToNBT(nbt);
	}
	
	public float getDelta() {
		long aux = time;
		time = System.nanoTime();
		return time - aux;
	}

	@Override
	public int getAddress() {
		return address;
	}

	@Override
	public void setAddress(int address) {
		this.address = address;
	}

	@Override
	public boolean isActive() {
		return false;
	}

	@Override
	public String getName() {
		return "RED";
	}

	//droide interface
	@Override
	public int readByte(int pointer) {
		return 0;
	}
	
	//droide interface
	@Override
	public void writeByte(int pointer, int data) {
		
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
		craft.sendProgressBarUpdate(cont, 1, (int) cond.getStorage());
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		switch(id){
		case 0: cond.setVoltage(value); break;
		case 1: cond.setStorage(value); break;
		}
	}

	@Override
	public IModuleCPU getCPU() {
		return cpu;
	}

	@Override
	public IModuleMemoryController getMemory() {
		return memory;
	}

	@Override
	public IModuleDiskDrive getDrive(int n) {
		return floppyDisk;
	}

	@Override
	public TileEntity getParent() {
		return this;
	}

	@Override
	public IElectricConductor initConductor() {
		return new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW, 50000, ElectricConstants.MACHINE_DISCHARGE, ElectricConstants.MACHINE_CHARGE);
	}
}