package com.cout970.magneticraft.items;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.electricity.IBatteryItem;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.items.block.ItemBlockMg;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.List;

public class ItemOilProspector extends ItemCharged {

    public static int CONSUMPTION = 500;

    public ItemOilProspector(String unlocalizedname) {
        super(unlocalizedname, (int) EnergyConversor.RFtoW(25000));
        setMaxStackSize(1);
        setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
    }

    public boolean onItemUse(ItemStack item, EntityPlayer p, World w, int x, int y, int z, int side, float offsetx, float offsety, float offsetz) {
        boolean found = false;

        if (item.getItem() instanceof IBatteryItem) {
            IBatteryItem bat = (IBatteryItem) item.getItem();
            if (bat.getCharge(item) >= CONSUMPTION) {
                bat.discharge(item, CONSUMPTION);
                if (w.isRemote) return true;
                for (int j = -1; j < 2; j++) {
                    for (int k = -1; k < 2; k++) {
                        for (int i = y; i > 0; i--) {
                            if (Block.isEqualTo(w.getBlock(x + j, i, z + k), ManagerBlocks.oilSource)) {
                                p.addChatComponentMessage(new ChatComponentText("Founded Oil source at " + i));
                                found = true;
                            } else if (Block.isEqualTo(w.getBlock(x + j, i, z + k), ManagerBlocks.oilSourceDrained)) {
                                p.addChatComponentMessage(new ChatComponentText("Founded drained Oil source at " + i));
                                found = true;
                            }
                            if (found) break;
                        }
                        if (found) break;
                    }
                    if (found) break;
                }
                if (!found) p.addChatComponentMessage(new ChatComponentText("Oil not found"));
                return true;
            } else {
                p.addChatComponentMessage(new ChatComponentText("No Energy"));
            }
        }
        return false;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer player, List list, boolean flag) {
        super.addInformation(item, player, list, flag);
        list.add(ItemBlockMg.format + "Looks for underground oil deposits in 3x3 area");
    }
}
