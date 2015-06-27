package com.cout970.magneticraft.compact.minetweaker;

import net.minecraft.block.Block;

import com.cout970.magneticraft.api.acces.MgRecipeRegister;
import com.cout970.magneticraft.api.acces.RecipeCrusher;
import com.cout970.magneticraft.api.util.BlockInfo;
import com.cout970.magneticraft.api.util.ThermopileFuel;
import com.cout970.magneticraft.compact.minetweaker.Crusher.AddRecipe;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.block.IBlock;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenClass("mods.magneticraft.Thermopile")
public class Thermopile {
	
	@ZenMethod
	public static void addHotSource(IBlock block, int metadata, int temp){
		Block b = MgMinetweaker.getBlock(block);
		MineTweakerAPI.apply(new HotSource(new BlockInfo(b, metadata),temp));
	}
	
	@ZenMethod
	public static void addColdSource(IBlock block, int metadata, int temp){
		Block b = MgMinetweaker.getBlock(block);
		MineTweakerAPI.apply(new ColdSource(new BlockInfo(b, metadata),temp));
	}
	
	@ZenMethod
	public static void removeHeatSource(IBlock block, int metadata){
		Block b = MgMinetweaker.getBlock(block);
		ThermopileFuel r = ThermopileFuel.getRecipe(new BlockInfo(b, metadata));
		if(r == null)return;
		MineTweakerAPI.apply(new RemoveSource(r));
	}
	
	public static class HotSource implements IUndoableAction{

		private final ThermopileFuel r;
		
		public HotSource(BlockInfo b, int temp) {
			this.r = new ThermopileFuel(b, temp, true);
		}

		@Override
		public void apply() {
			MgRecipeRegister.thermopileSources.add(r);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public String describe() {
			return "Adding "+r;
		}

		@Override
		public String describeUndo() {
			return "Removing "+r;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public void undo() {
			MgRecipeRegister.thermopileSources.remove(r);
		}
	}
	
	public static class ColdSource implements IUndoableAction{

		private final ThermopileFuel r;
		
		public ColdSource(BlockInfo b, int temp) {
			this.r = new ThermopileFuel(b, temp, false);
		}

		@Override
		public void apply() {
			MgRecipeRegister.thermopileSources.add(r);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public String describe() {
			return "Adding "+r;
		}

		@Override
		public String describeUndo() {
			return "Removing "+r;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public void undo() {
			MgRecipeRegister.thermopileSources.remove(r);
		}
	}
	
	public static class RemoveSource implements IUndoableAction{

		private final ThermopileFuel r;
		
		public RemoveSource(ThermopileFuel b) {
			this.r = b;
		}

		@Override
		public void apply() {
			MgRecipeRegister.thermopileSources.remove(r);
		}

		@Override
		public boolean canUndo() {
			return true;
		}

		@Override
		public String describe() {
			return "Removing "+r;
		}

		@Override
		public String describeUndo() {
			return "Re-Adding "+r;
		}

		@Override
		public Object getOverrideKey() {
			return null;
		}

		@Override
		public void undo() {
			MgRecipeRegister.thermopileSources.add(r);
		}
	}
}
