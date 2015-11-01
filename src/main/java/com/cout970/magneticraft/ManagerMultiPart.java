package com.cout970.magneticraft;

import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.MultiPartRegistry.IPartFactory;
import codechicken.multipart.TMultiPart;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.parts.PartOpticFiber;
import com.cout970.magneticraft.parts.electric.PartCableHigh;
import com.cout970.magneticraft.parts.electric.PartCableLow;
import com.cout970.magneticraft.parts.electric.PartCableMedium;
import com.cout970.magneticraft.parts.electric.wires.*;
import com.cout970.magneticraft.parts.fluid.PartBrassPipe;
import com.cout970.magneticraft.parts.fluid.PartCopperPipe;
import com.cout970.magneticraft.parts.fluid.PartIronPipe;
import com.cout970.magneticraft.parts.heat.PartHeatCable;

public class ManagerMultiPart {

    public void init() {
        MultiPartRegistry.registerParts(new CableLowFactory(), new String[]{ManagerItems.part_copper_cable_low.getUnlocalizedName()});
        MultiPartRegistry.registerParts(new CableMediumFactory(), new String[]{ManagerItems.part_copper_cable_medium.getUnlocalizedName()});
        MultiPartRegistry.registerParts(new CableHighFactory(), new String[]{ManagerItems.part_copper_cable_high.getUnlocalizedName()});
        MultiPartRegistry.registerParts(new CopperPipeFactory(), new String[]{ManagerItems.part_copper_pipe.getUnlocalizedName()});
        MultiPartRegistry.registerParts(new IronPipeFactory(), new String[]{ManagerItems.part_iron_pipe.getUnlocalizedName()});
        MultiPartRegistry.registerParts(new HeatCableFactory(), new String[]{ManagerItems.partheatcable.getUnlocalizedName()});
        if (Magneticraft.DEBUG) {
            MultiPartRegistry.registerParts(new OpticFiberFactory(), new String[]{ManagerItems.part_optic_fiber.getUnlocalizedName()});
            MultiPartRegistry.registerParts(new BrassPipeFactory(), new String[]{ManagerItems.part_brass_pipe.getUnlocalizedName()});
        }
        for (MgDirection d : MgDirection.values())
            MultiPartRegistry.registerParts(new CopperWireFactory(), new String[]{ManagerItems.part_copper_wire.getUnlocalizedName() + "_" + d.name()});
    }

    public class BrassPipeFactory implements IPartFactory {
        @Override
        public TMultiPart createPart(String arg0, boolean arg1) {
            return new PartBrassPipe();
        }
    }

    public class CableLowFactory implements IPartFactory {
        @Override
        public TMultiPart createPart(String arg0, boolean arg1) {
            return new PartCableLow();
        }
    }

    public class OpticFiberFactory implements IPartFactory {
        @Override
        public TMultiPart createPart(String arg0, boolean arg1) {
            return new PartOpticFiber();
        }
    }

    public class CableMediumFactory implements IPartFactory {
        @Override
        public TMultiPart createPart(String arg0, boolean arg1) {
            return new PartCableMedium();
        }
    }

    public class CableHighFactory implements IPartFactory {
        @Override
        public TMultiPart createPart(String arg0, boolean arg1) {
            return new PartCableHigh();
        }
    }

    public class CopperPipeFactory implements IPartFactory {
        @Override
        public TMultiPart createPart(String arg0, boolean arg1) {
            return new PartCopperPipe();
        }
    }

    public class IronPipeFactory implements IPartFactory {
        @Override
        public TMultiPart createPart(String arg0, boolean arg1) {
            return new PartIronPipe();
        }
    }

    public class HeatCableFactory implements IPartFactory {
        @Override
        public TMultiPart createPart(String arg0, boolean arg1) {
            return new PartHeatCable();
        }
    }

    public class CopperWireFactory implements IPartFactory {
        @Override
        public TMultiPart createPart(String name, boolean arg1) {
            MgDirection dir = MgDirection.valueOf(name.replaceFirst(ManagerItems.part_copper_wire.getUnlocalizedName() + "_", ""));
            if (dir == MgDirection.DOWN) return new PartWireCopper_Down();
            if (dir == MgDirection.UP) return new PartWireCopper_Up();
            if (dir == MgDirection.NORTH) return new PartWireCopper_North();
            if (dir == MgDirection.SOUTH) return new PartWireCopper_South();
            if (dir == MgDirection.WEST) return new PartWireCopper_West();
            if (dir == MgDirection.EAST) return new PartWireCopper_East();
            return null;
        }
    }
}
