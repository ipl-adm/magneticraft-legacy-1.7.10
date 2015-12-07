package com.cout970.magneticraft.tileentity.multiblock.controllers;

import com.cout970.magneticraft.api.access.RecipeOilDistillery;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VecIntUtil;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.tileentity.TileRefineryTank;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Base;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Energy_Low;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.tile.AverageBar;
import com.cout970.magneticraft.util.tile.MachineElectricConductor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileOilDistillery extends TileMB_Base implements IGuiSync, IElectricTile {

    public int drawCounter;
    public IElectricConductor side1, side2, own = new MachineElectricConductor(this);
    private TankMg input;
    private TankMg output;
    private double[] flow = new double[3];
    private AverageBar consumption = new AverageBar(20);
    private AverageBar production = new AverageBar(20);
    private AverageBar energy = new AverageBar(20);
    private double maxCost;

    public void updateEntity() {
        super.updateEntity();
        if (drawCounter > 0) drawCounter--;
        if (!isActive()) return;
        consumption.tick();
        production.tick();
        energy.tick();
        if (input == null || output == null || side1 == null || side2 == null) {
            search();
        } else {
            if (worldObj.isRemote) return;
            valance(side1, side2, 0);
            valance(side1, own, 1);
            valance(own, side2, 2);
            own.recache();
            own.iterate();
            if (own.getVoltage() > ElectricConstants.MACHINE_WORK && input.getFluidAmount() > 0 && output.getSpace() > 0) {
                RecipeOilDistillery recipe = RecipeOilDistillery.getRecipe(input.getFluid());
                if (recipe != null && (MgUtils.areEqual(recipe.getOutput(), output.getFluid()) || output.getFluid() == null) && output.getSpace() >= recipe.getOutput().amount) {
                    input.drain(recipe.getInput().amount, true);
                    consumption.addValue(recipe.getInput().amount);
                    output.fill(recipe.getOutput(), true, false);
                    production.addValue(recipe.getOutput().amount);
                    own.drainPower(recipe.getEnergyCost());
                    maxCost = recipe.getEnergyCost();
                    energy.addValue((float) recipe.getEnergyCost());
                }
            }
        }
    }

    private void valance(IElectricConductor a, IElectricConductor b, int i) {
        ElectricConductor.valance(a, b, flow, i);
    }

    private void search() {
        VecInt vec = getDirection().opposite().toVecInt().multiply(2);
        TileEntity t = MgUtils.getTileEntity(this, vec);
        if (t instanceof TileRefineryTank) {
            input = ((TileRefineryTank) t).getTank();
            input.setAllowInOut(true); //in case fluid was pumped accidentally
        }
        vec = getDirection().opposite().toVecInt().add(0, 1, 0);
        t = MgUtils.getTileEntity(this, vec);
        if (t instanceof TileRefineryTank) {
            output = ((TileRefineryTank) t).getTank();
            output.setAllowInput(false);
            output.setAllowOutput(true);
        }
        vec = getDirection().opposite().toVecInt().multiply(2).add(0, -1, 0);
        vec.add(getDirection().opposite().step(MgDirection.UP).toVecInt());
        t = MgUtils.getTileEntity(this, vec);
        if (t instanceof TileMB_Energy_Low) {
            side1 = ((TileMB_Energy_Low) t).getConds(VecInt.NULL_VECTOR, 0)[0];
        }
        vec = getDirection().opposite().toVecInt().multiply(2).add(0, -1, 0);
        vec.add(getDirection().opposite().step(MgDirection.DOWN).toVecInt());
        t = MgUtils.getTileEntity(this, vec);
        if (t instanceof TileMB_Energy_Low) {
            side2 = ((TileMB_Energy_Low) t).getConds(VecInt.NULL_VECTOR, 0)[0];
        }
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) own.getVoltage());
        craft.sendProgressBarUpdate(cont, 1, own.getStorage());
        if (input != null && output != null) {
            if (input.getFluidAmount() > 0) {
                craft.sendProgressBarUpdate(cont, 2, input.getFluid().getFluidID());
                craft.sendProgressBarUpdate(cont, 3, input.getFluidAmount());
            } else craft.sendProgressBarUpdate(cont, 2, -1);

            if (output.getFluidAmount() > 0) {
                craft.sendProgressBarUpdate(cont, 4, output.getFluid().getFluidID());
                craft.sendProgressBarUpdate(cont, 5, output.getFluidAmount());
            } else craft.sendProgressBarUpdate(cont, 4, -1);
        }
        craft.sendProgressBarUpdate(cont, 6, (int) consumption.getAverage()*16);
        craft.sendProgressBarUpdate(cont, 7, (int) production.getAverage()*16);
        craft.sendProgressBarUpdate(cont, 8, (int) maxCost*16);
        craft.sendProgressBarUpdate(cont, 9, (int) energy.getAverage()*16);
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        switch (id) {
            case 0:
                own.setVoltage(value);
                break;
            case 1:
                own.setStorage(value);
                break;
            case 6:
                consumption.setStorage(value/16f);
                break;
            case 7:
                production.setStorage(value/16f);
                break;
            case 8:
                maxCost = value/16f;
                break;
            case 9:
                energy.setStorage(value/16f);
                break;
        }
        if (input == null || output == null) return;
        switch (id) {
            case 2:
                if (value == -1) input.setFluid(null);
                else input.setFluid(new FluidStack(FluidRegistry.getFluid(value), 1));
                break;
            case 3:
                input.getFluid().amount = value;
                break;
            case 4:
                if (value == -1) output.setFluid(null);
                else output.setFluid(new FluidStack(FluidRegistry.getFluid(value), 1));
                break;
            case 5:
                output.getFluid().amount = value;
                break;
        }
    }

    @Override
    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    public boolean isActive() {
        return getBlockMetadata() > 5;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        own.load(nbt);
        flow[0] = nbt.getDouble("Conn1");
        flow[1] = nbt.getDouble("Conn2");
        flow[2] = nbt.getDouble("Conn3");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        own.save(nbt);
        nbt.setDouble("Conn1", flow[0]);
        nbt.setDouble("Conn2", flow[1]);
        nbt.setDouble("Conn3", flow[2]);
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        VecInt v1 = VecIntUtil.getRotatedOffset(getDirection().opposite(), -1, -1, 0);
        VecInt v2 = VecIntUtil.getRotatedOffset(getDirection().opposite(), 1, 1, 2);
        VecInt block = new VecInt(xCoord, yCoord, zCoord);

        return VecIntUtil.getAABBFromVectors(v1.add(block), v2.add(block));
    }

    public TankMg getInput() {
        return input;
    }

    public TankMg getOutput() {
        return output;
    }

    @Override
    public IElectricConductor[] getConds(VecInt dir, int Vtier) {
        if (VecInt.NULL_VECTOR.equals(dir)) return new IElectricConductor[]{own};
        return null;
    }

    public IBarProvider getEnergyBar(){
        return new IBarProvider() {
            @Override
            public String getMessage() {
                return String.format("Consumption %.3fkW", energy.getStorage()/1000);
            }

            @Override
            public float getLevel() {
                return energy.getStorage();
            }

            @Override
            public float getMaxLevel() {
                return maxCost != 0 ? (float) maxCost : energy.getStorage();
            }
        };
    }

    public float getConsumptionRate(){
        return consumption.getStorage();
    }


    public float getProductionRate(){
        return production.getStorage();
    }
}
