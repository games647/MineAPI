package com.w67clement.mineapi.nms.reflection.packets.status;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.packets.status.PacketStatusOutPong;
import java.lang.reflect.Constructor;

public class CraftPacketStatusOutPong extends PacketStatusOutPong<Object>
{
    private static Class<?> packetClass;
    private static Constructor<?> packetConstructor;

    static
    {
        if (MineAPI.isGlowstone())
        {
            packetClass = ReflectionAPI.getClass("net.glowstone.net.message.status.StatusPingMessage");
            packetConstructor = ReflectionAPI.getConstructor(packetClass, long.class);
        }
        else
        {
            packetClass = ReflectionAPI.getNmsClass("PacketStatusOutPong");
            packetConstructor = ReflectionAPI.getConstructor(packetClass, long.class);
        }
    }

    public CraftPacketStatusOutPong(final long pong)
    {
        super(pong);
    }

    @Override
    public Object constructPacket()
    {
        if (MineAPI.isSpigot())
        {
            return this.constructPacket_Bukkit();
        }
        else if (MineAPI.isGlowstone())
        {
            return this.constructPacket_Glowstone();
        }
        return this.constructPacket_Bukkit();
    }

    @Override
    public Object getHandle()
    {
        return this.constructPacket();
    }

    private Object constructPacket_Bukkit()
    {
        return ReflectionAPI.newInstance(packetConstructor, this.pong);
    }

    private Object constructPacket_Glowstone()
    {
        return ReflectionAPI.newInstance(packetConstructor, this.pong);
    }

}
