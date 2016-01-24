package com.w67clement.mineapi.system.messaging.pipeline;

import com.w67clement.mineapi.system.messaging.MessagingPacket;
import com.w67clement.mineapi.system.messaging.PacketBuffer;
import com.w67clement.mineapi.system.messaging.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Utilisateur on 20/01/2016.
 */
public class MineEncoder extends MessageToByteEncoder<MessagingPacket>
{

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessagingPacket messagingPacket, ByteBuf byteBuf) throws Exception
    {
        PacketBuffer buffer = new PacketBuffer(byteBuf);
        int id = PacketRegistry.getInstance().getIdByPacket(messagingPacket.getSender(), messagingPacket.getClass());
        buffer.writeString(messagingPacket.getSender());
        buffer.writeVarIntToBuffer(id);
        messagingPacket.encode(buffer);
    }
}
