package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.electricity.IBatteryItem;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.compat.ManagerIntegration;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemSmallBattery extends ItemCharged {

    public ItemSmallBattery(String unlocalizedname) {
        super(unlocalizedname, (int) EnergyConverter.RFtoW(8000));
        setMaxStackSize(1);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    public ItemStack onItemRightClick(ItemStack stack, World w, EntityPlayer p) {

        if (!p.isSneaking()) {
            ItemStack[] i = p.inventory.mainInventory;
            for (ItemStack s : i) {
                if (s != null) {
                    Item it = s.getItem();
                    if (it instanceof IBatteryItem) {
                        IBatteryItem st = (IBatteryItem) it;
                        if (st.canAcceptCharge(s) && !st.canProvideEnergy(s)) {
                            int space = st.getMaxCharge(s) - st.getCharge(s);
                            int toMove = Math.min(space, getCharge(stack));
                            if (toMove > 0) {
                                st.charge(s, toMove);
                                discharge(stack, toMove);
                            }
                        }
                    } else if (ManagerIntegration.COFH_ENERGY && (it instanceof IEnergyContainerItem)) {//calcs in RF
                        IEnergyContainerItem st = (IEnergyContainerItem) it;
                        int space = st.getMaxEnergyStored(s) - st.getEnergyStored(s);
                        int toMove = (int) Math.min(space, EnergyConverter.WtoRF(getCharge(stack)));
                        if (toMove > 0) {
                            st.receiveEnergy(s, toMove, false);
                            discharge(stack, (int) EnergyConverter.RFtoW(toMove));
                        }
                    }

                }
            }
        }
        return super.onItemRightClick(stack, w, p);
    }


    @Override
    public boolean canExtractCharge(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return true;
    }
}
