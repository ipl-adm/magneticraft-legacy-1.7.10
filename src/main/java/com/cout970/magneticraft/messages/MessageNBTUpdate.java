package com.cout970.magneticraft.messages;

import com.cout970.magneticraft.util.ITileHandlerNBT;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class MessageNBTUpdate implements IMessage, IMessageHandler<MessageNBTUpdate, IMessage> {

    public int x, y, z;
    public NBTTagCompound nbt;

    public MessageNBTUpdate() {
    }

    public MessageNBTUpdate(TileEntity t, NBTTagCompound nbt) {
        x = t.xCoord;
        y = t.yCoord;
        z = t.zCoord;
        this.nbt = nbt;
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
            nbt = new NBTTagCompound();
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
    public IMessage onMessage(MessageNBTUpdate message, MessageContext ctx) {
        TileEntity tileEntity = null;
        if (FMLClientHandler.instance().getClient().theWorld != null)
            tileEntity = FMLClientHandler.instance().getClient().theWorld.getTileEntity(message.x, message.y, message.z);
        if (tileEntity instanceof ITileHandlerNBT) {
            ((ITileHandlerNBT) tileEntity).loadInClient(message.nbt);
        }
        return null;
    }

}
