package com.w67clement.mineapi.enums;

public enum PacketType
{

    PACKETPLAYOUT("PacketPlayOut"),
    PACKETPLAYIN("PacketPlayIn"),
    PACKETSTATUS("PacketStatus"),
    HANDSHAKE("Handshake");

    private String a;

    PacketType(String param_String)
    {
        this.a = param_String;
    }

    public String getPacketTypeName()
    {
        return this.a;
    }

    public String toString()
    {
        return this.a;
    }
}
