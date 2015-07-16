package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.cout970.magneticraft.api.electricity.ElectricConductor;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.multiblock.Multiblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileSteamTurbineControl extends TileMB_Base implements IGuiSync{

	private static final int MAX_STEAM = 1200;
	public TankMg[] in = new TankMg[4];
	public IElectricConductor out;
	private double prod;
	private double counter;
	//render
	public int drawCounter;
	public float animation;
	public float speed;
	private long time;
	
	public IElectricConductor capacity = new ElectricConductor(this, 2, ElectricConstants.RESISTANCE_COPPER_MED){
		@Override
		public double getInvCapacity() {
			return EnergyConversor.RFtoW(1D);
		}
		 
		@Override
		public double getVoltageMultiplier() {
			return 100;
		}
	};
	private double flow;
	
	private void updateConductor() {
		if(out == null)return;
		double resistence = out.getResistance() + capacity.getResistance();
		double difference = out.getVoltage() - capacity.getVoltage();
		double change = flow;
		double slow = change * resistence;
		flow += ((difference - change * resistence) * out.getIndScale())/out.getVoltageMultiplier();
		change += (difference * out.getCondParallel())/out.getVoltageMultiplier();
		out.applyCurrent(-change);
		capacity.applyCurrent(change);
	}
	
	public boolean isActive() {
		return getBlockMetadata() > 6;
	}

	public void updateEntity(){
		super.updateEntity();
		if(drawCounter > 0)drawCounter--;
		if (!isActive())
			return;
		if(in[0] == null || out == null || worldObj.getTotalWorldTime() % 200 == 0){
			search();
			return;
		}
		float activity = 0.5f*(getFluidAmount()/64000f);
		if(activity < 0.01f)activity = 0;
		if(speed < activity){
			speed += 1/32f;
		}else if(speed > activity){
			speed -= 1/32f;
		}
		if (worldObj.isRemote)return;
		updateConductor();
		balanceTanks();
		double miss = (ElectricConstants.MAX_VOLTAGE - capacity.getVoltage()/100)*100;
		int steam = (int) Math.min(Math.min(getFluidAmount() > 1000 ? getFluidAmount()+1000 : getFluidAmount(), miss), MAX_STEAM);
		steam = (int) Math.min(steam, ((getFluidAmount()+1000)/64000f)*MAX_STEAM);
		if(steam > 0 && capacity.getVoltage() < ElectricConstants.MAX_VOLTAGE*100){
			drain(steam, true);
			double p = EnergyConversor.STEAMtoW(steam);
			capacity.applyPower(p);
			counter += p;
		}
		if(worldObj.getTotalWorldTime() % 20 == 1){
			prod = counter/20;
			counter = 0;
		}
	}

	private void balanceTanks() {
		int sum = getFluidAmount();
		int rest = sum%4;
		for(TankMg t : in){
			t.setFluid(null);
			t.fill(FluidRegistry.getFluidStack("steam", sum/4), true);
			if(rest > 0)
			rest -= t.fill(FluidRegistry.getFluidStack("steam", rest), true);
		}
	}

	public void drain(int steam, boolean b) {
		for(int i =0;i<4;i++){
			if(in[i].getFluid() != null && in[i].getFluid().getFluid() == FluidRegistry.getFluid("steam")){
				int extract = Math.min(in[i].getFluidAmount(), steam);
				in[i].drain(extract,b);
				steam -= extract;
			}
		}
	}

	public int getFluidAmount() {
		int steam = 0;
		for(int i =0;i<4;i++){
			if(in[i] != null && in[i].getFluid() != null && in[i].getFluid().getFluid() == FluidRegistry.getFluid("steam"))
				steam += in[i].getFluidAmount();
		}
		return steam;
	}

	private void search() {
		VecInt vec = getDirection().toVecInt().getOpposite();
		TileEntity t = MgUtils.getTileEntity(this, vec.copy().add(getDirection().opposite().step(MgDirection.UP).toVecInt()));
		if(t instanceof TileCopperTank){
			in[0] = ((TileCopperTank) t).getTank();
		}
		t = MgUtils.getTileEntity(this, vec.copy().add(getDirection().opposite().step(MgDirection.DOWN).toVecInt()));
		if(t instanceof TileCopperTank){
			in[1] = ((TileCopperTank) t).getTank();
		}
		t = MgUtils.getTileEntity(this, vec.copy().multiply(2).add(getDirection().opposite().step(MgDirection.UP).toVecInt()));
		if(t instanceof TileCopperTank){
			in[2] = ((TileCopperTank) t).getTank();
		}
		t = MgUtils.getTileEntity(this, vec.copy().multiply(2).add(getDirection().opposite().step(MgDirection.DOWN).toVecInt()));
		if(t instanceof TileCopperTank){
			in[3] = ((TileCopperTank) t).getTank();
		}
		t = MgUtils.getTileEntity(this, vec.copy().add(new VecInt(0, 1, 0)));
		
		if(t instanceof IElectricTile){
			out = ((IElectricTile) t).getConds(vec.getOpposite(), 2).getCond(0);
		}
	}

	@Override
	public void onDestroy(World w, VecInt p, Multiblock c, MgDirection e) {
		setActive(false);
		in = new TankMg[4];
		out = null;
	}

	@Override
	public void onActivate(World w, VecInt p, Multiblock c, MgDirection e) {
		setActive(true);
	}
	
	private void setActive(boolean b) {
		if(b)
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata()%6+6, 2);
		else
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata()%6, 2);
	}

	public int getCapacity() {
		return 64000;
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		if(out != null)
			craft.sendProgressBarUpdate(cont, 0, (int)capacity.getVoltage());
		craft.sendProgressBarUpdate(cont, 1, (int)prod);
		for(int i = 0;i< 4;i++){
			if(in[i] != null){
				if(in[i].getFluidAmount() > 0){
					craft.sendProgressBarUpdate(cont, i*2+2, in[i].getFluid().getFluidID());
					craft.sendProgressBarUpdate(cont, i*2+3, in[i].getFluidAmount());
				}else{
					craft.sendProgressBarUpdate(cont, i*2+2, -1);
				}
			}
		}
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)capacity.setVoltage(value);
		if(id == 1)prod = value;
		if(id >= 2 && id <= 9){
			int i = (id-2)/2;
			if(in[i] != null){
				if(id-i*2+2 != 0){
					if(in[i].getFluid() != null)
					in[i].setFluid(new FluidStack(in[i].getFluid(), value));
				}else{
					if(value == -1)
						in[i].setFluid(null);
					else
						in[i].setFluid(new FluidStack(FluidRegistry.getFluid(value),1));
				}
			}
		}
	}
	
	@Override
	public MgDirection getDirection() {
		return MgDirection.getDirection(getBlockMetadata()%6);
	}
	
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }
	
	public float getDelta() {
		long aux = time;
		time = System.nanoTime();
		return time - aux;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		super.readFromNBT(nbt);
		
		NBTTagList conduit = nbt.getTagList("Capacity_cond", 10);
		NBTTagCompound conduit_nbt = conduit.getCompoundTagAt(0);
		capacity.load(conduit_nbt);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt){
		super.writeToNBT(nbt);
		
		NBTTagList conduit = new NBTTagList();
		NBTTagCompound conduit_nbt = new NBTTagCompound();
		capacity.save(conduit_nbt);
		conduit.appendTag(conduit_nbt);
		nbt.setTag("Capacity_cond", conduit);
	}

	public IBarProvider getProductionBar() {
		return new IBarProvider() {
			
			@Override
			public String getMessage() {
				return String.format("Generating: %.3f kW",prod/1000d);
			}
			
			@Override
			public float getMaxLevel() {
				return (float) EnergyConversor.STEAMtoW(MAX_STEAM);
			}
			
			@Override
			public float getLevel() {
				return (float) Math.min(prod, getMaxLevel());
			}
		};
	}
}
