package com.w67clement.mineapi.nms.reflection.packets.play.in;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.packets.play.in.PacketPlayInChat;
import java.lang.reflect.Constructor;

public class CraftPacketPlayInChat extends PacketPlayInChat
{
    private static Class<?> packetClass;
    private static Constructor<?> packetConstructor;

    static
    {
        if (MineAPI.isGlowstone())
        {
            packetClass = ReflectionAPI.getClass("net.glowstone.net.message.play.game.IncomingChatMessage");
        }
        else
        {
            packetClass = ReflectionAPI.getNmsClass("PacketPlayInChat");
        }
        packetConstructor = ReflectionAPI.getConstructor(packetClass, String.class);
    }

    public CraftPacketPlayInChat(String msg)
    {
        super(msg);
    }

    @Override
    public Object constructPacket()
    {
        return ReflectionAPI.newInstance(packetConstructor, this.msg);
    }

}
