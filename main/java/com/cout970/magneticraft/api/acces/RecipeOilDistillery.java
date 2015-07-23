package com.cout970.magneticraft.api.acces;

import net.minecraftforge.fluids.FluidStack;

import com.cout970.magneticraft.api.util.MgUtils;

/**
 * 
 * @author Cout970
 *
 */
public class RecipeOilDistillery {

	private FluidStack input,output;//the amount of the fluid is the generation per tick
	private double cost;
	
	public RecipeOilDistillery(FluidStack in, FluidStack out, double cost){
		input = in;
		output = out;
		this.cost = cost;
	}
	
	public FluidStack getInput(){
		return input;
	}
	
	public FluidStack getOutput(){
		return output; 
	}
	
	public double getEnergyCost(){
		return cost;
	}
	
	public boolean matches(FluidStack f){
		return MgUtils.areEcuals(f, input);
	}
	
	public String toString(){
		return "Oil Distillery Recipe, Input: "+input.getLocalizedName()+", Amount:"+input.amount+", Output: "+output.getLocalizedName()+", Amount:"+input.amount+", Energy Cost: "+cost+"W";
	}
	
	public static RecipeOilDistillery getRecipe(FluidStack f){
		for(RecipeOilDistillery r : MgRecipeRegister.oilDistillery){
			if(r.matches(f))return r;
		}
		return null;
	}
}
