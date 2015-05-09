package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.util.BlockPosition;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.Log;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.multiblock.Multiblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileTurbineControl extends TileMB_Base implements IGuiSync,IBarProvider{

	private static final int MAX_STEAM = 1200;
	public TankMg[] in = new TankMg[4];
	public IElectricConductor out;
	private double prod;
	private double counter;
	//render
	public int drawCounter;
	public float animation;
	private long time;
	
	public boolean isActive() {
		return getBlockMetadata() > 6;
	}

	public void updateEntity(){
		super.updateEntity();
		if(drawCounter > 0)drawCounter--;
		if (!isActive())
			return;
		if(in[0] == null || out == null || worldObj.getWorldTime() % 200 == 0){
			search();
			return;
		}

		if (worldObj.isRemote)return;
		balanceTanks();
		int steam = (getFluidAmount()*MAX_STEAM)/64000;
		if(steam > 0 && out.getVoltage() < ElectricConstants.MAX_VOLTAGE*out.getVoltageMultiplier()){
			drain(steam, true);
			double p = EnergyConversor.STEAMtoW(steam);
			out.applyPower(p);
			counter += p;
		}
		if(worldObj.getWorldTime()%20 == 0){
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
			if(in[i].getFluid() != null && in[i].getFluid().getFluid().getID() == FluidRegistry.getFluidID("steam")){
				int extract = Math.min(in[i].getFluidAmount(), steam);
				in[i].drain(extract,b);
				steam -= extract;
			}
		}
	}

	public int getFluidAmount() {
		int steam = 0;
		for(int i =0;i<4;i++){
			if(in[i] != null && in[i].getFluid() != null && in[i].getFluid().getFluid().getID() == FluidRegistry.getFluidID("steam"))
				steam += in[i].getFluidAmount();
		}
		return steam;
	}

	private void search() {
		VecInt vec = getDirection().getVecInt().getOpposite();
		TileEntity t = MgUtils.getTileEntity(this, vec.copy().add(getDirection().opposite().step(MgDirection.UP).getVecInt()));
		if(t instanceof TileMgTank){
			in[0] = ((TileMgTank) t).getTank();
		}
		t = MgUtils.getTileEntity(this, vec.copy().add(getDirection().opposite().step(MgDirection.DOWN).getVecInt()));
		if(t instanceof TileMgTank){
			in[1] = ((TileMgTank) t).getTank();
		}
		t = MgUtils.getTileEntity(this, vec.copy().multiply(2).add(getDirection().opposite().step(MgDirection.UP).getVecInt()));
		if(t instanceof TileMgTank){
			in[2] = ((TileMgTank) t).getTank();
		}
		t = MgUtils.getTileEntity(this, vec.copy().multiply(2).add(getDirection().opposite().step(MgDirection.DOWN).getVecInt()));
		if(t instanceof TileMgTank){
			in[3] = ((TileMgTank) t).getTank();
		}
		t = MgUtils.getTileEntity(this, vec.copy().add(new VecInt(0, 1, 0)));
		
		if(t instanceof IElectricTile){
			out = ((IElectricTile) t).getConds(vec.getOpposite(), 2).getCond(0);
		}
	}

	@Override
	public void onDestroy(World w, BlockPosition p, Multiblock c, MgDirection e) {
		setActive(false);
		in = new TankMg[4];
		out = null;
	}

	@Override
	public void onActivate(World w, BlockPosition p, Multiblock c, MgDirection e) {
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
	public String getMessage() {
		return "Generating: "+prod+"W";
	}

	@Override
	public float getLevel() {
		return (float) Math.min(prod/EnergyConversor.STEAMtoW(MAX_STEAM),1);
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting craft) {
		if(out != null)
			craft.sendProgressBarUpdate(cont, 0, (int)out.getVoltage());
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
		if(id == 0)out.setVoltage(value);
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
}
