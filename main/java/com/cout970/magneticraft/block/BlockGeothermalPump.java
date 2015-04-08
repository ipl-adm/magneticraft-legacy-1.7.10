package com.cout970.magneticraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.cout970.magneticraft.Magneticraft;
import com.cout970.magneticraft.tileentity.TileGeothermalPump;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGeothermalPump extends BlockMg{

	public BlockGeothermalPump() {
		super(Material.iron);
	}

	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer p, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_){
		if(p.isSneaking())return false;
		p.openGui(Magneticraft.Instance, 0, w, x, y, z);
		return true;
	}
	
	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return new TileGeothermalPump();
	}

	@Override
	public String[] getTextures() {
		return new String[]{"geothermal_pump","geothermal_pump_top","geothermal_pump_on"};
	}

	@Override
	public String getName() {
		return "geothermal_pump";
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if(side == 0 || side == 1)return icons[1];
		if(meta != 0)return icons[2];
		return icons[0];
	}

}
