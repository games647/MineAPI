package com.w67clement.mineapi.api.wrappers;

import com.w67clement.mineapi.nms.NmsPacket;

/**
 * PacketWrapper for modify the packet before sending to client or handling by
 * server.
 *
 * @param <T> MineAPI packet.
 *
 * @author w67clement
 * @version MineAPI v2.2.0 (Event system v2)
 */
public class MC_PacketWrapper<T extends NmsPacket<?>>
{
    @Deprecated
    private PacketWrapper oldWrapper;
    private Object nms_packet;
    private T packet;

    public MC_PacketWrapper(T packet, Object nms_packet)
    {
        this.packet = packet;
        this.nms_packet = nms_packet;
        this.oldWrapper = new PacketWrapper(nms_packet);
    }

    /**
     * Gets the packet of MineAPI and modify values.
     *
     * @return MineAPI's packet.
     */
    public T getPacket()
    {
        return this.packet;
    }

    /**
     * Sets the packet of MineAPI and build the Nms' Packet.
     *
     * @param packet MineAPI's packet object.
     */
    public void setPacket(T packet)
    {
        this.packet = packet;
        this.buildNmsPacket();
    }

    /**
     * Gets the packet of Nms.
     *
     * @return Nms' packet.
     */
    public Object getNmsPacket()
    {
        return this.nms_packet;
    }

    /**
     * Gets the Old Wrapper (Before MineAPI v2.2.0)
     *
     * @return Old Wrapper
     */
    @Deprecated
    public PacketWrapper getOldWrapper()
    {
        return this.oldWrapper;
    }

    /**
     * Build the Nms' Packet with MineAPI's Packet.
     */
    @Deprecated
    public void buildNmsPacket()
    {
        this.nms_packet = this.packet.getHandle();
    }

    /**
     * Gets the Nms' packet name.
     *
     * @return Nms' packet name.
     */
    public String getPacketName()
    {
        return this.nms_packet.getClass().getSimpleName();
    }
}
