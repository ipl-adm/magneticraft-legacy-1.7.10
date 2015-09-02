package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.access.RecipeHammerTable;
import com.cout970.magneticraft.block.BlockMg;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBlockDustFX;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Random;

public class TileHammerTable extends TileBase {

    private ItemStack ore;
    public int progress;
    public int maxProgress;

    @Override
    public void onBlockBreaks() {
        if (ore != null) {
            BlockMg.dropItem(ore, worldObj.rand, xCoord, yCoord, zCoord, worldObj);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        NBTTagCompound tag = (NBTTagCompound) nbt.getTag("item");
        if (tag != null) {
            ore = ItemStack.loadItemStackFromNBT(tag);
        }
        progress = nbt.getInteger("progress");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (ore != null) {
            NBTTagCompound tag = new NBTTagCompound();
            ore.writeToNBT(tag);
            nbt.setTag("item", tag);
        }
        nbt.setInteger("progress", progress);
    }

    public void setInput(ItemStack i) {
        ore = i;
        markDirty();
    }

    public ItemStack getInput() {
        return ore;
    }

    public void tick(int maxHits) {
        maxProgress = maxHits;
        progress++;
        addParticles();
        if (progress >= maxProgress) {
            maxProgress = 0;
            progress = 0;
            ItemStack out = getOutput();
            ore = null;
            // TODO CHECK
            /*if (ManagerConfig.hammerTableDrops) {
                BlockMg.dropItem(out, worldObj.rand, xCoord, yCoord, zCoord, worldObj);
            } else {
            */    ore = out.copy(); /*
            } */
        }
    }

    public ItemStack getOutput() {
        RecipeHammerTable rec = RecipeHammerTable.getRecipe(ore);
        if (rec == null)
            return null;
        return rec.getOutput();
    }

    private void addParticles() {
        if (worldObj.isRemote) {
            Item item = ore.getItem();
            for (int i = 0; i < 20; i++) {
                float a, b, c;
                Random rnd = new Random();
                a = (rnd.nextFloat() - 0.5F) * 0.5F;
                b = (rnd.nextFloat() - 0.5F) * 0.5F;
                c = (rnd.nextFloat() - 0.5F) * 0.5F;
                // TODO FIX: some particles looks weird
                if (item instanceof ItemBlock) {
                    Block block = Block.getBlockFromItem(item);
                    Minecraft.getMinecraft().effectRenderer
                            .addEffect(new EntityBlockDustFX(worldObj, xCoord + 0.5 + a, yCoord + 0.95, zCoord + 0.5 + c,
                                    a * 0.15, 0.1f + b * 0.005, c * 0.15, block, 0).multipleParticleScaleBy(0.4F));
                } else {
                    Minecraft.getMinecraft().effectRenderer
                            .addEffect(new EntityBreakingFX(worldObj, xCoord + 0.5 + a, yCoord + 0.95, zCoord + 0.5 + c,
                                    a * 0.15, 0.1f + b * 0.005, c * 0.15, item, 0));
                }
            }
        }
    }

    public boolean canWork() {
        return (ore != null) && (RecipeHammerTable.getRecipe(ore) != null);
    }
}
