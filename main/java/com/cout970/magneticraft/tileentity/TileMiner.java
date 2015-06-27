package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import buildcraft.api.transport.IPipeTile;
import buildcraft.api.transport.IPipeTile.PipeType;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.electricity.ElectricConductor;
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
import com.cout970.magneticraft.util.IClientInformer;
import com.cout970.magneticraft.util.IGuiListener;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.InventoryUtils;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.tile.TileConductorMedium;

public class TileMiner extends TileConductorMedium implements IInventoryManaged,IGuiSync,IConsumer,IBarProvider, IGuiListener{

	public static final int MINING_COST_PER_BLOCK = 500;//500
	public InventoryComponent inv = new InventoryComponent(this, 1, "Miner");
	public WorkState state = WorkState.UNREADY;
	public List<BlockInfo> well = new ArrayList<BlockInfo>();
	public ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	public float coolDown = MINING_COST_PER_BLOCK;
	public float ConsumptionCounter;
	public float Consume;
	public int minedLastSecond;
	public int hole = 0;
	public int dim = 11;
	public int lag,counter;
	public float[] graf = new float[60];
	public int mined;
	
	public IElectricConductor capacity = new ElectricConductor(this,2, ElectricConstants.RESISTANCE_COPPER_MED){
		@Override
		public void computeVoltage() {
			V += 0.05d * I;
			if(V < 0 || Double.isNaN(V))V = 0;
			if(V > ElectricConstants.MAX_VOLTAGE*getVoltageMultiplier()*2)V = ElectricConstants.MAX_VOLTAGE*getVoltageMultiplier()*2;
			I = 0;
			Iabs = 0;
		}
		
		@Override
		public double getVoltageMultiplier() {
			return 100;
		}
		
		@Override
		public void drainPower(double power) {
			power = power * getVoltageMultiplier();
			//sqrt(V^2-(power))-V
			double square = this.V * this.V - Q1 * power;
	        double draining = square < 0.0D ? 0.0D : Math.sqrt(square) - this.V;
	        this.applyCurrent(Q2 * draining);
		}
	};
	private double flow;

	@Override
	public IElectricConductor initConductor() {
		return new ElectricConductor(this, 2, ElectricConstants.RESISTANCE_COPPER_MED);
	}

	public void updateEntity() {
		super.updateEntity();
		if(worldObj.isRemote){
			counter++;
			if(counter >= graf.length){
				counter = 0;
				Arrays.fill(graf, 0f);
			}
			graf[counter] = (float)(lag/1E6);
		}
		if(worldObj.isRemote)return;
		long time = System.nanoTime();
		updateConductor();
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
			
			if(items.isEmpty()){
				double p = (capacity.getVoltage()-ElectricConstants.MACHINE_WORK*100);
				p = (p*p/90);
				if(coolDown > 0){
					if(capacity.getVoltage() > ElectricConstants.MACHINE_WORK*100){
						coolDown -= EnergyConversor.WtoRF(p);
						ConsumptionCounter += p;
						capacity.drainPower(p);
					}
				}
				while(coolDown <= 0){
					if(mineOneBlock()){
						coolDown += MINING_COST_PER_BLOCK;
					}
					if(state != WorkState.WORKING)break;
				}
			}

			while(items.iterator().hasNext()){
				if(ejectItems(items.get(0))){
					items.remove(0);
				}else break;
			}
		}
		if(worldObj.getWorldTime() % 20 == 0){
			Consume = ConsumptionCounter;
			minedLastSecond = mined;
			ConsumptionCounter = 0;
			mined = 0;
		}
		lag = (int)(System.nanoTime()-time);
	}

	private void updateConductor() {
		double resistence = cond.getResistance() + capacity.getResistance();
		double difference = cond.getVoltage() - capacity.getVoltage();
		double change = flow;
		double slow = change * resistence;
		flow += ((difference - change * resistence) * cond.getIndScale())/cond.getVoltageMultiplier();
		change += (difference * cond.getCondParallel())/cond.getVoltageMultiplier();
		cond.applyCurrent(-change);
		capacity.applyCurrent(change);
	}

	private boolean mineOneBlock() {
		if(well.size() > 0){
			BlockInfo f = well.get(0);
			well.remove(f);
			Block b = worldObj.getBlock(f.getX(), f.getY(), f.getZ());
			int meta = worldObj.getBlockMetadata(f.getX(), f.getY(), f.getZ());
			BlockInfo f0 = new BlockInfo(b, meta);
			if(f0.getBlock() != Blocks.air && MgUtils.isMineableBlock(getWorldObj(), f0)){
				//break block
				items.addAll(f.getBlock().getDrops(worldObj, f.getX(), f.getY(), f.getZ(), f.getMeta(), 0));
				worldObj.setBlock(f.getX(), f.getY(), f.getZ(), ManagerBlocks.concreted_pipe, 0, 2);
				mined++;
				return true;
			}else{
				return false;
			}
		}else{
			state = WorkState.UNREADY;
		}
		return true;
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
	
	public boolean ejectItems(ItemStack i) {
		if(i == null)return true;
		for(MgDirection d : MgDirection.values()){
			TileEntity t = MgUtils.getTileEntity(this, d);
			if(t instanceof IInventory){
				if(t instanceof ISidedInventory){
					ISidedInventory s = (ISidedInventory) t;
					for(int j : s.getAccessibleSlotsFromSide(d.opposite().ordinal())){
						if(s.canInsertItem(j, i, d.opposite().ordinal())){
							s.setInventorySlotContents(j, InventoryUtils.addition(i,s.getStackInSlot(j)));
							return true;
						}
					}
				}else{
					IInventory s = (IInventory) t;
					for(int j=0;j < s.getSizeInventory();j++){
						if(s.getStackInSlot(j) == null){
							s.setInventorySlotContents(j, i);
							return true;
						}else if(s.getStackInSlot(j).isItemEqual(i) && s.getStackInSlot(j).stackSize + i.stackSize <= s.getInventoryStackLimit()){
							s.setInventorySlotContents(j, InventoryUtils.addition(i, s.getStackInSlot(j)));
							return true;
						}
					}
				}
			}else if(t instanceof IPipeTile){
				IPipeTile a = (IPipeTile) t;
				if(a.getPipeType() == PipeType.ITEM){
					int r = a.injectItem(i, true, d.toForgeDir().getOpposite());
					if(r > 0)return true;
				}
			}
		}
		return false;
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
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		inv.readFromNBT(nbt);
		
		NBTTagList conduit = nbt.getTagList("Capacity_cond", 11);
		NBTTagCompound conduit_nbt = conduit.getCompoundTagAt(0);
		capacity.load(conduit_nbt);
		dim = nbt.getInteger("Dimension");
		
		items.clear();
		int size = nbt.getInteger("BufferSize");
		NBTTagList list = nbt.getTagList("BufferData", 11);
		for(int i=0;i<size;i++){
			NBTTagCompound t = list.getCompoundTagAt(i);
			ItemStack it = ItemStack.loadItemStackFromNBT(t);
			items.add(it);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		inv.writeToNBT(nbt);
		
		NBTTagList conduit = new NBTTagList();
		NBTTagCompound conduit_nbt = new NBTTagCompound();
		capacity.save(conduit_nbt);
		conduit.appendTag(conduit_nbt);
		nbt.setTag("Capacity_cond", conduit);
		nbt.setInteger("Dimension", dim);
		
		if(!items.isEmpty()){
			nbt.setInteger("BufferSize", items.size());
			NBTTagList list = new NBTTagList();
			for(int i=0;i<items.size();i++){
				NBTTagCompound t = new NBTTagCompound();
				if(items.get(i) != null){
					items.get(i).writeToNBT(t);
				}
				list.appendTag(t);
			}
			nbt.setTag("BufferData", list);
		}
	}
	
	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, (int) capacity.getVoltage());
		craft.sendProgressBarUpdate(cont, 1, (int) coolDown);
		craft.sendProgressBarUpdate(cont, 2, state.ordinal());
		craft.sendProgressBarUpdate(cont, 3, (int) Consume);
		craft.sendProgressBarUpdate(cont, 4, minedLastSecond);
		craft.sendProgressBarUpdate(cont, 5, hole);
		craft.sendProgressBarUpdate(cont, 6, dim);
		craft.sendProgressBarUpdate(cont, 7, lag);
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)capacity.setVoltage(value);
		if(id == 1)coolDown = value;
		if(id == 2)state = WorkState.values()[value % WorkState.values().length];
		if(id == 3)Consume = value;
		if(id == 4)minedLastSecond = value;
		if(id == 5)hole = value;
		if(id == 6)dim = value;
		if(id == 7)lag = value;
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
		return 700000;
	}

	@Override
	public String getMessage() {
		return "Blocks mined in the last second: "+minedLastSecond;
	}

	@Override
	public float getLevel() {
		return Math.min(minedLastSecond/280f, 1);
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
	public void onMessageReceive(int id, int dato) {
		if(id == 0){
			dim+=dato;
			hole = 0;
			state = WorkState.UNREADY;
			sendUpdateToClient();
		}else if(id == 1 && dim-dato >= 10){
			dim-=dato;
			hole = 0;
			state = WorkState.UNREADY;
			sendUpdateToClient();
		}
	}
}
