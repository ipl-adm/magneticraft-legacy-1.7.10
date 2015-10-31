package com.cout970.magneticraft.tileentity.multiblock.controllers;

import com.cout970.magneticraft.api.access.RecipePolymerizer;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.*;
import com.cout970.magneticraft.client.gui.component.IBarProvider;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.tileentity.TileCopperTank;
import com.cout970.magneticraft.tileentity.TileHeater;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Base;
import com.cout970.magneticraft.util.IInventoryManaged;
import com.cout970.magneticraft.util.InventoryComponent;
import com.cout970.magneticraft.util.InventoryUtils;
import com.cout970.magneticraft.util.fluid.TankMg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TilePolymerizer extends TileMB_Base implements IInventoryManaged, ISidedInventory, IGuiSync {

    public int progress;
    public int maxProgress = 200;
    public InventoryComponent inv = new InventoryComponent(this, 2, "Polymerizer");
    public TankMg input;
    public IHeatConductor heater;
    public InventoryComponent in, out;
    public int drawCounter;


    public InventoryComponent getInv() {
        return inv;
    }

    public void updateEntity() {
        super.updateEntity();
        if (drawCounter > 0) drawCounter--;
        if (!isActive()) return;
        if (input == null || in == null || out == null || heater == null || worldObj.getTotalWorldTime() % 20 == 0) {
            searchTanks();
            return;
        }
        if (worldObj.isRemote) return;
        if (isControlled() && canCraft()) {
            if (progress >= maxProgress) {
                craft();
                progress = 0;
            } else {
                progress++;
            }
            heater.drainCalories(EnergyConverter.RFtoCALORIES(40));
        } else {
            progress = 0;
        }
        distributeItems();
    }

    private void distributeItems() {
        if (in != null) {
            if (((TileBase) in.tile).isControlled()) {
                if (getInv().getStackInSlot(0) != null) {
                    int s = InventoryUtils.findCombination(in, getInv().getStackInSlot(0));
                    if (s != -1) {
                        InventoryUtils.traspass(in, this, s, 0);
                    }
                } else {
                    setInventorySlotContents(0, InventoryUtils.getItemStack(in));
                }
            }
        }
        if (out != null) {
            if (((TileBase) out.tile).isControlled()) {
                if (getInv().getStackInSlot(1) != null) {
                    int s = InventoryUtils.getSlotForStack(out, getInv().getStackInSlot(1));
                    if (s != -1) {
                        InventoryUtils.traspass(this, out, 1, s);
                    }
                }
            }
        }
    }

    private void craft() {
        RecipePolymerizer recipe = RecipePolymerizer.getRecipe(getInv().getStackInSlot(0));
        decrStackSize(0, 1);
        if (getInv().getStackInSlot(1) == null) {
            getInv().setInventorySlotContents(1, recipe.getOutput().copy());
        } else {
            ItemStack i = getInv().getStackInSlot(1);
            i.stackSize += recipe.getOutput().stackSize;
            getInv().setInventorySlotContents(1, i);
        }
        heater.drainCalories(EnergyConverter.RFtoCALORIES(100));
        input.drain(500, true);
    }

    private boolean canCraft() {
        RecipePolymerizer recipe = RecipePolymerizer.getRecipe(getInv().getStackInSlot(0));
        if (recipe == null) return false;
        if (!MgUtils.areEqual(recipe.getFluid(), input.getFluid())) return false;
        if (input.getFluidAmount() < recipe.getFluid().amount) return false;
        if (heater.getTemperature() < recipe.getTemperature()) return false;
        ItemStack output = getInv().getStackInSlot(1);
        return (output == null)
                || ((MgUtils.areEqual(output, recipe.getOutput(), true)) && ((output.stackSize + recipe.getOutput().stackSize) <= getInventoryStackLimit()));
    }

    private void searchTanks() {
        MgDirection d = MgDirection.getDirection(getBlockMetadata() % 6).opposite();
        VecInt vec = d.toVecInt();
        TileEntity tile = MgUtils.getTileEntity(this, vec.copy().multiply(4));

        if (tile instanceof TileCopperTank) {
            input = ((TileCopperTank) tile).getTank();
        }
        tile = MgUtils.getTileEntity(this, vec.copy().add(d.step(MgDirection.DOWN).toVecInt().getOpposite()));
        if (tile instanceof IInventoryManaged) {
            in = ((IInventoryManaged) tile).getInv();
        }
        tile = MgUtils.getTileEntity(this, vec.copy().add(d.step(MgDirection.UP).toVecInt().getOpposite()));
        if (tile instanceof IInventoryManaged) {
            out = ((IInventoryManaged) tile).getInv();
        }
        tile = MgUtils.getTileEntity(this, vec.copy().multiply(3));
        if (tile instanceof TileHeater) {
            IHeatConductor[] comp = ((TileHeater) tile).getHeatCond(vec.getOpposite());
            if (comp != null) {
                heater = comp[0];
            }
        }
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        getInv().readFromNBT(nbt);
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        getInv().writeToNBT(nbt);
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        if (input == null || heater == null) return;
        craft.sendProgressBarUpdate(cont, 0, (int) heater.getTemperature());
        if (input.getFluidAmount() > 0) {
            craft.sendProgressBarUpdate(cont, 1, input.getFluid().getFluidID());
            craft.sendProgressBarUpdate(cont, 2, input.getFluidAmount());
        } else craft.sendProgressBarUpdate(cont, 1, -1);

        craft.sendProgressBarUpdate(cont, 3, progress);
        ((TileHeater) heater.getParent()).sendGUINetworkData(cont, craft);
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (input == null || heater == null) return;
        if (id == 0) heater.setTemperature(value);
        if (id == 1)
            if (value == -1) input.setFluid(null);
            else input.setFluid(new FluidStack(FluidRegistry.getFluid(value), 1));
        if (id == 2) input.getFluid().amount = value;
        if (id == 3) progress = value;
        ((TileHeater) heater.getParent()).getGUINetworkData(id, value);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return new int[]{};
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_,
                                 int p_102007_3_) {
        return false;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_,
                                  int p_102008_3_) {
        return false;
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

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        VecInt v1 = VecIntUtil.getRotatedOffset(getDirection().opposite(), -1, -1, 0);
        VecInt v2 = VecIntUtil.getRotatedOffset(getDirection().opposite(), 1, 1, 4);
        VecInt block = new VecInt(xCoord, yCoord, zCoord);

        return VecIntUtil.getAABBFromVectors(v1.add(block), v2.add(block));
    }

    @Override
    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    public IBarProvider getProgressBar() {
        return new IBarProvider() {

            @Override
            public String getMessage() {
                return null;
            }

            @Override
            public float getMaxLevel() {
                return maxProgress;
            }

            @Override
            public float getLevel() {
                return progress;
            }
        };
    }

    public boolean isActive() {
        return getBlockMetadata() > 5;
    }
}
