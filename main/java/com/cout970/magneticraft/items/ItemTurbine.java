package com.cout970.magneticraft.items;

import com.cout970.magneticraft.api.util.IWindTurbine;

public class ItemTurbine extends ItemBasic implements IWindTurbine{

	private int ID;
	private int height;
	private int lenght;
	private double potency;
	private float scale;

	public ItemTurbine(String n,int id, int h, int l,double p,float s) {
		super(n);
		ID = id;
		height = h;
		lenght = l;
		potency = p;
		scale = s;
	}

	@Override
	public int getID() {
		return ID;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getLenght() {
		return lenght;
	}

	@Override
	public double getPotency() {
		return potency;
	}

	@Override
	public float getScale() {
		return scale;
	}

}
