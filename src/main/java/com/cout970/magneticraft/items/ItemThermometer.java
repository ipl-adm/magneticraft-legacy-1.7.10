package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.heat.HeatUtils;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.items.block.ItemBlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.List;

public class ItemThermometer extends ItemBasic {

    public ItemThermometer(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.SteamAgeTab);
        setMaxStackSize(1);
    }

    public boolean onItemUse(ItemStack item, EntityPlayer p, World w, int x, int y, int z, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (w.isRemote) return false;
        TileEntity t = w.getTileEntity(x, y, z);
        IHeatConductor[] comp = HeatUtils.getHeatCond(t, VecInt.NULL_VECTOR);
        if (comp != null) {
            for (IHeatConductor heat : comp) {
                if (heat != null) {
                    p.addChatMessage(new ChatComponentText("Temperature: " + String.format("%.2f", heat.getTemperature())));
                }
            }
        }
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
        super.addInformation(item, player, list, flag);
        list.add(ItemBlockMg.format + "Used to measure temperature of a heat conductor");
    }
}
