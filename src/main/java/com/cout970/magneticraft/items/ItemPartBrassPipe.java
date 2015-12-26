package com.cout970.magneticraft.items;

import com.cout970.magneticraft.tabs.CreativeTabsMg;

public class ItemPartBrassPipe extends ItemPartBase {

    public ItemPartBrassPipe(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.SteamAgeTab);
    }
//
//    @Override
//    public TMultiPart newPart(ItemStack arg0, EntityPlayer arg1, World arg2,
//                              BlockCoord arg3, int arg4, Vector3 arg5) {
//        return new PartBrassPipe();
//    }
}
