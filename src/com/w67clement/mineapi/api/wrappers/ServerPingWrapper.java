package com.w67clement.mineapi.api.wrappers;

import java.util.List;

import org.bukkit.OfflinePlayer;

/**
 *  Use and change the ServerPing!
 * @author 67clement
 *
 */
public interface ServerPingWrapper {
	
	/**
	 *  Gets the Motd in the ServerPing!
	 * @return Motd
	 */
	public String getMotd();
	
	/**
	 *  Gets the Motd in the ServerPing!
	 * @return An object, who represent the ChatComponent motd
	 */
	public Object getChatComponentMotd();
	
	/**
	 *  Change the motd.
	 * @param obj The ChatComponent object value
	 */
	public void setMotd(Object obj);
	
	/**
	 *  Gets the version's name in the ServerPingData!
	 * @return The version's name
	 */
	public String getVersionName();
	
	/**
	 * Change the version name!
	 * @param version The Version name value
	 */
	public void setVersionName(String version);
	
	/**
	 *  Gets the protocol in the ServerPingData!
	 * @return The protocol version!
	 */
	public int getProtocolVersion();
	
	/**
	 *  Change the protocol version!
	 * @param protocol Version of Protocol
	 */
	public void setProtocolVersion(int protocol);
	
	/**
	 *  Gets the number of online players!
	 * @return Number of Online Players present in the ServerPing.
	 */
	public int getOnlinesPlayers();
	
	/**
	 *  Change the number of online players!
	 * @param onlines Number of Online Players
	 */
	public void setOnlinesPlayers(int onlines);
	
	/**
	 *  Gets the number of maximum players!
	 * @return Number of maximum players present in ServerPing
	 */
	public int getMaximumPlayers();
	
	/**
	 *  Change the number of maximum players!
	 * @param max Number
	 */
	public void setMaximumPlayers(int max);
	
	/**
	 *  Gets the player list!
	 * @return The list in ServerPing
	 */
	public List<OfflinePlayer> getPlayerList();
	
	/**
	 * Change the player list!
	 * @param players The list of Offline players!
	 */
	public void setPlayerList(List<OfflinePlayer> players);
	
	public Object toServerPing();
}
