package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.pressure.IPressureConductor;
import com.cout970.magneticraft.api.pressure.PressureUtils;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.List;

public class ItemManometer extends ItemBasic {

    public ItemManometer(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.SteamAgeTab);
        setMaxStackSize(1);
    }

    public boolean onItemUse(ItemStack item, EntityPlayer p, World w, int x, int y, int z, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (w.isRemote) return false;
        TileEntity t = w.getTileEntity(x, y, z);
        List<IPressureConductor> list = PressureUtils.getPressureCond(t, VecInt.NULL_VECTOR);
        for (IPressureConductor cond : list) {
            String s = String.format("Reading %.2fbar, %dpa, %.2fpsi", EnergyConverter.PAtoBAR(cond.getPressure()), (int) cond.getPressure(), EnergyConverter.PAtoPSI(cond.getPressure()));
            p.addChatMessage(new ChatComponentText(s));
        }
        return false;
    }

    @SuppressWarnings({"rawtypes"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
        super.addInformation(item, player, list, flag);
    }
}
