package com.w67clement.mineapi.nms.glowstone.play_out.message;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.gson.stream.JsonWriter;
import com.w67clement.mineapi.message.FancyMessage;

import net.glowstone.entity.GlowPlayer;
import net.glowstone.net.message.play.game.ChatMessage;

public class GlowFancyMessage implements FancyMessage
{
	private List<MessagePart> messageParts;

	public GlowFancyMessage(String string) {
		messageParts = new ArrayList<MessagePart>();
		messageParts.add(new MessagePart(string));
	}

	@Override
	public FancyMessage color(ChatColor color)
	{
		if (!color.isColor()) { throw new IllegalArgumentException(
				color.name() + " is not a color"); }
		latest().color = color;
		return this;
	}

	@Override
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

	@Override
	public FancyMessage getFile(String path)
	{
		this.onClick("open_file", path);
		return this;
	}

	@Override
	public FancyMessage addLink(String url)
	{
		this.onClick("open_url", url);
		return this;
	}

	@Override
	public FancyMessage suggestCommand(String command)
	{
		this.onClick("suggest_command", command);
		return this;
	}

	@Override
	public FancyMessage runCommand(String runCommand)
	{
		this.onClick("run_command", runCommand);
		return this;
	}

	@Override
	public FancyMessage addHoverMessage(String text)
	{
		this.onHover("show_text", text);
		return this;
	}

	@Override
	public FancyMessage then(Object obj)
	{
		messageParts.add(new MessagePart(obj.toString()));
		return this;
	}

	@Override
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

	@Override
	public void send(Player player)
	{
		JSONObject msg;
		try
		{
			msg = (JSONObject) new JSONParser().parse(this.toJSONString());
			ChatMessage packet = new ChatMessage(msg);
			((GlowPlayer) player).getSession().send(packet);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
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