package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.access.IThermophileDecay;
import com.cout970.magneticraft.api.access.MgRecipeRegister;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.ThermophileFuel;
import com.cout970.magneticraft.client.gui.component.IGuiSync;
import com.cout970.magneticraft.util.tile.TileConductorLow;
import net.minecraft.block.Block;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;

import java.util.ArrayList;
import java.util.List;

public class TileThermopile extends TileConductorLow implements IGuiSync {

    public int ticks;
    public double diff;
    public int tempHot;
    public int tempCold;

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this);
    }

    public void updateEntity() {
        super.updateEntity();

        if (worldObj.isRemote) return;
        if (this.cond.getVoltage() <= ElectricConstants.MAX_VOLTAGE && isControlled()) {

            ticks++;
            if (ticks > 20) {
                ticks = 0;
                updateTemps();
            }
            cond.applyPower(getCurrentFromDiff());
        }
    }

    public double getCurrentFromDiff() {
        return EnergyConverter.RFtoW(diff / 10);
    }

    private void updateTemps() {
        tempHot = 0;
        tempCold = 0;
        List<BlockInfo> list = new ArrayList<>();
        for (MgDirection d : MgDirection.VALID_DIRECTIONS) {
            Block bl = worldObj.getBlock(xCoord + d.getOffsetX(), yCoord + d.getOffsetY(), zCoord + d.getOffsetZ());
            int meta = worldObj.getBlockMetadata(xCoord + d.getOffsetX(), yCoord + d.getOffsetY(), zCoord + d.getOffsetZ());
            BlockInfo b = new BlockInfo(bl, meta, xCoord + d.getOffsetX(), yCoord + d.getOffsetY(), zCoord + d.getOffsetZ());
            list.add(b);
            tempHot += getHeat(b);
            tempCold += getCold(b);
        }
        diff = Math.min(tempHot, tempCold);
        for (IThermophileDecay t : MgRecipeRegister.thermopileDecays) {
            t.onCheck(worldObj, list, tempHot, tempCold);
        }
    }

    public double getHeat(BlockInfo b) {
        for (ThermophileFuel f : MgRecipeRegister.thermopileSources)
            if (f.heat && f.source.equals(b)) return f.temp;
        return 0;
    }

    public double getCold(BlockInfo b) {
        for (ThermophileFuel f : MgRecipeRegister.thermopileSources)
            if (!f.heat && f.source.equals(b)) return f.temp;
        return 0;
    }

    @Override
    public void sendGUINetworkData(Container cont, ICrafting craft) {
        craft.sendProgressBarUpdate(cont, 0, (int) diff * 1000);
        craft.sendProgressBarUpdate(cont, 1, tempHot);
        craft.sendProgressBarUpdate(cont, 2, tempCold);
        craft.sendProgressBarUpdate(cont, 3, (int) cond.getVoltage());
    }

    @Override
    public void getGUINetworkData(int id, int value) {
        if (id == 0) diff = value / 1000d;
        else if (id == 1) tempHot = value;
        else if (id == 2) tempCold = value;
        else if (id == 3) cond.setVoltage(value);
    }

    public double getMaxCurrentFromDiff() {
        return EnergyConverter.RFtoW(20);
    }
}
