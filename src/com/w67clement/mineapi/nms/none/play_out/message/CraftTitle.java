package com.w67clement.mineapi.nms.none.play_out.message;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.message.Title;

public class CraftTitle implements Title
{

	/*
	 * Time.
	 */
	private int fadeIn;
	private int stay;
	private int fadeOut;
	/*
	 * In seconds.
	 */
	private int fadeIn_Seconds = fadeIn * 20;
	private int stay_Seconds = stay * 20;
	private int fadeOut_Seconds = fadeOut * 20;
	/*
	 * String.
	 */
	private String title;
	private String subtitle;

	/*
	 * Reflection
	 */
	private static Class<?> packetTitleClass;
	private static Class<?> chatBaseComponentClass;
	private static Class<?> enumTitleAction;
	private static Object enum_title;
	private static Object enum_subtitle;

	public CraftTitle(int fadeIn, int stay, int fadeOut, String title, String subtitle) {
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
		this.title = title;
		this.subtitle = subtitle;
	}

	static
	{
		packetTitleClass = ReflectionAPI.getNmsClass("PacketPlayOutTitle");
		chatBaseComponentClass = ReflectionAPI.NmsClass.getIChatBaseComponentClass();
		if (MineAPI.getServerVersion().equals("v1_8_R1"))
		{
			enumTitleAction = ReflectionAPI.getNmsClass("EnumTitleAction");
		}
		else
		{
			enumTitleAction = ReflectionAPI.getNmsClass("PacketPlayOutTitle").getDeclaredClasses()[0];
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
		Object timeTitlePacket = ReflectionAPI.newInstance(packetTitleClass.getConstructors()[1], this.fadeIn,
				this.stay, this.fadeOut);
		// Send the packet title with the time.
		ReflectionAPI.NmsClass.sendPacket(player, timeTitlePacket);
		if (title != null)
		{
			title = ChatColor.translateAlternateColorCodes('&', title);
			// Convert a JSON string to IChatBaseComponent object
			Object chatComponentTitle = ReflectionAPI.invokeMethod(null,
					ReflectionAPI.getMethod(ReflectionAPI.NmsClass.getChatSerializerClass(), "a", String.class),
					"{text:\"" + this.title + ChatColor.RESET + "\"}");
			// Create packet
			Object titleTitlePacket = ReflectionAPI.newInstance(
					ReflectionAPI.getConstructor(packetTitleClass, enumTitleAction, chatBaseComponentClass), enum_title,
					chatComponentTitle);
			// Send the title
			ReflectionAPI.NmsClass.sendPacket(player, titleTitlePacket);
		}
		if (subtitle != null)
		{
			subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
			// Convert a JSON string to IChatBaseComponent object
			Object chatComponentSubTitle = ReflectionAPI.invokeMethod(null,
					ReflectionAPI.getMethod(ReflectionAPI.NmsClass.getChatSerializerClass(), "a", String.class),
					"{text:\"" + this.subtitle + ChatColor.RESET + "\"}");
			// Create packet
			Object subtitleTitlePacket = ReflectionAPI.newInstance(
					ReflectionAPI.getConstructor(packetTitleClass, enumTitleAction, chatBaseComponentClass),
					enum_subtitle, chatComponentSubTitle);
			// Send the subtitle
			ReflectionAPI.NmsClass.sendPacket(player, subtitleTitlePacket);
		}
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
	public int getFadeIn_InSeconds()
	{
		return this.fadeIn_Seconds;
	}

	@Override
	public int getFadeIn()
	{
		return this.fadeIn;
	}

	@Override
	public int getFadeOut_InSeconds()
	{
		return this.fadeOut_Seconds;
	}

	@Override
	public int getFadeOut()
	{
		return this.fadeOut;
	}

	@Override
	public int getStay_InSeconds()
	{
		return this.stay_Seconds;
	}

	@Override
	public int getStay()
	{
		return this.stay;
	}

	@Override
	public Title setFadeIn(int fadeIn)
	{
		this.fadeIn = fadeIn;
		return this;
	}

	@Override
	public Title setStay(int stay)
	{
		this.stay = stay;
		return this;
	}

	@Override
	public Title setFadeOut(int fadeOut)
	{
		this.fadeOut = fadeOut;
		return this;
	}

	@Override
	public String getTitle()
	{
		return this.title;
	}

	@Override
	public String getSubTitle()
	{
		return this.subtitle;
	}

	@Override
	public Title setTitle(String title)
	{
		this.title = title;
		return this;
	}

	@Override
	public Title setSubTitle(String subTitle)
	{
		this.subtitle = subTitle;
		return this;
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}
}
