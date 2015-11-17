package com.w67clement.mineapi.nms;

import org.bukkit.entity.Player;

public interface PacketSender extends NmsPacket {

	/**
	 *  Sends the packet of a player.
	 * @param player Represent player.
	 */
	public void send(Player player);
	
	/**
	 *  Send the packet of all players.
	 */
	public void sendAll();
	
}
