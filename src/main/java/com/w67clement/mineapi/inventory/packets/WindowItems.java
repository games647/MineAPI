package com.w67clement.mineapi.inventory.packets;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;
import java.util.Arrays;
import java.util.List;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public abstract class WindowItems extends PacketSender
{
	protected int windowId;
	protected List<ItemStack> items;

	public WindowItems(int windowId, List<ItemStack> items) {
		this.windowId = windowId;
		this.items = items;
	}

	public WindowItems(int windowId, Inventory inventory) {
		this(windowId, Arrays.asList(inventory.getContents()));
	}

	public int getWindowId()
	{
		return this.windowId;
	}

	public WindowItems setWindowId(int windowId)
	{
		this.windowId = windowId;
		return this;
	}

	public List<ItemStack> getItems()
	{
		return this.items;
	}

	public WindowItems setItems(List<ItemStack> items)
	{
		this.items = items;
		return this;
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

}
