package com.cout970.magneticraft.messages;

import com.cout970.magneticraft.util.IClientInformer;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class MessageClientStream implements IMessage, IMessageHandler<MessageClientStream, IMessage> {

    public int x, y, z;
    public NBTTagCompound nbt;

    public MessageClientStream() {
    }

    public MessageClientStream(IClientInformer t) {
        x = t.getParent().xCoord;
        y = t.getParent().yCoord;
        z = t.getParent().zCoord;
        nbt = new NBTTagCompound();
        t.saveInfoToMessage(nbt);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer PB = new PacketBuffer(buf);
        x = PB.readInt();
        y = PB.readShort();
        z = PB.readInt();
        try {
            nbt = PB.readNBTTagCompoundFromBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer PB = new PacketBuffer(buf);
        PB.writeInt(x);
        PB.writeShort(y);
        PB.writeInt(z);
        try {
            PB.writeNBTTagCompoundToBuffer(nbt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IMessage onMessage(MessageClientStream message, MessageContext ctx) {
        TileEntity tileEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
        if (tileEntity instanceof IClientInformer) {
            ((IClientInformer) tileEntity).loadInfoFromMessage(message.nbt);
        }
        return null;
    }

}
