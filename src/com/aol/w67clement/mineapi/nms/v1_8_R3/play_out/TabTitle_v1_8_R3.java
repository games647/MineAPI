package com.aol.w67clement.mineapi.nms.v1_8_R3.play_out;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.api.ReflectionAPI;
import com.aol.w67clement.mineapi.enums.PacketType;
import com.aol.w67clement.mineapi.tab.TabTitle;

/**
 * Set a title (header and footer) of the player list Tab.
 * 
 * @author 67clement
 * @version 2.0 - CraftBukkit 1.8.4-1.8.6
 */
public class TabTitle_v1_8_R3 implements TabTitle {

	private String header;
	private String footer;

	public TabTitle_v1_8_R3(String header, String footer) {
		this.header = header;
		this.footer = footer;
	}

	@Override
	public void send(Player player) {
		if (this.header == null)
			this.header = "";
		this.header = ChatColor.translateAlternateColorCodes('&', this.header);
		if (this.footer == null)
			this.footer = "";
		this.footer = ChatColor.translateAlternateColorCodes('&', this.footer);
		IChatBaseComponent tabHeader = IChatBaseComponent.ChatSerializer
				.a("{text:\"" + this.header + ChatColor.RESET + "\"}");
		IChatBaseComponent tabFooter = IChatBaseComponent.ChatSerializer
				.a("{text:\"" + this.footer + ChatColor.RESET + "\"}");
		PacketPlayOutPlayerListHeaderFooter tabTitlePacket = new PacketPlayOutPlayerListHeaderFooter(
				tabHeader);

		try {
			ReflectionAPI.setValue(tabTitlePacket,
					ReflectionAPI.getField(tabTitlePacket.getClass(), "b", true),
					tabFooter);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			((CraftPlayer) player).getHandle().playerConnection
					.sendPacket(tabTitlePacket);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void sendAll() {
		for (Player players : Bukkit.getOnlinePlayers()) {
			this.send(players);
		}
	}

	@Override
	public String getHeader() {
		return this.header;
	}

	@Override
	public String getFooter() {
		return this.footer;
	}

	@Override
	public TabTitle setHeader(String header) {
		this.header = header;
		return this;
	}

	@Override
	public TabTitle setFooter(String footer) {
		this.footer = footer;
		return this;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PACKETPLAYOUT;
	}
}

// End of TabTitle class