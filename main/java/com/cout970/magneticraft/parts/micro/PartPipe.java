package com.cout970.magneticraft.parts.micro;

import java.util.LinkedList;

import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fluids.IFluidTank;
import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import codechicken.multipart.scalatraits.TFluidHandlerTile;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.fluid.FluidNetwork;
import com.cout970.magneticraft.util.fluid.FluidUtils;
import com.cout970.magneticraft.util.fluid.IFluidTransport;
import com.cout970.magneticraft.util.fluid.TankMg;

public abstract class PartPipe extends MgPart implements IFluidTransport,TFluidHandlerTile, IFluidHandler1_8{

	private FluidNetwork net;
	public TankMg buffer;

	public PartPipe(Item a) {
		super(a);
	}

	@Override
	public void update() {
		if(W().isRemote)return;
		if(net == null){	
			boolean hasNetwork = false;
			for(MgDirection dir : MgDirection.values()){
				TileEntity e = MgUtils.getTileEntity(tile(), dir);
				if(FluidUtils.isPipe(e)){
					IFluidTransport it = FluidUtils.getFluidTransport(e);
					if(it != null && isComaptible(it)){
						if(this.canConectOnSide(dir) && it.canConectOnSide(dir.opposite())){
							if(it.getNetwork() != null){
								if(!hasNetwork){
									setNetwork(it.getNetwork());
									getNetwork().addPipe(this);
									hasNetwork = true;
								}else{
									getNetwork().mergeWith(it.getNetwork());
								}		
							}
						}
					}
				}
			}
			if(!hasNetwork){
				setNetwork(FluidNetwork.create(this,tile()));
			}
			if(net != null && !net.getPipes().contains(this))net.addPipe(this);
			getNetwork().refresh();
		}
	}

	public void save(NBTTagCompound nbt)
	{
		super.save(nbt);
		((TankMg) getTank()).writeToNBT(nbt, "net");
	}

	public void load(NBTTagCompound nbt)
	{
		super.load(nbt);
		((TankMg) getTank()).readFromNBT(nbt, "net");
		if(net != null && net.fluid == null){
			if(getTank().getFluid() != null)net.fluid = getTank().getFluid().getFluid();
		}
	}

	@Override
	public TileEntity getTileEntity() {
		return tile();
	}

	@Override
	public void setNetwork(FluidNetwork fluidNetwork) {
		net = fluidNetwork;
	}

	@Override
	public void onNetworkUpdate() {}

	@Override
	public FluidNetwork getNetwork() {
		return net;
	}

	@Override
	public IFluidTank getTank() {
		if(buffer == null)buffer = new TankMg(tile(), 100);
		return buffer;
	}
	
	@Override
	public void bindPart(TMultiPart arg0) {}

	@Override
	public void clearParts() {}

	@Override
	public void copyFrom(TileMultipart arg0) {}

	@Override
	public void partRemoved(TMultiPart arg0, int arg1) {}

	@Override
	public LinkedList<IFluidHandler> tankList() {
		LinkedList<IFluidHandler> l = new LinkedList<IFluidHandler>();
		l.add(this);
		return l;
	}

	@Override
	public void tankList_$eq(LinkedList<IFluidHandler> arg0) {}


	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(this instanceof IFluidHandler1_8)return((IFluidHandler1_8)this).fillMg(MgDirection.getDirection(from.ordinal()), resource, doFill);
		return 0;
	}

	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).drainMg_F(MgDirection.getDirection(from.ordinal()), resource,doDrain);
		return null;
	}

	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).drainMg(MgDirection.getDirection(from.ordinal()),maxDrain,doDrain);
		return null;
	}

	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).canFillMg(MgDirection.getDirection(from.ordinal()),fluid);
		return false;
	}

	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).canDrainMg(MgDirection.getDirection(from.ordinal()),fluid);
		return false;
	}

	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).getTankInfoMg(MgDirection.getDirection(from.ordinal()));
		return null;
	}

	public enum ConnectionMode{
		OUTPUT,INPUT,NOTHING;

		public static ConnectionMode getNext(ConnectionMode c) {
			if(c == null)return OUTPUT;
			if(c == OUTPUT)return INPUT;
			if(c == INPUT)return NOTHING;
			return OUTPUT;
		}
	}
}