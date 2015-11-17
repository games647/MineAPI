package com.w67clement.mineapi.message;

import com.w67clement.mineapi.nms.PacketSender;

/**
 * Displays message in the action bar message of the player!
 * 
 * @author w67clement
 *
 */
public interface ActionBarMessage extends PacketSender
{

	/**
	 * Define the message will be displayed in the action bar.
	 * 
	 * @param actionBarMessage
	 *            An sample text.
	 */
	public ActionBarMessage setMessage(String actionBarMessage);

	/**
	 * Gets the message will be displayed in the action bar.
	 * 
	 * @return The message.
	 */
	public String getMessage();

	/**
	 * To the JSON
	 * 
	 * @return JSON String.
	 */
	public String toJSONString();

}
