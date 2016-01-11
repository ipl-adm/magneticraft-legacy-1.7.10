package com.cout970.magneticraft.api.conveyor;

import com.cout970.magneticraft.api.util.Orientation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

/**
 * @author Cout970
 */
public interface IConveyorBelt {

    enum BeltInteraction {
        DIRECT, INVERSE, LEFT_T, RIGHT_T, NOTHING;

        public static BeltInteraction InterBelt(EnumFacing a, EnumFacing b) {
            if (a == b) return BeltInteraction.DIRECT;
            if (a == b.getOpposite()) return BeltInteraction.INVERSE;
            if (a == b.rotateAround(EnumFacing.DOWN.getAxis())) return BeltInteraction.LEFT_T;
            if (a == b.rotateAround(EnumFacing.UP.getAxis())) return BeltInteraction.RIGHT_T;
            return BeltInteraction.NOTHING;
        }
    }

    boolean addItem(EnumFacing in, int pos, IItemBox it, boolean simulated);

    boolean removeItem(IItemBox it, boolean isLeft, boolean simulated);

    IConveyorBeltLane getSideLane(boolean left);

    EnumFacing getDir();

    boolean extract(IItemBox box, boolean isOnLeft, boolean simulated);

    boolean inject(int pos, IItemBox box, boolean isOnLeft, boolean simulated);

    TileEntity getParent();

    Orientation getOrientation();

    void onChange();
}
