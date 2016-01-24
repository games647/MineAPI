package com.w67clement.mineapi.inventory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public abstract class InventoryEvent extends PlayerEvent
{

	private int windowId;

	public InventoryEvent(Player player, int windowId) {
		super(player);
		this.windowId = windowId;
	}

	public int getWindowId()
	{
		return this.windowId;
	}

}
