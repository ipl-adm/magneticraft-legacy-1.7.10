package com.cout970.magneticraft.items;

import com.cout970.magneticraft.tabs.CreativeTabsMg;

public class ItemPartCopperWire extends ItemPartBase {


    public ItemPartCopperWire(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.ElectricalAgeTab);
    }

//    @Override
//    public TMultiPart newPart(ItemStack i, EntityPlayer p, World w,
//                              BlockCoord pos, int side, Vector3 hit) {
//
//        VecInt base = new VecInt(pos.intArray());
//        base.add(MgDirection.getDirection(side).opposite().toVecInt());
//        if (!w.isSideSolid(base.getX(), base.getY(), base.getZ(), ForgeDirection.getOrientation(side), false)) {
//            return null;
//        }
//
//        if (side == 1) return new PartWireCopper_Down();
//        if (side == 0) return new PartWireCopper_Up();
//        if (side == 3) return new PartWireCopper_North();
//        if (side == 2) return new PartWireCopper_South();
//        if (side == 5) return new PartWireCopper_West();
//        if (side == 4) return new PartWireCopper_East();
//        return null;
//    }
}
