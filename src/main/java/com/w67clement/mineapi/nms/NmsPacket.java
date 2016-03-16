package com.w67clement.mineapi.nms;

import com.w67clement.mineapi.enums.PacketType;

public abstract class NmsPacket<T>
{
    protected T packet;

    public NmsPacket(T packet)
    {
        this.packet = packet;
    }

    /**
     * Construct packet Object, it was sent to player.
     *
     * @return Nms' Packet Object.
     */
    @Deprecated
    public T constructPacket()
    {
        return this.getHandle();
    }

    public abstract PacketType getPacketType();

    /**
     * Gets the Nms' Packet Instance.
     *
     * @return Instance of the Nms' Packet.
     */
    public T getHandle()
    {
        return packet;
    }

}

// End of NmsPacket interface