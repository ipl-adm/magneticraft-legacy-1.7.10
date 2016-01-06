package com.cout970.magneticraft.handlers;

import com.cout970.magneticraft.ManagerBlocks;
import com.cout970.magneticraft.ManagerFluids;
import com.cout970.magneticraft.ManagerItems;
import com.cout970.magneticraft.items.ItemBucket;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

public class HandlerBuckets {

    public static HandlerBuckets INSTANCE;
    public Map<Block, Item> buckets = new HashMap<>();

    public HandlerBuckets() {
        buckets.put(ManagerFluids.oilBlock, ManagerItems.bucket_oil);
        buckets.put(ManagerFluids.lightOilBlock, ManagerItems.bucket_light_oil);
        buckets.put(ManagerFluids.heavyOilBlock, ManagerItems.bucket_heavy_oil);
        buckets.put(ManagerFluids.hotCrudeBlock, ManagerItems.bucket_hot_crude);

        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(ManagerFluids.OIL_NAME, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ManagerItems.bucket_oil), new ItemStack(Items.bucket));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(ManagerFluids.LIGHT_OIL, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ManagerItems.bucket_light_oil), new ItemStack(Items.bucket));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(ManagerFluids.HEAVY_OIL, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ManagerItems.bucket_heavy_oil), new ItemStack(Items.bucket));
        FluidContainerRegistry.registerFluidContainer(FluidRegistry.getFluidStack(ManagerFluids.HOT_CRUDE, FluidContainerRegistry.BUCKET_VOLUME), new ItemStack(ManagerItems.bucket_hot_crude), new ItemStack(Items.bucket));
    }

    public FluidStack getFluid(ItemStack item) {
        Item i = item.getItem();
        if (i instanceof ItemBucket) {
            if (((ItemBucket) i).fluid.equals(ManagerFluids.OIL_NAME))
                return FluidRegistry.getFluidStack(ManagerFluids.OIL_NAME, 1000);
            if (((ItemBucket) i).fluid.equals(ManagerFluids.LIGHT_OIL))
                return FluidRegistry.getFluidStack(ManagerFluids.LIGHT_OIL, 1000);
            if (((ItemBucket) i).fluid.equals(ManagerFluids.HEAVY_OIL))
                return FluidRegistry.getFluidStack(ManagerFluids.HEAVY_OIL, 1000);
            if (((ItemBucket) i).fluid.equals(ManagerFluids.HOT_CRUDE))
                return FluidRegistry.getFluidStack(ManagerFluids.HOT_CRUDE, 1000);
        }
        return null;
    }

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent event) {

        Block block = event.world.getBlock(event.target.blockX, event.target.blockY, event.target.blockZ);
        if (block == ManagerBlocks.infinite_water) {
            event.setResult(Result.ALLOW);
            event.result = new ItemStack(Items.water_bucket);
            return;
        }
        ItemStack result = fillCustomBucket(event.world, event.target);

        if (result == null) return;

        event.result = result;
        event.setResult(Result.ALLOW);
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {

        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);

        Item bucket = buckets.get(block);
        if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
            world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
            return new ItemStack(bucket);
        } else
            return null;
    }
}
