package com.w67clement.mineapi.inventory.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class InventoryClickEvent extends InventoryEvent implements Cancellable
{

	private static final HandlerList handlers = new HandlerList();
	private boolean cancelled = false;
	private short slot;
	private ItemStack item;

	public InventoryClickEvent(Player player, int windowId, short slot,
			ItemStack item) {
		super(player, windowId);
		this.slot = slot;
		this.item = item;
	}

	public short getClickedSlot()
	{
		return this.slot;
	}

	public ItemStack getItem()
	{
		return this.item;
	}

	@Override
	public HandlerList getHandlers()
	{
		return handlers;
	}

	public static HandlerList getHandlerList()
	{
		return handlers;
	}

	@Override
	public boolean isCancelled()
	{
		return this.cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled)
	{
		this.cancelled = cancelled;
	}

}
