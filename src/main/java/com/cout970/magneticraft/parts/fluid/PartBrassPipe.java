package com.cout970.magneticraft.parts.fluid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.api.pressure.IExplodable;
import com.cout970.magneticraft.api.pressure.IPressureConductor;
import com.cout970.magneticraft.api.pressure.PressureUtils;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.client.tilerender.TileRenderBrassPipe;
import com.cout970.magneticraft.util.network.BasicNetwork;
import com.cout970.magneticraft.util.network.pressure.NodePressureConductor;
import com.cout970.magneticraft.util.network.pressure.PressureNetwork;

import codechicken.lib.data.MCDataInput;
import codechicken.lib.data.MCDataOutput;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import codechicken.microblock.ISidedHollowConnect;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class PartBrassPipe extends PartPressurePipe implements ISidedHollowConnect, IExplodable {

	public byte connections = -1;
	public int interactions;
	public static List<Cuboid6> boxes = new ArrayList<>();
	public NodePressureConductor pressure;
	public NBTTagCompound tempNBT;
	private static TileRenderBrassPipe render;

	static {
		double w = 2 / 16d;
		boxes.add(new Cuboid6(0.5 - w, 0, 0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w));// down
		boxes.add(new Cuboid6(0.5 - w, 0.5 + w, 0.5 - w, 0.5 + w, 1, 0.5 + w));// up
		boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0, 0.5 + w, 0.5 + w, 0.5 - w));// north
		boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w, 1));// south
		boxes.add(new Cuboid6(0, 0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w));// west
		boxes.add(new Cuboid6(0.5 + w, 0.5 - w, 0.5 - w, 1, 0.5 + w, 0.5 + w));// east
		boxes.add(new Cuboid6(0.5 - w, 0.5 - w, 0.5 - w, 0.5 + w, 0.5 + w, 0.5 + w));// base
	}

	public PartBrassPipe() {
		super(ManagerItems.part_brass_pipe);
	}

	public void update() {
		super.update();
		if (pressure == null && tile() != null) {
			create();
		}
		if ((connections == -1 || W().isRemote) && W().getTotalWorldTime() % 20 == 0) {
			recache();
		}
		if (W().isRemote)
			return;
		if (tempNBT != null) {
			pressure.load(tempNBT);
			tempNBT = null;
		}
		pressure.iterate();
		if (W().getTotalWorldTime() % 200 == 0 && !W().isRemote) {
			sendDescUpdate();
		}
	}

	public void onRemoved() {
		if (network != null) {
			network.removeNode(this);
		}
	}

	private void create() {
		pressure = new NodePressureConductor(tile(), 360, this);
	}

	public void recache() {
		connections = 0;
		for (MgDirection dir : MgDirection.values()) {
			TileEntity t = MgUtils.getTileEntity(tile(), dir);
			List<IPressureConductor> conds = PressureUtils.getPressureCond(t, dir.opposite().toVecInt());
			if (!conds.isEmpty()) {
				connections |= 1 << dir.ordinal();
			}
		}
	}

	@Override
	public List<Cuboid6> getOcclusionCubes() {
		return Collections.singletonList(boxes.get(6));
	}

	@Override
	public List<Cuboid6> getCollisionCubes() {
		ArrayList<Cuboid6> t2 = new ArrayList<>();
		t2.add(boxes.get(6));
		for (byte i = 0; i < 6; i++) {
			if ((connections & (1 << i)) > 0) {
				t2.add(boxes.get(i));
			}
		}
		return t2;
	}

	@Override
	public int getHollowSize(int arg0) {
		return 4;
	}

	@Override
	public void renderPart(Vector3 pos) {
		if (render == null)
			render = new TileRenderBrassPipe();
		render.render(this, pos);
	}

	@Override
	public void explode(World world, int x, int y, int z, boolean explodeNeighbors) {
		if (!world.isRemote) {
			world.setBlock(x, y, z, Blocks.air);

			if (explodeNeighbors) {
				for (MgDirection dir : MgDirection.values()) {
					VecInt pos = new VecInt(tile()).add(dir);
					IExplodable exp = PressureUtils.getExplodable(world, pos);
					if (exp != null) {
						exp.explode(world, pos.getX(), pos.getY(), pos.getZ(), explodeNeighbors);
					}
				}
			}
			Explosion e = world.createExplosion(null, x, y, z, 1f, true);
			e.doExplosionA();
		}
	}

	@Override
	public IPressureConductor getPressureConductor() {
		return pressure;
	}

	public void save(NBTTagCompound nbt) {
		super.save(nbt);
		if (tile() == null)
			return;
		if (pressure != null)
			pressure.save(nbt);
	}

	public void load(NBTTagCompound nbt) {
		super.load(nbt);
		tempNBT = nbt;
	}

	public void writeDesc(MCDataOutput p) {
		super.writeDesc(p);
		if (pressure == null) {
			p.writeDouble(0);
			p.writeDouble(0);
			p.writeDouble(0);
			p.writeString("null");
		} else {
			p.writeDouble(pressure.getVolume());
			p.writeDouble(pressure.getTemperature());
			p.writeDouble(pressure.getMoles());
			if (pressure.getFluid() != null) {
				p.writeString(FluidRegistry.getFluidName(pressure.getFluid()));
			} else {
				p.writeString("null");
			}
		}
	}

	public void readDesc(MCDataInput p) {
		super.readDesc(p);
		if (pressure == null) {
			p.readDouble();
			p.readDouble();
			p.readDouble();
			p.readString();
		} else {
			pressure.setVolume(p.readDouble());
			pressure.setTemperature(p.readDouble());
			pressure.setMoles(p.readDouble());
			String s = p.readString();
			if (!s.equals("null")) {
				Fluid f = FluidRegistry.getFluid(s);
				if (f != null) {
					pressure.setFluid(f);
				}
			} else {
				pressure.setFluid(null);
			}
		}
	}

	@Override
	public NodePressureConductor getConductor() {
		return pressure;
	}

	@Override
	public BasicNetwork createNetwork() {
		return new PressureNetwork(this);
	}

	@Override
	public BasicNetwork getNetwork() {
		if (network == null) {
			network = createNetwork();
			((PressureNetwork) network).getPressureCond().network = (PressureNetwork) network;
			network.refresh();
		}
		return network;
	}
}
