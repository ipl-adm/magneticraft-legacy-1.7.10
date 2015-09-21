package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.conveyor.IConveyorBelt;
import com.cout970.magneticraft.api.conveyor.IConveyorBeltLane;
import com.cout970.magneticraft.api.conveyor.IItemBox;
import com.cout970.magneticraft.api.conveyor.prefab.ConveyorBeltLane;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.Orientation;
import com.cout970.magneticraft.block.BlockMg;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

public class TileConveyorBelt extends TileBase implements IConveyorBelt {

    public ConveyorBeltLane left = new ConveyorBeltLane(this, true);
    public ConveyorBeltLane right = new ConveyorBeltLane(this, false);
    public long time;

    public MgDirection getDir() {
        return getOrientation().getDirection();
    }

    public void onBlockBreaks() {
        if (worldObj.isRemote) return;
        for (IItemBox b : left.getItemBoxes()) {
            BlockMg.dropItem(b.getContent(), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
        }
        for (IItemBox b : right.getItemBoxes()) {
            BlockMg.dropItem(b.getContent(), worldObj.rand, xCoord, yCoord, zCoord, worldObj);
        }
    }

    public void updateEntity() {
        super.updateEntity();
        moveCole(left);
        moveCole(right);
        time = System.currentTimeMillis();
        if (worldObj.getTotalWorldTime() % 200 == 0)
            sendUpdateToClient();
    }

    private void moveCole(ConveyorBeltLane side) {
        if (worldObj.getTotalWorldTime() % 10 == 0) {
            side.getHitBoxes().clear();
        }

//		for (int i = side.content.size()-1; i >= 0 ; i--) {
        for (int i = 0; i < side.getItemBoxes().size(); i++) {
            IItemBox b = side.getItemBoxes().get(i);
            if (b.getLastUpdateTick() == worldObj.getTotalWorldTime()) continue;
            b.setLastUpdateTick(worldObj.getTotalWorldTime());
            if (b.getPosition() < 15) {
                side.avance(b);
                continue;
            }
            TileEntity t = ConveyorBeltLane.getFrontConveyor(this);
            if (!(t instanceof IConveyorBelt)) continue;
            IConveyorBelt con = (IConveyorBelt) t;
            BeltInteraction iter = BeltInteraction.InterBelt(getDir(), con.getDir());
            if (iter == BeltInteraction.DIRECT) {
                if (b.getPosition() < 16) side.avance(b);
                if (b.getPosition() == 16) {
                    con.getSideLane(side.isOnLeft()).setHitBoxSpace(0, false);
                    if (con.inject(0, b, side.isOnLeft(), false)) {
                        extract(b, side.isOnLeft(), false);
                    }
                }
            } else if (con.getOrientation().getLevel() == 0) {
                if (iter == BeltInteraction.LEFT_T) {
                    if (b.getPosition() < 18) side.avance(b);
                    if (b.getPosition() == 18) {
                        int new_pos = side.isOnLeft() ? 2 : 10;
                        con.getSideLane(false).setHitBoxSpace(new_pos, false);
                        if (con.inject(new_pos, b, false, false)) {
                            extract(b, side.isOnLeft(), false);
                        }
                    }
                } else if (iter == BeltInteraction.RIGHT_T) {
                    if (b.getPosition() < 18) side.avance(b);
                    if (b.getPosition() == 18) {
                        int new_pos = side.isOnLeft() ? 10 : 2;
                        con.getSideLane(true).setHitBoxSpace(new_pos, false);
                        if (con.inject(new_pos, b, true, false)) {
                            extract(b, side.isOnLeft(), false);
                        }
                    }
                }
            }
        }
    }

    //IConveyor

    @Override
    public boolean extract(IItemBox box, boolean isOnLeft, boolean simulated) {
        ConveyorBeltLane side = isOnLeft ? left : right;
        if (side.getItemBoxes().contains(box)) {
            if (!simulated) {
                side.getItemBoxes().remove(box);
                side.setHitBoxSpace(box.getPosition(), false);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean inject(int pos, IItemBox box, boolean isOnLeft, boolean simulated) {
        ConveyorBeltLane side = isOnLeft ? left : right;
        if (side.hasHitBoxSpace(pos)) {
            if (!simulated) {
                box.setPosition(pos);
                box.setOnLeft(isOnLeft);
                side.setHitBoxSpace(pos, true);
                side.getItemBoxes().add(box);
            }
            return true;
        }
        return false;
    }

    public boolean addItem(MgDirection dir, int pos, IItemBox b, boolean sim) {
        if (b == null) return false;
        boolean onLeft;
        if (dir.isPerpendicular(getDir())) {
            onLeft = dir == getDir().step(MgDirection.UP);
        } else {
            onLeft = b.isOnLeft();
        }
        return inject(pos, b, onLeft, sim);
    }

    //save and load

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        left.save(nbt);
        right.save(nbt);
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        left.load(nbt);
        right.load(nbt);
    }

    @Override
    public TileEntity getParent() {
        return this;
    }

    @Override
    public IConveyorBeltLane getSideLane(boolean left) {
        return left ? this.left : this.right;
    }

    public Orientation getOrientation() {
        return Orientation.fromMeta(getBlockMetadata());
    }

    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1 + 5 / 16f, yCoord + 1 + 5 / 16f, zCoord + 1 + 5 / 16f);
    }

    @Override
    public boolean removeItem(IItemBox it, boolean isLeft, boolean simulated) {
        return extract(it, isLeft, simulated);
    }

    @Override
    public void onChange() {
        sendUpdateToClient();
    }
}
