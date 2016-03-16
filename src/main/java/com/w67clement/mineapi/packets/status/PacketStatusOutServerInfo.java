package com.w67clement.mineapi.packets.status;

import com.w67clement.mineapi.api.wrappers.ServerPingWrapper;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.NmsPacket;

/**
 * PacketStatusOutServerInfo is the packet send to client when the client ping
 * in the server list the server. <br>
 * <a href=http://wiki.vg/Protocol#Response>Wiki.vg PacketStatusOutServerInfo packet</a>
 *
 * @author w67clement
 * @version MineAPI v2.2.0
 */
public abstract class PacketStatusOutServerInfo<T> extends NmsPacket<T>
{
    public PacketStatusOutServerInfo(T packet)
    {
        super(packet);
    }

    /**
     * Gets the ServerPing of the packet. <br>
     * It contains the informations of the ping.
     *
     * @return ServerPingWrapper.
     */
    public abstract ServerPingWrapper getServerPing();

    /**
     * Sets the ServerPing of the packet. <br>
     * It contains the informations of the ping.
     *
     * @param ping ServerPingWrapper.
     */
    public abstract void setServerPing(ServerPingWrapper ping);

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETSTATUS;
    }

}
