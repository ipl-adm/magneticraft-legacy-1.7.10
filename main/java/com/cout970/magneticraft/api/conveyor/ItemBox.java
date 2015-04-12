package com.cout970.magneticraft.api.conveyor;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemBox {
	
	private ItemStack content;
	private double position;
//	private double x,y,z;
	private boolean isOnLeft;

	
	public ItemBox(ItemStack it) {
		content = it;
	}
	
//	public void setPos(double x, double y, double z){
//		this.x = x;
//		this.y = y;
//		this.z = z;
//	}
	
//	public void move(VecDouble dir){
//		x += dir.getX();
//		y += dir.getY();
//		z += dir.getZ();
//	}
	
//	public double getX() {
//		return x;
//	}
//
//	public double getY() {
//		return y;
//	}
//
//	public double getZ() {
//		return z;
//	}	

	public ItemStack getContent() {
		return content;
	}

	public void setContent(ItemStack content) {
		this.content = content;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public boolean isOnLeft() {
		return isOnLeft;
	}

	public void setOnLeft(boolean isLeft) {
		this.isOnLeft = isLeft;
	}

	public void advances() {
		if(position < 1)
			position += 1/16f;
	}

	public void save(NBTTagCompound t) {
		getContent().writeToNBT(t);
		t.setDouble("Pos", getPosition());
		t.setBoolean("Left", isOnLeft());
	}

	public void load(NBTTagCompound t) {
		setContent(ItemStack.loadItemStackFromNBT(t));
		setPosition(t.getDouble("Pos"));
		setOnLeft(t.getBoolean("Left"));
	}
}
