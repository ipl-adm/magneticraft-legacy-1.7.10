package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.Random;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.api.util.EnergyConversor;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.block.BlockMg;
import com.cout970.magneticraft.util.MgBeltUtils;
import com.cout970.magneticraft.util.tile.TileConductorLow;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

public class TileBreaker extends TileConductorLow{

	public boolean signal;
	
	public void updateEntity(){
		super.updateEntity();
		if(worldObj.isRemote)return;
		if(worldObj.provider.getWorldTime() % 20 == 0 && isControled() && cond.getVoltage() > ElectricConstants.MACHINE_WORK)
			BreakBlock();
	}

	public void onNeigChange(){
		super.onNeigChange();
	}


	public void BreakBlock() {
		ForgeDirection d = ForgeDirection.getOrientation(getBlockMetadata());
		for(int g=1;g<=16;g++){
			int x,y,z;
			x = xCoord+d.offsetX*g;
			y = yCoord+d.offsetY*g;
			z = zCoord+d.offsetZ*g;
			if(!worldObj.getBlock(x,y,z).isAir(worldObj, x, y, z)){
				if(worldObj.getBlock(x,y,z) != ManagerBlocks.permagnet && MgUtils.isMineableBlock(worldObj,new BlockInfo(worldObj.getBlock(x,y,z),worldObj.getBlockMetadata(x, y, z),x,y,z))){
					ArrayList<ItemStack> items = new ArrayList<ItemStack>();
					Block id = worldObj.getBlock(x,y,z);
					int metadata = worldObj.getBlockMetadata(x,y,z);
					
					cond.drainPower(EnergyConversor.RFtoW(500));
					items = id.getDrops(worldObj, x, y, z, metadata, 0);
					for(ItemStack i : items)
						ejectItems(i);

					worldObj.setBlockToAir(x,y,z);
				}
				break;
			}
		}
	}
	
	public void ejectItems(ItemStack i) {
		if(i == null)return;
		MgDirection d = MgDirection.getDirection(getBlockMetadata());
		TileEntity t = MgUtils.getTileEntity(this, d.opposite());
		if(MgBeltUtils.isInventory(t)){
			if(MgBeltUtils.dropItemStackIntoInventory((IInventory) t, i, d.opposite(), true) == 0){
				MgBeltUtils.dropItemStackIntoInventory((IInventory) t, i, d.opposite(), false);
				return;
			}
		}
		Random rand = worldObj.rand;
		if (i.stackSize > 0) {
			BlockMg.dropItem(i, rand, xCoord, yCoord, zCoord, worldObj);
		}
	}

	@Override
	public IElectricConductor initConductor() {
		return new ElectricConductor(this);
	}
}
