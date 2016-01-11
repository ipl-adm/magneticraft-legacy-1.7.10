package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.util.ITileHandlerNBT;
import com.cout970.magneticraft.util.tile.RedstoneControl;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileBase extends Tile1_8Updater implements ITileHandlerNBT {

    public boolean powered;
    public RedstoneControl redstone = RedstoneControl.NORMAL;

    public static RedstoneControl step(RedstoneControl state) {
        return state == RedstoneControl.NORMAL ? RedstoneControl.INVERSE : state == RedstoneControl.INVERSE ? RedstoneControl.DISABLE : RedstoneControl.NORMAL;
    }

    public void onNeigChange() {
        powered = isPowered();
    }

    public void onBlockBreaks() {
    }

    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 16384.0D;
    }

    public void setRedstoneControl(RedstoneControl newState) {
        redstone = newState;
        onNeigChange();
        markDirty();
    }

    public boolean isControlled() {
        if (redstone == RedstoneControl.NORMAL) return !powered;
        return redstone != RedstoneControl.INVERSE || powered;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        redstone = RedstoneControl.values()[nbt.getByte("RedstoneControl")];
        powered = nbt.getBoolean("Powered");
    }

    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setByte("RedstoneControl", (byte) redstone.ordinal());
        nbt.setBoolean("Powered", powered);
    }

    public Packet getDescriptionPacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        this.writeToNBT(nbt);
        return new S35PacketUpdateTileEntity(pos, 0, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.getNbtCompound());
    }

    @Override
    public void saveInServer(NBTTagCompound nbt) {
        writeToNBT(nbt);
    }

    @Override
    public void loadInClient(NBTTagCompound nbt) {
        readFromNBT(nbt);
    }
}
