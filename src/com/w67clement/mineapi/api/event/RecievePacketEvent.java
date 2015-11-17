package com.w67clement.mineapi.api.event;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.w67clement.mineapi.api.wrappers.PacketWrapper;

public class RecievePacketEvent
{

	private PacketWrapper packet;
	private PacketCancellable cancellable;
	private Player player;

	public RecievePacketEvent(PacketWrapper packet, PacketCancellable cancellable, Player player) {
		this.packet = packet;
		this.cancellable = cancellable;
		this.player = player;
	}

	public PacketWrapper getPacket()
	{
		return this.packet;
	}

	public Player getPlayer()
	{
		return this.player;
	}

	public UUID getPlayerUUID()
	{
		return this.player.getUniqueId();
	}

	@Deprecated
	public String getRecieverName()
	{
		return this.player.getName();
	}

	public boolean isCancelled()
	{
		return this.cancellable.isCancelled();
	}

	public void setCancelled(boolean cancel)
	{
		this.cancellable.setCancelled(cancel);
	}

}
