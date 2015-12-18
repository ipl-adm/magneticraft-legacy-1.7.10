package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.electricity.ElectricUtils;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import cofh.api.energy.IEnergyHandler;
import ic2.api.tile.IEnergyStorage;
import mods.railcraft.api.electricity.IElectricGrid;
import mods.railcraft.api.electricity.IElectricGrid.ChargeHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemVoltmeter extends ItemBasic {

    public ItemVoltmeter(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
        setMaxStackSize(1);
    }

    public boolean onItemUse(ItemStack item, EntityPlayer p, World w, int x, int y, int z, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (w.isRemote) return false;
        TileEntity t = w.getTileEntity(x, y, z);
        for (int i = 0; i < 5; i++) {
            IElectricConductor[] comp = ElectricUtils.getElectricCond(t, VecInt.NULL_VECTOR, i);
            if (comp != null) {
                for (IElectricConductor cond : comp) {
                    double I = cond.getIntensity();
                    String s = String.format("Reading %.2fV %.3fA (%.3fkW)", cond.getVoltage(), I, cond.getVoltage() * I / 1000);
                    p.addChatMessage(new ChatComponentText(s));
                }
            }
        }

        if (ManagerIntegration.RAILCRAFT && (t instanceof IElectricGrid)) {
            IElectricGrid h = (IElectricGrid) t;
            ChargeHandler handler = h.getChargeHandler();
            String s = String.format("Charge: %.2fc | Draw: %.2fc/t | Loss: %.2fc/t", handler.getCharge(), handler.getDraw(), handler.getLosses());
            p.addChatMessage(new ChatComponentText(s));
        }

        if (ManagerIntegration.IC2 && (t instanceof IEnergyStorage)) {
            IEnergyStorage h = (IEnergyStorage) t;
            int stored = h.getStored();
            int max = h.getCapacity();
            String s = "Energy Stored: " + stored + "/" + max + " EU";
            p.addChatMessage(new ChatComponentText(s));
        }

        if (ManagerIntegration.COFH_ENERGY && (t instanceof IEnergyHandler)) {
            IEnergyHandler h = (IEnergyHandler) t;
            int stored = h.getEnergyStored(ForgeDirection.getOrientation(side));
            int max = h.getMaxEnergyStored(ForgeDirection.getOrientation(side));
            String s = "Energy Stored: " + stored + "/" + max + " RF";
            p.addChatMessage(new ChatComponentText(s));
        }
        return false;
    }
}
