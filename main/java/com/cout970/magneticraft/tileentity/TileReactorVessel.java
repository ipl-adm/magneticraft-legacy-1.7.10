package com.cout970.magneticraft.tileentity;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.cout970.magneticraft.api.heat.HeatConductor;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.radiation.IRadiactiveItem;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IManagerInventory;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.TileHeatConductor;

public class TileReactorVessel extends TileHeatConductor implements IManagerInventory,IGuiSync,IBarProvider{

	private static final double AVOGADROS_CONSTANT = 6.022E23;
	public InventoryComponent inv = new InventoryComponent(this,4,"ReactorVessel");
	private double controlRods = 100;
	private double neutrons;
	private double production;
	
	@Override
	public InventoryComponent getInv(){
		return inv;
	}
	
	public String getName(){
		return "Reactor Vessel";
	}
	
	@Override
	public IHeatConductor initHeatCond() {
		return new HeatConductor(this, 2000, 1000);
	}
	
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		production = 0;
		double desintegration = 0;
		for(int l = 0; l< 4;l++){
			ItemStack g = inv.getStackInSlot(l);
			if(g != null){
				if(g.getItem() instanceof IRadiactiveItem){
					IRadiactiveItem item = (IRadiactiveItem) g.getItem();
					double initialMass = item.getGrams(g);//mass
					double NewMass = initialMass*Math.exp(-item.getDecayConstant(g)*3600);//natural decay
					NewMass *= Math.exp(-item.getDecayConstant(g)*getRadiation());
					item.setGrams(g,NewMass);
					desintegration += ((initialMass-NewMass)*2.9d);//neutrons emited
					double prod = ((initialMass-NewMass)*AVOGADROS_CONSTANT*item.getEnergyPerFision(g));
					prod = EnergyConversor.RealJOULEStoCALORIES(prod);
					
					g.setItemDamage(g.getItem().getDamage(g));
					heat.applyCalories(prod);
					production += prod;
//					System.out.println(prod+" "+neutrons+" "+(initialMass-NewMass));
//					double life = (Math.log(2)/(i.getDecayConstant(g)))/getSpeed();
//					System.out.println(life/20+"s "+life/3600+"h "+life/(31556926)+"y ");//the half life of the element
				}
			}
		}
		neutrons = 0;
		addRadiation(desintegration);
	}

	public void addRadiation(double d) {
		neutrons += d;
	}

	public double getRadiation() {
		return neutrons;
	}

	public double getSpeed() {
		return 100;
	}
	
	public void activateControlRods(int percent){
		this.controlRods = percent;
	}
	
	public void desactivateControlsRods(){
		controlRods = 100;
	}
	
	public boolean isControlsRodsDesplegated(){
		return controlRods != 100;
	}

	@Override
	public void sendGUINetworkData(Container cont, ICrafting c) {
		c.sendProgressBarUpdate(cont, 0, (int)heat.getTemperature());
		c.sendProgressBarUpdate(cont, 1, (int)production);
	}

	@Override
	public void getGUINetworkData(int id, int value) {
		if(id == 0)heat.setTemperature(value);
		if(id == 1)production = value;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		getInv().readFromNBT(nbt);
		controlRods = nbt.getDouble("Rods");
		neutrons = nbt.getDouble("Neutrons");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		getInv().writeToNBT(nbt);
		nbt.setDouble("Rods", controlRods);
		nbt.setDouble("Neutrons", neutrons);
	}

	@Override
	public String getMessage() {
		return "Calories generated: "+production+"cal/t";
	}

	@Override
	public float getLevel() {
		return (float) Math.min(Math.sqrt(production)*0.01,1);
	}
}
