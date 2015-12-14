package com.w67clement.mineapi.nms.glowstone.play_out.tab;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.tab.TabTitle;

import net.glowstone.entity.GlowPlayer;
import net.glowstone.net.message.play.game.UserListHeaderFooterMessage;
import net.glowstone.util.TextMessage;

public class GlowTabTitle extends TabTitle
{

	public GlowTabTitle(String header, String footer) {
		super(header, footer);
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

	@Override
	public void send(Player player)
	{
		UserListHeaderFooterMessage packet = new UserListHeaderFooterMessage(
				new TextMessage(ChatColor.translateAlternateColorCodes('&',
						this.header)),
				new TextMessage(ChatColor.translateAlternateColorCodes('&',
						this.footer)));
		((GlowPlayer) player).getSession().send(packet);
	}

}
