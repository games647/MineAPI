package com.w67clement.mineapi.api.event;

import java.util.UUID;

import org.bukkit.entity.Player;

import com.w67clement.mineapi.api.wrappers.MC_PacketWrapper;
import com.w67clement.mineapi.api.wrappers.PacketWrapper;
import com.w67clement.mineapi.nms.NmsPacket;

public class SendPacketEvent<T extends NmsPacket>
{

	private MC_PacketWrapper<T> packet;
	private PacketCancellable cancellable;
	private Player player;

	public SendPacketEvent(MC_PacketWrapper<T> packet,
			PacketCancellable cancellable, Player player) {
		this.packet = packet;
		this.cancellable = cancellable;
		this.player = player;
	}

	@Deprecated
	public PacketWrapper getPacket()
	{
		return this.packet.getOldWrapper();
	}

	/**
	 * Gets the MineAPI's packet wrapper.
	 */
	public MC_PacketWrapper<T> getPacketWrapper()
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

	public boolean hasForceChanges()
	{
		return this.cancellable.hasForceChanges();
	}

	public void setForceChanges(boolean forceChanges)
	{
		this.cancellable.setForceChanges(forceChanges);
	}
}
