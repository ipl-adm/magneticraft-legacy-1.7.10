package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileGeothermalPump extends TileHeatConductor implements IGuiSync{

	private List<BlockPosition> pipes = new ArrayList<BlockPosition>();
	private List<BlockPosition> lava = new ArrayList<BlockPosition>();
	private List<BlockPosition> finder = new ArrayList<BlockPosition>();
	private List<BlockPosition> Visited = new ArrayList<BlockPosition>();
	private int alt;
	private boolean update;
	private boolean working;
	private int cooldown;
	private boolean blocked;
	private MgDirection[] sides = {MgDirection.NORTH,MgDirection.EAST,MgDirection.SOUTH,MgDirection.WEST,MgDirection.DOWN,MgDirection.UP};
	public int buffer;

	@Override
	public IHeatConductor initHeatCond() {
		return new HeatConductor(this, 1600, 1000);
	}

	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		if(worldObj.provider.getWorldTime() % 20 == 0){
			if(working && !isActive()){
				setActive(true);
			}else if(!working && isActive()){
				setActive(false);
			}
		}
		if(!update){
			if(worldObj.provider.getWorldTime() % 40 == 0)
				update = searchForLava();
		}else{ 
			if(!blocked){
				if(cooldown > 0)cooldown--;
				if(cooldown <= 0){
					cooldown = 20;	
					if(pipes.size() == 0){
						getLava();
						blocked = true;
					}else{
						BlockPosition c = pipes.get(0);
						worldObj.setBlock(c.getX(),c.getY(),c.getZ(), ManagerBlocks.concreted_pipe);
						pipes.remove(0);
					}
				}
			}else if(isControled()){
				
				if(heat.getTemperature() < 1200 && buffer > 0){
					int i =  Math.min(10,buffer);
					heat.applyCalories(EnergyConversor.FUELtoCALORIES(i));
					buffer -= i;
					working = true;
				}else{
					working = false;
				}
				if(buffer <= 0){
					if(lava.size() == 0){
						update = false;
						blocked = false;
						working = false;
						return;
					}else{
						BlockPosition b = lava.get(0);
						Block bl = worldObj.getBlock(b.getX(), b.getY(), b.getZ());
						if(Block.isEqualTo(bl, Blocks.lava)){
							worldObj.setBlock(b.getX(), b.getY(), b.getZ(), Blocks.obsidian);
							buffer = 20000;
							working = true;
						}
						lava.remove(0);
					}
				}
			}
		}
	}
	
	private void setActive(boolean b) {
		if(b)
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 1, 2);
		else
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, 0, 2);
	}

	public boolean isActive() {
		return getBlockMetadata() != 0;
	}

	private void getLava() {
		lava.clear();
		BlockPosition bc = new BlockPosition(xCoord, alt, zCoord);
		Block b = worldObj.getBlock(bc.getX(), bc.getY(), bc.getZ());
		int m = worldObj.getBlockMetadata(bc.getX(), bc.getY(), bc.getZ());

		if(Block.isEqualTo(b, Blocks.lava)){ 
			if(m == 0)
				lava.add(bc);
			if(!finder.contains(bc))finder.add(bc);
		}if(Block.isEqualTo(b, Blocks.obsidian)){
			if(!finder.contains(bc))finder.add(bc);
		}
		pathFinder(bc);
		Visited.clear();
		finder.clear();
	}

	public void pathFinder(BlockPosition c){
		if(lava.size() > 20)return;
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

			if(Block.isEqualTo(b, Blocks.lava)){ 
				if(m == 0)
					lava.add(bc);
				if(!finder.contains(bc))finder.add(bc);
			}if(Block.isEqualTo(b, Blocks.obsidian)){
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

	public boolean searchForLava(){
		pipes.clear();
		for(int y=yCoord-1;y>0;y--){
			Block b = worldObj.getBlock(xCoord, y, zCoord);
			if(Block.isEqualTo(b, Blocks.obsidian) || Block.isEqualTo(b, Blocks.lava)){
				alt = y;
				getLava();
				if(lava.isEmpty()){
					alt = 0;
				}else{
					return true;
				}
			}else if(!Block.isEqualTo(b, ManagerBlocks.concreted_pipe)){
				pipes.add(new BlockPosition(xCoord, y, zCoord));
			} 
		}
		return false;
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		craft.sendProgressBarUpdate(cont, 0, (int) heat.getTemperature());
		craft.sendProgressBarUpdate(cont, 1, buffer);
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)heat.setTemperature(value);
		if(id == 1)buffer = value;
	}

	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		buffer = nbt.getInteger("Buffer");
	}
	
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		nbt.setInteger("Buffer", buffer);
	}
}
