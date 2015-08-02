package com.cout970.magneticraft;

import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.parts.micro.PartCableHigh;
import com.cout970.magneticraft.parts.micro.PartCableLow;
import com.cout970.magneticraft.parts.micro.PartCableMedium;
import com.cout970.magneticraft.parts.micro.PartCopperPipe;
import com.cout970.magneticraft.parts.micro.PartHeatCable;
import com.cout970.magneticraft.parts.micro.PartIronPipe;
import com.cout970.magneticraft.parts.micro.PartOpticFiber;
import com.cout970.magneticraft.parts.micro.wires.PartWireCopper_Down;
import com.cout970.magneticraft.parts.micro.wires.PartWireCopper_East;
import com.cout970.magneticraft.parts.micro.wires.PartWireCopper_North;
import com.cout970.magneticraft.parts.micro.wires.PartWireCopper_South;
import com.cout970.magneticraft.parts.micro.wires.PartWireCopper_Up;
import com.cout970.magneticraft.parts.micro.wires.PartWireCopper_West;

import codechicken.multipart.MultiPartRegistry;
import codechicken.multipart.MultiPartRegistry.IPartFactory;
import codechicken.multipart.TMultiPart;

public class ManagerMultiPart {
	
	public void init(){
		MultiPartRegistry.registerParts(new CableLowFactory(), new String[]{ManagerItems.cablelow.getUnlocalizedName()});
		MultiPartRegistry.registerParts(new CableMediumFactory(), new String[]{ManagerItems.cablemedium.getUnlocalizedName()});
		MultiPartRegistry.registerParts(new CableHighFactory(), new String[]{ManagerItems.cablehigh.getUnlocalizedName()});
		MultiPartRegistry.registerParts(new CopperPipeFactory(), new String[]{ManagerItems.partcopperpipe.getUnlocalizedName()});
		MultiPartRegistry.registerParts(new IronPipeFactory(), new String[]{ManagerItems.partironpipe.getUnlocalizedName()});
		MultiPartRegistry.registerParts(new HeatCableFactory(), new String[]{ManagerItems.partheatcable.getUnlocalizedName()});
		MultiPartRegistry.registerParts(new OpticFiberFactory(), new String[]{ManagerItems.part_optic_fiber.getUnlocalizedName()});
		for(MgDirection d : MgDirection.values())
		MultiPartRegistry.registerParts(new CopperWireFactory(), new String[]{ManagerItems.wire_copper.getUnlocalizedName()+"_"+d.name()});
	}
	
	public class CableLowFactory implements IPartFactory{
		@Override
		public TMultiPart createPart(String arg0, boolean arg1) {
			return new PartCableLow();
		}
	}
	
	public class OpticFiberFactory implements IPartFactory{
		@Override
		public TMultiPart createPart(String arg0, boolean arg1) {
			return new PartOpticFiber();
		}
	}
	
	public class CableMediumFactory implements IPartFactory{
		@Override
		public TMultiPart createPart(String arg0, boolean arg1) {
			return new PartCableMedium();
		}
	}
	
	public class CableHighFactory implements IPartFactory{
		@Override
		public TMultiPart createPart(String arg0, boolean arg1) {
			return new PartCableHigh();
		}
	}
	
	public class CopperPipeFactory implements IPartFactory{
		@Override
		public TMultiPart createPart(String arg0, boolean arg1) {
			return new PartCopperPipe();
		}
	}
	
	public class IronPipeFactory implements IPartFactory{
		@Override
		public TMultiPart createPart(String arg0, boolean arg1) {
			return new PartIronPipe();
		}
	}
	
	public class HeatCableFactory implements IPartFactory{
		@Override
		public TMultiPart createPart(String arg0, boolean arg1) {
			return new PartHeatCable();
		}
	}
	
	public class CopperWireFactory implements IPartFactory{
		@Override
		public TMultiPart createPart(String name, boolean arg1) {
			MgDirection dir = MgDirection.valueOf(name.replaceFirst(ManagerItems.wire_copper.getUnlocalizedName()+"_", ""));
			if(dir == MgDirection.DOWN)return new PartWireCopper_Down();
			if(dir == MgDirection.UP)return new PartWireCopper_Up();
			if(dir == MgDirection.NORTH)return new PartWireCopper_North();
			if(dir == MgDirection.SOUTH)return new PartWireCopper_South();
			if(dir == MgDirection.WEST)return new PartWireCopper_West();
			if(dir == MgDirection.EAST)return new PartWireCopper_East();
			return null;
		}
	}
}
