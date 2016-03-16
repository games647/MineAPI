package com.w67clement.mineapi.nms.reflection.packets.status;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI.*;
import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.packets.status.PacketStatusOutServerInfo;
import java.lang.reflect.Field;


import static com.w67clement.mineapi.api.ReflectionAPI.*;

public class CraftPacketStatusOutServerInfo extends PacketStatusOutServerInfo<Object>
{
    private static final Class<?> packetClass;
    private static final Field serverPingField;

    static
    {
        packetClass = getNmsClass("PacketStatusOutServerInfo");
        serverPingField = getFirstFieldOfType(packetClass, getNmsClass("ServerPing"), true);
    }

    public CraftPacketStatusOutServerInfo(Object packet)
    {
        super(packet);
    }

    public CraftPacketStatusOutServerInfo(ServerPingWrapper ping)
    {
        super(null);
        this.packet = SunUnsafe.newInstance(packetClass);
        setServerPing(ping);
    }

    @Override
    public ServerPingWrapper getServerPing()
    {
        return MineAPI.getNmsManager().getServerPingWrapper(getValue(packet, serverPingField));
    }

    @Override
    public void setServerPing(ServerPingWrapper ping)
    {
        setValue(packetClass, serverPingField, ping.toServerPing());
    }
}
