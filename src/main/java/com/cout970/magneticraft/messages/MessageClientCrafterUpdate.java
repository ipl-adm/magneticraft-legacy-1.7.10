package com.cout970.magneticraft.messages;

import com.cout970.magneticraft.tileentity.TileCrafter;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

import java.io.IOException;

public class MessageClientCrafterUpdate implements IMessage, IMessageHandler<MessageClientCrafterUpdate, IMessage> {

    public int x, y, z;
    public int slot;
    public NBTTagCompound nbt;

    public MessageClientCrafterUpdate() {
    }

    public MessageClientCrafterUpdate(TileEntity t, int slot, ItemStack item) {
        x = t.xCoord;
        y = t.yCoord;
        z = t.zCoord;
        this.slot = slot;
        nbt = new NBTTagCompound();
        if (item != null)
            item.writeToNBT(nbt);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        PacketBuffer PB = new PacketBuffer(buf);
        x = PB.readInt();
        y = PB.readShort();
        z = PB.readInt();
        slot = PB.readInt();
        try {
            nbt = PB.readNBTTagCompoundFromBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        PacketBuffer PB = new PacketBuffer(buf);
        PB.writeInt(x);
        PB.writeShort(y);
        PB.writeInt(z);
        PB.writeInt(slot);
        try {
            PB.writeNBTTagCompoundToBuffer(nbt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IMessage onMessage(MessageClientCrafterUpdate message, MessageContext ctx) {
        TileEntity tileEntity = ctx.getServerHandler().playerEntity.worldObj.getTileEntity(message.x, message.y, message.z);
        if (tileEntity instanceof TileCrafter) {
            ItemStack item = ItemStack.loadItemStackFromNBT(message.nbt);
            ((TileCrafter) tileEntity).getRecipe().setInventorySlotContents(message.slot, item);
            ((TileCrafter) tileEntity).refreshItemMatches();
        }
        return null;
    }

}
