package com.w67clement.mineapi.inventory.packets;

import org.bukkit.inventory.Inventory;

import com.w67clement.mineapi.enums.PacketType;
import com.w67clement.mineapi.nms.PacketSender;

public abstract class WindowOpen extends PacketSender
{
	protected int id;
	protected WindowType type;
	protected String title;
	protected int size;
	protected int horse_id = 0;

	public WindowOpen(int id, WindowType type, String title, int size) {
		this.id = id;
		this.type = type;
		this.title = title;
		this.size = size;
	}

	public WindowOpen(int id, WindowType type, String title, int size,
			int horse_id) {
		this(id, type, title, size);
		this.horse_id = horse_id;
	}

	public WindowOpen(int id, Inventory inventory) {
		this.id = id;
		this.type = WindowType.getByInventory(inventory.getType());
		if (type == null) { throw new IllegalArgumentException(
				"Type of the inventory was not recognized!"); }
		this.title = inventory.getTitle();
		this.size = inventory.getSize();
	}

	public int getId()
	{
		return this.id;
	}

	public WindowType getWindowType()
	{
		return this.type;
	}

	public String getTitle()
	{
		return this.title;
	}

	public int getSize()
	{
		return this.size;
	}

	public int getHorseId()
	{
		return this.horse_id;
	}

	public WindowOpen setId(int id)
	{
		this.id = id;
		return this;
	}

	public WindowOpen setWindowType(WindowType type)
	{
		this.type = type;
		return this;
	}

	public WindowOpen setTitle(String title)
	{
		this.title = title;
		return this;
	}

	public WindowOpen setSize(int size)
	{
		this.size = size;
		return this;
	}

	public WindowOpen setHorseId(int horse_id)
	{
		this.horse_id = horse_id;
		return this;
	}

	@Override
	public PacketType getPacketType()
	{
		return PacketType.PACKETPLAYOUT;
	}

}
