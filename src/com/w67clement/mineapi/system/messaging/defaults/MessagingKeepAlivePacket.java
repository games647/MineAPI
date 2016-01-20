package com.w67clement.mineapi.system.messaging.defaults;

import com.w67clement.mineapi.system.messaging.MessagingPacket;
import com.w67clement.mineapi.system.messaging.PacketBuffer;

public class MessagingKeepAlivePacket extends MessagingPacket
{
    private String identifier;

    public MessagingKeepAlivePacket()
    {
    }

    public MessagingKeepAlivePacket(String identifier)
    {
        this.identifier = identifier;
    }

    @Override
    public void encode(PacketBuffer buffer)
    {
        buffer.writeString(this.identifier);
    }

    @Override
    public void decode(PacketBuffer buffer)
    {
        this.identifier = buffer.readStringFromBuffer(256);
    }

    @Override
    public void handle()
    {

    }

}
