package com.cout970.magneticraft.tileentity;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.cout970.magneticraft.api.conveyor.ConveyorSide;
import com.cout970.magneticraft.api.conveyor.IConveyor;
import com.cout970.magneticraft.api.conveyor.ItemBox;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.util.MgBeltUtils;
import com.cout970.magneticraft.util.Orientation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileConveyorBelt extends TileBase implements IConveyor{

	public ConveyorSide left = new ConveyorSide(this, true);
	public ConveyorSide right = new ConveyorSide(this, false);
	public long time;
	
	public MgDirection getDir(){
		return getOrientation().getDirection();
	}
	
	public void onBlockBreaks(){
		if(worldObj.isRemote)return;
		for(ItemBox b : left.content){
			BlockMg.dropItem(b.getContent(), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
		}
		for(ItemBox b : right.content){
			BlockMg.dropItem(b.getContent(), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
		}
	}

	public void updateEntity(){
		super.updateEntity();
		moveCole(left);
		moveCole(right);
		time = System.currentTimeMillis();
		if(worldObj.getTotalWorldTime() % 200 == 0)
			sendUpdateToClient();
	}

	private void moveCole(ConveyorSide side) {
		if(worldObj.getTotalWorldTime() % 10 == 0){
			Arrays.fill(side.spaces, false);
		}
		
//		for (int i = side.content.size()-1; i >= 0 ; i--) {
		for (int i = 0; i<side.content.size() ; i++) {
			ItemBox b = side.content.get(i);
			if(b.lastTick == worldObj.getTotalWorldTime())continue;
			b.lastTick = worldObj.getTotalWorldTime();
			if(b.getPosition() < 15){
				side.addvance(b);
				continue;
			}
			TileEntity t = ConveyorSide.getFrontConveyor(this);
			if(!MgBeltUtils.isBelt(t))continue;
			IConveyor con = (IConveyor) t;
			BeltInteraction iter = BeltInteraction.InterBelt(getDir(), con.getDir());
			if(iter == BeltInteraction.DIRECT){
				if(b.getPosition() < 16)side.addvance(b);
				if(b.getPosition() == 16){
					con.getSideLane(side.isLeft).setSpace(0, false);
					if(con.inject(0, b, side.isLeft, false)){
						extract(b, side.isLeft, false);
					}
				}
			}else if(con.getOrientation().getLevel() == 0){ 
				if(iter == BeltInteraction.LEFT_T){
					if(b.getPosition() < 18)side.addvance(b);
					if(b.getPosition() == 18){
						int new_pos = side.isLeft ? 2 : 10;
						con.getSideLane(false).setSpace(new_pos, false);
						if(con.inject(new_pos, b, false, false)){
							extract(b, side.isLeft, false);
						}
					}
				}else if(iter == BeltInteraction.RIGHT_T){
					if(b.getPosition() < 18)side.addvance(b);
					if(b.getPosition() == 18){
						int new_pos = side.isLeft ? 10 : 2;
						con.getSideLane(true).setSpace(new_pos, false);
						if(con.inject(new_pos, b, true, false)){
							extract(b, side.isLeft, false);
						}
					}
				}
			}
		}
	}

	//IConveyor

	@Override
	public boolean extract(ItemBox box, boolean isOnLeft, boolean simulated) {
		ConveyorSide side = isOnLeft ? left : right;
		if(side.content.contains(box)){
			if(!simulated){
				side.content.remove(box);
				side.setSpace(box.getPosition(), false);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean inject(int pos, ItemBox box, boolean isOnLeft, boolean simulated) {
		ConveyorSide side = isOnLeft ? left : right;
		if(side.hasSpace(pos)){
			if(!simulated){
				box.setPosition(pos);
				box.setOnLeft(isOnLeft);
				side.setSpace(pos, true);
				side.content.add(box);
			}
			return true;
		}
		return false;
	}

	public boolean addItem(MgDirection dir, int pos, ItemBox b, boolean sim){
		if(b == null)return false;
		boolean onLeft;
		if(dir.isPerpendicular(getDir())){
			if(dir == getDir().step(MgDirection.UP)){
				onLeft = true;
			}else{
				onLeft = false;
			}
		}else{
			if(b.isOnLeft()){
				onLeft = true;
			}else{
				onLeft = false;
			}
		}
		boolean ret = inject(pos, b, onLeft, sim);
		sendUpdateToClient();
		return ret;
	}

	//save and load

	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		left.save(nbt);
		right.save(nbt);
	}

	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		left.load(nbt);
		right.load(nbt);
	}

	@Override
	public TileEntity getParent() {
		return this;
	}

	@Override
	public ConveyorSide getSideLane(boolean left) {
		return left ? this.left : this.right;
	}

	public Orientation getOrientation() {
		return Orientation.fromMeta(getBlockMetadata());
	}
	
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord+1+5/16f, yCoord+1+5/16f, zCoord+1+5/16f);
    }

	@Override
	public boolean removeItem(ItemBox it, boolean isLeft, boolean simulated) {
		boolean ret = extract(it, isLeft, simulated);
		if(!simulated)sendUpdateToClient();
		return ret;
	}
}
