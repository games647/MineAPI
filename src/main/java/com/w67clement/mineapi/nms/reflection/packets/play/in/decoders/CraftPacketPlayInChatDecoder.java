package com.w67clement.mineapi.nms.reflection.packets.play.in.decoders;

import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.nms.reflection.packets.play.in.CraftPacketPlayInChat;
import com.w67clement.mineapi.packets.play.in.PacketPlayInChat;

/**
 * Created by w67clement on 06/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketPlayInChatDecoder implements IndividualPacketDecoder<PacketPlayInChat<Object>>
{
    @Override
    public PacketPlayInChat<Object> decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketPlayInChat.getPacketName()) || PacketList.PacketPlayInChat.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            return new CraftPacketPlayInChat(packet);
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
