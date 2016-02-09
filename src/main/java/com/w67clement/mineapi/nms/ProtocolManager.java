package com.w67clement.mineapi.nms;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.system.ProtocolInjector;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class ProtocolManager implements Listener
{

	private ProtocolInjector injector;
	private MineAPI mineapi;

	public ProtocolManager(MineAPI mineapi, ProtocolInjector injector) {
		this.injector = injector;
		this.mineapi = mineapi;
	}

	public void disable()
	{
		for (Player players : Bukkit.getOnlinePlayers())
		{
			this.injector.removeChannel(players);
		}
		this.injector.disable();
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(final PlayerLoginEvent e)
	{
		final Player player = e.getPlayer();
		if ((!player.isBanned())
				&& (e.getResult() == PlayerLoginEvent.Result.ALLOWED))
		{
			Bukkit.getScheduler().scheduleSyncDelayedTask(this.mineapi, () -> ProtocolManager.this.injector.addChannel(player), 2L);
		}
	}

}
