package com.w67clement.mineapi.nms.glowstone.play_out.message;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.message.ActionBarMessage;

import net.glowstone.entity.GlowPlayer;
import net.glowstone.net.message.play.game.ChatMessage;
import net.glowstone.util.TextMessage;

public class GlowActionBarMessage extends ActionBarMessage
{

	public GlowActionBarMessage(String message) {
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
		TextMessage msg = new TextMessage(
				ChatColor.translateAlternateColorCodes('&', this.message));
		ChatMessage packet = new ChatMessage(msg, 2);
		((GlowPlayer) player).getSession().send(packet);
	}

}
