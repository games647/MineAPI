package com.aol.w67clement.mineapi.api.event;

import org.bukkit.entity.Player;

public class PacketEvent {

	private Player player;
	private boolean isCancelled;
	
	public PacketEvent(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return this.player;
	}

	public boolean isCancelled() {
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
}
