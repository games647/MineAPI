package com.w67clement.mineapi.system.messaging;

import com.w67clement.mineapi.MineAPI;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;

/**
 * Created by w67clement on 20/01/2016. <br><br/>
 * <p>
 * Class of project: MineAPI
 */
public class MessagingManager
{
    private final MineAPI plugin;
    private boolean isInit = false;
    private PacketRegistry packetRegistry = PacketRegistry.getInstance();

    public MessagingManager(MineAPI plugin)
    {
        this.plugin = plugin;
    }

    public PacketRegistry getPacketRegistry()
    {
        return this.packetRegistry;
    }

    public void init()
    {
        if (!isInit)
        {
            Bukkit.getMessenger().registerOutgoingPluginChannel(this.plugin, "MineMessaging");
            Bukkit.getMessenger().registerIncomingPluginChannel(this.plugin, "MineMessaging", new MessagingListener(this));
        }
        else
            throw new UnsupportedOperationException("MessagingManager is already initialized.");
    }

    public void sendPacket(String plugin, MessagingPacket packet)
    {
        Validate.notNull(plugin, "Plugin cannot be null.");
        Validate.notNull(packet, "Packet cannot be null.");
        if (plugin.length() > 64)
            plugin = plugin.substring(0, 64);
        PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        int id = packetRegistry.getIdByPacket(plugin, packet.getClass());
        buffer.writeString(plugin);
        buffer.writeVarIntToBuffer(id);
        packet.encode(buffer);
        // To bytes method from http://stackoverflow.com/questions/19296386/netty-java-getting-data-from-bytebuf
        byte[] bytes;
        int length = buffer.readableBytes();
        if (buffer.hasArray()) {
            bytes = buffer.array();
        } else {
            bytes = new byte[length];
            buffer.getBytes(buffer.readerIndex(), bytes);
        }
        Bukkit.getServer().sendPluginMessage(this.plugin, "MineMessaging", bytes);
    }

}
