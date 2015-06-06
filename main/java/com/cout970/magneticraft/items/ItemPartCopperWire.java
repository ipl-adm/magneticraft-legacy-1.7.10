package com.cout970.magneticraft.items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.JItemMultiPart;
import codechicken.multipart.TMultiPart;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.parts.micro.wires.PartWireCopper_Down;
import com.cout970.magneticraft.parts.micro.wires.PartWireCopper_East;
import com.cout970.magneticraft.parts.micro.wires.PartWireCopper_North;
import com.cout970.magneticraft.parts.micro.wires.PartWireCopper_South;
import com.cout970.magneticraft.parts.micro.wires.PartWireCopper_Up;
import com.cout970.magneticraft.parts.micro.wires.PartWireCopper_West;
import com.cout970.magneticraft.tabs.CreativeTabsMg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemPartCopperWire extends JItemMultiPart{
	
public static final String Base = "magneticraft:";
	
	public ItemPartCopperWire(String unlocalizedname){
		super();
		setUnlocalizedName(unlocalizedname);
		setCreativeTab(CreativeTabsMg.MainTab);
		setTextureName(Base+"void");
	}

	public String getUnlocalizedName(ItemStack i){
		return getUnlocalizedName();
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister IR){
        this.itemIcon = IR.registerIcon(this.getIconString());
    }

	@Override
	public TMultiPart newPart(ItemStack i, EntityPlayer p, World w,
			BlockCoord pos, int side, Vector3 hit) {
		
		VecInt base = new VecInt(pos.intArray());
		base.add(MgDirection.getDirection(side).opposite().getVecInt());
		if(!w.isSideSolid(base.getX(), base.getY(), base.getZ(), ForgeDirection.getOrientation(side), false)){
			return null;
		}
				
		if(side == 1)return new PartWireCopper_Down();
		if(side == 0)return new PartWireCopper_Up();
		if(side == 3)return new PartWireCopper_North();
		if(side == 2)return new PartWireCopper_South();
		if(side == 5)return new PartWireCopper_West();
		if(side == 4)return new PartWireCopper_East();
		return null;
	}

}
