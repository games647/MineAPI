package com.w67clement.mineapi.nms.reflection.packets.play.out.decoders;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.api.wrappers.BlockPositionWrapper;
import com.w67clement.mineapi.api.wrappers.ChatComponentWrapper;
import com.w67clement.mineapi.enums.PacketList;
import com.w67clement.mineapi.nms.IndividualPacketDecoder;
import com.w67clement.mineapi.packets.play.out.PacketUpdateSign;
import java.lang.reflect.Array;
import org.bukkit.Location;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

/**
 * Created by w67clement on 06/03/2016.
 * <p>
 * Class of project: MineAPI
 */
public class CraftPacketUpdateSignDecoder implements IndividualPacketDecoder<PacketUpdateSign>
{
    @Override
    public PacketUpdateSign decode(Object packet)
    {
        if (packet.getClass().getSimpleName().equals(PacketList.PacketPlayOutUpdateSign.getPacketName()) || PacketList.PacketPlayOutUpdateSign.getPacketAliases().contains(packet.getClass().getSimpleName()))
        {
            PacketUpdateSign minePacket;
            if (MineAPI.isGlowstone())
            {
                int x = getIntValue(packet, getField(packet.getClass(), "x", true));
                int y = getIntValue(packet, getField(packet.getClass(), "y", true));
                int z = getIntValue(packet, getField(packet.getClass(), "z", true));
                Location location = new Location(null, x, y, z);
                Object lines = getValue(packet, getField(packet.getClass(), "message", true));
                String[] contents = new String[4];
                contents[0] = this.decodeGlowstoneTextMessage(Array.get(lines, 0));
                contents[1] = this.decodeGlowstoneTextMessage(Array.get(lines, 1));
                contents[2] = this.decodeGlowstoneTextMessage(Array.get(lines, 2));
                contents[3] = this.decodeGlowstoneTextMessage(Array.get(lines, 3));
                minePacket = MineAPI.getNmsManager().getPacketUpdateSign(location, contents);
            }
            else
            {
                Location location = new BlockPositionWrapper(getValue(packet, getField(packet.getClass(), "b", true))).toLocation(null);
                Object lines = getValue(packet, getField(packet.getClass(), "c", true));
                String[] contents = new String[4];
                contents[0] = ChatComponentWrapper.makeJsonByChatComponent(Array.get(lines, 0));
                contents[1] = ChatComponentWrapper.makeJsonByChatComponent(Array.get(lines, 1));
                contents[2] = ChatComponentWrapper.makeJsonByChatComponent(Array.get(lines, 2));
                contents[3] = ChatComponentWrapper.makeJsonByChatComponent(Array.get(lines, 3));
                minePacket = MineAPI.getNmsManager().getPacketUpdateSign(location, contents);
            }
            return minePacket;
        }
        else
            throw new RuntimeException("Invalid packet given.");
    }

    private String decodeGlowstoneTextMessage(Object obj)
    {
        return ReflectionAPI.invokeMethodWithType(obj, ReflectionAPI.getMethod(obj, "encode"), String.class);
    }
}
