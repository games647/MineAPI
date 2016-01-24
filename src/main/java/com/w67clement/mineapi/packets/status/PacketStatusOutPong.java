package com.w67clement.mineapi.packets.status;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.NmsPacket;

/**
 * The server will respond with the
 * <a href="http://wiki.vg/Protocol#Pong">Pong</a> packet and
 * then close the connection. <br>
 * <b>This packet is read only!</b>
 *
 * @author w67clement
 */
public abstract class PacketStatusOutPong implements NmsPacket
{

    protected final long pong;

    public PacketStatusOutPong(final long pong)
    {
        this.pong = pong;
    }

    /**
     * Should be the same as sent by the client.
     *
     * @return Same as sent by the client.
     */
    public final long getPong()
    {
        return this.pong;
    }

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETSTATUS;
    }

}
