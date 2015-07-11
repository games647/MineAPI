package com.aol.w67clement.mineapi.nms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.system.ProtocolInjector;

public class ProtocolManager implements Listener {

	private ProtocolInjector injector;
	private MineAPI mineapi;

	public ProtocolManager(MineAPI mineapi, ProtocolInjector injector) {
		this.injector = injector;
		this.mineapi = mineapi;
	}

	@SuppressWarnings("deprecation")
	public void disable() {
		for (Player players : Bukkit.getOnlinePlayers()) {
			this.injector.removeChannel(players);
		}
	}
	
	@EventHandler
	public void onJoin(final PlayerLoginEvent e) {
		final Player player = e.getPlayer();
		Bukkit.getScheduler().scheduleSyncDelayedTask(this.mineapi, new Runnable() {
			@Override
			public void run() {
				ProtocolManager.this.injector.addChannel(player);
			}
		}, 2L);
	}
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerQuit(PlayerQuitEvent e) {
		this.injector.removeChannel(e.getPlayer());
	}
	
}
