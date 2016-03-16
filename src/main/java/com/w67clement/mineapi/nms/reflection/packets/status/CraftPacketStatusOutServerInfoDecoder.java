package com.w67clement.mineapi.nms.reflection.packets.status;

import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.packets.status.PacketStatusOutServerInfo;

/**
 * Created by w67clement on 06/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketStatusOutServerInfoDecoder implements IndividualPacketDecoder<PacketStatusOutServerInfo<Object>>
{
    @Override
    public PacketStatusOutServerInfo<Object> decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketStatusOutServerInfo.getPacketName()) || PacketList.PacketStatusOutServerInfo.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            return new CraftPacketStatusOutServerInfo(packet);
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
