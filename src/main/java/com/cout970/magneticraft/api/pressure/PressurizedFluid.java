package com.cout970.magneticraft.api.pressure;

import net.minecraftforge.fluids.Fluid;

public class PressurizedFluid {

	private Fluid fluid;
	private double amount;
	private double temperature;
	
	public PressurizedFluid(Fluid fluid, double amount, double temperature) {
		this.fluid = fluid;
		this.amount = amount;
		this.temperature = temperature;
	}

	public Fluid getFluid() {
		return fluid;
	}

	public void setFluid(Fluid fluid) {
		this.fluid = fluid;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
}
