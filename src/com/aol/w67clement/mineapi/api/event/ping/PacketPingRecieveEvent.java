package com.aol.w67clement.mineapi.api.event.ping;

import com.aol.w67clement.mineapi.api.event.PacketCancellable;
import com.aol.w67clement.mineapi.api.wrappers.PacketWrapper;

public class PacketPingRecieveEvent {

	private PacketWrapper packet;
	private PacketCancellable cancellable;
	
	public PacketPingRecieveEvent(PacketWrapper packet, PacketCancellable cancellable) {
		this.packet = packet;
		this.cancellable = cancellable;
	}
	
	public PacketWrapper getPacket() {
		return this.packet;
	}

	public boolean isCancelled() {
		return this.cancellable.isCancelled();
	}

	public void setCancelled(boolean cancel) {
		this.cancellable.setCancelled(cancel);
	}
	
}
