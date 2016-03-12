package com.w67clement.mineapi.nms.reflection.packets.status;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.packets.status.PacketStatusOutPong;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 06/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketStatusOutPongDecoder implements IndividualPacketDecoder<PacketStatusOutPong>
{
    @Override
    public PacketStatusOutPong decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketStatusOutPong.getPacketName()) || PacketList.PacketStatusOutPong.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            PacketStatusOutPong minePacket;
            if (MineAPI.isGlowstone())
            {
                long pong = getValueWithType(packet, getField(packet.getClass(), "time", true), long.class);
                minePacket = MineAPI.getNmsManager().getPacketStatusOutPong(pong);
            }
            else
            {
                long pong = getValueWithType(packet, getField(packet.getClass(), "a", true), long.class);
                minePacket = MineAPI.getNmsManager().getPacketStatusOutPong(pong);
            }
            return minePacket;
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
