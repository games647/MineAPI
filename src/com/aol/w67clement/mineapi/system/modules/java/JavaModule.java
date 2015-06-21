package com.aol.w67clement.mineapi.system.modules.java;

import org.bukkit.Bukkit;
import org.bukkit.Server;

import com.aol.w67clement.mineapi.MineAPI;
import com.aol.w67clement.mineapi.nms.NmsManager;
import com.aol.w67clement.mineapi.system.ModuleManager;
import com.aol.w67clement.mineapi.system.modules.Module;
import com.aol.w67clement.mineapi.system.modules.ModuleDescription;

public class JavaModule implements Module {

	private ModuleDescription description;
	private boolean isEnabled;

	@Override
	public String getName() {
		return this.description.getName();
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onDisable() {

	}

	@Override
	public ModuleDescription getModuleDescription() {
		return this.description;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void setDescription(ModuleDescription description) {
		if (this.description == null)
			this.description = description;
	}

	@Override
	public Server getServer() {
		return Bukkit.getServer();
	}

	@Override
	public NmsManager getNmsManager() {
		return MineAPI.getNmsManager();
	}

	@Override
	public ModuleManager getModuleManager() {
		return MineAPI.getModuleManager();
	}

}
