package com.w67clement.mineapi.bungee;

import com.w67clement.mineapi.system.messaging.defaults.BungeeServerDataPacket;

/**
 * Created by w67clement on 19/04/2016.
 * <p>
 * Class of project: MineAPI
 */
public class BungeeServer
{

    private String name;
    private String version;
    private int protocol;
    private int onlineCount;
    private int maxPlayersCount;
    private String[] players;

    public BungeeServer(BungeeServerDataPacket packet)
    {
        name = packet.getName();
        version = packet.getVersion();
        protocol = packet.getProtocol();
        onlineCount = packet.getOnlineCount();
        maxPlayersCount = packet.getMaxPlayersCount();
        players = packet.getPlayers();
    }

    /**
     * Gets the list of connected players on the server.
     *
     * @return The list of connected players on the server.
     */
    public String[] getPlayers()
    {
        return players;
    }

    /**
     * Gets the name of the server.
     *
     * @return Name of the server.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the version of the server.
     *
     * @return Version of the server.
     */
    public String getVersion()
    {
        return version;
    }

    /**
     * Gets the Protocol of the server.
     *
     * @return Protocol of the server.
     */
    public int getProtocol()
    {
        return protocol;
    }

    /**
     * Gets the online count of the server.
     *
     * @return Online count of the server.
     */
    public int getOnlineCount()
    {
        return onlineCount;
    }

    /**
     * Gets the maximum online count of the server.
     *
     * @return Maximum online count of the server.
     */
    public int getMaxPlayersCount()
    {
        return maxPlayersCount;
    }

    /**
     * Update the data of the server with the packet.
     *
     * @param packet Packet for updating data.
     */
    public void update(BungeeServerDataPacket packet)
    {
        name = packet.getName();
        version = packet.getVersion();
        protocol = packet.getProtocol();
        onlineCount = packet.getOnlineCount();
        maxPlayersCount = packet.getMaxPlayersCount();
        players = packet.getPlayers();
    }
}
