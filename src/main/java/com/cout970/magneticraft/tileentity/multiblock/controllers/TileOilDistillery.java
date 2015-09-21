package com.cout970.magneticraft.tileentity.multiblock.controllers;

import com.cout970.magneticraft.api.access.RecipeOilDistillery;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IElectricTile;
import com.cout970.magneticraft.api.electricity.prefab.BufferedConductor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.tileentity.TileRefineryTank;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Base;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Energy_Low;
import com.cout970.magneticraft.util.fluid.TankMg;
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
    private TankMg input;
    private TankMg output;
    public IElectricConductor side1, side2, own = new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW, 8000, ElectricConstants.MACHINE_DISCHARGE, ElectricConstants.MACHINE_CHARGE);
    private double[] flow = new double[3];

    public void updateEntity() {
        super.updateEntity();
        if (drawCounter > 0) drawCounter--;
        if (!isActive()) return;
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
                    output.fill(recipe.getOutput(), true);
                    own.drainPower(recipe.getEnergyCost());
                }
            }
        }
    }

    private void valance(IElectricConductor a, IElectricConductor b, int i) {
        double resistence = a.getResistance() + b.getResistance();
        double difference = a.getVoltage() - b.getVoltage();
        double change = flow[i];
        double slow = change * resistence;
        flow[i] += ((difference - slow) * a.getIndScale()) / a.getVoltageMultiplier();
        change += (difference * a.getCondParallel()) / a.getVoltageMultiplier();
        a.applyCurrent(-change);
        b.applyCurrent(change);
    }

    private void search() {
        VecInt vec = getDirection().opposite().toVecInt().multiply(2);
        TileEntity t = MgUtils.getTileEntity(this, vec);
        if (t instanceof TileRefineryTank) {
            input = ((TileRefineryTank) t).getTank();
        }
        vec = getDirection().opposite().toVecInt().add(0, 1, 0);
        t = MgUtils.getTileEntity(this, vec);
        if (t instanceof TileRefineryTank) {
            output = ((TileRefineryTank) t).getTank();
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
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 0) own.setVoltage(value);
        else if (id == 1) own.setStorage(value);
        if (input == null || output == null) return;
        if (id == 2)
            if (value == -1) input.setFluid(null);
            else input.setFluid(new FluidStack(FluidRegistry.getFluid(value), 1));
        if (id == 3) input.getFluid().amount = value;

        if (id == 4)
            if (value == -1) output.setFluid(null);
            else output.setFluid(new FluidStack(FluidRegistry.getFluid(value), 1));
        if (id == 5) output.getFluid().amount = value;
    }

    @Override
    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata() % 6);
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
        return TileEntity.INFINITE_EXTENT_AABB;
    }

    public TankMg getInput() {
        return input;
    }

    public TankMg getOutput() {
        return output;
    }

    @Override
    public IElectricConductor[] getConds(VecInt dir, int Vtier) {
        if (dir == VecInt.NULL_VECTOR) return new IElectricConductor[]{own};
        return null;
    }
}
