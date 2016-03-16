package com.w67clement.mineapi.nms.reflection.packets.handshake;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.packets.ProtocolState;
import com.w67clement.mineapi.packets.handshake.PacketHandshake;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 06/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketHandshakeDecoder implements IndividualPacketDecoder<PacketHandshake>
{
    @Override
    public PacketHandshake decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.Handshake.getPacketName()) || PacketList.Handshake.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            PacketHandshake minePacket;
            if (MineAPI.isGlowstone())
            {
                int protocol = ReflectionAPI.getIntValue(packet, ReflectionAPI.getField(packet.getClass(), "version", true));
                String address = ReflectionAPI.getValueWithType(packet, ReflectionAPI.getField(packet.getClass(), "address", true), String.class);
                int port = ReflectionAPI.getIntValue(packet, ReflectionAPI.getField(packet.getClass(), "port", true));
                int nextProtocolState = ReflectionAPI.getIntValue(packet, ReflectionAPI.getField(packet.getClass(), "state", true));
                minePacket = MineAPI.getNmsManager().getPacketHandshake(protocol, address, port, ProtocolState.getById(nextProtocolState));
            }
            else
            {
                int protocol = ReflectionAPI.getIntValue(packet, ReflectionAPI.getField(packet.getClass(), "a", true));
                String hostname = ReflectionAPI.getValueWithType(packet, ReflectionAPI.getField(packet.getClass(), "hostname", true), String.class);
                int port = ReflectionAPI.getIntValue(packet, ReflectionAPI.getField(packet.getClass(), "port", true));
                Object protocolState = ReflectionAPI.getValue(packet, ReflectionAPI.getField(packet.getClass(), "d", true));
                int nextProtocolState = invokeMethodWithType(protocolState, ReflectionAPI.getMethod(protocolState, "a"), int.class);
                minePacket = MineAPI.getNmsManager().getPacketHandshake(protocol, hostname, port, ProtocolState.getById(nextProtocolState));
            }
            return minePacket;
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
