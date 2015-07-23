package com.cout970.magneticraft.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.acces.RecipeSifter;
import com.cout970.magneticraft.api.electricity.CompoundElectricCables;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.InventoryUtils;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.multiblock.Multiblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileSifter extends TileMB_Base{

	public int drawCounter;
	public boolean working;
	public int speed = 100;
	public int progress;
	public float[] leverCount = new float[4];
	public boolean[] leverState = new boolean[4];
	public IElectricConductor cond;
	public InventoryComponent in,out;
	public InventoryComponent inv = new InventoryComponent(this, 3, "Sifter");
	private long time;
	
	public void onBlockBreaks(){
		if(worldObj.isRemote)return;
		BlockMg.dropItem(inv.getStackInSlot(0), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
		BlockMg.dropItem(inv.getStackInSlot(1), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
		BlockMg.dropItem(inv.getStackInSlot(2), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
	}
	
	public void updateEntity() {
		super.updateEntity();
		if(drawCounter > 0)drawCounter--;
		if (!isActive())return;
		if(worldObj.getTotalWorldTime() % 40 == 0){
			cond = null;
		}
		if(cond == null || in == null || out == null){
			search();
		}
		if(worldObj.getTotalWorldTime() % 40 == 0){
			((TileBase) cond.getParent()).sendUpdateToClient();
		}
		if(working){
			if(inv.getStackInSlot(0) == null){
				inputItem();
			}
			if(canCraft()){
				if(cond.getVoltage() > ElectricConstants.MACHINE_WORK){
					cond.drainPower(EnergyConversor.RFtoW(40)*(100F/speed));
					progress++;
					if(progress > speed){
						craft();
						progress = 0;
					}
				}
			}else{
				progress = 0;
			}
		}
	}

	@Override
	public void onDestroy(World w, VecInt p, Multiblock c, MgDirection e) {}
	
	private void inputItem() {
		if(inv.getStackInSlot(1) == null){
			for(int i = 0; i < in.getSizeInventory(); i++){
				ItemStack it = in.getStackInSlot(i);
				RecipeSifter r = RecipeSifter.getRecipe(it);
				if(r != null){
					ItemStack item = it.copy();
					in.decrStackSize(i, 1);
					item.stackSize = 1;
					inv.setInventorySlotContents(0, item);
					inv.setInventorySlotContents(1, r.getOutput().copy());
					if(r.getProb() >= worldObj.rand.nextFloat() && r.getExtra() != null){
						inv.setInventorySlotContents(2, r.getExtra().copy());
					}else{
						inv.setInventorySlotContents(2, null);
					}
					return;
				}
			}
		}else{
			outputItem();
		}
	}

	private boolean canCraft() {
		return  inv.getStackInSlot(0) != null;
	}

	private void craft() {
		inv.setInventorySlotContents(0, null);
		outputItem();
	}

	private void outputItem() {
		if(inv.getStackInSlot(1) != null){
			if(InventoryUtils.dropIntoInventory(inv.getStackInSlot(1), out)){
				inv.setInventorySlotContents(1, null);
			}
		}
		if(inv.getStackInSlot(2) != null){
			if(InventoryUtils.dropIntoInventory(inv.getStackInSlot(2), out)){
				inv.setInventorySlotContents(2, null);
			}
		}
	}

	private void search() {
		VecInt vec = new VecInt(this).add(getDirection().opposite().toVecInt());
		TileEntity tile = vec.getTileEntity(worldObj);
		if(tile != null){
			CompoundElectricCables comp = MgUtils.getElectricCond(tile, VecInt.NULL_VECTOR, 0);
			if(comp != null){
				cond = comp.getCond(0);
			}
		}
		vec = new VecInt(this).add(getDirection().opposite().toVecInt()).add(0, 1, 0);
		tile = vec.getTileEntity(worldObj);
		if(tile instanceof TileMB_Inv){
			in = ((TileMB_Inv) tile).getInv();
		}
		
		vec = new VecInt(this).add(getDirection().opposite().toVecInt());
		vec.add(getDirection().opposite().step(MgDirection.DOWN).toVecInt());
		tile = vec.getTileEntity(worldObj);
		if(tile instanceof TileMB_Inv){
			out = ((TileMB_Inv) tile).getInv();
		}
	}

	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		inv.readFromNBT(nbt);
		working = nbt.getBoolean("Work");
		progress = nbt.getInteger("Progress");
		speed = nbt.getInteger("Speed");
		for(int i = 0; i < 4; i++){
			leverState[i] = nbt.getBoolean("Lever_"+i);
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		inv.writeToNBT(nbt);
		nbt.setBoolean("Work", working);
		nbt.setInteger("Progress", progress);
		nbt.setInteger("Speed", speed);
		for(int i = 0; i < 4; i++){
			nbt.setBoolean("Lever_"+i, leverState[i]);
		}
	}

	public boolean isActive() {
		return getBlockMetadata() > 5;
	}
	
	public MgDirection getDirection(){
		return MgDirection.getDirection(getBlockMetadata());
	}
	
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox(){
        return INFINITE_EXTENT_AABB;
    }

	public void switchClick(int id) {
		leverCount[id] = 50;
		leverState[id] ^= true; 
		working = leverState[0];
		int speed = 100;
		if(leverState[1])speed -= 25;
		if(leverState[2])speed -= 25;
		if(leverState[3])speed -= 25;
		this.speed = speed;
	}
	
	public float getDelta() {
		long aux = time;
		time = System.nanoTime();
		return time - aux;
	}
}
