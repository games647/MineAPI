package com.w67clement.mineapi.nms.reflection.packets.play.in;

import com.w67clement.mineapi.packets.play.in.PacketPlayInChat;
import java.lang.reflect.Field;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

public class CraftPacketPlayInChat extends PacketPlayInChat<Object>
{
    private static final Class<?> packetClass;
    private static final Field messageField;

    static
    {
        packetClass = getNmsClass("PacketPlayInChat");
        messageField = getFirstFieldOfType(packetClass, String.class, true);
    }

    public CraftPacketPlayInChat(Object packet)
    {
        super(packet);
    }

    public CraftPacketPlayInChat(String msg)
    {
        super(SunUnsafe.newInstance(packetClass));
        setMessage(msg);
    }

    @Override
    public String getMessage()
    {
        return getStringValue(packet, messageField);
    }

    @Override
    public void setMessage(String msg)
    {
        setValue(packet, messageField, msg);
    }
}
