package com.cout970.magneticraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.multiblock.Multiblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileSifter extends TileMB_Base{

	public int drawCounter;

	public void updateEntity() {
		super.updateEntity();
		if(drawCounter > 0)drawCounter--;
		if (!isActive())return;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
	}

	public boolean isActive() {
		return getBlockMetadata() > 5;
	}
	
	public MgDirection getDirection(){
		return MgDirection.getDirection(getBlockMetadata());
	}
	
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox(){
        return INFINITE_EXTENT_AABB;
    }
}
