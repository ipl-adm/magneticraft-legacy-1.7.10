package com.cout970.magneticraft.items;

import cofh.api.energy.IEnergyContainerItem;
import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.api.electricity.IBatteryItem;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.items.block.ItemBlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.lwjgl.input.Keyboard;

import java.util.List;


public class ItemBattery extends ItemCharged {

    public ItemBattery(String unlocalizedname) {
        super(unlocalizedname, (int) EnergyConversor.RFtoW(500000));
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
                            int space = (int) (st.getMaxCharge(s) - st.getCharge(s));
                            int toMove = Math.min(space, getCharge(stack));
                            if (toMove > 0) {
                                st.charge(s, toMove);
                                discharge(stack, toMove);
                            }
                        }
                    } else if (Magneticraft.COFH_ENERGY && (it instanceof IEnergyContainerItem)) {//calcs in RF
                        IEnergyContainerItem st = (IEnergyContainerItem) it;
                        int space = (int) (st.getMaxEnergyStored(s) - st.getEnergyStored(s));
                        int toMove = (int) Math.min(space, EnergyConversor.WtoRF(getCharge(stack)));
                        if (toMove > 0) {
                            st.receiveEnergy(s, toMove, false);
                            discharge(stack, (int) EnergyConversor.RFtoW(toMove));
                        }
                    }

                }
            }
        }
        return super.onItemRightClick(stack, w, p);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer p, @SuppressWarnings("rawtypes") List info, boolean flag) {
        super.addInformation(item, p, info, flag);
        if (p != null && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            info.add(ItemBlockMg.format + (int) EnergyConversor.WtoRF(getCharge(item)) + "/" + (int) EnergyConversor.WtoRF(getMaxCharge(item)) + " RF");
        }
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
