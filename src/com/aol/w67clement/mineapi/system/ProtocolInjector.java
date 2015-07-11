package com.aol.w67clement.mineapi.system;

import org.bukkit.entity.Player;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.system.event.IHandler;
import com.aol.w67clement.mineapi.system.event.INCHandler;

public class ProtocolInjector {
	
	private IHandler handler;
	
	public boolean createInjector(MineAPI mineapi) {
		try {
			this.handler = new INCHandler(mineapi);
			System.out.println(MineAPI.SIMPLE_PREFIX + "Using INCHandler...");
			return true;
		} catch (Throwable throwable) {
			
		}
		return false;
	}
	
	public void addChannel(Player player) {
		this.handler.addChannel(player);
	}
	
	public void removeChannel(Player player) {
		this.handler.removeChannel(player);
	}
	
	public void addServerConnectionChannel() {
		this.handler.addServerConnectionChannel();
	}

}
