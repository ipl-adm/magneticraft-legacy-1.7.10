package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.heat.prefab.HeatConductor;
import com.cout970.magneticraft.api.radiation.IRadiactiveItem;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.IReactorComponent;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.tile.TileHeatConductor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileReactorVessel extends TileHeatConductor implements IInventoryManaged, IGuiSync, IBarProvider, IReactorComponent {

    private static final double AVOGADROS_CONSTANT = 6.022E23;
    public InventoryComponent inv = new InventoryComponent(this, 1, "ReactorVessel");
    private double neutrons;
    private double production;

    @Override
    public InventoryComponent getInv() {
        return inv;
    }

    public String getName() {
        return "Reactor Vessel";
    }

    @Override
    public IHeatConductor initHeatCond() {
        return new HeatConductor(this, 2000, 1000);
    }

    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        production = 0;
        double desintegration = 0;
        ItemStack g = inv.getStackInSlot(0);
        if (g != null) {
            if (g.getItem() instanceof IRadiactiveItem) {
                IRadiactiveItem item = (IRadiactiveItem) g.getItem();
                double initialMass = item.getGrams(g);//mass
                double NewMass = initialMass * Math.exp(-item.getDecayConstant(g) * 5E11);//natural decay
                item.setGrams(g, NewMass);
                double prod = ((initialMass - NewMass) * AVOGADROS_CONSTANT * item.getEnergyPerFision(g));
                prod *= 1E-10;
                g.setItemDamage(g.getItem().getDamage(g));
                heat.applyCalories(EnergyConverter.RFtoCALORIES(prod));
                production += EnergyConverter.RFtoCALORIES(prod);
            }
        }
        neutrons = 0;
        addRadiation(desintegration);
    }

    public void addRadiation(double d) {
        neutrons += d;
    }

    public void setRadiation(double rad) {
        neutrons = rad;
    }

    public double getRadiation() {
        return neutrons;
    }

    public double getSpeed() {
        return 1E-12;//7E9;
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting c) {
        c.sendProgressBarUpdate(cont, 0, (int) heat.getTemperature());
        c.sendProgressBarUpdate(cont, 1, (int) production);
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 0) heat.setTemperature(value);
        if (id == 1) production = value;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        getInv().readFromNBT(nbt);
        neutrons = nbt.getDouble("Neutrons");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        getInv().writeToNBT(nbt);
        nbt.setDouble("Neutrons", neutrons);
    }

    @Override
    public String getMessage() {
        return "Calories generated: " + production + "cal/t";
    }

    @Override
    public float getLevel() {
        return (float) Math.min(Math.sqrt(production) * 0.01, 1);
    }

    @Override
    public int getType() {
        return IReactorComponent.ID_VESSEL;
    }

    public int getSizeInventory() {
        return getInv().getSizeInventory();
    }

    public ItemStack getStackInSlot(int s) {
        return getInv().getStackInSlot(s);
    }

    public ItemStack decrStackSize(int a, int b) {
        return getInv().decrStackSize(a, b);
    }

    public ItemStack getStackInSlotOnClosing(int a) {
        return getInv().getStackInSlotOnClosing(a);
    }

    public void setInventorySlotContents(int a, ItemStack b) {
        getInv().setInventorySlotContents(a, b);
    }

    public String getInventoryName() {
        return getInv().getInventoryName();
    }

    public boolean hasCustomInventoryName() {
        return getInv().hasCustomInventoryName();
    }

    public int getInventoryStackLimit() {
        return getInv().getInventoryStackLimit();
    }

    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    public void openInventory() {
    }

    public void closeInventory() {
    }

    public boolean isItemValidForSlot(int a, ItemStack b) {
        return getInv().isItemValidForSlot(a, b);
    }

    @Override
    public float getMaxLevel() {
        // TODO Auto-generated method stub
        return 0;
    }
}
