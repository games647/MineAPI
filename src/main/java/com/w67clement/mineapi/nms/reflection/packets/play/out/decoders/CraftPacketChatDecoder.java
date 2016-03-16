package com.w67clement.mineapi.nms.reflection.packets.play.out.decoders;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.message.PacketChat;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.nms.reflection.packets.play.out.CraftPacketChat;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 06/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketChatDecoder implements IndividualPacketDecoder<PacketChat<Object>>
{
    @Override
    public PacketChat<Object> decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketPlayOutChat.getPacketName()) || PacketList.PacketPlayOutChat.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            PacketChat<Object> minePacket;
            if (MineAPI.isGlowstone())
            {
                minePacket = MineAPI.getNmsManager().getPacketChat("");
                minePacket.setContent(this.decodeGlowstoneTextMessage(getValue(packet, getField(packet.getClass(), "text", true))));
                minePacket.setData(getValueWithType(packet, getField(packet.getClass(), "mode", true), byte.class));
            }
            else
            {
                minePacket = new CraftPacketChat(packet);
            }
            return minePacket;
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }

    private String decodeGlowstoneTextMessage(Object obj)
    {
        return invokeMethodWithType(obj, getMethod(obj, "encode"), String.class);
    }
}
