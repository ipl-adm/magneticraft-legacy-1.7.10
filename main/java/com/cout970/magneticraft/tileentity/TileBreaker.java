package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.Random;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IGuiListener;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.MgBeltUtils;
import com.cout970.magneticraft.util.tile.TileConductorLow;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

public class TileBreaker extends TileConductorLow implements IInventory, IGuiListener, IGuiSync{

	public InventoryComponent inv = new InventoryComponent(this, 9, "Block Breaker");
	public InventoryComponent filter = new InventoryComponent(this, 9, "Filter");
	public boolean whiteList;
	public boolean ignoreNBT;
	public boolean ignoreMeta;
	public boolean ignoreDict;
	
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		if(worldObj.provider.getWorldTime() % 20 == 0 && isControled() && cond.getVoltage() > ElectricConstants.MACHINE_WORK)
			BreakBlock();
		ejectFromInv();
	}

	private void ejectFromInv() {
		MgDirection d = MgDirection.getDirection(getBlockMetadata()).opposite();
		TileEntity t = MgUtils.getTileEntity(this, d);
		if(t instanceof IInventory){
			for(int i = 0; i<inv.getSizeInventory(); i++){
				if(inv.getStackInSlot(i) != null){
					if(MgBeltUtils.dropItemStackIntoInventory((IInventory)t, inv.getStackInSlot(i), d.opposite(), true) == 0){
						MgBeltUtils.dropItemStackIntoInventory((IInventory)t, inv.getStackInSlot(i), d.opposite(), false);
						inv.setInventorySlotContents(i, null);
						markDirty();
						return;
					}
				}
			}
		}
	}

	public void BreakBlock() {
		ForgeDirection d = ForgeDirection.getOrientation(getBlockMetadata());
		for(int g=1;g<=16;g++){
			int x,y,z;
			x = xCoord+d.offsetX*g;
			y = yCoord+d.offsetY*g;
			z = zCoord+d.offsetZ*g;
			if(!worldObj.getBlock(x,y,z).isAir(worldObj, x, y, z)){
				BlockInfo bi = new BlockInfo(worldObj.getBlock(x,y,z),worldObj.getBlockMetadata(x, y, z),x,y,z);
				if(worldObj.getBlock(x,y,z) != ManagerBlocks.permagnet && MgUtils.isMineableBlock(worldObj, bi) && canBeStored(bi)){
					ArrayList<ItemStack> items = new ArrayList<ItemStack>();
					Block id = worldObj.getBlock(x,y,z);
					int metadata = worldObj.getBlockMetadata(x,y,z);
					
					cond.drainPower(EnergyConversor.RFtoW(500));
					items = id.getDrops(worldObj, x, y, z, metadata, 0);
					for(ItemStack i : items)
						ejectItems(i);
					markDirty();
					worldObj.setBlockToAir(x,y,z);
					break;
				}
			}
		}
	}
	
	private boolean canBeStored(BlockInfo bi) {
		ArrayList<ItemStack> list = bi.getBlock().getDrops(worldObj, bi.getX(), bi.getY(), bi.getZ(), bi.getMeta(), 0);
		for(ItemStack i : list){
			if(i == null)continue;
			if(canPassFilter(i)){ 
				if(MgBeltUtils.dropItemStackIntoInventory((IInventory) inv, i, MgDirection.UP, true) != 0){
					return false;
				}
			}else{
				return false;
			}
		}
		return true;
	}

	private boolean canPassFilter(ItemStack s) {
		if(s == null)return false;
		if(whiteList){
			for(int i = 0; i < filter.getSizeInventory(); i++){
				if(checkFilter(i, s))return true;
			}
			return false;
		}else{
			for(int i = 0; i < filter.getSizeInventory(); i++){
				if(checkFilter(i, s))return false;
			}
			return true;
		}
	}
	
	public boolean checkFilter(int slot, ItemStack i){
		ItemStack f = filter.getStackInSlot(slot);
		if(f == null)return false;
		if(!ignoreDict){
			int[] c = OreDictionary.getOreIDs(i);
			int[] d = OreDictionary.getOreIDs(f);
			if(c.length > 0 && d.length > 0){
				for(int k : c){
					for(int j : d)
						if(k == j)return true;
				}
			}
		}
		if(f.getItem() != i.getItem())return false;
		if(!ignoreMeta)
			if(f.getItemDamage() != i.getItemDamage())return false;
		if(!ignoreNBT)
			if(f.getTagCompound() != i.getTagCompound())return false;
		return true;
	}

	public void ejectItems(ItemStack i) {
		if(i == null)return;
		if(MgBeltUtils.dropItemStackIntoInventory(inv, i, MgDirection.UP, true) == 0){
			MgBeltUtils.dropItemStackIntoInventory(inv, i, MgDirection.UP, false);
			return;
		}
		Random rand = worldObj.rand;
		if (i.stackSize > 0) {
			BlockMg.dropItem(i, rand, xCoord, yCoord, zCoord, worldObj);
		}
	}

	@Override
	public IElectricConductor initConductor() {
		return new ElectricConductor(this);
	}
	
	public InventoryComponent getInv(){
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
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		inv.readFromNBT(nbt, "Inv");
		filter.readFromNBT(nbt, "Filt");
		whiteList = nbt.getBoolean("white");
		ignoreNBT = nbt.getBoolean("nbt");
		ignoreMeta = nbt.getBoolean("meta");
		ignoreDict = nbt.getBoolean("dict");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		inv.writeToNBT(nbt, "Inv");
		filter.writeToNBT(nbt, "Filt");
		nbt.setBoolean("white", whiteList);
		nbt.setBoolean("nbt", ignoreNBT);
		nbt.setBoolean("meta", ignoreMeta);
		nbt.setBoolean("dict", ignoreDict);
	}

	@Override
	public void onMessageReceive(int id, int dato) {
		if(id == 0){
			whiteList = dato == 1;
			sendUpdateToClient();
		}else if(id == 1){
			ignoreMeta = dato == 1;
			sendUpdateToClient();
		}else if(id == 2){
			ignoreNBT = dato == 1;
			sendUpdateToClient();
		}else if(id == 3){
			ignoreDict = dato == 1;
			sendUpdateToClient();
		}
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, (int)cond.getVoltage());
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)cond.setVoltage(value);
	}
}
