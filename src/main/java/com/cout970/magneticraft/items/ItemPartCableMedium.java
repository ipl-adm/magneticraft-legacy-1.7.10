package com.cout970.magneticraft.items;

import java.util.List;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.parts.micro.PartCableMedium;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.TMultiPart;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPartCableMedium extends ItemPartBase{
	
	
	public ItemPartCableMedium(String unlocalizedname){
		super(unlocalizedname);
		setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
	}

	@Override
	public TMultiPart newPart(ItemStack arg0, EntityPlayer arg1, World arg2,
			BlockCoord arg3, int arg4, Vector3 arg5) {
		return new PartCableMedium();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
		super.addInformation(item, player, list, flag);
		list.add(ItemBlockMg.format+"Has a resistance of "+ElectricConstants.RESISTANCE_COPPER_MED+" Omhs");
	}
}
