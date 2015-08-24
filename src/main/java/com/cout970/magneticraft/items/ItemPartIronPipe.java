package com.cout970.magneticraft.items;

import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.TMultiPart;
import com.cout970.magneticraft.parts.micro.PartIronPipe;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemPartIronPipe extends ItemPartBase {


    public ItemPartIronPipe(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.SteamAgeTab);
    }

    @Override
    public TMultiPart newPart(ItemStack arg0, EntityPlayer arg1, World arg2,
                              BlockCoord arg3, int arg4, Vector3 arg5) {
        return new PartIronPipe();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
        super.addInformation(item, player, list, flag);
        list.add(ItemBlockMg.format + "Can transfer " + PartIronPipe.MAX_EXTRACT + "mB/t per side");
    }
}
