package com.w67clement.mineapi.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public abstract class PacketSender implements NmsPacket
{

    /**
     * Sends the packet of a player.
     *
     * @param player Represent player.
     */
    public abstract void send(Player player);

    /**
     * Send the packet of all players.
     */
    public void sendAll()
    {
        Bukkit.getOnlinePlayers().forEach(this::send);
    }

}
