package com.w67clement.mineapi.nms.none.play_out.message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.message.ActionBarMessage;

public class CraftActionBarMessage extends ActionBarMessage
{

	public CraftActionBarMessage(String message) {
		super(message);
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

	@Override
	public void send(Player player)
	{
		// Convert JSON String to IChatBaseComponent
		Object messageComponent = ReflectionAPI.invokeMethod(null,
				ReflectionAPI.getMethod(
						ReflectionAPI.NmsClass.getChatSerializerClass(), "a",
						String.class),
				"{text:\"" + ChatColor.translateAlternateColorCodes('&',
						this.message) + "\"}");
		// Create Packet
		Object actionBaMessagePacket = ReflectionAPI.newInstance(ReflectionAPI
				.getConstructor(ReflectionAPI.getNmsClass("PacketPlayOutChat"),
						ReflectionAPI.NmsClass.getIChatBaseComponentClass(),
						byte.class),
				messageComponent, (byte) 2);
		// Send packet
		ReflectionAPI.NmsClass.sendPacket(player, actionBaMessagePacket);
	}

}
