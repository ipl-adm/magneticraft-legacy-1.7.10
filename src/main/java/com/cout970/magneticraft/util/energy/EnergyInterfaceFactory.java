package com.cout970.magneticraft.util.energy;

import cofh.api.energy.IEnergyHandler;
import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.electricity.IEnergyInterfaceFactory;
import com.cout970.magneticraft.api.electricity.InteractionHelper;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.compat.ManagerIntegration;
import cpw.mods.fml.common.Optional;
import ic2.api.energy.tile.IEnergySink;
import mods.railcraft.api.electricity.IElectricGrid;
import mods.railcraft.api.electricity.IElectricGrid.ChargeHandler.ConnectType;
import net.minecraft.tileentity.TileEntity;

public class EnergyInterfaceFactory implements IEnergyInterfaceFactory {

    public static void init() {
        InteractionHelper.registerEnergyInterfaceFactory(new EnergyInterfaceFactory());
    }

    @Override
    public boolean shouldHandleTile(TileEntity tile, VecInt f, int tier) {
        return (tier == 0) && ((ManagerIntegration.RAILCRAFT && (tile instanceof IElectricGrid)) || (ManagerIntegration.COFH_ENERGY && (tile instanceof IEnergyHandler)) || (ManagerIntegration.IC2 && (tile instanceof IEnergySink)));
    }

    @Override
    public IEnergyInterface getEnergyInterface(TileEntity tile, VecInt f, int tier) {
        if (tier == 0) {
            if ((ManagerIntegration.RAILCRAFT) && (tile instanceof IElectricGrid)) {
                if (((IElectricGrid) tile).getChargeHandler().getType() == ConnectType.BLOCK)
                    return getElectricalGrid((IElectricGrid) tile);
            }
            if (ManagerIntegration.COFH_ENERGY && (tile instanceof IEnergyHandler) && (f.toMgDirection() != null)) {
                if (((IEnergyHandler) tile).canConnectEnergy(f.toMgDirection().toForgeDir())) {
                    return getEnergyHandler((IEnergyHandler) tile, f.toMgDirection());
                }
            }
            if (ManagerIntegration.IC2 && (tile instanceof IEnergySink) && (f.toMgDirection() != null)) {
                if (((IEnergySink) tile).acceptsEnergyFrom(null, f.toMgDirection().toForgeDir())) {
                    return getEnergySink((IEnergySink) tile, f.toMgDirection());
                }
            }
        }
        return null;
    }

    @Optional.Method(modid = "IC2")
    public static IEnergyInterface getEnergySink(IEnergySink tile, MgDirection dir) {
        return new EU_EnergyInterfaceSink(tile, dir);
    }

    @Optional.Method(modid = "Railcraft")
    public static IEnergyInterface getElectricalGrid(IElectricGrid g) {
        return new RailcraftChargeEnergyInteface(g);
    }

    @Optional.Method(modid = "CoFHAPI|energy")
    public static IEnergyInterface getEnergyHandler(IEnergyHandler tile, MgDirection dir) {
        return new RF_EnergyInterface(tile, dir);
    }
}
