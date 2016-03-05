package com.w67clement.mineapi.system.messaging;

/**
 * MessagingPacket is the base for packets of MineAPI's plugin messaging system.
 *
 * @author w67clement
 * @version Dev Build MineAPI 2.2.0-pre1
 */
public abstract class MessagingPacket
{
    /**
     */
    public MessagingPacket()
    {

    }

    /**
     * Encode the packet to bytes.
     *
     * @param buffer ByteBuf modified for more functions.
     */
    public abstract void encode(PacketBuffer buffer);

    /**
     * Decode the packet from bytes.
     *
     * @param buffer ByteBuf modified for more functions.
     */
    public abstract void decode(PacketBuffer buffer);

    /**
     * Use functions when the packet is received.
     */
    public abstract void handle();
}
