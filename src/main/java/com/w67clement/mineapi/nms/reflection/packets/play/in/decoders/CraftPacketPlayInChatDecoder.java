package com.w67clement.mineapi.nms.reflection.packets.play.in.decoders;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.packets.play.in.PacketPlayInChat;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 06/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketPlayInChatDecoder implements IndividualPacketDecoder<PacketPlayInChat>
{
    @Override
    public PacketPlayInChat decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketPlayInChat.getPacketName()) || PacketList.PacketPlayInChat.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            PacketPlayInChat minePacket = MineAPI.getNmsManager().getPacketPlayInChat("");
            if (MineAPI.isGlowstone())
            {
                minePacket.setMessage(getValueWithType(packet, getField(packet.getClass(), "text", true), String.class));
            }
            else
            {
                minePacket.setMessage(getValueWithType(packet, getField(packet.getClass(), "a", true), String.class));
            }
            return minePacket;
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
