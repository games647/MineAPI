package com.w67clement.mineapi.system.messaging.defaults;

import com.w67clement.mineapi.chat.ChatComponent;
import com.w67clement.mineapi.system.messaging.MessagingPacket;
import com.w67clement.mineapi.system.messaging.PacketBuffer;
import java.util.UUID;
import org.bukkit.entity.Player;

/**
 * Created by w67clement on 06/04/2016.
 * <p>
 * Class of project: MineAPI
 */
public class SendMessagePacket extends MessagingPacket
{
    private UUID uuid;
    private ChatComponent component;
    private byte data;

    public SendMessagePacket()
    {
    }

    public SendMessagePacket(UUID uuid, ChatComponent component, byte data)
    {
        this.uuid = uuid;
        this.component = component;
        this.data = data;
    }

    public SendMessagePacket(UUID uuid, ChatComponent component)
    {
        this.uuid = uuid;
        this.component = component;
        data = 1;
    }

    /**
     * Gets the UUID of the player who received the message.
     *
     * @return UUID of the player.
     */
    public UUID getUuid()
    {
        return uuid;
    }

    /**
     * Sets the UUID of the player who received the message.
     *
     * @param uuid UUID of the player.
     */
    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    /**
     * Sets the UUID by the player who received the message.
     *
     * @param player Player who received the message.
     */
    @Deprecated
    public void setPlayer(Player player)
    {
        setUuid(player.getUniqueId());
    }

    /**
     * Gets the content of the message.
     *
     * @return Content.
     */
    public ChatComponent getComponent()
    {
        return component;
    }

    /**
     * Sets the content of the message.
     *
     * @param component Content.
     */
    public void setComponent(ChatComponent component)
    {
        this.component = component;
    }

    /**
     * Gets the data of the message.
     *
     * @return Data of the message.
     */
    public byte getData()
    {
        return data;
    }

    /**
     * Sets the data of the message.
     *
     * @param data Data of the message.
     */
    public void setData(byte data)
    {
        this.data = data;
    }

    @Override
    public void encode(PacketBuffer buffer)
    {
        buffer.writeUuid(uuid);
        buffer.writeChatComponent(component);
        buffer.writeByte(data);
    }

    @Override
    public void decode(PacketBuffer buffer)
    {
        uuid = buffer.readUuid();
        component = buffer.readChatComponent();
        data = buffer.readByte();
    }

    @Override
    public void handle()
    {
        throw new UnsupportedOperationException("SendMessagePacket must be handle by BungeeCord.");
    }
}
