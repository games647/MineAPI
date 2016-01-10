package com.w67clement.mineapi.message;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.google.gson.stream.JsonWriter;
import com.w67clement.mineapi.MineAPI;

public class FancyMessage {
	
	private List<MessagePart> messageParts;

	public FancyMessage(String string) {
		messageParts = new ArrayList<MessagePart>();
		messageParts.add(new MessagePart(string));
	}

	/**
	 *  Sets the Color of the text.
	 * @param color The Color of the text.
	 */
	public FancyMessage color(ChatColor color)
	{
		if (!color.isColor()) { throw new IllegalArgumentException(
				color.name() + " is not a color"); }
		latest().color = color;
		return this;
	}

	/**
	 *  Sets the Style of the text.
	 * @param styles The Style of the text.
	 */
	public FancyMessage style(ChatColor... styles)
	{
		for (ChatColor style : styles)
		{
			if (!style.isFormat()) { throw new IllegalArgumentException(
					style.name() + " is not a style"); }
		}
		latest().styles = styles;
		return this;
	}

	/**
	 *  Gets the File.
	 * @param path ???
	 */
	public FancyMessage getFile(String path)
	{
		this.onClick("open_file", path);
		return this;
	}

	/**
	 *  Sets a click action as open URL with the text.
	 * @param url URL of the webSite
	 */
	public FancyMessage addLink(String url)
	{
		this.onClick("open_url", url);
		return this;
	}

	/**
	 *  Suggest an command with the text.
	 * @param command The suggested command.
	 */
	public FancyMessage suggestCommand(String command)
	{
		this.onClick("suggest_command", command);
		return this;
	}

	/**
	 *  Run an command with the text.
	 * @param runCommand The runnable command.
	 */
	public FancyMessage runCommand(String runCommand)
	{
		this.onClick("run_command", runCommand);
		return this;
	}

	/**
	 *  Send an hover Message with the text.
	 * @param text The text in a hover Message.
	 */
	public FancyMessage addHoverMessage(String text)
	{
		this.onHover("show_text", text);
		return this;
	}

	/**
	 *  Add new part.
	 */
	public FancyMessage then(Object obj)
	{
		messageParts.add(new MessagePart(obj.toString()));
		return this;
	}

	/**
	 *  To the JSON
	 * @return JSON String.
	 */
	public String toJSONString()
	{
		StringWriter stringWriter = new StringWriter();
		JsonWriter json = new JsonWriter(stringWriter);
		try
		{
			if (messageParts.size() == 1)
			{
				latest().writeJson(json);
			}
			else
			{
				json.beginObject().name("text").value("").name("extra")
						.beginArray();
				for (MessagePart part : messageParts)
				{
					part.writeJson(json);
				}
				json.endArray().endObject();
			}

		}
		catch (IOException e)
		{
			throw new RuntimeException("invalid message");
		}
		return stringWriter.toString();
	}

	/**
	 *  Sends a message with a NMS class.
	 * @param player Represents the player.
	 */
	public void send(Player player)
	{
		MineAPI.getNmsManager().getPacketChat(this.toJSONString(), (byte) 1).send(player);
	}

	private MessagePart latest()
	{
		return messageParts.get(messageParts.size() - 1);
	}

	private void onClick(String name, String data)
	{
		MessagePart latest = latest();
		latest.clickActionName = name;
		latest.clickActionData = data;
	}

	private void onHover(String name, String data)
	{
		MessagePart latest = latest();
		latest.hoverActionName = name;
		latest.hoverActionData = data;
	}

	static class MessagePart
	{

		public ChatColor color = null;
		public ChatColor[] styles = null;
		public String clickActionName = null;
		public String clickActionData = null;
		public String hoverActionName = null;
		public String hoverActionData = null;
		public final String text;

		public MessagePart(final String text) {
			this.text = text;
		}

		public JsonWriter writeJson(final JsonWriter json) throws IOException
		{
			json.beginObject().name("text").value(text);
			if (color != null)
			{
				json.name("color").value(color.name().toLowerCase());
			}
			if (styles != null)
			{
				for (final ChatColor style : styles)
				{
					json.name(style == ChatColor.UNDERLINE ? "underlined"
							: style.name().toLowerCase()).value(true);
				}
			}
			if (clickActionName != null && clickActionData != null)
			{
				json.name("clickEvent").beginObject().name("action")
						.value(clickActionName).name("value")
						.value(clickActionData).endObject();
			}
			if (hoverActionName != null && hoverActionData != null)
			{
				json.name("hoverEvent").beginObject().name("action")
						.value(hoverActionName).name("value")
						.value(hoverActionData).endObject();
			}
			return json.endObject();
		}
	}

}

// End of the FancyMessage class