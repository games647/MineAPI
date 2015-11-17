package com.w67clement.mineapi.system;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.system.event.IHandler;
import com.w67clement.mineapi.system.event.INCHandler;

public class ProtocolInjector
{

	private IHandler handler;

	public boolean createInjector(MineAPI mineapi)
	{
		try
		{
			this.handler = new INCHandler(mineapi);
			MineAPI.console.sendMessage(
					MineAPI.PREFIX + ChatColor.GREEN + "Using INCHandler...");
			return true;
		}
		catch (Throwable throwable)
		{

		}
		return false;
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

}
