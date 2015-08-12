package com.cout970.magneticraft.items;

public class ItemProduct extends ItemResource{

	private String oredict;
	private String base;
	
	public ItemProduct(String unloc, String oredict, String base) {
		super(unloc);
		this.oredict = oredict;
		this.base = base;
	}
	
	public String getOreDictName(){
		return oredict;
	}
	
	public String getBaseName(){
		return base;
	}
}
