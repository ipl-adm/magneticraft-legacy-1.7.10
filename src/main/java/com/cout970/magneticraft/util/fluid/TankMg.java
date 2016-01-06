package com.cout970.magneticraft.util.fluid;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TankMg extends FluidTank {
    private boolean allowInput = true;
    private boolean allowOutput = true;

    public TankMg(TileEntity t, int capacity) {
        super(null, capacity);
        tile = t;
    }

    public void readFromNBT(NBTTagCompound main, String string) {
        NBTTagCompound nbt = (NBTTagCompound) main.getTag(string);
        if (nbt == null) {
            return;
        }
        this.readFromNBT(nbt);
    }

    @Override
    public FluidTank readFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("inputAllowed")) {
            allowInput = nbt.getBoolean("inputAllowed");
        }
        if (nbt.hasKey("outputAllowed")) {
            allowOutput = nbt.getBoolean("outputAllowed");
        }
        return super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setBoolean("inputAllowed", allowInput);
        nbt.setBoolean("outputAllowed", allowOutput);
        return super.writeToNBT(nbt);
    }

    public void writeToNBT(NBTTagCompound main, String string) {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        main.setTag(string, nbt);
    }

    public int getSpace() {
        return getCapacity() - getFluidAmount();
    }

    public TileEntity getParent() {
        return tile;
    }

    public boolean isInputAllowed() {
        return allowInput;
    }

    public void setAllowInput(boolean allowInput) {
        this.allowInput = allowInput;
    }

    public boolean isOutputAllowed() {
        return allowOutput;
    }

    public void setAllowOutput(boolean allowOutput) {
        this.allowOutput = allowOutput;
    }

    public int fill(FluidStack resource, boolean doFill, boolean respectMode) {
        if (respectMode && !isInputAllowed()) {
            return 0;
        }
        return super.fill(resource, doFill);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return fill(resource, doFill, true);
    }

    public FluidStack drain(int maxDrain, boolean doDrain, boolean respectMode) {
        if (respectMode && !isOutputAllowed()) {
            return null;
        }
        return super.drain(maxDrain, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return drain(maxDrain, doDrain, true);
    }

    public void setAllowInOut(boolean allowInOut) {
        setAllowInput(allowInOut);
        setAllowOutput(allowInOut);
    }
}
