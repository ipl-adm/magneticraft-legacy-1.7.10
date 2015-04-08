package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.electricity.Conductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.tile.TileConductorLow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TilePumpJack extends TileConductorLow implements IFluidHandler1_8{

	//client
	public float m;
	public MgDirection facing;
	public boolean active;
	//server
	public TankMg tank = new TankMg(this, 4000);
	public long time;
	private List<BlockPosition> pipes = new ArrayList<BlockPosition>();
	private List<BlockPosition> oil = new ArrayList<BlockPosition>();
	private List<BlockPosition> finder = new ArrayList<BlockPosition>();
	private List<BlockPosition> Visited = new ArrayList<BlockPosition>();
	private int alt;
	private boolean update;
	private int cooldown;
	private boolean blocked;
	private MgDirection[] sides = {MgDirection.NORTH,MgDirection.EAST,MgDirection.SOUTH,MgDirection.WEST,MgDirection.DOWN,MgDirection.UP};
	private int buffer;
	public static Block replacement = ManagerBlocks.oilSourceDrained;
	private int Speed = 50;
	private boolean working;
	

	@Override
	public IElectricConductor initConductor() {
		return new Conductor(this);
	}

	public void updateEntity(){
		super.updateEntity();
		if(cond.getVoltage() > ElectricConstants.MACHINE_WORK){	
			working = true;
		}else{
			working = false;
		}
		if(worldObj.getWorldTime()%20 == 0){
			if(working && !isActive()){
				setActive(true);
			}else if(!working && isActive()){
				setActive(false);
			}
		}
		if(!update){
			facing = getOrientation(getBlockMetadata());
			if(worldObj.isRemote)return;
			if(worldObj.provider.getWorldTime() % 40 == 0)
				update = searchForOil();
			export();
			return;
		}
		if(worldObj.isRemote)return;
		export();
		if(!blocked){
			if(cooldown > 0)cooldown--;
			if(cooldown <= 0){
				cooldown = 20;	
				if(pipes.size() == 0){
					getOil();
					blocked = true;
				}else{
					if(cond.getVoltage() > ElectricConstants.MACHINE_WORK){
						BlockPosition c = pipes.get(0);
						worldObj.setBlock(c.getX(),c.getY(),c.getZ(), ManagerBlocks.concreted_pipe);
						cond.drainPower(1000);
						pipes.remove(0);
					}
				}
			}
		}else{
			if(cond.getVoltage() > ElectricConstants.MACHINE_WORK && tank.getSpace() > 0 && buffer > 0){
				int i =  Math.min(Speed,buffer);
				buffer -= tank.fill(FluidRegistry.getFluidStack("oil", i), true);
				cond.drainPower(i*100);
			}
			
			if(buffer <= 0){
				if(oil.size() == 0){
					update = false;
					blocked = false;
					return;
				}else{
					BlockPosition b = oil.get(0);
					int m = worldObj.getBlockMetadata(b.getX(), b.getY(), b.getZ());
					if(m > 0){
						worldObj.setBlockMetadataWithNotify(b.getX(), b.getY(), b.getZ(), m-1, 2);
					}else{
						worldObj.setBlock(b.getX(), b.getY(), b.getZ(), replacement);
						oil.remove(0);
					}
					buffer = 1000;
				}
			}
		}
	}
	
	private void setActive(boolean b) {
		active = b;
		sendUpdateToClient();
	}

	public boolean isActive() {
		return active;
	}

	private void export() {
		if(tank.getFluidAmount() > 0){
			for(MgDirection d : MgDirection.VALID_DIRECTIONS){
				TileEntity t = MgUtils.getTileEntity(this, d);
				if(t instanceof IFluidHandler){
					IFluidHandler f = (IFluidHandler) t;
					if(f.canFill(d.getForgeDir(), FluidRegistry.getFluid("oil"))){
						int m = f.fill(d.getForgeDir(), tank.drain(100, false), true);
						tank.drain(m, true);
					}
					if(tank.getFluidAmount() == 0)break;
				}
			}
		}
	}

	private void getOil() {
		oil.clear();
		pathFinder(new BlockPosition(xCoord, alt, zCoord));
		Visited.clear();
		finder.clear();
	}

	public void pathFinder(BlockPosition c){
		if(oil.size() > 20)return;
		if(Visited.size() > 4000){
			alt--;
			return;
		}
		for(MgDirection d : sides){
			BlockPosition bc = new BlockPosition(c.getX()+d.getOffsetX(), c.getY()+d.getOffsetY(), c.getZ()+d.getOffsetZ());
			if(Visited.contains(bc))continue;
			Visited.add(bc);

			Block b = worldObj.getBlock(bc.getX(), bc.getY(), bc.getZ());
			int m = worldObj.getBlockMetadata(bc.getX(), bc.getY(), bc.getZ());

			if(Block.isEqualTo(b, ManagerBlocks.oilSource)){ 
				oil.add(bc);
				if(!finder.contains(bc))finder.add(bc);
			}else if(Block.isEqualTo(b,replacement)){
				if(!finder.contains(bc))finder.add(bc);
			}
		}
		List<BlockPosition> temp = new ArrayList<BlockPosition>();
		temp.addAll(finder);
		for(BlockPosition cc : temp){
			finder.remove(cc);
			pathFinder(cc);
		}
	}

	public boolean searchForOil(){
		pipes.clear();
		for(int y=yCoord-1;y>0;y--){
			Block b = worldObj.getBlock(xCoord+facing.getOffsetX(), y, zCoord+facing.getOffsetZ());
			if(Block.isEqualTo(b, ManagerBlocks.oilSource) || Block.isEqualTo(b, replacement)){
				alt = y;
				getOil();
				if(oil.isEmpty()){
					alt = 0;
				}else{
					return true;
				}
			}else if(!Block.isEqualTo(b, ManagerBlocks.concreted_pipe)){
				pipes.add(new BlockPosition(xCoord+facing.getOffsetX(), y, zCoord+facing.getOffsetZ()));
			} 
		}
		return false;
	}


	@Override
	public int fill(MgDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(MgDirection from, FluidStack resource,
			boolean doDrain) {
		return drain(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(MgDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(MgDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrain(MgDirection from, Fluid fluid) {
		return fluid.getName() == "oil";
	}

	@Override
	public FluidTankInfo[] getTankInfo(MgDirection from) {
		return new FluidTankInfo[]{tank.getInfo()};
	}

	public float getDelta() {
		long aux = time;
		time = System.nanoTime();
		return time - aux;
	}
	
	public MgDirection getOrientation(int meta) {
		if(meta == 0)return MgDirection.EAST;
		if(meta == 1)return MgDirection.SOUTH;
		if(meta == 2)return MgDirection.WEST;
		if(meta == 3)return MgDirection.NORTH;
		return MgDirection.NORTH;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt, "oil");
		buffer = nbt.getInteger("Buffer");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt, "oil");
		nbt.setInteger("Buffer", buffer);
	}
	
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(xCoord-1, yCoord, zCoord-1, xCoord+1, yCoord+2, zCoord+1);
    }
}
