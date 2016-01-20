package com.w67clement.mineapi.system.messaging;

import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

/**
 * MessagingPacket is the base for packets of MineAPI's plugin messaging system.
 *
 * @author w67clement
 * @version Dev Build MineAPI 2.2.0-pre1
 */
public abstract class MessagingPacket
{
    private String sender = null;

    /**
     * Use only if the packet is sent by BungeeCord.
     */
    public MessagingPacket()
    {

    }

    /**
     * Constructor used only if the packet is send to BungeeCord.
     *
     * @param plugin Your plugin instance.
     */
    public MessagingPacket(Plugin plugin)
    {
        this.sender = plugin.getName();
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

    /**
     * Set the sender of the packet used only whether the BungeeCord sent the
     * packet.
     *
     * @param sender The plugin's name
     */
    public void setSender(String sender)
    {
        Validate.notNull(sender);
        Validate.notEmpty(sender);
        if (this.sender == null)
            this.sender = sender;
        else if (this.sender.isEmpty())
            this.sender = sender;
        else
            throw new IllegalArgumentException("Sender is already set.");
    }

    /**
     * Gets the sender of the packet, it is the name of the plugin have sent it.
     *
     * @return The sender of messaging packet.
     */
    public String getSender()
    {
        return this.sender;
    }
}
