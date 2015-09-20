package com.cout970.magneticraft.tileentity;

import codechicken.lib.vec.BlockCoord;
import codechicken.lib.vec.Vector3;
import codechicken.multipart.BlockMultipart;
import com.cout970.magneticraft.api.heat.HeatUtils;
import com.cout970.magneticraft.api.heat.IHeatConductor;
import com.cout970.magneticraft.api.util.EnergyConverter;
import com.cout970.magneticraft.api.util.VecInt;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileMirror extends TileBase {

    public float rotation, angle;
    public BlockCoord target;
    public float distance;
    public BlockCoord load;

    public void updateEntity() {
        super.updateEntity();
        if (load != null) {
            setTarget(load);
            load = null;
        }
        if (worldObj.isRemote) return;
        if (target != null) {
            if (worldObj.isDaytime() && !worldObj.isRaining() && worldObj.canBlockSeeTheSky(this.xCoord, this.yCoord, this.zCoord) && !this.worldObj.provider.hasNoSky) {
                TileEntity t = worldObj.getTileEntity(target.x, target.y, target.z);
                if (t instanceof TileSolarTowerCore) {
                    IHeatConductor[] comp = HeatUtils.getHeatCond(t, VecInt.NULL_VECTOR);
                    if (comp != null) {
                        IHeatConductor heat = comp[0];
                        if ((heat != null) && (heat.getTemperature() < 1200))
                            heat.applyCalories(EnergyConverter.RFtoCALORIES(2));
                    }
                }
            }
        }
    }

    public void onNeigChange() {
        super.onNeigChange();
        updateAngle();
    }

    public void updateAngle() {
        setTarget(target);
    }

    public void setTarget(BlockCoord ta) {
        target = ta;
        if (target != null) {
            this.markDirty();
            Vector3 t = new Vector3(xCoord - target.x, yCoord - target.y, zCoord - target.z);
            if (yCoord - target.y > 0) {
                rotation = 0;
                angle = 0;
                return;
            }
            distance = (float) (t.x * t.x + t.z * t.z);
            if (distance < 400) {
                boolean empty = true;
                for (int h = 0; h < (int) distance * 10; h++) {

                    int x, y, z;
                    x = (int) Math.floor(-t.x * (h / (distance * 10)) + xCoord + 0.5);
                    y = (int) Math.floor(-t.y * (h / (distance * 10)) + yCoord + 0.5);
                    z = (int) Math.floor(-t.z * (h / (distance * 10)) + zCoord + 0.5);

                    if ((x == xCoord) && (y == yCoord) && (z == zCoord)) continue;
                    if ((x == target.x) && (y == target.y) && (z == target.z)) continue;

                    Block block = this.worldObj.getBlock(x, y, z);
                    if (!isTransparent(block)) {
                        empty = false;
                        rotation = 0;
                        angle = 0;
                        break;
                    }
                }
                if (empty) {
                    rotation = (float) (Math.toRadians(180) + Math.atan2(t.z, t.x));
                    angle = (float) (Math.toRadians(180) + t.angle(new Vector3(0, 1, 0)));
                } else {
                    rotation = 0;
                    angle = 0;
                }
            }
        }
    }

    private boolean isTransparent(Block block) {
        //TODO find a better way to check transparency. Checking for vanilla blocks only is an awful idea in modded MC.
        return (block.getMaterial() == Material.air)
                || Block.isEqualTo(block, Blocks.glass)
                || Block.isEqualTo(block, Blocks.stained_glass)
                || (block instanceof BlockMultipart);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        int[] i = nbt.getIntArray("Coords");
        if (i != null && i.length == 3) {
            load = new BlockCoord(i);
        }
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        if (target != null)
            nbt.setIntArray("Coords", this.target.intArray());
        else if (load != null) {
            nbt.setIntArray("Coords", this.load.intArray());
        }
    }
}
