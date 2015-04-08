package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

import com.cout970.magneticraft.api.conveyor.IConveyor;
import com.cout970.magneticraft.api.conveyor.ItemBox;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.util.MgBeltUtils;

public class TileConveyorBelt extends TileBase implements IConveyor{

	public ItemBox[] LeftSide = new ItemBox[4];
	public ItemBox[] RightSide = new ItemBox[4];

	public MgDirection getDir(){
		return MgDirection.getDirection(getBlockMetadata()).opposite();
	}
	
	public void onBlockBreaks(){
		if(worldObj.isRemote)return;
		for(int i=0;i<4;i++){
			if(LeftSide[i] != null){
				BlockMg.dropItem(LeftSide[i].getContent(), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
			}
			if(RightSide[i] != null){
				BlockMg.dropItem(RightSide[i].getContent(), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
			}
		}
	}

	public void updateEntity(){
		super.updateEntity();
		moveCole(LeftSide);
		moveCole(RightSide);
		if(worldObj.getWorldTime()% 20 == 0)
			sendUpdateToClient();
	}

	public void moveCole(ItemBox[] arr){
		float[] dist = {2/16f,6/16f,10/16f,14/16f};
		for(int i= arr.length-1;i>=0;i--){
			ItemBox b = arr[i];
			if(b == null)continue;
			if(b.getPosition() < dist[i]){
				b.advances();
			}else{
				if(i < arr.length-1){
					if(arr[i+1] == null){
						arr[i] = null;
						arr[i+1] = b;
					}
				}else{
					if(moveToNextConveyor(b))
						arr[arr.length-1] = null;
				}
			}
		}
	}

	private boolean moveToNextConveyor(ItemBox b){
		TileEntity t = MgUtils.getTileEntity(this, getDir());
		if(MgBeltUtils.isBelt(t)){
			if(MgBeltUtils.injectInBelt((IConveyor) t, b, getDir().opposite())){
				return true;
			}
		}
		return false;
	}

	//IConveyor

	@Override
	public boolean extract(int pos, ItemBox box, boolean isOnLeft, boolean simulated) {
		ItemBox[] v = null;
		if(isOnLeft)v = LeftSide;
		else v = RightSide;
		if(v[pos] == box){
			if(!simulated){
				v[pos] = null;
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean inject(int pos, ItemBox box, boolean isOnLeft, boolean simulated) {
		ItemBox[] v = null;
		if(isOnLeft)v = LeftSide;
		else v = RightSide;
		if(v[pos] == null){
			if(!simulated){
				v[pos] = box;
			}
			return true;
		}
		return false;
	}

	public boolean addItem(MgDirection dir, int pos, ItemBox b, boolean sim){
		if(b == null)return false;
		ItemBox[] vec = null;
		boolean onLeft;
		boolean successful;
		if(dir.isPerpendicular(getDir())){
			if(dir == getDir().step(MgDirection.UP)){
				vec = LeftSide;
				onLeft = true;
			}else{
				vec = RightSide;
				onLeft = false;
			}
		}else{
			if(b.isOnLeft()){
				vec = LeftSide;
				onLeft = true;
			}else{
				vec = RightSide;
				onLeft = false;
			}
		}
		successful = inject(pos, b, onLeft, sim);
		if(!sim && successful){
			b.setOnLeft(onLeft);
			b.setPosition(pos*5/16f-2/16f);
		}
		return successful;//Successful
	}

	@Override
	public ItemBox[] getContent(boolean onLeft) {
		if(onLeft)return LeftSide;
		return RightSide;
	}

	//save and load

	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for(int i=0;i<LeftSide.length;i++){
			NBTTagCompound t = new NBTTagCompound();
			if(LeftSide[i] != null){
				LeftSide[i].save(t);
			}
			list.appendTag(t);
		}
		nbt.setTag("Left_Boxes", list);
		list = new NBTTagList();
		for(int i=0;i<RightSide.length;i++){
			NBTTagCompound t = new NBTTagCompound();
			if(RightSide[i] != null){
				RightSide[i].save(t);
			}
			list.appendTag(t);
		}
		nbt.setTag("Right_Boxes", list);
	}

	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Left_Boxes",10);
		for(int i=0;i<LeftSide.length;i++){
			NBTTagCompound t = list.getCompoundTagAt(i);
			if(t.hasKey("Left")){
				if(LeftSide[i] == null)
					LeftSide[i] = new ItemBox(null);
				LeftSide[i].load(t);
			}else{
				LeftSide[i] = null;
			}
		}
		list = nbt.getTagList("Right_Boxes",10);
		for(int i=0;i<RightSide.length;i++){
			NBTTagCompound t = list.getCompoundTagAt(i);
			if(t.hasKey("Left")){
				if(RightSide[i] == null)
					RightSide[i] = new ItemBox(null);
				RightSide[i].load(t);
			}else{
				RightSide[i] = null;
			}
		}
	}
}
