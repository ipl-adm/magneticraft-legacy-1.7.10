package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricUtils;
import com.cout970.magneticraft.api.electricity.IElectricConductor;
import com.cout970.magneticraft.api.electricity.IEnergyInterface;
import com.cout970.magneticraft.api.electricity.IIndexedConnection;
import com.cout970.magneticraft.api.electricity.prefab.ElectricConductor;
import com.cout970.magneticraft.api.electricity.prefab.IndexedConnection;
import com.cout970.magneticraft.api.util.ConnectionClass;
import com.cout970.magneticraft.api.util.MgDirection;
import com.cout970.magneticraft.api.util.MgUtils;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.util.tile.TileConductorLow;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileDiode extends TileConductorLow{

	@Override
	public IElectricConductor initConductor() {
		return new ElectricConductor(this, 5){
			
			@Override
			public void recache(){
				if(!connected){
					connected = true;
					con.clear();
					int sides = 0;
					for(VecInt f : getValidConnections()){
						TileEntity target = MgUtils.getTileEntity(tile, f);
						IElectricConductor[] c = ElectricUtils.getElectricCond(target, f.getOpposite(), getTier());
						IEnergyInterface inter = ElectricUtils.getInterface(target, f.getOpposite(), getTier());
						if(c != null){
							for(IElectricConductor e : c){
								if(e == this)continue;
								if(this.isAbleToConnect(e, f) && e.isAbleToConnect(this, f.getOpposite())){
									con.add(new IndexedConnection(this, f,e,sides));
									sides++;
								}
							}
						}
						if(inter != null && inter.canConnect(f)){
							con.add(new IndexedConnection(this, f, inter,sides));
							sides++;
						}
					}
					if(currents == null){
						currents = new double[sides];
					}else{
						if(currents.length != sides){
							double[] temp = new double[sides];
							for(int i=0;i< Math.min(sides, currents.length);i++){
								temp[i] = currents[i];
							}
							currents = temp;
						}
					}
				}
			}
			
			@Override
			public void iterate() {
				TileEntity tile = getParent();
				World w = tile.getWorldObj();
				if(w.isRemote)return;
				tile.markDirty();
				this.getVoltage();

				for(IIndexedConnection f : con){
					ElectricConductor.valance(f, currents);
				}
			}
			
			@Override
			public VecInt[] getValidConnections() {
				return new VecInt[]{getDirection().toVecInt(), getDirection().opposite().toVecInt()};
			}
			
			@Override
			public boolean canFlowPower(IIndexedConnection con) {
				return con.getSource().getClass().isAssignableFrom(getClass());
			}
			
			@Override
			public ConnectionClass getConnectionClass(VecInt v) {
				return ConnectionClass.CABLE_LOW;
			}
		};
	}

	@Override
	public IElectricConductor[] getConds(VecInt dir, int tier) {
		if(tier != 0 && tier !=-1)return null;
		if(dir == VecInt.NULL_VECTOR || getDirection().toVecInt().equals(dir) || getDirection().opposite().toVecInt().equals(dir))
		return new IElectricConductor[]{cond};
		return null;
	}
	
	public MgDirection getDirection() {
		return MgDirection.getDirection(getBlockMetadata());
	}
}
