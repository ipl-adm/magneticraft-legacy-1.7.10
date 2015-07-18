package com.cout970.magneticraft.block;

import java.util.ArrayList;
import java.util.List;

import com.cout970.magneticraft.util.Log;

import codechicken.lib.raytracer.IndexedCuboid6;
import codechicken.lib.raytracer.RayTracer;
import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Cuboid6;
import codechicken.lib.vec.Vector3;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockButtonArray extends BlockMg{

	public BlockButtonArray() {
		super(Material.iron);
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
		return null;
	}

	@Override
	public String[] getTextures() {
		return new String[]{"button_array"};
	}

	@Override
	public String getName() {
		return "button_array";
	}

	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitx, float hity, float hitz){
		MovingObjectPosition hit = RayTracer.retraceBlock(world, player, x, y, z);
		if(hit == null)
			return false;
		return true;
	}

	public MovingObjectPosition collisionRayTrace(World w, int x, int y, int z, Vec3 start, Vec3 end){
		List<IndexedCuboid6> list = new ArrayList<IndexedCuboid6>();
		list.add(new IndexedCuboid6(0, new Cuboid6(x, y, z, x+1, y+1, z+1)));
		return RayTracer.instance().rayTraceCuboids(new Vector3(start), new Vector3(end), list, new BlockCoord(x, y, z), this);
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_){
		return null;
	}

	public boolean renderAsNormalBlock() {
		return false;
	}
	
	public boolean isOpaqueCube(){
		return false;
	}
}
