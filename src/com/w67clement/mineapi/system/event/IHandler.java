package com.w67clement.mineapi.system.event;

import org.bukkit.entity.Player;

public interface IHandler {
	
	public void addChannel(Player player);
	
	public void removeChannel(Player player);
	
	public void addServerConnectionChannel();

}
