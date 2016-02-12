package com.w67clement.mineapi.nms.none.play_out.inventory;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.inventory.packets.WindowItems;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CraftWindowItems extends WindowItems
{

	private static Class<?> packetClass;

	static
	{
		if (MineAPI.isGlowstone())
		{
			packetClass = ReflectionAPI.getClass(
					"net.glowstone.net.message.play.inv.SetWindowContentsMessage");
		}
		else
			packetClass = ReflectionAPI.getNmsClass("PacketPlayOutWindowItems");
	}

	public CraftWindowItems(int windowId, List<ItemStack> items) {
		super(windowId, items);
	}

	public CraftWindowItems(int windowId, Inventory inventory) {
		super(windowId, inventory);
	}

	@Override
	public void send(Player player)
	{
		ReflectionAPI.NmsClass.sendPacket(player, this.constructPacket());
	}

	@Override
	public Object constructPacket()
	{
		if (MineAPI.isSpigot())
		{
			return this.constructPacketBukkit();
		}
		else if (MineAPI.isGlowstone()) return this.constructPacketGlowstone();
		return this.constructPacketBukkit();
	}

	private Object constructPacketBukkit()
	{
		List<Object> items = new ArrayList<>();
		this.items.forEach(item -> {
			if (item != null)
				items.add(ReflectionAPI.ItemStackConverter.toNms(item));
			else
				items.add(null);
		});
		return ReflectionAPI.newInstance(ReflectionAPI.getConstructor(
				packetClass, int.class, List.class), this.windowId, items);
	}

	private Object constructPacketGlowstone()
	{
		ItemStack[] items = new ItemStack[this.items.size()];
		items = this.items.toArray(items);
		return ReflectionAPI.newInstance(ReflectionAPI
				.getConstructor(packetClass, int.class, ItemStack[].class),
				this.windowId, items);
	}

}
