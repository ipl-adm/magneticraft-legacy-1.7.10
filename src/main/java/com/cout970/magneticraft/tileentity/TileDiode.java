package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.api.electricity.ElectricConstants;
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

public class TileDiode extends TileConductorLow {

    @Override
    public IElectricConductor initConductor() {
        return new ElectricConductor(this, 0.01D) {

        	@Override
            public void recache() {
                if (!connected) {
                    connected = true;
                    con.clear();
                    int sides = 0;
                    for (VecInt f : getValidConnections()) {//search for other conductors

                        TileEntity target = MgUtils.getTileEntity(tile, f);
                        IElectricConductor[] c = ElectricUtils.getElectricCond(target, f.getOpposite(), getTier());
                        IEnergyInterface inter = ElectricUtils.getInterface(target, f.getOpposite(), getTier());

                        if (c != null) {
                            for (IElectricConductor e : c) {
                                if (e == this) continue;
                                if (this.isAbleToConnect(e, f) && e.isAbleToConnect(this, f.getOpposite())) {
                                	con.add(new IndexedConnection(this, f, e, sides));
                                	sides++;
                                }
                            }
                        }
                        if (inter != null && inter.canConnect(f)) {
                            con.add(new IndexedConnection(this, f, inter, sides));
                            sides++;
                        }
                    }
                    if (currents == null) {
                        currents = new double[sides];
                    } else {
                        if (currents.length != sides) {
                            double[] temp = new double[sides];
                            System.arraycopy(currents, 0, temp, 0, Math.min(sides, currents.length));
                            currents = temp;
                        }
                    }
                }
            }
        	
        	@Override
            public void iterate() {
                TileEntity tile = getParent();
                World w = tile.getWorldObj();
                //only calculated on server side
                if (w.isRemote) return;
                tile.markDirty();
                //make sure the method computeVoltage was called
                this.getVoltage();
                for (IIndexedConnection f : con) {
                	valance_diode(f, currents);
                }
            }

        	public void valance_diode(IIndexedConnection f, double[] currents) {
                IElectricConductor cond = f.getConductor();
                IEnergyInterface c = f.getEnergyInterface();
                if (cond != null) {
                    //the resistance of the connection
                    double resistence = (f.getSource().getResistance() + cond.getResistance());
                    //the voltage differennce
                    double deltaV = f.getSource().getVoltage() - cond.getVoltage();
                    //sanity check for infinite current
                    if (Double.isNaN(currents[f.getIndex()])) currents[f.getIndex()] = 0;
                     
                    if((f.getOffset().equals(getDirection().toVecInt()) && deltaV > 0) || !f.getOffset().equals(getDirection().toVecInt())){
                    	//the extra current from the last tick
                    	double current = currents[f.getIndex()];
                    	// (V - I*R) I*R is the voltage difference that this conductor should have using the ohm's law, and V the real one
                    	//vDiff is the voltage difference bvetween the current voltager difference and the proper voltage difference using the ohm's law
                    	double vDiff = (deltaV - current * resistence);
                    	//make sure the vDiff is not in the incorrect direction when the resistance is too big
                    	vDiff = Math.min(vDiff, Math.abs(deltaV));
                    	vDiff = Math.max(vDiff, -Math.abs(deltaV));
                    	// add to the next tick current an extra to get the proper voltage difference on the two conductors
                    	currents[f.getIndex()] += (vDiff * f.getSource().getIndScale()) / f.getSource().getVoltageMultiplier();
                    	// to the extra current add the current generated by the voltage difference
                    	current += (deltaV * 0.5D) / (f.getSource().getVoltageMultiplier());
                    	//moves the charge
                    	f.getSource().applyCurrent(-current);
                    	cond.applyCurrent(current);
                    }
                }
                if (c != null) {
                    if (f.getSource().getVoltage() > ElectricConstants.ENERGY_INTERFACE_LEVEL && c.canAcceptEnergy(f)) {
                        double watt = Math.min(c.getMaxFlow(), (f.getSource().getVoltage() - ElectricConstants.ENERGY_INTERFACE_LEVEL) * ElectricConstants.CONVERSION_SPEED);
                        if (watt > 0)
                            f.getSource().drainPower(c.applyEnergy(watt));
                    }
                }
            }
        	
            @Override
            public VecInt[] getValidConnections() {
                return new VecInt[]{getDirection().toVecInt(), getDirection().opposite().toVecInt()};
            }

            @Override
            public ConnectionClass getConnectionClass(VecInt v) {
                return ConnectionClass.CABLE_LOW;
            }
            
            @Override
            public boolean canFlowPower(IIndexedConnection con) {
                return false;
            }
        };
    }

    @Override
    public IElectricConductor[] getConds(VecInt dir, int tier) {
        if (tier != 0 && tier != -1) return null;
        if (VecInt.NULL_VECTOR.equals(dir) || getDirection().toVecInt().equals(dir) || getDirection().opposite().toVecInt().equals(dir))
            return new IElectricConductor[]{cond};
        return null;
    }

    public MgDirection getDirection() {
        return MgDirection.getDirection(getBlockMetadata());
    }
}
