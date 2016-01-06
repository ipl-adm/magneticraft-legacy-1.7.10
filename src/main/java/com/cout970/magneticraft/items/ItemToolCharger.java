package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.electricity.IBatteryItem;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemToolCharger extends ItemCharged {

    public ItemToolCharger(String unlocalizedname) {
        super(unlocalizedname, (int) EnergyConverter.RFtoW(500000));
        setMaxStackSize(1);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    public void onUpdate(ItemStack item, World w, Entity p, int c, boolean flag) {
        super.onUpdate(item, w, p, c, flag);
        if (p instanceof EntityPlayer) {
            ItemStack[] i = ((EntityPlayer) p).inventory.mainInventory;
            for (ItemStack s : i) {
                if (s != null) {
                    Item it = s.getItem();
                    if (it instanceof IBatteryItem) {
                        IBatteryItem st = (IBatteryItem) it;
                        if (st.canProvideEnergy(item)) {
                            int space = getMaxCharge(item) - getCharge(item);
                            int toMove = Math.min(space, st.getCharge(s));
                            toMove = (int) Math.min(toMove, EnergyConverter.RFtoW(500));
                            if (toMove > 0) {
                                st.discharge(s, toMove);
                                charge(item, toMove);
                            }
                        } else if (st.canAcceptCharge(item)) {
                            int space = st.getMaxCharge(s) - st.getCharge(s);
                            int toMove = Math.min(space, getCharge(item));
                            toMove = (int) Math.min(toMove, EnergyConverter.RFtoW(500));
                            if (toMove > 0) {
                                st.charge(s, toMove);
                                discharge(item, toMove);
                            }
                        }
                    } else if (ManagerIntegration.COFH_ENERGY && (it instanceof IEnergyContainerItem)) {//calcs in RF
                        IEnergyContainerItem st = (IEnergyContainerItem) it;
                        int space = st.getMaxEnergyStored(s) - st.getEnergyStored(s);
                        int toMove = (int) Math.min(space, EnergyConverter.WtoRF(getCharge(item)));
                        if (toMove > 0) {
                            st.receiveEnergy(s, toMove, false);
                            discharge(item, (int) EnergyConverter.RFtoW(toMove));
                        }
                    }
                }
            }
        }
    }
}
