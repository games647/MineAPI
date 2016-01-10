package com.w67clement.mineapi.message;

import org.bukkit.entity.Player;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;

/**
 * Displays message in the action bar message of the player!
 * 
 * @author w67clement
 *
 */
public class ActionBarMessage extends PacketSender
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

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

	@Override
	public void send(Player player)
	{
		MineAPI.getNmsManager().getPacketChat("{text:\"" + this.message + "\"}",
				(byte) 2).send(player);
	}

	@Override
	public Object constructPacket()
	{
		return MineAPI.getNmsManager().getPacketChat("{text:\"" + this.message + "\"}",
				(byte) 2).constructPacket();
	}

}
