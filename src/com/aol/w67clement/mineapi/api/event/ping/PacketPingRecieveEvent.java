package com.aol.w67clement.mineapi.api.event.ping;

import com.aol.w67clement.mineapi.api.event.PacketCancellable;
import com.aol.w67clement.mineapi.api.wrappers.PacketWrapper;

public class PacketPingRecieveEvent {

	private PacketWrapper packet;
	private PacketCancellable cancellable;
	private String ip;
	
	public PacketPingRecieveEvent(PacketWrapper packet, PacketCancellable cancellable, String ip) {
		this.packet = packet;
		this.cancellable = cancellable;
		this.ip = ip;
	}
	
	public PacketWrapper getPacket() {
		return this.packet;
	}
	
	public String getIp() {
		return this.ip;
	}

	public boolean isCancelled() {
		return this.cancellable.isCancelled();
	}

	public void setCancelled(boolean cancel) {
		this.cancellable.setCancelled(cancel);
	}
	
}
