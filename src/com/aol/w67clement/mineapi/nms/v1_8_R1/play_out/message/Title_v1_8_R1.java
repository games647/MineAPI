package com.aol.w67clement.mineapi.nms.v1_8_R1.play_out.message;

import net.minecraft.server.v1_8_R1.ChatSerializer;
import net.minecraft.server.v1_8_R1.EnumTitleAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutTitle;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.enums.PacketType;
import com.aol.w67clement.mineapi.message.Title;

public class Title_v1_8_R1 implements Title {

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

	public Title_v1_8_R1(int fadeIn, int stay, int fadeOut, String title,
			String subtitle) {
		this.fadeIn = fadeIn;
		this.stay = stay;
		this.fadeOut = fadeOut;
		this.title = title;
		this.subtitle = subtitle;
	}

	@Override
	public void send(Player player) {
		// Create a Packet
		PacketPlayOutTitle timeTitlePacket = new PacketPlayOutTitle(
				EnumTitleAction.TIMES, null, this.fadeIn, this.stay,
				this.fadeOut);
		// Send the packet title with the time.
		((CraftPlayer) player).getHandle().playerConnection
				.sendPacket(timeTitlePacket);
		if (title != null) {
			title = ChatColor.translateAlternateColorCodes('&', title);
			// Convert a JSON string to IChatBaseComponent object
			IChatBaseComponent title2 = ChatSerializer.a("{text:\""
					+ this.title + ChatColor.RESET + "\"}");
			// Send the title
			((CraftPlayer) player).getHandle().playerConnection
					.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE,
							title2));
		}
		if (subtitle != null) {
			subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
			// Convert a JSON string to IChatBaseComponent object
			IChatBaseComponent subtitle2 = ChatSerializer.a("{text:\""
					+ this.subtitle + ChatColor.RESET + "\"}");
			// Send the subtitle
			((CraftPlayer) player).getHandle().playerConnection
					.sendPacket(new PacketPlayOutTitle(
							EnumTitleAction.SUBTITLE, subtitle2));
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void sendAll() {
		for (Player allPlayers : Bukkit.getOnlinePlayers()) {
			this.send(allPlayers);
		}
	}

	@Override
	public int getFadeIn_InSeconds() {
		return this.fadeIn_Seconds;
	}

	@Override
	public int getFadeIn() {
		return this.fadeIn;
	}

	@Override
	public int getFadeOut_InSeconds() {
		return this.fadeOut_Seconds;
	}

	@Override
	public int getFadeOut() {
		return this.fadeOut;
	}

	@Override
	public int getStay_InSeconds() {
		return this.stay_Seconds;
	}

	@Override
	public int getStay() {
		return this.stay;
	}

	@Override
	public Title setFadeIn(int fadeIn) {
		this.fadeIn = fadeIn;
		return this;
	}

	@Override
	public Title setStay(int stay) {
		this.stay = stay;
		return this;
	}

	@Override
	public Title setFadeOut(int fadeOut) {
		this.fadeOut = fadeOut;
		return this;
	}

	@Override
	public String getTitle() {
		return this.title;
	}

	@Override
	public String getSubTitle() {
		return this.subtitle;
	}

	@Override
	public Title setTitle(String title) {
		this.title = title;
		return this;
	}

	@Override
	public Title setSubTitle(String subTitle) {
		this.subtitle = subTitle;
		return this;
	}

	@Override
	public PacketType getPacketType() {
		return PacketType.PACKETPLAYOUT;
	}
}
