package com.w67clement.mineapi.nms.reflection.packets.status;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.packets.status.PacketStatusOutServerInfo;

/**
 * Created by w67clement on 06/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketStatusOutServerInfoDecoder implements IndividualPacketDecoder<PacketStatusOutServerInfo>
{
    @Override
    public PacketStatusOutServerInfo decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketStatusOutServerInfo.getPacketName()) || PacketList.PacketStatusOutServerInfo.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            PacketStatusOutServerInfo minePacket = MineAPI.getNmsManager().getPacketStatusOutServerInfo(MineAPI.getNmsManager().getServerPingWrapper());
            if (MineAPI.isGlowstone())
            {
                minePacket.setServerPing(ServerPingWrapper.Serializer.jsonToServerPing(ReflectionAPI.getValueWithType(packet, ReflectionAPI.getField(packet.getClass(), "json", true), String.class)));
            }
            else
            {
                minePacket.setServerPing(MineAPI.getNmsManager().getServerPingWrapper(ReflectionAPI.getValue(packet, ReflectionAPI.getField(packet.getClass(), "b", true))));
            }
            return minePacket;
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
