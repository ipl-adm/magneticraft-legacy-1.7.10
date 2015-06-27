package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.cout970.magneticraft.api.conveyor.ConveyorSide;
import com.cout970.magneticraft.api.conveyor.IConveyor;
import com.cout970.magneticraft.api.conveyor.ItemBox;
import com.cout970.magneticraft.api.electricity.BufferedConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecDouble;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.util.IGuiListener;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.MgBeltUtils;
import com.cout970.magneticraft.util.tile.TileConductorLow;

public class TileInserter extends TileBase implements IGuiListener{

	public InventoryComponent inv = new InventoryComponent(this, 1, "Inserter");
	public InventoryComponent filter = new InventoryComponent(this, 9, "Filter");
	public boolean whiteList;
	public boolean ignoreNBT;
	public boolean ignoreMeta;
	public byte valid_dirs = 0xF;
	public int counter = 0;
	public InserterAnimation anim = null;
	public int speed = 20;
	

	public MgDirection getDir(){
		return MgDirection.getDirection(getBlockMetadata());
	}
	
	public void onBlockBreaks(){
		if(worldObj.isRemote)return;
		BlockMg.dropItem(getInv().getStackInSlot(0), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
	}

	public void updateEntity(){
		super.updateEntity();
//		Log.debug(anim+" "+counter);
		if(anim == InserterAnimation.Retracting_Short || anim == InserterAnimation.Retracting_Large){
			if(counter >= 180){
				anim = InserterAnimation.Rotating;
				counter = 0;
				sendUpdateToClient();
			}else{
				counter+=speed;
			}
		}else if(anim == InserterAnimation.Retracting_INV_Short || anim == InserterAnimation.Retracting_INV_Large){
			if(counter >= 180){
				anim = InserterAnimation.Rotating_INV;
				counter = 0;
				sendUpdateToClient();
			}else{
				counter+=speed;
			}
		}else if(anim == InserterAnimation.Rotating){
			if(counter >= 180){
				TileEntity o = MgUtils.getTileEntity(this, getDir().opposite());
				if(o instanceof IConveyor && ((IConveyor) o).getOrientation().getLevel() == 0 && ((IConveyor) o).getDir().isPerpendicular(getDir())){
					anim = InserterAnimation.Extending_INV_Large;
				}else{
					anim = InserterAnimation.Extending_INV_Short;
				}
				counter = 0;
				sendUpdateToClient();
			}else{
				counter+=speed;
			}
		}else if(anim == InserterAnimation.Rotating_INV){
			if(counter >= 180){
				anim = InserterAnimation.Extending_Short;
				counter = 0;
				sendUpdateToClient();
			}else{
				counter+=speed;
			}
		}else if(getInv().getStackInSlot(0) != null){
			//leave a item
			if(counter >= 180 && (anim == InserterAnimation.Extending_INV_Short || anim == InserterAnimation.Extending_INV_Large)){
				TileEntity t = MgUtils.getTileEntity(this, getDir()), o = MgUtils.getTileEntity(this, getDir().opposite());
				if(o instanceof IInventory)
					dropToInv((IInventory)o);
				else if(o instanceof IConveyor && ((IConveyor) o).getOrientation().getLevel() == 0){
					dropToBelt((IConveyor)o);
				}else if(canDropItems() && worldObj.getWorldTime() % 10 == 0){
					dropItem();
				}
				if(getInv().getStackInSlot(0) == null){
					if(anim == InserterAnimation.Extending_INV_Short){
						anim = InserterAnimation.Retracting_INV_Short;
					}else{
						anim = InserterAnimation.Retracting_INV_Large;
					}
					counter = 0;
				}
				sendUpdateToClient();
			}else{
				counter+=speed;
				if(anim != InserterAnimation.Extending_INV_Short && anim != InserterAnimation.Extending_INV_Large){
					anim = InserterAnimation.Extending_INV_Short;
					sendUpdateToClient();
				}
			}
		}else if(getInv().getStackInSlot(0) == null){
			//cach a item
			if(counter >= 180 && (anim == InserterAnimation.Extending_Short || anim == InserterAnimation.Extending_Large)){
				if(isControled()){
					TileEntity t = MgUtils.getTileEntity(this, getDir()), o = MgUtils.getTileEntity(this, getDir().opposite());
					if(t instanceof IInventory){
						suckFromInv((IInventory)t, o);
					}
					else if(t instanceof IConveyor && ((IConveyor) t).getOrientation().getLevel() == 0){
						suckFromBelt((IConveyor)t, o);
					}
					if(getInv().getStackInSlot(0) != null){
						if(anim == InserterAnimation.Extending_Short){
							anim = InserterAnimation.Retracting_Short;
						}else{
							anim = InserterAnimation.Retracting_Large;
						}
						counter = 0;
						sendUpdateToClient();
					}
				}
			}else{
				counter+=speed;
				if(anim != InserterAnimation.Extending_Short || anim != InserterAnimation.Extending_Large){
					anim = InserterAnimation.Extending_Short;
					sendUpdateToClient();
				}
			}
		}else{
			anim = InserterAnimation.Extending_Short;
			counter = 0;
		}
	}

	
	
	private void dropItem() {
		if(worldObj.isRemote)return;
		VecInt vec1 = new VecInt(this).add(getDir().opposite().toVecInt());
		Block b = worldObj.getBlock(vec1.getX(), vec1.getY(), vec1.getZ());
		if(Block.isEqualTo(Blocks.air,b)){
			List l = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(vec1.getX(), vec1.getY(), vec1.getZ(), vec1.getX()+1, vec1.getY()+1, vec1.getZ()+1));
			if(l.isEmpty()){
				ItemStack item = getInv().getStackInSlot(0);
				if(item == null)return;
				VecDouble vec2 = new VecDouble(vec1);
				vec2.add(0.5, 0.5, 0.5);
				EntityItem entityItem = new EntityItem(worldObj, vec2.getX(), vec2.getY(), vec2.getZ(), new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}
				entityItem.motionX = 0;
				entityItem.motionY = 0;
				entityItem.motionZ = 0;
				worldObj.spawnEntityInWorld(entityItem);
				getInv().setInventorySlotContents(0, null);
			}
		}
	}

	private boolean canDropItems() {
		return false;//has a upgrade
	}

	private void dropToBelt(IConveyor t) {
		ItemStack s = getInv().getStackInSlot(0);
		if(t.addItem(getDir(), 2, new ItemBox(s), true)){
			getInv().setInventorySlotContents(0, null);
			t.addItem(getDir(), 2, new ItemBox(s), false);
			if(t instanceof TileBase)
			((TileBase) t).sendUpdateToClient();
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

	public boolean extractFromBelt(ConveyorSide side, IConveyor t, boolean left, Object obj){
		if(side.content.isEmpty())return false;
		ItemBox b = side.content.get(0);
		if(t.extract(b, left, true)){
			if(canInject(obj, b.getContent()) && canExtract(t, b.getContent())){
				t.extract(b, left, false);
				getInv().setInventorySlotContents(0, b.getContent());
				if(t instanceof TileBase)
					((TileBase) t).sendUpdateToClient();
				return true;
			}
		}
		return false;
	}

	private void suckFromInv(IInventory t, Object obj) {
		int slot = getSlotWithItemStack(t);
		if(slot != -1){
			ItemStack s = t.getStackInSlot(slot);
			if(canInject(obj, s) && canExtract(t, s)){
				getInv().setInventorySlotContents(0,s);
				t.setInventorySlotContents(slot, null);
			}
		}
	}

	public boolean canExtract(Object obj, ItemStack s){
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
	
	public int getSlotWithItemStack(IInventory i){
		for(MgDirection d : getValidDirections()){
			int s = MgBeltUtils.getSlotWithItemStack(i, d);
			if(s != -1)return s;
		}
		return -1;
	}
	
	private List<MgDirection> getValidDirections() {
		List<MgDirection> list = new ArrayList<MgDirection>();
		for(MgDirection d : MgDirection.values())
		if((valid_dirs & (1 << d.ordinal())) > 0)list.add(d);
		return list;
	}

	public boolean checkFilter(int slot, ItemStack i){
		ItemStack f = filter.getStackInSlot(slot);
		if(f == null)return false;
		if(f.getItem() != i.getItem())return false;
		if(!ignoreMeta)
			if(f.getItemDamage() != i.getItemDamage())return false;
		if(!ignoreNBT)
			if(f.getTagCompound() != i.getTagCompound())return false;
		return true;
	}
	
	public boolean canInject(Object obj, ItemStack s){
		if(obj instanceof IInventory){
			return MgBeltUtils.dropItemStackIntoInventory((IInventory)obj, s, MgDirection.UP, true) == 0;
		}else if(obj instanceof IConveyor){
			return ((IConveyor) obj).addItem(getDir(), 2, new ItemBox(s), true);
		}else{
			return canDropItems();
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
		anim = InserterAnimation.values()[nbt.getInteger("Animation") % InserterAnimation.values().length];
		whiteList = nbt.getBoolean("WhiteList");
		ignoreMeta = nbt.getBoolean("IgnoreMeta");
		ignoreNBT= nbt.getBoolean("IgnoreNBT");
		valid_dirs = nbt.getByte("ValidDirs");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		getInv().writeToNBT(nbt);
		nbt.setInteger("Stage", counter);
		nbt.setInteger("Animation", anim == null ? 0 : anim.ordinal());
		nbt.setBoolean("WhiteList", whiteList);
		nbt.setBoolean("IgnoreMeta", ignoreMeta);
		nbt.setBoolean("IgnoreNBT", ignoreNBT);
		nbt.setByte("ValidDirs", valid_dirs);
	}
	
	public enum InserterAnimation{
		Default, Rotating, Rotating_INV, Retracting_Short, Extending_Short, Retracting_INV_Short, Extending_INV_Short, Retracting_Large, Extending_Large, Retracting_INV_Large, Extending_INV_Large;
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
		}
	}
}
