package com.cout970.magneticraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.electricity.BufferedConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.tool.IFurnaceCoil;
import com.cout970.magneticraft.client.gui.component.IBurningTime;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileElectricFurnace extends TileConductorLow implements IInventoryManaged, IGuiSync, IBurningTime, ISidedInventory{

	public InventoryComponent inv = new InventoryComponent(this, 3, "Electric Furnace");
	public int progress = 0;
	private boolean working;
	
	public TileElectricFurnace(){}
	
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		if(worldObj.getWorldTime()%20 == 0){
			if(working && !isActive()){
				setActive(true);
			}else if(!working && isActive()){
				setActive(false);
			}
		}
		if(cond.getVoltage() >= ElectricConstants.MACHINE_WORK && isControled()){
			if(canSmelt()){
				progress++;
				cond.drainPower(1000*getConsumption());
				if(progress >= getMaxProgres()){
					smelt();
					progress = 0;
				}
				working = true;
			}else{
				working = false;
				progress = 0;
			}
		}else{
			working = false;
		}
	}
	
	private void setActive(boolean b) {
		if(b)
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata()+6, 2);
		else
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata()-6, 2);
	}

	public boolean isActive() {
		return getBlockMetadata() > 5;
	}
	
	public double getConsumption() {
		if(getInv().getStackInSlot(2) == null)return 0;
		if(!(getInv().getStackInSlot(2).getItem() instanceof IFurnaceCoil))return 0;
		return ((IFurnaceCoil) getInv().getStackInSlot(2).getItem()).getElectricConsumption();
	}

	private void smelt() {
		if(canSmelt()){
			ItemStack a = getInv().getStackInSlot(0);
			ItemStack b = getInv().getStackInSlot(1);
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(a);

			if (b == null){
				getInv().setInventorySlotContents(1,itemstack.copy());
			}else if (b.isItemEqual(itemstack)){
				b.stackSize += itemstack.stackSize;
			}
			--a.stackSize;
			if (a.stackSize <= 0){
				getInv().setInventorySlotContents(0, null);
			}
		}
	}

	private boolean canSmelt() {
		ItemStack a = getInv().getStackInSlot(2);
		if(a == null)return false;
		if(!(a.getItem() instanceof IFurnaceCoil))return false;
		if (getInv().getStackInSlot(0) == null){
			return false;
		}else{
			ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(getInv().getStackInSlot(0));
			if (itemstack == null) return false;
			if (getInv().getStackInSlot(1) == null) return true;
			if (!getInv().getStackInSlot(1).isItemEqual(itemstack)) return false;
			int result = getInv().getStackInSlot(1).stackSize + itemstack.stackSize;
			return (result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack item) {
		return slot == 0;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbtTagCompound) {
		super.readFromNBT(nbtTagCompound);
		getInv().readFromNBT(nbtTagCompound);
		progress = nbtTagCompound.getInteger("P");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbtTagCompound) {
		super.writeToNBT(nbtTagCompound);
		getInv().writeToNBT(nbtTagCompound);
		nbtTagCompound.setInteger("P", progress);
	}

	@Override
	public IElectricConductor initConductor() {
		return new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW, 8000, ElectricConstants.MACHINE_DISCHARGE, ElectricConstants.MACHINE_CHARGE);
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		//0 => V
		craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
		craft.sendProgressBarUpdate(cont, 1, (int) progress);
		craft.sendProgressBarUpdate(cont, 2, cond.getStorage());
	}

	@Override
	public void getGUINetworkData(int i, int value) {
		if(i == 0)cond.setVoltage(value);
		if(i == 1)progress = value;
		if(i == 2)cond.setStorage(value);
	}

	@Override
	public int getProgres() {
		return (int) progress;
	}

	@Override
	public int getMaxProgres(){
		if(getInv().getStackInSlot(2) == null)return -1;
		if(!(getInv().getStackInSlot(2).getItem() instanceof IFurnaceCoil))return -1;
		return ((IFurnaceCoil) getInv().getStackInSlot(2).getItem()).getCookTime();
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		return new int[]{0,1};
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		return slot == 0;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int side) {
		return slot == 1;
	}

	public InventoryComponent getInv() {
		return inv;
	}
	
	public int getSizeInventory() {
		return getInv().getSizeInventory();
	}

	public ItemStack getStackInSlot(int s) {
		return getInv().getStackInSlot(s);
	}

	public ItemStack decrStackSize(int a, int b) {
		return getInv().decrStackSize(a, b);
	}

	public ItemStack getStackInSlotOnClosing(int a) {
		return getInv().getStackInSlotOnClosing(a);
	}

	public void setInventorySlotContents(int a, ItemStack b) {
		getInv().setInventorySlotContents(a, b);
	}

	public String getInventoryName() {
		return getInv().getInventoryName();
	}

	public boolean hasCustomInventoryName() {
		return getInv().hasCustomInventoryName();
	}

	public int getInventoryStackLimit() {
		return getInv().getInventoryStackLimit();
	}

	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
		return true;
	}

	public void openInventory() {}

	public void closeInventory() {}
}
