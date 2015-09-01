package com.cout970.magneticraft.messages;

import com.cout970.magneticraft.tileentity.TileBase;
import com.cout970.magneticraft.util.tile.RedstoneControl;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class MessageRedstoneControlUpdate implements IMessage, IMessageHandler<MessageRedstoneControlUpdate, IMessage> {

    public int x, y, z;
    public NBTTagCompound nbt;
    public Byte state = -1;

    public MessageRedstoneControlUpdate() {
    }

    public MessageRedstoneControlUpdate(TileEntity t, RedstoneControl state) {
        x = t.xCoord;
        y = t.yCoord;
        z = t.zCoord;
        this.state = (byte) state.ordinal();
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer PB = new PacketBuffer(buf);
        x = PB.readInt();
        y = PB.readShort();
        z = PB.readInt();
        state = PB.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer PB = new PacketBuffer(buf);
        PB.writeInt(x);
        PB.writeShort(y);
        PB.writeInt(z);
        PB.writeByte(state);
    }

    @Override
    public IMessage onMessage(MessageRedstoneControlUpdate message, MessageContext ctx) {
        TileEntity tileEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
        if (tileEntity instanceof TileBase) {
            ((TileBase) tileEntity).setRedstoneControl(RedstoneControl.values()[message.state]);
        }
        return null;
    }

}
