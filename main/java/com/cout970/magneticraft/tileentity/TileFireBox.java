package com.cout970.magneticraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.client.gui.component.IBurningTime;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileFireBox extends TileHeatConductor implements IInventoryManaged,IGuiSync,IBurningTime{

	private int Progres;
	private boolean updated;
	private int maxProgres;
	private InventoryComponent inv = new InventoryComponent(this, 1, "Fire Box");
	
	@Override
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;

		if(Progres > 0){
			if(!updated){
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
				updated = true;
			}
			//fuel to heat
			if(heat.getTemperature() < heat.getMaxTemp()){
				int i = 8;//burning speed
				if(Progres - i < 0){
					heat.applyCalories(EnergyConversor.FUELtoCALORIES(Progres));
					Progres = 0;
				}else{
					Progres -= i;
					heat.applyCalories(EnergyConversor.FUELtoCALORIES(i));
				}
			}
		}

		if(Progres <= 0){
			if(getInv().getStackInSlot(0) != null && isControled()){
				int fuel = TileEntityFurnace.getItemBurnTime(getInv().getStackInSlot(0));
				if(fuel > 0 && heat.getTemperature() < heat.getMaxTemp()){
					Progres = fuel;
					maxProgres = fuel;
					if(getInv().getStackInSlot(0) != null){
						getInv().getStackInSlot(0).stackSize--;
						if(getInv().getStackInSlot(0).stackSize <= 0){
							getInv().setInventorySlotContents(0, getInv().getStackInSlot(0).getItem().getContainerItem(getInv().getStackInSlot(0)));
						}
					}
					markDirty();
				}
			}
			if(Progres <= 0){
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
				updated = false;
			}
		}			
	}

	

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, Progres);
		craft.sendProgressBarUpdate(cont, 1, maxProgres);
		craft.sendProgressBarUpdate(cont, 2, (int) heat.getTemperature());
	}

	@Override
	public void getGUINetworkData(int i, int value) {
		if(i == 0)Progres = value;
		if(i == 1)maxProgres = value;
		if(i == 2)heat.setTemperature(value);
	}

	@Override
	public int getProgres() {
		return Progres;
	}

	@Override
	public int getMaxProgres() {
		return maxProgres;
	}

	@Override
	public IHeatConductor initHeatCond() {
		return new HeatConductor(this, 1400D, 1000.0D);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		Progres = nbt.getInteger("Progres");
		maxProgres = nbt.getInteger("maxProgres");
		getInv().readFromNBT(nbt);
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Progres", Progres);
		nbt.setInteger("maxProgres", maxProgres);
		getInv().writeToNBT(nbt);
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

	public boolean isItemValidForSlot(int a, ItemStack b) {
		return getInv().isItemValidForSlot(a, b);
	}
}
