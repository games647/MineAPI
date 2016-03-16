package com.w67clement.mineapi.packets.play.in;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.NmsPacket;

/**
 * Packet sent by the client with a text for the chat or process commands. <br>
 * <a href="http://wiki.vg/Protocol#Chat_Message_2">Wiki.vg PacketPlayInChat</a>
 *
 * @author w67clement
 */
public abstract class PacketPlayInChat<T> extends NmsPacket<T>
{
    public PacketPlayInChat(T packet)
    {
        super(packet);
    }

    /**
     * Client sent raw input.
     *
     * @return The client sends the raw input, not ChatComponent.
     */
    public abstract String getMessage();

    /**
     * Sets the client sent raw input.
     *
     * @param msg The new raw input.
     */
    public abstract void setMessage(String msg);

    @Override
    public PacketType getPacketType()
    {
        return PacketType.PACKETPLAYIN;
    }

}
