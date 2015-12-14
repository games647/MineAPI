package com.w67clement.mineapi.nms.none.play_out.tab;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.tab.TabTitle;

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
		ReflectionAPI.setValue(tabTitlePacket,
				ReflectionAPI.getField(tabtitlePacketClass, "a", true),
				headerComponent);
		ReflectionAPI.setValue(tabTitlePacket,
				ReflectionAPI.getField(tabtitlePacketClass, "b", true),
				footerComponent);
		// Send packet
		ReflectionAPI.NmsClass.sendPacket(player, tabTitlePacket);
	}

}
