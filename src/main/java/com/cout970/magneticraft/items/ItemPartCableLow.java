package com.cout970.magneticraft.items;

import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.TMultiPart;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.items.block.ItemBlockMg;
import com.cout970.magneticraft.parts.electric.PartCableLow;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class ItemPartCableLow extends ItemPartBase {

    public ItemPartCableLow(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    @Override
    public TMultiPart newPart(ItemStack arg0, EntityPlayer arg1, World arg2,
                              BlockCoord arg3, int arg4, Vector3 arg5) {
        return new PartCableLow();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
        super.addInformation(item, player, list, flag);
        list.add(ItemBlockMg.format + "Has a resistance of " + ElectricConstants.RESISTANCE_COPPER_LOW + " Omhs");
    }
}
