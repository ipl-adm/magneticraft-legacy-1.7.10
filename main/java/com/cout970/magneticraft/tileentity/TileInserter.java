package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.conveyor.ConveyorSide;
import com.cout970.magneticraft.api.conveyor.IConveyor;
import com.cout970.magneticraft.api.conveyor.ItemBox;
import com.cout970.magneticraft.api.electricity.BufferedConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.MgBeltUtils;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileInserter extends TileBase{

	public InventoryComponent inv = new InventoryComponent(this, 1, "Inserter");
	public int counter = 0;
	public int speed = 20;
	
//	@Override
//	public IElectricConductor initConductor() {
//		return new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_2X2, 8000, ElectricConstants.MACHINE_DISCHARGE, ElectricConstants.MACHINE_CHARGE);
//	}

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
					else if(o instanceof IConveyor && ((IConveyor) o).getOrientation().getLevel() == 0)dropToBelt((IConveyor)o);
					sendUpdateToClient();
				}
			}else if(counter < 540){
				counter+= speed;
			}else {
				counter-= speed;
			}
		}else if(getInv().getStackInSlot(0) == null){
			if(counter == 0){
				if(t instanceof IInventory){
					suckFromInv((IInventory)t, o);
				}
				else if(t instanceof IConveyor && ((IConveyor) t).getOrientation().getLevel() == 0){
					suckFromBelt((IConveyor)t, o);
				}
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

	private void suckFromBelt(IConveyor t, Object obj) {
		ConveyorSide side = t.getSideLane(true);
		if(extractFromBelt(side, t, true, obj))return;
		side = t.getSideLane(false);
		if(extractFromBelt(side, t, false, obj))return;
	}
	
	public boolean extractFromBelt(ConveyorSide side,IConveyor t,boolean left, Object obj){
		if(side.content.isEmpty())return false;
		ItemBox b = side.content.get(0);
		if(t.extract(b, left, true)){
			if(canInject(obj, b.getContent())){
				t.extract(b, left, false);
				getInv().setInventorySlotContents(0, b.getContent());
				return true;
			}
		}
		return false;
	}

	private void suckFromInv(IInventory t, Object obj) {
		int slot = MgBeltUtils.getSlotWithItemStack(t, MgDirection.UP);
		if(slot != -1){
			ItemStack s = t.getStackInSlot(slot);
			if(canInject(obj, s)){
				getInv().setInventorySlotContents(0,s);
				t.setInventorySlotContents(slot, null);
			}
		}
	}
	
	public boolean canInject(Object obj, ItemStack s){
//		if(obj instanceof IInventory){
//			return MgBeltUtils.dropItemStackIntoInventory((IInventory)obj, s, MgDirection.UP, true) == 0;
//		}else if(obj instanceof IConveyor){
//			return ((IConveyor) obj).addItem(getDir(), 2, new ItemBox(s), true);
//		}
//		return false;
		return true;
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
	
	public enum InserterAnimation{
		Default,Rotated
	}
}
