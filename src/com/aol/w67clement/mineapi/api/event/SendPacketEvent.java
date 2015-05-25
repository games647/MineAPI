package com.aol.w67clement.mineapi.api.event;

import com.aol.w67clement.mineapi.api.wrappers.PacketWrapper;

public class SendPacketEvent {

	private PacketWrapper packet;
	private PacketCancellable cancellable;
	private String recieverName;
	
	public SendPacketEvent(PacketWrapper packet, PacketCancellable cancellable, String recieverName) {
		this.packet = packet;
		this.cancellable = cancellable;
		this.recieverName = recieverName;
	}
	
	public PacketWrapper getPacket() {
		return this.packet;
	}
	
	public String getRecieverName() {
		return this.recieverName;
	}

	public boolean isCancelled() {
		return this.cancellable.isCancelled();
	}

	public void setCancelled(boolean cancel) {
		this.cancellable.setCancelled(cancel);
	}
}
