package com.cout970.magneticraft.items;

import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.TMultiPart;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.items.block.ItemBlockMg;
import com.cout970.magneticraft.parts.electric.wires.*;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.List;

public class ItemPartCopperWire extends ItemPartBase {


    public ItemPartCopperWire(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

    @Override
    public TMultiPart newPart(ItemStack i, EntityPlayer p, World w,
                              BlockCoord pos, int side, Vector3 hit) {

        VecInt base = new VecInt(pos.intArray());
        base.add(MgDirection.getDirection(side).opposite().toVecInt());
        if (!w.isSideSolid(base.getX(), base.getY(), base.getZ(), ForgeDirection.getOrientation(side), false)) {
            return null;
        }

        if (side == 1) return new PartWireCopper_Down();
        if (side == 0) return new PartWireCopper_Up();
        if (side == 3) return new PartWireCopper_North();
        if (side == 2) return new PartWireCopper_South();
        if (side == 5) return new PartWireCopper_West();
        if (side == 4) return new PartWireCopper_East();
        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
        super.addInformation(item, player, list, flag);
        list.add(ItemBlockMg.format + "Has a resistance of " + ElectricConstants.RESISTANCE_COPPER_LOW + " Omhs");
    }
}
