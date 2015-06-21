package com.aol.w67clement.mineapi.system.modules;

import org.bukkit.Server;

import com.aol.w67clement.mineapi.nms.NmsManager;
import com.aol.w67clement.mineapi.system.ModuleManager;

public interface Module {
	
	public String getName();
	
	public void onEnable();
	
	public void onDisable();
	
	public ModuleDescription getModuleDescription();
	
	public void setEnabled(boolean isEnabled);
	
	public boolean isEnabled();
	
	public Server getServer();
	
	public NmsManager getNmsManager();
	
	public ModuleManager getModuleManager();

}
