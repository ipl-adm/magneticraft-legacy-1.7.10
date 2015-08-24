package com.cout970.magneticraft.tileentity;

import com.cout970.magneticraft.ManagerNetwork;
import com.cout970.magneticraft.api.util.VecInt;
import com.cout970.magneticraft.messages.MessageNBTUpdate;
import com.cout970.magneticraft.util.ITileHandlerNBT;
import com.cout970.magneticraft.util.tile.RedstoneControl;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;

public class TileBase extends Tile1_8Updater implements ITileHandlerNBT {

    public boolean powered;
    public RedstoneControl redstone = RedstoneControl.NORMAL;

    public void onNeigChange() {
        powered = isPowered();
    }

    public void onBlockBreaks() {
    }

    public void sendUpdateToClient() {
        if (worldObj.isRemote) return;
        NBTTagCompound nbt = new NBTTagCompound();
        saveInServer(nbt);
        MessageNBTUpdate message = new MessageNBTUpdate(this, nbt);
        for (Object obj : worldObj.playerEntities) {
            if (obj instanceof EntityPlayerMP) {
                EntityPlayerMP player = (EntityPlayerMP) obj;
                if (getDistanceSquaredFrom(player, this) <= 16384) {
                    ManagerNetwork.INSTANCE.sendTo(message, player);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 16384.0D;
    }

    protected int getDistanceSquaredFrom(EntityPlayerMP pl, TileBase tile) {
        VecInt vecPl = new VecInt(pl);
        VecInt vecTE = new VecInt(tile);
        return vecPl.add(vecTE.getOpposite()).squareDistance();
    }

    public void setRedstoneControl(RedstoneControl newState) {
        redstone = newState;
        onNeigChange();
        sendUpdateToClient();
    }

    public boolean isControled() {
        if (redstone == RedstoneControl.NORMAL) return !powered;
        if (redstone == RedstoneControl.INVERSE) return powered;
        return true;
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
        S35PacketUpdateTileEntity p = new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, nbt);
        return p;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }

    public static RedstoneControl step(RedstoneControl state) {
        return state == RedstoneControl.NORMAL ? RedstoneControl.INVERSE : state == RedstoneControl.INVERSE ? RedstoneControl.DISBLE : RedstoneControl.NORMAL;
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
