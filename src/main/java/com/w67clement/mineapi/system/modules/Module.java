package com.w67clement.mineapi.system.modules;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.nms.NmsManager;
import com.w67clement.mineapi.system.ModuleManager;

public abstract class Module
{

	private ModuleInformations informations;
	private boolean isEnabled;
	protected MineAPI mineapi;
	protected boolean enable_startup = false;

	public String getName()
	{
		return this.informations.getName();
	}

	public abstract void onEnable();

	public abstract void onDisable();

	public ModuleInformations getModuleInformations()
	{
		return this.informations;
	}

	public boolean isEnabled()
	{
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled)
	{
		this.isEnabled = isEnabled;
	}

	public void setModuleInformations(ModuleInformations description)
	{
		if (this.informations == null) this.informations = description;
	}

	protected void setModuleInformations(ModuleInformations description,
			boolean force)
	{
		if (this.informations == null || force) this.informations = description;
	}

	public void setMineAPI(MineAPI mineapi)
	{
		this.mineapi = mineapi;
	}

	public NmsManager getNmsManager()
	{
		return MineAPI.getNmsManager();
	}

	public ModuleManager getModuleManager()
	{
		return MineAPI.getModuleManager();
	}

	public boolean isEnableStartup()
	{
		return this.enable_startup;
	}

}
