package com.cout970.magneticraft.items;

public class ItemResource extends ItemBasic{

	public ItemResource(String unlocalizedname) {
		super(unlocalizedname.toLowerCase());
		setTextureName(Base+"ores/"+unlocalizedname);
	}
}
