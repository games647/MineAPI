package com.w67clement.mineapi.message;

import com.w67clement.mineapi.nms.PacketSender;

/**
 * Displays message in the action bar message of the player!
 * 
 * @author w67clement
 *
 */
public abstract class ActionBarMessage extends PacketSender
{

	protected String message;

	public ActionBarMessage(String message) {
		this.setMessage(message);
	}

	/**
	 * Define the message will be displayed in the action bar.
	 * 
	 * @param actionBarMessage
	 *            An sample text.
	 */
	public ActionBarMessage setMessage(String actionBarMessage)
	{
		if (actionBarMessage != null) this.message = actionBarMessage;
		else
			this.message = "";
		return this;
	}

	/**
	 * Gets the message will be displayed in the action bar.
	 * 
	 * @return The message.
	 */
	public String getMessage()
	{
		return this.message;
	}

}
