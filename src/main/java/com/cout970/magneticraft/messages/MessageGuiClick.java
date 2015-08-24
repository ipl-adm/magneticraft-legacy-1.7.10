package com.cout970.magneticraft.messages;

import com.cout970.magneticraft.util.IGuiListener;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class MessageGuiClick implements IMessage, IMessageHandler<MessageGuiClick, IMessage> {

    public int x, y, z;
    public int id;
    public int data;

    public MessageGuiClick() {
    }

    public MessageGuiClick(TileEntity t, int id, int data) {
        x = t.xCoord;
        y = t.yCoord;
        z = t.zCoord;
        this.id = id;
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer PB = new PacketBuffer(buf);
        x = PB.readInt();
        y = PB.readShort();
        z = PB.readInt();
        id = PB.readInt();
        data = PB.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer PB = new PacketBuffer(buf);
        PB.writeInt(x);
        PB.writeShort(y);
        PB.writeInt(z);
        PB.writeInt(id);
        PB.writeInt(data);
    }

    @Override
    public IMessage onMessage(MessageGuiClick message, MessageContext ctx) {
        TileEntity tileEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
        if (tileEntity instanceof IGuiListener) {
            ((IGuiListener) tileEntity).onMessageReceive(message.id, message.data);
        }
        return null;
    }

}
