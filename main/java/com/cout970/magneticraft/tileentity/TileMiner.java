package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import buildcraft.api.transport.IPipeTile;
import buildcraft.api.transport.IPipeTile.PipeType;

import com.cout970.magneticraft.api.electricity.Conductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IConsumer;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IManagerInventory;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.InventoryUtils;
import com.cout970.magneticraft.util.tile.TileConductorMedium;

public class TileMiner extends TileConductorMedium implements IManagerInventory,IGuiSync,IConsumer,IBarProvider{

	public static final int MINING_COST_PER_BLOCK = 500;
	public InventoryComponent inv = new InventoryComponent(this, 1, "Miner");
	public WorkState state = WorkState.UNREADY;
	public List<BlockInfo> well = new ArrayList<BlockInfo>();
	public ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	public float coolDown = MINING_COST_PER_BLOCK;
	public float ConsumptionCounter;
	public float Consume;
	public int minedLastSecond;
	public int hole = 0;
	public int dim = 18;
	public int mined;
	

	@Override
	public IElectricConductor initConductor() {
		return new Conductor(this, 2, ElectricConstants.RESISTANCE_COPPER_2X2);
	}

	public void updateEntity() {
		super.updateEntity();
		if(worldObj.isRemote)return;
		if (state == WorkState.UNREADY) {
			scanWell();
			hole++;
			if(hole > dim*dim){
				state = WorkState.FINISHED;
				hole = 0;
			}else
				state = WorkState.WORKING;
		}

		if(state == WorkState.WORKING) {
			if(coolDown > 0){
				if(cond.getVoltage() > ElectricConstants.MACHINE_WORK*100){
					double p =  ActivationFunction((cond.getVoltage()-ElectricConstants.MACHINE_WORK*100));
					coolDown -= EnergyConversor.WtoRF(p);
					ConsumptionCounter += p;
					cond.drainPower(p);
				}
			}
			while(coolDown <= 0){
				coolDown += MINING_COST_PER_BLOCK;
				mineOneBlock();
			}

			while(items.iterator().hasNext()){
				ejectItems(items.get(0));
				items.remove(0);
			}
		}
		if(worldObj.getWorldTime() % 20 == 0){
			Consume = ConsumptionCounter;
			minedLastSecond = mined;
			ConsumptionCounter = 0;
			mined = 0;
		}
	}

	private double ActivationFunction(double p) {
		return p = (p*p)/160;
	}

	private void mineOneBlock() {
		if(well.size() > 0){
			BlockInfo f = well.get(0);
			items.addAll(f.getBlock().getDrops(worldObj, f.getX(), f.getY(), f.getZ(), f.getMeta(), 0));
			worldObj.setBlockToAir(f.getX(), f.getY(), f.getZ());
			well.remove(f);
			mined++;
		}else{
			state = WorkState.UNREADY;
		}
	}

	private void scanWell() {
		MgDirection d = getDirection();
		int x,z;
		if(d == MgDirection.NORTH){
			x = (hole/dim)-dim/2;
			z = -((hole%dim)+1);
		}else if(d == MgDirection.SOUTH){
			x = (hole/dim)-dim/2;
			z = (hole%dim)+1;
		}else if(d == MgDirection.EAST){
			x = (hole%dim)+1;
			z = (hole/dim)-dim/2;
		}else{
			x = -((hole%dim)+1);
			z = (hole/dim)-dim/2;
		}
		VecInt pos = new VecInt(xCoord+x, 0, zCoord+z);
		well.clear();
		for (int i = yCoord + 5; i >= 0; i--) {
			Block b = worldObj.getBlock(pos.getX(), i, pos.getZ());
			int meta = worldObj.getBlockMetadata(pos.getX(), i, pos.getZ());
			BlockInfo info = new BlockInfo(b, meta, pos.copy().add(0, i, 0));
			if(MgUtils.isMineableBlock(worldObj,info)){
				well.add(info);
			}
		}
	}
	
	public void ejectItems(ItemStack i) {
		if(i == null)return;
		for(MgDirection d : MgDirection.values()){
			TileEntity t = MgUtils.getTileEntity(this, d);
			if(t instanceof IInventory){
				if(t instanceof ISidedInventory){
					ISidedInventory s = (ISidedInventory) t;
					for(int j : s.getAccessibleSlotsFromSide(d.opposite().ordinal())){
						if(s.canInsertItem(j, i, d.opposite().ordinal())){
							s.setInventorySlotContents(j, InventoryUtils.addition(i,s.getStackInSlot(j)));
							return;
						}
					}
				}else{
					IInventory s = (IInventory) t;
					for(int j=0;j < s.getSizeInventory();j++){
						if(s.getStackInSlot(j) == null){
							s.setInventorySlotContents(j, i);
							return;
						}else if(s.getStackInSlot(j).isItemEqual(i) && s.getStackInSlot(j).stackSize + i.stackSize <= s.getInventoryStackLimit()){
							s.setInventorySlotContents(j, InventoryUtils.addition(i, s.getStackInSlot(j)));
							return;
						}
					}
				}
			}else if(t instanceof IPipeTile){
				IPipeTile a = (IPipeTile) t;
				if(a.getPipeType() == PipeType.ITEM){
					int r = a.injectItem(i, true, d.getForgeDir().getOpposite());
					if(r > 0)return;
				}
			}
		}
		Random rand = worldObj.rand;
		if (i != null && i.stackSize > 0) {
			float rx = rand.nextFloat() * 0.8F + 0.1F;
			float ry = rand.nextFloat() * 0.8F + 0.1F;
			float rz = rand.nextFloat() * 0.8F + 0.1F;
			EntityItem entityItem = new EntityItem(worldObj,
					xCoord + rx, yCoord + ry, zCoord + rz,
					new ItemStack(i.getItem(), i.stackSize, i.getItemDamage()));
			if (i.hasTagCompound()) {
				entityItem.getEntityItem().setTagCompound((NBTTagCompound) i.getTagCompound().copy());
			}
			float factor = 0.05F;
			entityItem.motionX = rand.nextGaussian() * factor;
			entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
			entityItem.motionZ = rand.nextGaussian() * factor;
			worldObj.spawnEntityInWorld(entityItem);
			i.stackSize = 0;
		}
	}

	public MgDirection getDirection() {
		return MgDirection.getDirection(getBlockMetadata()).opposite();
	}

	public enum WorkState {
		UNREADY, FINISHED, WORKING, IDLE, BLOCKED;
	}
	
	public InventoryComponent getInv(){
		return inv;
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, (int) cond.getVoltage());
		craft.sendProgressBarUpdate(cont, 1, (int) coolDown);
		craft.sendProgressBarUpdate(cont, 2, state.ordinal());
		craft.sendProgressBarUpdate(cont, 3, (int) Consume);
		craft.sendProgressBarUpdate(cont, 4, minedLastSecond);
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)cond.setVoltage(value);
		if(id == 1)coolDown = value;
		if(id == 2)state = WorkState.values()[value % WorkState.values().length];
		if(id == 3)Consume = value;
		if(id == 4)minedLastSecond = value;
	}

	@Override
	public float getConsumptionInTheLastTick() {
		return ConsumptionCounter;
	}

	@Override
	public float getConsumptionInTheLastSecond() {
		return Consume;
	}

	@Override
	public float getMaxConsumption() {
		return 250000;
	}

	@Override
	public String getMessage() {
		return "Blocks mined in the last second: "+minedLastSecond;
	}

	@Override
	public float getLevel() {
		return Math.min(minedLastSecond/100f, 1);
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
