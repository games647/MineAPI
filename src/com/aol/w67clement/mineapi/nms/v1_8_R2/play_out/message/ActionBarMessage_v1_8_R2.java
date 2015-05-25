package com.aol.w67clement.mineapi.nms.v1_8_R2.play_out.message;

import java.io.IOException;
import java.io.StringWriter;

import net.minecraft.server.v1_8_R2.IChatBaseComponent;
import net.minecraft.server.v1_8_R2.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.libs.com.google.gson.stream.JsonWriter;
import org.bukkit.craftbukkit.v1_8_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.enums.PacketType;
import com.aol.w67clement.mineapi.message.ActionBarMessage;

/**
 * Create a different title in action bar!
 * 
 * @author 67clement
 * @version 1.0 - CraftBukkit 1.8.3
 */
public class ActionBarMessage_v1_8_R2 implements ActionBarMessage {

	private String message;

	public ActionBarMessage_v1_8_R2(String message) {
		this.message = message;
	}

	@Override
	public String toJSONString() {
		StringWriter stringWriter = new StringWriter();
		try {
			new JsonWriter(stringWriter).beginObject().name("text")
					.value(this.message).endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringWriter.toString();
	}

	@Override
	public void send(Player player) {
		this.message = ChatColor
				.translateAlternateColorCodes('&', this.message);
		// Convert a JSON String to IChatBaseComponent object.
		IChatBaseComponent actionBarTitle = IChatBaseComponent.ChatSerializer
				.a(this.toJSONString());
		// Create packet
		PacketPlayOutChat packet = new PacketPlayOutChat(actionBarTitle,
				(byte) 2);
		// Send packet
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void sendAll() {
		for (Player allPlayers : Bukkit.getOnlinePlayers()) {
			this.send(allPlayers);
		}
	}

	@Override
	public ActionBarMessage setMessage(String message) {
		this.message = message;
		return this;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PACKETPLAYOUT;
	}
}
