package com.w67clement.mineapi.system;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.system.event.IHandler;
import com.w67clement.mineapi.system.event.INCHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ProtocolInjector
{

    private IHandler handler;

    public boolean createInjector(MineAPI mineapi)
    {
        if (MineAPI.isGlowstone())
        {
        }
        else if (MineAPI.isRainbow())
        {
        }
        else
        {
            try
            {
                MineAPI.sendMessageToConsole(MineAPI.PREFIX + ChatColor.GREEN + "Using INCHandler...");
                this.handler = new INCHandler(mineapi);
                return true;
            }
            catch (Throwable ignored)
            {

            }
        }
        return false;
    }

    public void injectModuleInjector(IHandler handler)
    {
        this.handler = handler;
    }

    public void addChannel(Player player)
    {
        this.handler.addChannel(player);
    }

    public void removeChannel(Player player)
    {
        this.handler.removeChannel(player);
    }

    public void addServerConnectionChannel()
    {
        this.handler.addServerConnectionChannel();
    }

    public void disable()
    {
        this.handler.disable();
    }

}
