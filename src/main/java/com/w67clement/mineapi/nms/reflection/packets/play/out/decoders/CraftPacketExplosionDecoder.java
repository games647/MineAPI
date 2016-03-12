package com.w67clement.mineapi.nms.reflection.packets.play.out.decoders;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.world.PacketExplosion;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 06/03/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketExplosionDecoder implements IndividualPacketDecoder<PacketExplosion>
{
    @Override
    public PacketExplosion decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketPlayOutExplosion.getPacketName()) || PacketList.PacketPlayOutExplosion.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            PacketExplosion minePacket;
            if (MineAPI.isGlowstone())
            {
                double x = getValueWithType(packet, getField(packet.getClass(), "x", true), double.class);
                double y = getValueWithType(packet, getField(packet.getClass(), "y", true), double.class);
                double z = getValueWithType(packet, getField(packet.getClass(), "z", true), double.class);
                float radius = getValueWithType(packet, getField(packet.getClass(), "radius", true), float.class);
                minePacket = MineAPI.getNmsManager().getExplosionPacket(null, x, y, z, radius, true);
            }
            else
            {
                double x = getValueWithType(packet, getField(packet.getClass(), "a", true), double.class);
                double y = getValueWithType(packet, getField(packet.getClass(), "b", true), double.class);
                double z = getValueWithType(packet, getField(packet.getClass(), "c", true), double.class);
                float radius = getValueWithType(packet, getField(packet.getClass(), "d", true), float.class);
                minePacket = MineAPI.getNmsManager().getExplosionPacket(null, x, y, z, radius, true);
            }
            return minePacket;
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }
}
