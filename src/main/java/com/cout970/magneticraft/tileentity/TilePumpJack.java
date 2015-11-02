package com.cout970.magneticraft.tileentity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.api.electricity.ElectricConstants;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.api.util.VecIntUtil;
import com.cout970.magneticraft.update1_8.IFluidHandler1_8;
import com.cout970.magneticraft.util.fluid.TankMg;
import com.cout970.magneticraft.util.tile.TileConductorLow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

public class TilePumpJack extends TileConductorLow implements IFluidHandler1_8 {

	// client
	public float m;
	public boolean active;
	// server
	public TankMg tank = new TankMg(this, 4000);
	public long time;
	private List<VecInt> pipes = new LinkedList<>();
	private List<VecInt> oil = new LinkedList<>();
	private List<VecInt> fluid = new LinkedList<>();
	private List<VecInt> finder = new LinkedList<>();
	private List<VecInt> visited = new LinkedList<>();
	private int alt;
	private boolean update;
	private int cooldown;
	private boolean blocked;
	private MgDirection[] sides = { MgDirection.NORTH, MgDirection.EAST, MgDirection.SOUTH, MgDirection.WEST, MgDirection.DOWN, MgDirection.UP };
	private int buffer;
	public static Block replacement = ManagerBlocks.oilSourceDrained;
	private static int speed = 50;
	private static Block fluidOil;

	@Override
	public IElectricConductor[] getConds(VecInt dir, int tier) {
		if (tier != 0)
			return null;
		if (VecInt.NULL_VECTOR.equals(dir))
			return new IElectricConductor[] { cond };
		return null;
	}

	@Override
	public IElectricConductor initConductor() {
		return new ElectricConductor(this){
			@Override
		    public VecInt[] getValidConnections() {
		        return new VecInt[]{getOrientation().toVecInt()};
		    }
		};
	}

	public MgDirection getOrientation() {
		return MgDirection.AXIX_Y[getBlockMetadata() % MgDirection.AXIX_Y.length];
	}

	public void updateEntity() {
		super.updateEntity();
		boolean working = cond.getVoltage() > ElectricConstants.MACHINE_WORK;

		if (worldObj.getTotalWorldTime() % 20 == 0) {
			if (working && !isActive()) {
				setActive(true);
			} else if (!working && isActive()) {
				setActive(false);
			}
		}

		if (worldObj.isRemote)
			return;

		if (!update) {
			if (fluidOil == null)
				fluidOil = FluidRegistry.getFluid("oil").getBlock();
			if (worldObj.getTotalWorldTime() % 80 == 0)
				update = searchForOil();
			export();
			return;
		}

		if (worldObj.getTotalWorldTime() % 80 == 0) {
			if (alt != 0) {
				pipes.clear();
				for (int y = yCoord - 1; y > 0; y--) {
					Block b = worldObj.getBlock(xCoord, y, zCoord);
					if (Block.isEqualTo(b, ManagerBlocks.oilSource) || Block.isEqualTo(b,
							replacement) || Block.isEqualTo(b, fluidOil)) {
						break;
					} else if (!Block.isEqualTo(b, ManagerBlocks.concreted_pipe)) {
						pipes.add(new VecInt(xCoord, y, zCoord));
					}
				}
			}
			blocked = false;
		}

		export();
		if (!blocked) {
			if (cooldown > 0)
				cooldown--;
			if (cooldown <= 0) {
				cooldown = 20;
				if (pipes.size() == 0) {
					update = false;
					blocked = true;
				} else {
					if (cond.getVoltage() > ElectricConstants.MACHINE_WORK) {
						VecInt c = pipes.get(0);
						replaceBlock(c.getX(), c.getY(), c.getZ(), ManagerBlocks.concreted_pipe);
						cond.drainPower(EnergyConverter.RFtoW(80));
						pipes.remove(0);
					}
				}
			}
		}
		if (blocked) {
			if (cond.getVoltage() > ElectricConstants.MACHINE_WORK && tank.getSpace() > 0 && buffer > 0) {
				int i = Math.min(speed, buffer);
				buffer -= tank.fill(FluidRegistry.getFluidStack("oil", i), true);
				cond.drainPower(EnergyConverter.RFtoW(i));
			}

			if (buffer <= 0 && cond.getVoltage() > ElectricConstants.MACHINE_WORK) {
				if (fluid.isEmpty()) {
					if (oil.isEmpty()) {
						update = false;
						blocked = false;
					} else {
						VecInt b = oil.get(0);
						int m = worldObj.getBlockMetadata(b.getX(), b.getY(), b.getZ());
						if (m > 0) {
							worldObj.setBlockMetadataWithNotify(b.getX(), b.getY(), b.getZ(), m - 1, 2);
						} else {
							worldObj.setBlock(b.getX(), b.getY(), b.getZ(), replacement);
							oil.remove(0);
						}
						buffer = 1000;
					}
				} else {
					VecInt b = fluid.get(0);
					int m = worldObj.getBlockMetadata(b.getX(), b.getY(), b.getZ());
					Block bl = worldObj.getBlock(b.getX(), b.getY(), b.getZ());
					if (m == 0 && bl == fluidOil) {
						worldObj.setBlock(b.getX(), b.getY(), b.getZ(), Blocks.air);
						buffer = 1000;
					}
					fluid.remove(0);
				}
			}
		}
	}

	public void replaceBlock(int x, int y, int z, Block replace) {
		if (worldObj.getBlock(x, y, z).isAir(worldObj, x, y, z) || MgUtils.isMineableBlock(worldObj,
				new BlockInfo(worldObj.getBlock(x, y, z), worldObj.getBlockMetadata(x, y, z), x, y, z))) {
			ArrayList<ItemStack> items;
			Block id = worldObj.getBlock(x, y, z);
			int metadata = worldObj.getBlockMetadata(x, y, z);
			items = id.getDrops(worldObj, x, y, z, metadata, 0);
			for (ItemStack item : items){
				if (item != null && item.stackSize > 0) {
		            float rx = 0F;
		            float ry = 0.1F;
		            float rz = 0F;
		            EntityItem entityItem = new EntityItem(worldObj,
		                    xCoord + rx, yCoord + ry, zCoord + rz,
		                    new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));
		            if (item.hasTagCompound()) {
		                entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
		            }
		            worldObj.spawnEntityInWorld(entityItem);
		        }
			}

			worldObj.setBlock(x, y, z, replace);
		}
	}

	private void setActive(boolean b) {
		active = b;
		sendUpdateToClient();
	}

	public boolean isActive() {
		return active;
	}

	private void export() {
		if (tank.getFluidAmount() > 0) {
			for (MgDirection d : MgDirection.VALID_DIRECTIONS) {
				TileEntity t = MgUtils.getTileEntity(this, d);
				if (t instanceof IFluidHandler) {
					IFluidHandler f = (IFluidHandler) t;
					if (f.canFill(d.toForgeDir(), FluidRegistry.getFluid("oil"))) {
						int m = f.fill(d.toForgeDir(), tank.drain(100, false), true);
						tank.drain(m, true);
					}
					if (tank.getFluidAmount() == 0)
						break;
				}
			}
		}
	}

	private void getOil() {
		oil.clear();
		fluid.clear();
		pathFinder(new VecInt(xCoord, alt, zCoord));
		visited.clear();
		finder.clear();
	}

	public void pathFinder(VecInt c) {
		if (oil.size() > 20)
			return;
		if (visited.size() > 4000) {
			alt--;
			return;
		}
		for (MgDirection d : sides) {
			VecInt bc = new VecInt(c.getX() + d.getOffsetX(), c.getY() + d.getOffsetY(),
					c.getZ() + d.getOffsetZ());
			if (visited.contains(bc))
				continue;
			visited.add(bc);

			Block b = worldObj.getBlock(bc.getX(), bc.getY(), bc.getZ());

			if (Block.isEqualTo(b, ManagerBlocks.oilSource)) {
				oil.add(bc);
				if (!finder.contains(bc))
					finder.add(bc);
			} else if (Block.isEqualTo(b, replacement)) {
				if (!finder.contains(bc))
					finder.add(bc);
			} else if (Block.isEqualTo(b, fluidOil)) {
				fluid.add(bc);
				if (!finder.contains(bc))
					finder.add(bc);
			}
		}
		List<VecInt> temp = new ArrayList<>(finder.size());
		temp.addAll(finder);
		for (VecInt cc : temp) {
			finder.remove(cc);
			pathFinder(cc);
		}
	}

	public boolean searchForOil() {
		pipes.clear();
		for (int y = yCoord - 1; y > 0; y--) {
			Block b = worldObj.getBlock(xCoord, y, zCoord);

			if (Block.isEqualTo(b, ManagerBlocks.oilSource) || Block.isEqualTo(b, replacement) || Block
					.isEqualTo(b, fluidOil)) {
				alt = y;
				getOil();
				if (oil.isEmpty() && fluid.isEmpty()) {
					alt = 0;
				} else {
					return true;
				}
			} else if (!Block.isEqualTo(b, ManagerBlocks.concreted_pipe)) {
				pipes.add(new VecInt(xCoord, y, zCoord));
			}
		}
		return false;
	}

	@Override
	public int fillMg(MgDirection from, FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drainMg_F(MgDirection from, FluidStack resource, boolean doDrain) {
		return drainMg(from, resource.amount, doDrain);
	}

	@Override
	public FluidStack drainMg(MgDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFillMg(MgDirection from, Fluid fluid) {
		return false;
	}

	@Override
	public boolean canDrainMg(MgDirection from, Fluid fluid) {
		return fluid.getName().equals("oil");
	}

	@Override
	public FluidTankInfo[] getTankInfoMg(MgDirection from) {
		return new FluidTankInfo[] { tank.getInfo() };
	}

	public float getDelta() {
		long aux = time;
		time = System.nanoTime();
		return time - aux;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		tank.readFromNBT(nbt, "oil");
		buffer = nbt.getInteger("Buffer");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		tank.writeToNBT(nbt, "oil");
		nbt.setInteger("Buffer", buffer);
	}

	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox() {
		VecInt v1 = VecIntUtil.getRotatedOffset(getOrientation().opposite(), 0, 0, 1);
		VecInt v2 = VecIntUtil.getRotatedOffset(getOrientation(), 0, 3, 2);
		VecInt block = new VecInt(xCoord, yCoord, zCoord);

		return VecIntUtil.getAABBFromVectors(v1.add(block), v2.add(block));
	}

	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		return this.fillMg(MgDirection.getDirection(from.ordinal()), resource, doFill);
	}

	public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
		return this.drainMg_F(MgDirection.getDirection(from.ordinal()), resource, doDrain);
	}

	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return this.drainMg(MgDirection.getDirection(from.ordinal()), maxDrain, doDrain);
	}

	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return this.canFillMg(MgDirection.getDirection(from.ordinal()), fluid);
	}

	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return this.canDrainMg(MgDirection.getDirection(from.ordinal()), fluid);
	}

	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return this.getTankInfoMg(MgDirection.getDirection(from.ordinal()));
	}

	public int getConnections() {
		return -1;
	}
}
