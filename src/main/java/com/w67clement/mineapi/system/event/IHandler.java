package com.w67clement.mineapi.system.event;

import org.bukkit.entity.Player;

public interface IHandler {
	
	void addChannel(Player player);
	
	void removeChannel(Player player);
	
	void addServerConnectionChannel();

	void disable();

}
