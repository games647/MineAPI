package com.w67clement.mineapi.nms.reflection.packets.handshake;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.packets.ProtocolState;
import com.w67clement.mineapi.packets.handshake.PacketHandshake;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class CraftPacketHandshake extends PacketHandshake<Object>
{
    private static Class<?> packetClass;
    private static Constructor<?> packetConstructor;
    private static Field protocolField;
    private static Field hostNameOrIPField;
    private static Field portField;
    private static Field nextProtocolTypeField;

    static
    {
        if (MineAPI.isGlowstone())
        {
            packetClass = ReflectionAPI.getClass("net.glowstone.net.message.handshake.HandshakeMessage");
            packetConstructor = ReflectionAPI.getConstructor(packetClass, int.class, String.class, int.class, int.class);
            protocolField = ReflectionAPI.getField(packetClass, "version", true);
            hostNameOrIPField = ReflectionAPI.getField(packetClass, "address", true);
            portField = ReflectionAPI.getField(packetClass, "port", true);
            nextProtocolTypeField = ReflectionAPI.getField(packetClass, "state", true);
        }
        else
        {
            packetClass = ReflectionAPI.getNmsClass("PacketHandshakingInSetProtocol");
            packetConstructor = ReflectionAPI.getConstructor(packetClass);
            protocolField = ReflectionAPI.getField(packetClass, "a", true);
            hostNameOrIPField = ReflectionAPI.getField(packetClass, "hostname", true);
            portField = ReflectionAPI.getField(packetClass, "port", true);
            nextProtocolTypeField = ReflectionAPI.getField(packetClass, "d", true);
        }
    }

    public CraftPacketHandshake(final int protocol, final String hostNameOrIP, final int port, final ProtocolState nextProtocolType)
    {
        super(protocol, hostNameOrIP, port, nextProtocolType);
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
        Object packet = ReflectionAPI.newInstance(packetConstructor);
        ReflectionAPI.setValue(packet, protocolField, this.protocol);
        ReflectionAPI.setValue(packet, hostNameOrIPField, this.hostNameOrIP);
        ReflectionAPI.setValue(packet, portField, this.port);
        Method toEnumProtocol = ReflectionAPI.getMethod(ReflectionAPI.getNmsClass("EnumProtocol"), "a", int.class);
        ReflectionAPI.setValue(packet, nextProtocolTypeField, ReflectionAPI.invokeMethod(null, toEnumProtocol, this.nextProtocolType.getProtocolId()));
        return packet;
    }

    private Object constructPacket_Glowstone()
    {
        return ReflectionAPI.newInstance(packetConstructor, this.protocol, this.hostNameOrIP, this.port, this.nextProtocolType.getProtocolId());
    }

}
