package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.conveyor.IConveyor;
import com.cout970.magneticraft.api.conveyor.ItemBox;
import com.cout970.magneticraft.api.electricity.BatteryConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.MgBeltUtils;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileInserter extends TileConductorLow{

	public InventoryComponent inv = new InventoryComponent(this, 1, "Inserter");
	public int counter = 0;
	public int speed = 20;
	
	@Override
	public IElectricConductor initConductor() {
		return new BatteryConductor(this, ElectricConstants.RESISTANCE_COPPER_2X2, 8000, ElectricConstants.MACHINE_DISCHARGE, ElectricConstants.MACHINE_CHARGE);
	}

	public MgDirection getDir(){
		return MgDirection.getDirection(getBlockMetadata());
	}
	
	public void onBlockBreaks(){
		if(worldObj.isRemote)return;
		BlockMg.dropItem(getInv().getStackInSlot(0), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
	}

	public void updateEntity(){
		super.updateEntity();
		
		TileEntity t = MgUtils.getTileEntity(this, getDir()), o = MgUtils.getTileEntity(this, getDir().opposite());
		
		if(getInv().getStackInSlot(0) != null){
			if(counter == 540){
				if(getInv().getStackInSlot(0) != null){
					if(o instanceof IInventory)dropToInv((IInventory)o);
					else if(o instanceof IConveyor)dropToBelt((IConveyor)o);
					sendUpdateToClient();
				}
			}else if(counter < 540){
				counter+= speed;
			}else {
				counter-= speed;
			}
		}else if(getInv().getStackInSlot(0) == null){
			if(counter == 0){
				if(t instanceof IInventory)suckFromInv((IInventory)t);
				else if(t instanceof IConveyor)suckFromBelt((IConveyor)t);
				sendUpdateToClient();
			}else if(counter > 0 && counter <= 540){
				counter -= speed;
			}else{
				counter += speed;
				counter = counter % 1080;
			}
		}
	}

	private void dropToBelt(IConveyor t) {
		ItemStack s = getInv().getStackInSlot(0);
		if(t.addItem(getDir(), 2, new ItemBox(s), true)){
			getInv().setInventorySlotContents(0, null);
			t.addItem(getDir(), 2, new ItemBox(s), false);
		}
	}

	private void dropToInv(IInventory t) {
		ItemStack s = getInv().getStackInSlot(0);
		if(MgBeltUtils.dropItemStackIntoInventory(t, s, MgDirection.UP, true) == 0){
			getInv().setInventorySlotContents(0, null);
			MgBeltUtils.dropItemStackIntoInventory(t, s, MgDirection.UP, false);
		}
	}

	private void suckFromBelt(IConveyor t) {
		ItemBox[] v = t.getContent(true);
		int[] order = {2,1,0,3};
		for(int i : order){
			if(extractFromBelt(v, i, t, true))return;
		}
		v = t.getContent(false);
		for(int i : order){
			if(extractFromBelt(v, i, t, false))return;
		}
	}
	
	public boolean extractFromBelt(ItemBox[] v, int pos,IConveyor t,boolean side){
		if(v[pos] != null){
			if(t.extract(pos,v[pos],side,true)) {
				getInv().setInventorySlotContents(0, v[pos].getContent());
				t.extract(pos,v[pos],side,false);
				return true;
			}
		}
		return false;
	}

	private void suckFromInv(IInventory t) {
		int slot = MgBeltUtils.getSlotWithItemStack(t, MgDirection.UP);
		if(slot != -1){
			getInv().setInventorySlotContents(0,t.getStackInSlot(slot));
			t.setInventorySlotContents(slot, null);
		}
	}

	public InventoryComponent getInv() {
		return inv;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		getInv().readFromNBT(nbt);
		counter = nbt.getInteger("Stage");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		getInv().writeToNBT(nbt);
		nbt.setInteger("Stage", counter);
	}
}
