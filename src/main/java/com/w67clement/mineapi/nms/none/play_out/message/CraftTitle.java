package com.w67clement.mineapi.nms.none.play_out.message;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.message.Title;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CraftTitle extends Title
{

	/*
	 * Reflection
	 */
	private static Class<?> packetTitleClass;
	private static Class<?> chatBaseComponentClass;
	private static Class<?> enumTitleAction;
	private static Object enum_title;
	private static Object enum_subtitle;

	public CraftTitle(int fadeIn, int stay, int fadeOut, String title,
			String subtitle) {
		super(fadeIn, stay, fadeOut, title, subtitle);
	}

	static
	{
		packetTitleClass = ReflectionAPI.getNmsClass("PacketPlayOutTitle");
		chatBaseComponentClass = ReflectionAPI.NmsClass
				.getIChatBaseComponentClass();
		if (MineAPI.getServerVersion().equals("v1_8_R1"))
		{
			enumTitleAction = ReflectionAPI.getNmsClass("EnumTitleAction");
		}
		else
		{
			enumTitleAction = ReflectionAPI.getNmsClass("PacketPlayOutTitle")
					.getDeclaredClasses()[0];
		}
		for (Object obj : enumTitleAction.getEnumConstants())
		{
			if (obj.toString().equals("TIMES"))
			{
				// Not used.
			}
			else if (obj.toString().equals("TITLE"))
			{
				enum_title = obj;
			}
			else if (obj.toString().equals("SUBTITLE"))
			{
				enum_subtitle = obj;
			}
		}
	}

	@Override
	public void send(Player player)
	{
		// Create a Packet
		Object timeTitlePacket = ReflectionAPI.newInstance(
				packetTitleClass.getConstructors()[1], this.fadeIn, this.stay,
				this.fadeOut);
		// Send the packet title with the time.
		ReflectionAPI.NmsClass.sendPacket(player, timeTitlePacket);
		if (title != null)
		{
			title = ChatColor.translateAlternateColorCodes('&', title);
			// Convert a JSON string to IChatBaseComponent object
			Object chatComponentTitle = ReflectionAPI.invokeMethod(null,
					ReflectionAPI.getMethod(
							ReflectionAPI.NmsClass.getChatSerializerClass(),
							"a", String.class),
					"{text:\"" + this.title + ChatColor.RESET + "\"}");
			// Create packet
			Object titleTitlePacket = ReflectionAPI.newInstance(
					ReflectionAPI.getConstructor(packetTitleClass,
							enumTitleAction, chatBaseComponentClass),
					enum_title, chatComponentTitle);
			// Send the title
			ReflectionAPI.NmsClass.sendPacket(player, titleTitlePacket);
		}
		if (subtitle != null)
		{
			subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
			// Convert a JSON string to IChatBaseComponent object
			Object chatComponentSubTitle = ReflectionAPI.invokeMethod(null,
					ReflectionAPI.getMethod(
							ReflectionAPI.NmsClass.getChatSerializerClass(),
							"a", String.class),
					"{text:\"" + this.subtitle + ChatColor.RESET + "\"}");
			// Create packet
			Object subtitleTitlePacket = ReflectionAPI.newInstance(
					ReflectionAPI.getConstructor(packetTitleClass,
							enumTitleAction, chatBaseComponentClass),
					enum_subtitle, chatComponentSubTitle);
			// Send the subtitle
			ReflectionAPI.NmsClass.sendPacket(player, subtitleTitlePacket);
		}
	}
	
	public Object constructPacket() {
		return null;
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}
}
