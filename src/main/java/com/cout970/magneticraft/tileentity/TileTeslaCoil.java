package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IBatteryItem;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.BufferedConductor;
import com.cout970.magneticraft.api.util.ConnectionClass;
import com.cout970.magneticraft.api.util.IConnectable;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TileTeslaCoil extends TileConductorLow {

    private List<EntityPlayer> nearPlayers = new ArrayList<>();

    @Override
    public IElectricConductor initConductor() {
        return new BufferedConductor(this, ElectricConstants.RESISTANCE_COPPER_LOW, 320000, ElectricConstants.MACHINE_DISCHARGE, ElectricConstants.MACHINE_CHARGE) {
            @Override
            public boolean isAbleToConnect(IConnectable e, VecInt v) {
                return e.getConnectionClass(v.getOpposite()) == ConnectionClass.FULL_BLOCK || e.getConnectionClass(v.getOpposite()) == ConnectionClass.SLAB_BOTTOM || VecInt.fromDirection(MgDirection.DOWN).equals(v);
            }

            @Override
            public ConnectionClass getConnectionClass(VecInt v) {
                if (v.toMgDirection() == MgDirection.DOWN) return ConnectionClass.FULL_BLOCK;
                return ConnectionClass.SLAB_BOTTOM;
            }
        };
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) return;
        if (cond.getVoltage() > ElectricConstants.MACHINE_WORK) {
            if (worldObj.getTotalWorldTime() % 20 == 0) {
                nearPlayers.clear();
                List<EntityPlayer> e = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - 10, yCoord - 10, zCoord - 10, xCoord + 10, yCoord + 10, zCoord + 10));
                nearPlayers.addAll(e.stream().filter(o -> o != null).collect(Collectors.toList()));
            }

            for (EntityPlayer p : nearPlayers) {
                for (int i = 0; i < p.inventory.getSizeInventory(); i++) {
                    ItemStack stack = p.inventory.getStackInSlot(i);
                    if (stack != null && stack.getItem() instanceof IBatteryItem) {
                        IBatteryItem batteryItem = (IBatteryItem) stack.getItem();
                        if (batteryItem.canAcceptCharge(stack)) {
                            int space = batteryItem.getMaxCharge(stack) - batteryItem.getCharge(stack);
                            if (space > 0 && cond.getVoltage() > ElectricConstants.MACHINE_WORK) {
                                int change = (int) Math.min(space, cond.getVoltage() * 12);
                                if (change > 0) {
                                    batteryItem.charge(stack, change);
                                    cond.drainPower(change);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 3, zCoord + 1);
    }
}
