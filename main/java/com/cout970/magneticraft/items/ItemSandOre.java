package com.cout970.magneticraft.items;

public class ItemSandOre extends ItemBasic{

	public String locName;

	public ItemSandOre(String unlocalizedname,String loc) {
		super(unlocalizedname);
		setTextureName(Base+"ores/"+unlocalizedname);
		locName = loc;
	}
}
