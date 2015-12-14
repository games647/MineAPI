package com.w67clement.mineapi.nms.glowstone.play_in;

import org.bukkit.entity.Player;

import com.w67clement.mineapi.entity.player.ClientCommand;
import com.w67clement.mineapi.enums.PacketType;

import net.glowstone.entity.GlowPlayer;
import net.glowstone.net.message.play.player.ClientStatusMessage;

public class GlowClientCommand extends ClientCommand
{

	public GlowClientCommand(ClientCommandType commandType) {
		super(commandType);
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYIN;
	}

	@Override
	public void send(Player player)
	{
		ClientStatusMessage packet = new ClientStatusMessage(
				this.commandType.getId());
		((GlowPlayer) player).getSession().send(packet);
	}

}
