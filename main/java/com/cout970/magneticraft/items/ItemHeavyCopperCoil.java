package com.cout970.magneticraft.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.electricity.CompoundElectricCables;
import com.cout970.magneticraft.api.electricity.wires.IElectricPole;
import com.cout970.magneticraft.api.electricity.wires.ITileElectricPole;
import com.cout970.magneticraft.api.electricity.wires.WireConnection;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.NBTUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.tabs.CreativeTabsMg;
import com.cout970.magneticraft.util.Log;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemHeavyCopperCoil extends ItemBasic{

	public ItemHeavyCopperCoil(String unlocalizedname) {
		super(unlocalizedname);
		setCreativeTab(CreativeTabsMg.IndustrialAgeTab);
	}

	public boolean onItemUse(ItemStack item, EntityPlayer p, World w, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_){
		TileEntity t = w.getTileEntity(x, y, z);
		if(t instanceof ITileElectricPole){
			IElectricPole pole1 = MgUtils.getElectricPole(t);
			if(pole1 != null){
				if(NBTUtils.getBoolean("Connected", item)){
					TileEntity tile = w.getTileEntity(NBTUtils.getInteger("xCoord", item), NBTUtils.getInteger("yCoord", item), NBTUtils.getInteger("zCoord", item));
					IElectricPole pole2 = MgUtils.getElectricPole(tile);
					if(pole2 != null){
						if(pole1 == pole2){
							if(!w.isRemote)
								p.addChatMessage(new ChatComponentText("You cannot attach this wire in the same pole"));
							return false;
						}
						if(pole1.canConnectWire(pole2.getTier(), pole2) && pole2.canConnectWire(pole1.getTier(), pole1)){
							WireConnection wire = new WireConnection(new VecInt(pole1.getParent()), new VecInt(pole2.getParent()), pole1.getParent().getWorldObj());
							if(wire.getDistance() <= 16){
								pole1.onConnect(wire);
								pole2.onConnect(wire);
								NBTUtils.setBoolean("Connected", item, false);
								if(!w.isRemote)
									p.addChatMessage(new ChatComponentText("Successfully attached wire betwenn poles"));
								return true;
							}else{
								if(!w.isRemote)
									p.addChatMessage(new ChatComponentText("The poles are too far"));
							}
						}else{
							if(!w.isRemote)
								p.addChatMessage(new ChatComponentText("The poles cannot be connected"));
						}
					}
				}else{
					NBTUtils.setBoolean("Connected", item, true);
					NBTUtils.setInteger("xCoord", item, pole1.getParent().xCoord);
					NBTUtils.setInteger("yCoord", item, pole1.getParent().yCoord);
					NBTUtils.setInteger("zCoord", item, pole1.getParent().zCoord);
					if(!w.isRemote){
						p.addChatMessage(new ChatComponentText("Attached wire on this pole"));
					}
					return true;
				}
			}
		}else if(p.isSneaking()){
			NBTUtils.setBoolean("Connected", item, false);
			if(!w.isRemote){
				p.addChatMessage(new ChatComponentText("Cleared Settings"));
			}
		}
		return false;
	}
	
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack item, EntityPlayer p, List info, boolean flag) {
		super.addInformation(item, p, info, flag);
		info.add(ItemBlockMg.format+"Allow to connect manually two poles");
		if(NBTUtils.getBoolean("Connected", item)){
			int x,y,z;
			x = NBTUtils.getInteger("xCoord", item);
			y = NBTUtils.getInteger("yCoord", item);
			z = NBTUtils.getInteger("zCoord", item);
			info.add(new String("Linked to: "+x+", "+y+", "+z));
		}
	}
}
