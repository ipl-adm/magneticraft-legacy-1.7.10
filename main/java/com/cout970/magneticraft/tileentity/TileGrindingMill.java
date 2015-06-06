package com.cout970.magneticraft.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.cout970.magneticraft.api.kinetic.IKineticConductor;
import com.cout970.magneticraft.api.kinetic.IKineticTile;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.multiblock.Multiblock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileGrindingMill extends TileMB_Base{

	public boolean active;
	public int drawCounter;
	public float rotation;
	private long time;
	public IKineticConductor kinetic;

	public void updateEntity() {
		super.updateEntity();
		if(drawCounter > 0)drawCounter--;
		if(kinetic == null){
			search();
		}
	}
	
	private void search() {
		VecInt vec = getDirection().getVecInt().add(0, -2, 0);
		TileEntity t = MgUtils.getTileEntity(this, vec);
		if(t instanceof IKineticTile){
			((IKineticTile) t).getKineticConductor(MgDirection.UP);
		}
	}

	@Override
	public void onDestroy(World w, VecInt p, Multiblock c, MgDirection e) {
		active = false;
	}

	@Override
	public void onActivate(World w, VecInt p, Multiblock c, MgDirection e) {
		active = true;
	}

	public boolean isActive() {
		return getBlockMetadata() > 6;
	}
	
	@Override
	public MgDirection getDirection() {
		return MgDirection.getDirection(getBlockMetadata()%6);
	}
	
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox()
    {
        return INFINITE_EXTENT_AABB;
    }

	public float getDelta() {
		long aux = time;
		time = System.nanoTime();
		return time - aux;
	}
	
}
