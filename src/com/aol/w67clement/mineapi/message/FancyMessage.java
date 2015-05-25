package com.aol.w67clement.mineapi.message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public interface FancyMessage {
	
	/**
	 *  Set the Color of the text.
	 * @param color The Color of the text.
	 */
	public FancyMessage color(final ChatColor color);

	/**
	 *  Set the Style of the text.
	 * @param styles The Style of the text.
	 */
	public FancyMessage style(final ChatColor... styles);
	
	/**
	 *  Gets the File.
	 * @param path ???
	 */
	public FancyMessage getFile(final String path);
	
	/**
	 *  Gets an URL with the text.
	 * @param url URL of the webSite
	 */
	public FancyMessage addLink(final String url);
	
	/**
	 *  Suggest an command with the text.
	 * @param command The command.
	 */
	public FancyMessage suggestCommand(final String command);
	
	/**
	 *  Run an command with the text.
	 * @param runCommand The command.
	 */
	public FancyMessage runCommand(final String runCommand);
	
	/**
	 *  Send an hover Message with the text.
	 * @param text The text in a hover Message.
	 */
	public FancyMessage addHoverMessage(final String text);

	/**
	 *  Add an text.
	 */
	public FancyMessage then(final Object obj);
	
	/**
	 *  To the JSON
	 * @return JSON String.
	 */
	public String toJSONString();

	/**
	 *  Sends a message with a NMS class.
	 * @param player Represents the player.
	 */
	public void send(Player player);
}
