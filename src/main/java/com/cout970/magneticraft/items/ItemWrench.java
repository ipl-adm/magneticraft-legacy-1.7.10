package com.cout970.magneticraft.items;

import buildcraft.api.tools.IToolWrench;
import cofh.api.item.IToolHammer;
import com.cout970.magneticraft.api.tool.IWrench;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

@Optional.InterfaceList({
        @Optional.Interface(iface = "cofh.api.item.IToolHammer", modid = "CoFHAPI|item"),
        @Optional.Interface(iface = "buildcraft.api.tools.IToolWrench", modid = "BuildCraft|Core")
})
public class ItemWrench extends ItemBasic implements IWrench, IToolWrench, IToolHammer {

    public ItemWrench(String unlocalizedname) {
        super(unlocalizedname);
        this.setMaxStackSize(1);
        setCreativeTab(CreativeTabsMg.SteamAgeTab);
    }

    @Override
    public boolean onItemUse(ItemStack i, EntityPlayer p, World w, int x, int y, int z, int par7, float par8, float par9, float par10) {
        return false;
    }

    @Override
    public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
        return true;
    }

    @Override
    @Optional.Method(modid = "BuildCraft|Core")
    public boolean canWrench(EntityPlayer player, int x, int y, int z) {
        return true;
    }

    @Override
    @Optional.Method(modid = "BuildCraft|Core")
    public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
    }

    @SuppressWarnings({"rawtypes"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
        super.addInformation(item, player, list, flag);
    }

    @Override
    @Optional.Method(modid = "CoFHAPI|item")
    public boolean isUsable(ItemStack item, EntityLivingBase user, int x, int y, int z) {
        return true;
    }

    @Override
    @Optional.Method(modid = "CoFHAPI|item")
    public void toolUsed(ItemStack item, EntityLivingBase user, int x, int y, int z) {
    }
}
