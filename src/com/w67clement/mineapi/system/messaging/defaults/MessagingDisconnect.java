package com.w67clement.mineapi.system.messaging.defaults;

import com.w67clement.mineapi.system.messaging.MessagingPacket;
import com.w67clement.mineapi.system.messaging.PacketBuffer;

/**
 * Created by w67clement on 20/01/2016.
 */
public class MessagingDisconnect extends MessagingPacket
{
    private String reason = "default";

    public MessagingDisconnect()
    {
    }

    public MessagingDisconnect(String reason)
    {
        this.reason = reason;
    }

    @Override
    public void encode(PacketBuffer buffer)
    {
        if (reason.length() > 256)
            reason = reason.substring(0, 256);
        buffer.writeString(this.reason);
    }

    @Override
    public void decode(PacketBuffer buffer)
    {
        this.reason = buffer.readStringFromBuffer(256);
    }

    @Override
    public void handle()
    {

    }

    public String getReason()
    {
        return this.reason;
    }
}
