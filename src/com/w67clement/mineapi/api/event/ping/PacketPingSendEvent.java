package com.w67clement.mineapi.api.event.ping;

import com.w67clement.mineapi.api.event.PacketCancellable;
import com.w67clement.mineapi.api.wrappers.PacketWrapper;

public class PacketPingSendEvent
{

	private PacketWrapper packet;
	private PacketCancellable cancellable;
	private String ip;

	public PacketPingSendEvent(PacketWrapper packet, PacketCancellable cancellable, String ip) {
		this.packet = packet;
		this.cancellable = cancellable;
		this.ip = ip;
	}

	public PacketWrapper getPacket()
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

}
