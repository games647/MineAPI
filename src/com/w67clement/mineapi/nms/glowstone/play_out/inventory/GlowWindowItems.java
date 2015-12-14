package com.w67clement.mineapi.nms.glowstone.play_out.inventory;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.w67clement.mineapi.inventory.packets.WindowItems;

import net.glowstone.entity.GlowPlayer;
import net.glowstone.net.message.play.inv.SetWindowContentsMessage;

public class GlowWindowItems extends WindowItems
{

	public GlowWindowItems(int windowId, List<ItemStack> items) {
		super(windowId, items);
	}

	public GlowWindowItems(int windowId, Inventory inventory) {
		super(windowId, inventory);
	}

	@Override
	public void send(Player player)
	{
		ItemStack[] items = new ItemStack[this.items.size()];
		items = this.items.toArray(items);
		SetWindowContentsMessage packet = new SetWindowContentsMessage(
				this.windowId, items);
		((GlowPlayer) player).getSession().send(packet);
	}

}
