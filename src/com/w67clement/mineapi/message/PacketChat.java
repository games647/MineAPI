package com.w67clement.mineapi.message;

import com.google.gson.JsonParser;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;

/**
 * Packet for chat message, used by ActionBarMessage and FancyMessage.
 * 
 * @author w67clement
 * @version MineAPI v2.2.0 (Event system v2)
 */
public abstract class PacketChat extends PacketSender
{
	protected static final JsonParser parser = new JsonParser();
	protected String json;
	protected byte data;

	public PacketChat(String json) {
		this(json, (byte) 1);
	}

	public PacketChat(String json, byte data) {
		this.setContent(json);
		this.setData(data);
	}

	/**
	 * Gets the content text in Json.
	 */
	public String getContent()
	{
		return this.json;
	}

	/**
	 * Gets the data of the message, default is 1. <br />
	 * The data have <b>2 possible</b> values: <br />
	 * <ul>
	 * <li>1: Display content in chat.</li>
	 * <li>2: Display content in ActionBar.</li>
	 * </ul>
	 * 
	 * @return
	 */
	public byte getData()
	{
		return this.data;
	}

	/**
	 * Sets the content of the message.
	 * 
	 * @param json
	 *            Content as Json.
	 * @return Json is valid or not.
	 */
	public boolean setContent(String json)
	{
		try
		{
			parser.parse(json);
		}
		catch (Exception e)
		{
			if (this.json == null || this.json.isEmpty())
				this.json = "{\"text\":\"\"}";
			return false;
		}
		this.json = json;
		return true;
	}

	/**
	 * Sets the data of the message, default is 1. <br />
	 * The data have <b>2 possible</b> values: <br />
	 * <ul>
	 * <li>1: Display content in chat.</li>
	 * <li>2: Display content in ActionBar.</li>
	 * </ul>
	 * 
	 * <b>If you give too big value, it set to 2.</b> <br />
	 * <b>If you give too small value, it set to 1.</b> <br />
	 * 
	 * @param data
	 *            Data of the message and it is 1 or 2.
	 */
	public PacketChat setData(byte data)
	{
		if (data < 1) data = 1;
		else if (data > 2) this.data = 2;
		else
			this.data = data;
		return this;
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

}
