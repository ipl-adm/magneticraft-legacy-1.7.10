package com.cout970.magneticraft.util.network;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cout970.magneticraft.api.util.VecInt;

import codechicken.multipart.TMultiPart;
import codechicken.multipart.TileMultipart;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class Finder {

	@SuppressWarnings("unchecked")
	public static <T> List<T> find(T obj, World w, VecInt pos, boolean blocks){
		List<T> list = new ArrayList<>();
		if(blocks){
			Block b = pos.getBlock(w);
			if(obj.getClass().isAssignableFrom(b.getClass())){
				list.add((T) b);
			}
		}
		TileEntity tile = pos.getTileEntity(w);
		if(tile != null && obj.getClass().isAssignableFrom(tile.getClass())){
			list.add((T) tile);
		}else if(tile instanceof TileMultipart){
			list.addAll(((TileMultipart) tile).jPartList().stream().filter(part -> part != null && obj.getClass().isAssignableFrom(part.getClass())).map(part -> (T) part).collect(Collectors.toList()));
		}
		return list;
	}
}
