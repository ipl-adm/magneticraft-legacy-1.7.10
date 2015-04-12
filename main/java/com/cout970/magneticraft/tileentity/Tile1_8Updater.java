package com.cout970.magneticraft.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;

import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.InventoryComponent;

public class Tile1_8Updater extends TileEntity{
	
	public void update() {
		updateEntity();
	}
	
	public void updateEntity(){}

	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(this instanceof IFluidHandler1_8)return((IFluidHandler1_8)this).fillMg(MgDirection.getDirection(from.ordinal()), resource, doFill);
		return 0;
	}

	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).drainMg_F(MgDirection.getDirection(from.ordinal()), resource,doDrain);
		return null;
	}

	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).drainMg(MgDirection.getDirection(from.ordinal()),maxDrain,doDrain);
		return null;
	}

	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).canFillMg(MgDirection.getDirection(from.ordinal()),fluid);
		return false;
	}

	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).canDrainMg(MgDirection.getDirection(from.ordinal()),fluid);
		return false;
	}

	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).getTankInfoMg(MgDirection.getDirection(from.ordinal()));
		return null;
	}	
	
	public BlockPosition getPos() {
		return new BlockPosition(xCoord, yCoord, zCoord);
	}
	
	public boolean isPowered(){
		//worldObj.isBlockPowered(getPos());
		return worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord);
	}

//	public int getSizeInventory() {
//		return getInv().getSizeInventory();
//	}
//
//	public ItemStack getStackInSlot(int s) {
//		return getInv().getStackInSlot(s);
//	}
//
//	public ItemStack decrStackSize(int a, int b) {
//		return getInv().decrStackSize(a, b);
//	}
//
//	public ItemStack getStackInSlotOnClosing(int a) {
//		return getInv().getStackInSlotOnClosing(a);
//	}
//
//	public void setInventorySlotContents(int a, ItemStack b) {
//		getInv().setInventorySlotContents(a, b);
//	}
//
//	public String getInventoryName() {
//		return getInv().getInventoryName();
//	}
//
//	public boolean hasCustomInventoryName() {
//		return getInv().hasCustomInventoryName();
//	}
//
//	public int getInventoryStackLimit() {
//		return getInv().getInventoryStackLimit();
//	}
//
//	public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
//		return true;
//	}
//
//	public void openInventory() {}
//
//	public void closeInventory() {}
//
//	public boolean isItemValidForSlot(int a, ItemStack b) {
//		return getInv().isItemValidForSlot(a, b);
//	}

	public InventoryComponent getInv() {
		return null;
	}
}
