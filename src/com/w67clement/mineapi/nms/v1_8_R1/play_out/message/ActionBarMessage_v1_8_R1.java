package com.w67clement.mineapi.nms.v1_8_R1.play_out.message;

import java.io.IOException;
import java.io.StringWriter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.message.ActionBarMessage;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;

/**
 * Please view {link com.w67clement.mineapi.message.ActionBarMessage}
 * 
 * @author w67clement
 * @version 1.0 - CraftBukkit 1.8
 */
public class ActionBarMessage_v1_8_R1 implements ActionBarMessage
{

	private String message;

	public ActionBarMessage_v1_8_R1(String message) {
		this.message = message;
	}

	@SuppressWarnings("resource")
	@Override
	public String toJSONString()
	{
		StringWriter stringWriter = new StringWriter();
		try
		{
			new JsonWriter(stringWriter).beginObject().name("text").value(this.message).endObject();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

	@Override
	public void send(Player player)
	{
		this.message = ChatColor.translateAlternateColorCodes('&', this.message);
		// Convert a JSON String to IChatBaseComponent object.
		IChatBaseComponent actionBarTitle = ChatSerializer.a(this.toJSONString());
		// Create packet
		PacketPlayOutChat packet = new PacketPlayOutChat(actionBarTitle, (byte) 2);
		// Send packet
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	@Override
	public void sendAll()
	{
		for (Player allPlayers : Bukkit.getOnlinePlayers())
		{
			this.send(allPlayers);
		}
	}

	@Override
	public ActionBarMessage setMessage(String message)
	{
		this.message = message;
		return this;
	}

	@Override
	public String getMessage()
	{
		return this.message;
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}
}
