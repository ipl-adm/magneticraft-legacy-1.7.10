package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.electricity.BatteryConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.item.IBatteryItem;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IBlockWithData;
import com.cout970.magneticraft.util.IManagerInventory;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileBattery extends TileConductorLow implements IGuiSync, IManagerInventory, ISidedInventory, IBlockWithData{

	private InventoryComponent inv = new InventoryComponent(this, 2, "Battery");
	public static int BATTERY_CHARGE_SPEED = 500;

	@Override
	public IElectricConductor initConductor() {
		return new BatteryConductor(this, ElectricConstants.RESISTANCE_BASE, 1280000, ElectricConstants.BATTERY_DISCHARGE, ElectricConstants.BATTERY_CHARGE){
			
			public void iterate(){
				super.iterate();
				
				if (getVoltage() > max && Storage < maxStorage){
					int change;
					change = (int) Math.min((getVoltage() - max)*10, 200);
					change = Math.min(change, maxStorage - Storage);
					drainPower((double)(change * 100));
					Storage += change;
				}else{
					if(!isControled())return;
					if(getVoltage() < min && Storage > 0){
						int change;
						change = (int) Math.min((min - getVoltage())*10, 200);
						change = Math.min(change, Storage);
						applyPower((double)(change * 100));
						Storage -= change;
					}
				}
			}
		};
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(!worldObj.isRemote){
			ItemStack i = getInv().getStackInSlot(0);
			if(i != null){
				if(i.getItem() instanceof IBatteryItem){
					IBatteryItem b = (IBatteryItem) i.getItem();
					int canFill = Math.min(b.getMaxCharge()-b.getCharge(i), cond.getStorage());
					canFill = Math.min(canFill, BATTERY_CHARGE_SPEED);
					if(canFill > 0){
						b.charge(i, canFill);
						cond.drainCharge(canFill);
					}
				}
			}
			i = getInv().getStackInSlot(1);
			if(i != null){
				if(i.getItem() instanceof IBatteryItem){
					IBatteryItem b = (IBatteryItem) i.getItem();
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
	
	public InventoryComponent getInv(){
		return inv;
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
		craft.sendProgressBarUpdate(cont, 1, (int) cond.getStorage());
	}

	@Override
	public void getGUINetworkData(int i, int value) {
		if(i == 0)cond.setVoltage(value);
		if(i == 1)cond.setStorage(value);
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
}
