package com.w67clement.mineapi.nms.none.play_out.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.w67clement.mineapi.api.ReflectionAPI;
import com.w67clement.mineapi.inventory.packets.WindowItems;

public class CraftWindowItems extends WindowItems
{

	private static Class<?> packetClass;

	public CraftWindowItems(int windowId, List<ItemStack> items) {
		super(windowId, items);
	}

	public CraftWindowItems(int windowId, Inventory inventory) {
		super(windowId, inventory);
	}

	static
	{
		packetClass = ReflectionAPI.getNmsClass("PacketPlayOutWindowItems");
	}

	@Override
	public void send(Player player)
	{
		List<Object> items = new ArrayList<Object>();
		this.items.forEach(item -> {
			if (item != null)
				items.add(ReflectionAPI.ItemStackConverter.toNms(item));
			else
				items.add(null);
		});
		Object packet = ReflectionAPI.newInstance(ReflectionAPI.getConstructor(
				packetClass, int.class, List.class), this.windowId, items);
		ReflectionAPI.NmsClass.sendPacket(player, packet);
	}

}
