package com.w67clement.mineapi.nms.none.play_out.tab;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.tab.TabTitle;
import java.lang.reflect.Constructor;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Set a title (header and footer) of the player list Tab.
 * 
 * @author w67clement
 * @version 3.0 - For none NMS version.
 */
public class CraftTabTitle extends TabTitle
{
	private static Class<?> tabtitlePacketClass;

	public CraftTabTitle(String header, String footer) {
		super(header, footer);
	}

	static
	{
		if (MineAPI.isGlowstone())
		{
			tabtitlePacketClass = ReflectionAPI.getClass(
					"net.glowstone.net.message.play.game.UserListHeaderFooterMessage");
		}
		else
			tabtitlePacketClass = ReflectionAPI
					.getNmsClass("PacketPlayOutPlayerListHeaderFooter");
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

	@Override
	public void send(Player player)
	{
		ReflectionAPI.NmsClass.sendPacket(player, this.constructPacket());
	}

	@Override
	public Object constructPacket()
	{
		if (MineAPI.isSpigot())
		{
			return this.constructPacket_Bukkit();
		}
		else if (MineAPI
				.isGlowstone()) { return this.constructPacket_Glowstone(); }
		return this.constructPacket_Bukkit();
	}

	private Object constructPacket_Bukkit()
	{
		this.header = ChatColor.translateAlternateColorCodes('&', this.header);
		this.footer = ChatColor.translateAlternateColorCodes('&', this.footer);

		// Create packet
		Object tabTitlePacket = ReflectionAPI
				.newInstance(ReflectionAPI.getConstructor(tabtitlePacketClass));

		// Convert JSON strings to IChatBaseComponent objects
		Object headerComponent = ReflectionAPI.invokeMethod(null,
				ReflectionAPI.getMethod(
						ReflectionAPI.NmsClass.getChatSerializerClass(), "a",
						String.class),
				"{text:\"" + this.header + ChatColor.RESET + "\"}");
		Object footerComponent = ReflectionAPI.invokeMethod(null,
				ReflectionAPI.getMethod(
						ReflectionAPI.NmsClass.getChatSerializerClass(), "a",
						String.class),
				"{text:\"" + this.footer + ChatColor.RESET + "\"}");
		// Set fields
		if ((this.header != null) && (!this.header.isEmpty()))
			ReflectionAPI.setValue(tabTitlePacket,
					ReflectionAPI.getField(tabtitlePacketClass, "a", true),
					headerComponent);
		if ((this.footer != null) && (!this.footer.isEmpty()))
			ReflectionAPI.setValue(tabTitlePacket,
					ReflectionAPI.getField(tabtitlePacketClass, "b", true),
					footerComponent);
		return tabTitlePacket;
	}

	private Object constructPacket_Glowstone()
	{
		this.header = ChatColor.translateAlternateColorCodes('&', this.header);
		this.footer = ChatColor.translateAlternateColorCodes('&', this.footer);

		Class<?> textMsgClass = ReflectionAPI
				.getClass("net.glowstone.util.TextMessage");
		Class<?> jsonObject = ReflectionAPI
				.getClass("org.json.simple.JSONObject");
		Class<?> jsonParserClass = ReflectionAPI
				.getClass("org.json.simple.parser.JSONParser");
		Object jsonParser = ReflectionAPI
				.newInstance(ReflectionAPI.getConstructor(jsonParserClass));
		Constructor<?> textMsgConstructor = ReflectionAPI
				.getConstructor(textMsgClass, jsonObject);

		Object header = ReflectionAPI.newInstance(textMsgConstructor,
				ReflectionAPI.invokeMethod(jsonParser,
						ReflectionAPI.getMethod(jsonParserClass, "parse",
								String.class),
						"{\"text\":\"" + this.header + "\"}"));
		Object footer = ReflectionAPI.newInstance(textMsgConstructor,
				ReflectionAPI.invokeMethod(jsonParser,
						ReflectionAPI.getMethod(jsonParserClass, "parse",
								String.class),
						"{\"text\":\"" + this.footer + "\"}"));

		// Create packet
		Object tabTitlePacket = ReflectionAPI
				.newInstance(ReflectionAPI.getConstructor(tabtitlePacketClass,
						textMsgClass, textMsgClass), header, footer);
		return tabTitlePacket;
	}

}
