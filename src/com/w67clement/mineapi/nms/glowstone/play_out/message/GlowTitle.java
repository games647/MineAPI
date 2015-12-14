package com.w67clement.mineapi.nms.glowstone.play_out.message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.message.Title;

import net.glowstone.entity.GlowPlayer;
import net.glowstone.net.message.play.game.TitleMessage;

public class GlowTitle extends Title
{

	public GlowTitle(int fadeIn, int stay, int fadeOut, String title,
			String subtitle) {
		super(fadeIn, stay, fadeOut, title, subtitle);
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

	@Override
	public void send(Player player)
	{
		GlowPlayer glow_player = (GlowPlayer) player;
		TitleMessage packet_times = new TitleMessage(TitleMessage.Action.TIMES,
				this.fadeIn, this.stay, this.fadeOut);
		glow_player.getSession().send(packet_times);
		if (this.title != null)
		{
			TitleMessage packet_title = new TitleMessage(
					TitleMessage.Action.TITLE,
					"{text:\"" + ChatColor.translateAlternateColorCodes('&',
							this.title) + ChatColor.RESET + "\"}");
			glow_player.getSession().send(packet_title);
		}
		if (this.subtitle != null)
		{
			TitleMessage packet_subtitle = new TitleMessage(
					TitleMessage.Action.SUBTITLE,
					"{text:\"" + ChatColor.translateAlternateColorCodes('&',
							this.subtitle) + ChatColor.RESET + "\"}");
			glow_player.getSession().send(packet_subtitle);
		}
	}

}
