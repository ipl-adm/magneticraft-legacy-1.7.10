package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.electricity.ElectricUtils;
import com.cout970.magneticraft.api.electricity.IElectricPole;
import com.cout970.magneticraft.api.electricity.ITileElectricPole;
import com.cout970.magneticraft.api.electricity.prefab.InterPoleWire;
import com.cout970.magneticraft.api.util.NBTUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemHeavyCopperCoil extends ItemBasic {

    public ItemHeavyCopperCoil(String unlocalizedname) {
        super(unlocalizedname);
        setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
    }

    public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer p) {
        if (w.isRemote) {
            return is;
        }
        MovingObjectPosition mop = getMovingObjectPositionFromPlayer(w, p, false);
        if (p.isSneaking() && ((mop == null) || (mop.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK))) {
            NBTUtils.setBoolean("Connected", is, false);
            p.addChatMessage(new ChatComponentText("Connection reset."));
        }

        return super.onItemRightClick(is, w, p);
    }

    public boolean onItemUse(ItemStack item, EntityPlayer p, World w, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        TileEntity t = w.getTileEntity(x, y, z);
        if (t instanceof ITileElectricPole) {
            IElectricPole pole1 = ElectricUtils.getElectricPole(t);
            if (pole1 != null) {
                if (NBTUtils.getBoolean("Connected", item)) {
                    TileEntity tile = w.getTileEntity(NBTUtils.getInteger("xCoord", item), NBTUtils.getInteger("yCoord", item), NBTUtils.getInteger("zCoord", item));
                    IElectricPole pole2 = ElectricUtils.getElectricPole(tile);
                    if (pole2 != null) {
                        if (pole1 == pole2) {
                            if (!w.isRemote)
                                p.addChatMessage(new ChatComponentText("You cannot attach a wire to the same pole."));
                            return false;
                        }
                        if (pole1.canConnectWire(pole2.getTier(), pole2, true) && pole2.canConnectWire(pole1.getTier(), pole1, true)) {
                            InterPoleWire wire = new InterPoleWire(new VecInt(pole1.getParent()), new VecInt(pole2.getParent()));
                            wire.setWorld(pole1.getParent().getWorldObj());
                            if (wire.getDistance() <= 16) {
                                pole1.onConnect(wire);
                                pole2.onConnect(wire);
                                NBTUtils.setBoolean("Connected", item, false);
                                if (!w.isRemote)
                                    p.addChatMessage(new ChatComponentText("Wire attached successfully."));
                                return true;
                            } else {
                                if (!w.isRemote)
                                    p.addChatMessage(new ChatComponentText("Distance between poles is too high."));
                            }
                        } else {
                            if (!w.isRemote)
                                p.addChatMessage(new ChatComponentText("Poles could not be connected."));
                        }
                    }
                } else {
                    NBTUtils.setBoolean("Connected", item, true);
                    NBTUtils.setInteger("xCoord", item, pole1.getParent().xCoord);
                    NBTUtils.setInteger("yCoord", item, pole1.getParent().yCoord);
                    NBTUtils.setInteger("zCoord", item, pole1.getParent().zCoord);
                    if (!w.isRemote) {
                        p.addChatMessage(new ChatComponentText("Attached wire to the pole at " + pole1.getParent().xCoord + ", " + pole1.getParent().yCoord + ", " + pole1.getParent().zCoord + "."));
                    }
                    return true;
                }
            }
        } else if (p.isSneaking()) {
            NBTUtils.setBoolean("Connected", item, false);
            if (!w.isRemote) {
                p.addChatMessage(new ChatComponentText("Connection reset."));
            }
        }
        return false;
    }
}
