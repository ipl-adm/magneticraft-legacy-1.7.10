package com.cout970.magneticraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IBatteryItem;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IBlockWithData;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileBattery extends TileConductorLow implements IGuiSync, IInventoryManaged, ISidedInventory, IBlockWithData{

	private InventoryComponent inv = new InventoryComponent(this, 2, "Battery");
	public static int BATTERY_CHARGE_SPEED = (int) EnergyConversor.RFtoW(400);//RF

	@Override
	public IElectricConductor initConductor() {
		return new ElectricConductor(this){

			public int storage = 0;
			public int maxStorage = (int) EnergyConversor.RFtoW(2000000);
			public double min = ElectricConstants.BATTERY_DISCHARGE;
			public double max = ElectricConstants.BATTERY_CHARGE;

			public void iterate(){
				super.iterate();
				if(!isControled())return;
				if (getVoltage() > max && storage < maxStorage){
					int change;
					change = (int) Math.min((getVoltage() - max)*80, EnergyConversor.RFtoW(400));
					change = Math.min(change, maxStorage - storage);
					drainPower(change);
					storage += change;
				}else if(getVoltage() < min && storage > 0){
					int change;
					change = (int) Math.min((min - getVoltage())*80, EnergyConversor.RFtoW(400));
					change = Math.min(change, storage);
					applyPower(change);
					storage -= change;
				}
			}

			@Override
			public int getStorage() {
				return storage;
			}

			@Override
			public int getMaxStorage() {
				return maxStorage;
			}

			@Override
			public void setStorage(int charge) {
				storage = charge;
			}

			@Override
			public void applyCharge(int charge) {
				storage += charge;
				if(storage > maxStorage)
					storage = maxStorage;
			}

			@Override
			public void drainCharge(int charge){
				storage -= charge;
				if(storage < 0)storage = 0;
			}

			@Override
			public void save(NBTTagCompound nbt) {
				super.save(nbt);
				nbt.setInteger("Storage", storage);
			}

			@Override
			public void load(NBTTagCompound nbt) {
				super.load(nbt);
				storage = nbt.getInteger("Storage");
			}	
		};
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(!worldObj.isRemote){
			if(worldObj.getTotalWorldTime() % 40 == 0){
				int amount = (int) Math.floor(cond.getStorage()*11F / cond.getMaxStorage());
				int meta = getBlockMetadata();
				if(amount != meta){
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, amount, 3);
				}
			}
			ItemStack i = getInv().getStackInSlot(0);
			if(i != null){
				if(i.getItem() instanceof IBatteryItem){
					IBatteryItem b = (IBatteryItem) i.getItem();
					if(b.canAcceptCharge(i)){
						int canFill = Math.min(b.getMaxCharge(i)-b.getCharge(i), cond.getStorage());
						canFill = Math.min(canFill, BATTERY_CHARGE_SPEED);
						if(canFill > 0){
							b.charge(i, canFill);
							cond.drainCharge(canFill);
						}
					}
				}
			}
			i = getInv().getStackInSlot(1);
			if(i != null){
				if(i.getItem() instanceof IBatteryItem){
					IBatteryItem b = (IBatteryItem) i.getItem();
					if(b.canExtractCharge(i)){
						int canDrain = Math.min(b.getCharge(i), cond.getMaxStorage()-cond.getStorage());
						canDrain = Math.min(canDrain, BATTERY_CHARGE_SPEED);
						if(canDrain > 0){
							b.discharge(i, canDrain);
							cond.applyCharge(canDrain);
						}
					}
				}
			}
		}
	}
	
	public InventoryComponent getInv(){
		return inv;
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
		craft.sendProgressBarUpdate(cont, 1, (cond.getStorage() & 0xFFFF));
		craft.sendProgressBarUpdate(cont, 2, ((cond.getStorage() & 0xFFFF0000) >>> 16));
	}

	@Override
	public void getGUINetworkData(int i, int value) {
		if(i == 0)cond.setVoltage(value);
		if(i == 1)cond.setStorage(value & 0xFFFF);
		if(i == 2)cond.setStorage(cond.getStorage() | (value << 16));
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int s) {
		if(s == 0 || s == 1)return new int[]{0};
		return new int[]{1};
	}

	@Override
	public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,
			int p_102007_3_) {
		return true;
	}

	@Override
	public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
			int p_102008_3_) {
		return true;
	}

	@Override
	public void saveData(NBTTagCompound nbt) {
		nbt.setInteger("Stored", cond.getStorage());
	}

	@Override
	public void loadData(NBTTagCompound nbt) {
		cond.setStorage(nbt.getInteger("Stored"));
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
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		getInv().readFromNBT(nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		getInv().writeToNBT(nbt);
	}
}
