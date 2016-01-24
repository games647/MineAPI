package com.w67clement.mineapi.api.event.ping;

import com.w67clement.mineapi.api.event.PacketCancellable;
import com.w67clement.mineapi.api.wrappers.MC_PacketWrapper;
import com.w67clement.mineapi.api.wrappers.PacketWrapper;
import com.w67clement.mineapi.nms.NmsPacket;

public class PacketPingReceiveEvent<T extends NmsPacket>
{

	private MC_PacketWrapper<T> packet;
	private PacketCancellable cancellable;
	private String ip;

	public PacketPingReceiveEvent(MC_PacketWrapper<T> packet,
			PacketCancellable cancellable, String ip) {
		this.packet = packet;
		this.cancellable = cancellable;
		this.ip = ip;
	}

	@Deprecated
	public PacketWrapper getPacket()
	{
		return this.packet.getOldWrapper();
	}

	/**
	 * Gets the MineAPI's packet wrapper.
	 *
	 * @return MineAPI's packet wrapper.
	 */
	public MC_PacketWrapper<T> getPacketWrapper()
	{
		return this.packet;
	}

	public String getIp()
	{
		return this.ip;
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
