package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.acces.MgRegister;
import com.cout970.magneticraft.api.acces.RecipeBiomassBurner;
import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.client.gui.component.IBurningTime;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IManagerInventory;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileBiomassBurner extends TileHeatConductor implements IManagerInventory,IGuiSync,IBurningTime{

	private InventoryComponent inv = new InventoryComponent(this, 1, "Biomass Burner");
	private int Progres;
	private boolean updated;
	private int maxProgres;
	
	@Override
	public IHeatConductor initHeatCond() {
		return new HeatConductor(this, 1400.0D, 750.0D);
	}
	
	public InventoryComponent getInv(){
		return inv;
	}
	
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
			if(heat.getTemperature() < heat.getMaxTemp() && isControled()){
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
				int fuel = getItemBurnTime(getInv().getStackInSlot(0));
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
	
	private int getItemBurnTime(ItemStack stackInSlot) {
		for(RecipeBiomassBurner r : MgRegister.BiomassBurner){
			if(r.matches(stackInSlot)){
				return r.getBurnTime();
			}
		}
		return 0;
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

	@Override
	public int getProgres() {
		return Progres;
	}

	@Override
	public int getMaxProgres() {
		return maxProgres;
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, Progres);
		craft.sendProgressBarUpdate(cont, 1, maxProgres);
		craft.sendProgressBarUpdate(cont, 2, (int) heat.getTemperature());		
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)Progres = value;
		if(id == 1)maxProgres = value;
		if(id == 2)heat.setTemperature(value);
	}
}
