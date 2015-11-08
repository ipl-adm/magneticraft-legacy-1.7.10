package com.cout970.magneticraft.tileentity.multiblock.controllers;

import com.cout970.magneticraft.api.access.RecipeRefinery;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VecIntUtil;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.tileentity.TileRefineryTank;
import com.cout970.magneticraft.tileentity.multiblock.TileMB_Base;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.multiblock.Multiblock;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileRefinery extends TileMB_Base implements IGuiSync {

    public TankMg input, output0, output1, output2;
    public int drawCounter;

    public void updateEntity() {
        super.updateEntity();
        if (drawCounter > 0) drawCounter--;
        if (!isActive()) return;
        if (input == null) {
            searchTanks();
        }
        if (worldObj.isRemote) return;
        if (input == null || output0 == null || output1 == null || output2 == null) return;
        if (input.getFluidAmount() > 0 && isControlled()) {
            RecipeRefinery recipe = RecipeRefinery.getRecipe(input.getFluid());
            if (recipe != null) {
                if (input.getFluidAmount() >= recipe.getInput().amount) {
                    if (recipe.getOut0() == null || output0.getFluid() == null || (MgUtils.areEqual(recipe.getOut0(), output0.getFluid()) && recipe.getOut0().amount <= output0.getSpace())) {
                        if (recipe.getOut1() == null || output1.getFluid() == null || (MgUtils.areEqual(recipe.getOut1(), output1.getFluid()) && recipe.getOut1().amount <= output1.getSpace())) {
                            if (recipe.getOut2() == null || output2.getFluid() == null || (MgUtils.areEqual(recipe.getOut2(), output2.getFluid()) && recipe.getOut2().amount <= output2.getSpace())) {
                                input.drain(recipe.getInput().amount, true, false);
                                output0.fill(recipe.getOut0().copy(), true, false);
                                output1.fill(recipe.getOut1().copy(), true, false);
                                output2.fill(recipe.getOut2().copy(), true, false);
                            }
                        }
                    }
                }
            }
        }
    }

    public void searchTanks() {
        VecInt vec = MgDirection.getDirection(getBlockMetadata() % 6).opposite().toVecInt();
        TileEntity tile = MgUtils.getTileEntity(this, vec.copy().multiply(2));

        if (tile instanceof TileRefineryTank) {
            input = ((TileRefineryTank) tile).getTank();
            input.setAllowInOut(true);
        }
        tile = MgUtils.getTileEntity(this, vec.copy().multiply(2).add(new VecInt(0, 2, 0)));
        if (tile instanceof TileRefineryTank) {
            output0 = ((TileRefineryTank) tile).getTank();
            output0.setAllowInput(false);
            output0.setAllowOutput(true);
        }
        tile = MgUtils.getTileEntity(this, vec.copy().multiply(2).add(new VecInt(0, 4, 0)));
        if (tile instanceof TileRefineryTank) {
            output1 = ((TileRefineryTank) tile).getTank();
            output1.setAllowInput(false);
            output1.setAllowOutput(true);
        }
        tile = MgUtils.getTileEntity(this, vec.copy().multiply(2).add(new VecInt(0, 6, 0)));
        if (tile instanceof TileRefineryTank) {
            output2 = ((TileRefineryTank) tile).getTank();
            output2.setAllowInput(false);
            output2.setAllowOutput(true);
        }
    }

    @Override
    public void onDestroy(World w, VecInt p, Multiblock c, MgDirection e) {
        setActive(false);
        input = null;
        output0 = null;
        output1 = null;
        output2 = null;
    }

    @Override
    public void onActivate(World w, VecInt p, Multiblock c, MgDirection e) {
        setActive(true);
    }

    public MgDirection getDirectionMeta() {
        return MgDirection.getDirection(getBlockMetadata());
    }

    @Override
    public MgDirection getDirection() {
        return getDirectionMeta();
    }

    public boolean isActive() {
        return getBlockMetadata() > 5;
    }

    private void setActive(boolean b) {
        if (b)
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() % 6 + 6, 2);
        else
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, getBlockMetadata() % 6, 2);
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        VecInt v1 = VecIntUtil.getRotatedOffset(getDirection().opposite(), -1, -1, 0);
        VecInt v2 = VecIntUtil.getRotatedOffset(getDirection().opposite(), 1, 6, 2);
        VecInt block = new VecInt(xCoord, yCoord, zCoord);

        return VecIntUtil.getAABBFromVectors(v1.add(block), v2.add(block));
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        if (input == null || output0 == null || output1 == null || output2 == null) return;
        if (input.getFluidAmount() > 0) {
            craft.sendProgressBarUpdate(cont, 1, input.getFluid().getFluidID());
            craft.sendProgressBarUpdate(cont, 2, input.getFluidAmount());
        } else craft.sendProgressBarUpdate(cont, 1, -1);

        if (output0.getFluidAmount() > 0) {
            craft.sendProgressBarUpdate(cont, 3, output0.getFluid().getFluidID());
            craft.sendProgressBarUpdate(cont, 4, output0.getFluidAmount());
        } else craft.sendProgressBarUpdate(cont, 3, -1);

        if (output1.getFluidAmount() > 0) {
            craft.sendProgressBarUpdate(cont, 5, output1.getFluid().getFluidID());
            craft.sendProgressBarUpdate(cont, 6, output1.getFluidAmount());
        } else craft.sendProgressBarUpdate(cont, 5, -1);

        if (output2.getFluidAmount() > 0) {
            craft.sendProgressBarUpdate(cont, 7, output2.getFluid().getFluidID());
            craft.sendProgressBarUpdate(cont, 8, output2.getFluidAmount());
        } else craft.sendProgressBarUpdate(cont, 7, -1);
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (input == null || output0 == null || output1 == null || output2 == null) return;
        if (id == 1)
            if (value == -1) input.setFluid(null);
            else input.setFluid(new FluidStack(FluidRegistry.getFluid(value), 1));
        if (id == 2) input.getFluid().amount = value;

        if (id == 3)
            if (value == -1) output0.setFluid(null);
            else output0.setFluid(new FluidStack(FluidRegistry.getFluid(value), 1));
        if (id == 4) output0.getFluid().amount = value;

        if (id == 5)
            if (value == -1) output1.setFluid(null);
            else output1.setFluid(new FluidStack(FluidRegistry.getFluid(value), 1));
        if (id == 6) output1.getFluid().amount = value;

        if (id == 7)
            if (value == -1) output2.setFluid(null);
            else output2.setFluid(new FluidStack(FluidRegistry.getFluid(value), 1));
        if (id == 8) output2.getFluid().amount = value;
    }
}
