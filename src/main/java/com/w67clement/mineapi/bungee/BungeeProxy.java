package com.w67clement.mineapi.bungee;

import com.w67clement.mineapi.system.messaging.defaults.BungeeServerDataPacket;
import java.util.HashMap;
import java.util.function.BiConsumer;

/**
 * Created by w67clement on 19/04/2016.
 * <p>
 * Class of project: MineAPI
 */
public class BungeeProxy
{
    private static BungeeProxy instance;
    private HashMap<String, BungeeServer> servers = new HashMap<>();
    private boolean useBungeecord = false;

    private BungeeProxy(boolean useBungeecord)
    {
        instance = this;
        this.useBungeecord = useBungeecord;
    }

    /**
     * Gets the instance of BungeeProxy.
     *
     * @return The instance of BungeeProxy.
     */
    public static BungeeProxy getInstance()
    {
        return instance;
    }

    /**
     * Initialize BungeeProxy.
     * <p>This method can be call only 1 time.</p>
     */
    public static void init(boolean useBungeecord)
    {
        if (getInstance() != null)
            throw new IllegalArgumentException("The instance of BungeeProxy is already defined.");
        new BungeeProxy(useBungeecord);
    }

    /**
     * Check whether the server with the name is exist.
     *
     * @param name Name of the server.
     *
     * @return Whether the server exists or not.
     */
    public boolean hasServer(String name)
    {
        return useBungeecord() && servers.containsKey(name);
    }

    /**
     * Gets the Server Data by name.
     * <p>Whether the server don't exists, returns null.</p>
     *
     * @param name Name of the server.
     *
     * @return The Server's Data.
     */
    public BungeeServer getServer(String name)
    {
        if ((!hasServer(name)) || (!useBungeecord()))
            return null;
        return servers.get(name);
    }

    /**
     * Put a new server with the data packet.
     *
     * @param packet Packet for data of the new server.
     */
    public void put(BungeeServerDataPacket packet)
    {
        if (!useBungeecord())
            throw new IllegalArgumentException("BungeeCord support is disabled.");
        if (packet == null)
            throw new IllegalArgumentException("Impossible to put server with a null packet.");
        String name = packet.getName();
        if (hasServer(name))
            getServer(name).update(packet);
        else
            servers.put(name, new BungeeServer(packet));
    }

    /**
     * List all servers with Java 8 lambda.
     *
     * @param action Lambda expression, it's the action.
     */
    public void forEachServers(BiConsumer<String, BungeeServer> action)
    {
        if (!useBungeecord())
            throw new IllegalArgumentException("BungeeCord support is disabled.");
        servers.forEach(action);
    }

    /**
     * Gets the number of BungeeCord's servers.
     *
     * @return Number of servers.
     */
    public int getNumberOfServers()
    {
        return servers.size();
    }

    /**
     * Check whether Spigot enable BungeeCord or not.
     * <p>If Spigot disable BungeeCord, some methods in BungeeProxy return null.</p>
     *
     * @return Whether Spigot enable BungeeCord or not.
     */
    public boolean useBungeecord()
    {
        return useBungeecord;
    }
}
