package com.cout970.magneticraft.tileentity;

import java.util.Random;

import com.cout970.magneticraft.ManagerConfig;
import com.cout970.magneticraft.api.access.RecipeCrushingTable;
import com.cout970.magneticraft.block.BlockMg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBlockDustFX;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileCrushingTable extends TileBase {

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

        ore = ItemStack.loadItemStackFromNBT(tag);

        progress = nbt.getInteger("progress");
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTagCompound tag = new NBTTagCompound();
        if (ore != null) {
            ore.writeToNBT(tag);
        }
        nbt.setTag("item", tag);
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
        if (worldObj.isRemote) {
            addParticles();
        }
        if (progress >= maxProgress) {
            maxProgress = 0;
            progress = 0;
            ItemStack out = getOutput();
            ore = null;
            if (ManagerConfig.CRUSHING_TABLE_DROPS) {
            	if(!worldObj.isRemote)
                BlockMg.dropItem(out, worldObj.rand, xCoord, yCoord, zCoord, worldObj);
            } else {
                ore = out.copy(); 
            } 
        }
    }

    public ItemStack getOutput() {
        RecipeCrushingTable rec = RecipeCrushingTable.getRecipe(ore);
        if (rec == null)
            return null;
        return rec.getOutput().copy();
    }

    @SideOnly(Side.CLIENT)
    private void addParticles() {
        if (worldObj.isRemote) {
            Item item = ore.getItem(); 
            Random rnd = new Random();
            for (int i = 0; i < 20; i++) {
                float a, b, c;
                a = (rnd.nextFloat() - 0.5F) * 0.5F;
                b = (rnd.nextFloat() - 0.5F) * 0.5F;
                c = (rnd.nextFloat() - 0.5F) * 0.5F;
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
        return (ore != null) && (RecipeCrushingTable.getRecipe(ore) != null);
    }
}
