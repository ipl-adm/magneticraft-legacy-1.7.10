package com.cout970.magneticraft.messages;

import com.cout970.magneticraft.container.ContainerShelvingUnit;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.inventory.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

import java.io.IOException;

public class MessageShelfSlotUpdate implements IMessage, IMessageHandler<MessageShelfSlotUpdate, IMessage> {
    private int curInv;
    private int scroll;
    private String filter;
    private int level;
    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer PB = new PacketBuffer(buf);
        curInv = PB.readInt();
        scroll = PB.readInt();
        level = PB.readInt();
        int length = PB.readInt();
        try {
            filter = PB.readStringFromBuffer(length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer PB = new PacketBuffer(buf);
        PB.writeInt(curInv);
        PB.writeInt(scroll);
        PB.writeInt(level);
        PB.writeInt(filter.length());
        try {
            PB.writeStringToBuffer(filter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MessageShelfSlotUpdate(int curInv, int scroll, String filter, int level) {
        this.curInv = curInv;
        this.scroll = scroll;
        this.filter = filter;
        this.level = level;
    }

    public MessageShelfSlotUpdate() {}

    @Override
    public IMessage onMessage(MessageShelfSlotUpdate message, MessageContext ctx) {
        Container container = ctx.getServerHandler().playerEntity.openContainer;
        if (container instanceof ContainerShelvingUnit) {
            ((ContainerShelvingUnit) container).adjustSlots(message.curInv, message.scroll, message.filter, message.level);
        }
        return null;
    }
}
