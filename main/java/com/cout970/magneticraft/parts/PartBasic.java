package com.cout970.magneticraft.parts;

import java.util.LinkedList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.ISidedHollowConnect;
import codechicken.multipart.JNormalOcclusion;
import codechicken.multipart.NormalOcclusionTest;
import codechicken.multipart.TMultiPart;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;

public abstract class PartBasic extends TMultiPart implements JNormalOcclusion, ISidedHollowConnect{
 
	public static float pixel = 1f/16f;
	
	public Item base;
	
	public PartBasic(Item a){
		base = a;
	}
	
	public Cuboid6[] boundingBoxes = setupBoundingBoxes();

	public abstract Cuboid6[] setupBoundingBoxes();

	@Override
	public boolean occlusionTest(TMultiPart part) {
		return NormalOcclusionTest.apply(this, part);
	}
	
	public void onNeigUpdate() {
		onNeighborChanged();
	}
	
	@Override
	public ItemStack pickItem(MovingObjectPosition hit) {
		return new ItemStack(base, 1);
	}
	
	@Override
	public String getType() {
		return base.getUnlocalizedName();
	}
	
	@Override
	public Iterable<ItemStack> getDrops() {
		LinkedList<ItemStack> items = new LinkedList<ItemStack>();
		items.add(new ItemStack(base, 1));
		return items;
	}
	
	@Override
	public float getStrength(MovingObjectPosition hit, EntityPlayer p){
		return 10f;
	}
	
	public static boolean isHited(Cuboid6 c, Vector3 v){
		if(c == null || v == null)return false;
		if((float) c.max.y == (float) v.y || (float) c.min.y == (float) v.y){
			if((c.min.x <= v.x) && (c.max.x >= v.x)){
				if((c.min.z <= v.z) && (c.max.z >= v.z)){
					return true;
				}
			}
		}
		if((float) c.max.x == (float) v.x || (float) c.min.x == (float) v.x){
			if((c.min.y <= v.y) && (c.max.y >= v.y)){
				if((c.min.z <= v.z) && (c.max.z >= v.z)){
					return true;
				}
			}
		}
		if((float) c.max.z == (float) v.z || (float) c.min.z == (float) v.z){
			if((c.min.x <= v.x) && (c.max.x >= v.x)){
				if((c.min.y <= v.y) && (c.max.y >= v.y)){
					return true;
				}
			}
		}
		return false;
	}
	
	//remove for the 1.8
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if(this instanceof IFluidHandler1_8)return((IFluidHandler1_8)this).fillMg(MgDirection.getDirection(from.ordinal()), resource, doFill);
		return 0;
	}

	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).drainMg_F(MgDirection.getDirection(from.ordinal()), resource,doDrain);
		return null;
	}

	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).drainMg(MgDirection.getDirection(from.ordinal()),maxDrain,doDrain);
		return null;
	}

	public boolean canFill(ForgeDirection from, Fluid fluid) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).canFillMg(MgDirection.getDirection(from.ordinal()),fluid);
		return false;
	}

	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).canDrainMg(MgDirection.getDirection(from.ordinal()),fluid);
		return false;
	}

	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		if(this instanceof IFluidHandler1_8)return ((IFluidHandler1_8)this).getTankInfoMg(MgDirection.getDirection(from.ordinal()));
		return null;
	}
}