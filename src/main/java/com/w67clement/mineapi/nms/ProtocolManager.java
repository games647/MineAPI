package com.w67clement.mineapi.nms;

import com.w67clement.mineapi.system.ProtocolInjector;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class ProtocolManager implements Listener
{

    private ProtocolInjector injector;
    private ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(10);

    public ProtocolManager(ProtocolInjector injector)
    {
        this.injector = injector;
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
        if ((!player.isBanned()) && (e.getResult() == PlayerLoginEvent.Result.ALLOWED))
        {
            this.threadPool.schedule(() -> this.injector.addChannel(player), 100, TimeUnit.MILLISECONDS);
        }
    }

}
