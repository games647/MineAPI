package com.w67clement.mineapi.system.messaging;

/**
 * Created by w67clement on 20/01/2016.
 */
public class MessagingManager
{
    private PacketRegistry packetRegistry = PacketRegistry.getInstance();

    public PacketRegistry getPacketRegistry()
    {
        return this.packetRegistry;
    }

}
