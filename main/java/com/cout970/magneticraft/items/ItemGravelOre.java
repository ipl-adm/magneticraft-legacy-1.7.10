package com.cout970.magneticraft.items;


public class ItemGravelOre extends ItemBasic{

	public String locName;

	public ItemGravelOre(String unlocalizedname,String loc) {
		super(unlocalizedname);
		setTextureName(Base+"ores/"+unlocalizedname);
		locName = loc;
	}
}
