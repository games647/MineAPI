package com.w67clement.mineapi.system.messaging;

import java.util.HashMap;
import java.util.Map.Entry;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

public class PacketRegistry
{

    private static final PacketRegistry instance = new PacketRegistry();

    private HashMap<String, HashMap<Integer, Class<? extends MessagingPacket>>> packets = new HashMap<String, HashMap<Integer, Class<? extends MessagingPacket>>>();

    private PacketRegistry()
    {
    }

    public void registerPlugin(Plugin plugin)
    {
        this.registerPlugin(plugin.getName());
    }

    public void registerPlugin(String plugin)
    {
        Validate.notNull(plugin);
        if (this.packets.containsKey(plugin))
            throw new IllegalArgumentException("Plugin '" + plugin + "' is already register in the PacketRegistry.");
        this.packets.put(plugin, new HashMap<>());
    }

    /**
     * Registers the messaging packet of your plugin.
     *
     * @param plugin   Plugin's name.
     * @param packetId The id of your messaging packet.
     * @param packet   The class of your messaging packet.
     *
     * @see PacketRegistry#registerPacket(String, int, Class)
     */
    public void registerPacket(Plugin plugin, int packetId, Class<? extends MessagingPacket> packet)
    {
        Validate.notNull(plugin, "Plugin cannot be null.");
        this.registerPacket(plugin.getName(), packetId, packet);
    }

    /**
     * Registers the messaging packet of your plugin.
     *
     * @param plugin   Plugin's name.
     * @param packetId The id of your messaging packet.
     * @param packet   The class of your messaging packet.
     */
    public void registerPacket(String plugin, int packetId, Class<? extends MessagingPacket> packet)
    {
        Validate.notNull(plugin, "Plugin's name cannot be null.");
        Validate.notNull(packet, "Packet class cannot be null.");
        if (!this.packets.containsKey(plugin))
            this.registerPlugin(plugin);
        HashMap<Integer, Class<? extends MessagingPacket>> packetsOfPlugin = this.packets.get(plugin);
        if (packetsOfPlugin.containsKey(packetId))
            throw new IllegalArgumentException("Packet id '" + packetId + "' is already assigned to packet '" + packetsOfPlugin.get(packetId).getSimpleName() + "'.");
        packetsOfPlugin.put(packetId, packet);
        this.packets.put(plugin, packetsOfPlugin);
    }

    /**
     * Gets packet class by Id.
     *
     * @param plugin   Instance of the plugin who registered the packet.
     * @param packetId Id of the messaging packet.
     *
     * @return Packet Class if found else throw a RuntimeException.
     */
    public Class<? extends MessagingPacket> getPacketById(Plugin plugin, int packetId)
    {
        return this.getPacketById(plugin.getName(), packetId);
    }

    /**
     * Gets messaging packet class by Id.
     *
     * @param plugin   Name of the plugin who registered the packet.
     * @param packetId Id of the messaging packet.
     *
     * @return Packet Class if found else throw a RuntimeException.
     */
    public Class<? extends MessagingPacket> getPacketById(String plugin, int packetId)
    {
        if (this.hasPlugin(plugin))
        {
            HashMap<Integer, Class<? extends MessagingPacket>> packets = this.packets.get(plugin);
            if (packets.containsKey(packetId))
                return packets.get(packetId);
            else
                throw new RuntimeException("Unknown id '" + packetId + "'.");
        }
        else
            throw new RuntimeException("Cannot found packets with the plugin's name given.");
    }

    /**
     * Gets Id by messaging packet class
     *
     * @param plugin Name of the plugin who registered the packet.
     * @param packet Class of the messaging packet.
     *
     * @return Packet Id if found else throw a RuntimeException.
     */
    public int getIdByPacket(String plugin, Class<? extends MessagingPacket> packet)
    {
        if (this.hasPlugin(plugin))
        {
            HashMap<Integer, Class<? extends MessagingPacket>> packets = this.packets.get(plugin);
            if (packets.containsValue(packet))
            {
                for (Entry<Integer, Class<? extends MessagingPacket>> entry : packets.entrySet())
                {
                    if (entry.getValue() == packet)
                        return entry.getKey();
                }
                return 0;
            }
            else
                throw new RuntimeException("Unknown packet '" + packet.getSimpleName() + "'.");
        }
        else
            throw new RuntimeException("Cannot found packets with the plugin's name given.");
    }

    /**
     * Check if plugin is registered.
     *
     * @param plugin Name of the plugin.
     *
     * @return Plugin is registered or not.
     */
    public boolean hasPlugin(String plugin)
    {
        return this.packets.containsKey(plugin);
    }

    /**
     * Gets the unique instance of the PacketRegistry.
     *
     * @return The unique instance.
     */
    public static PacketRegistry getInstance()
    {
        return instance;
    }
}
