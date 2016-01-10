package com.w67clement.mineapi.system;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.w67clement.mineapi.system.modules.InvalidModuleException;
import com.w67clement.mineapi.system.modules.Module;
import com.w67clement.mineapi.system.modules.ModuleLoader;

public class ModuleManager
{

	private ModuleLoader loader;

	public ModuleManager(ModuleLoader loader) {
		this.loader = loader;
	}

	/**
	 * Gets an MineAPI's module.
	 * 
	 * @param name
	 *            Name of the module.
	 * @return Object of the module.
	 */
	public Module getModule(String name)
	{
		return this.loader.getModules().get(name);
	}

	/**
	 * Check is module enabled by name.
	 * 
	 * @param name
	 *            Name of the module.
	 * @return Module Enable value.
	 */
	public boolean isModuleEnabled(String name)
	{
		return this.isModuleEnabled(getModule(name));
	}

	/**
	 * Check is module enabled by module Object.
	 * 
	 * @param module
	 *            Object of the module.
	 * @return Module enable value.
	 */
	public boolean isModuleEnabled(Module module)
	{
		if (module != null) return module.isEnabled();
		return false;
	}

	public Module loadModule(File file)
	{
		try
		{
			return this.loader.loadModule(file);
		}
		catch (InvalidModuleException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void enableModule(Module module)
	{
		this.loader.enableModule(module);
	}

	public void enableModules()
	{
		for (Module module : this.getModules())
		{
			if (!this.isModuleEnabled(module)) this.enableModule(module);
		}
	}

	public void disableModule(Module module)
	{
		this.loader.disableModule(module);
	}

	public void disableModules()
	{
		for (Module module : this.getModules())
		{
			if (this.isModuleEnabled(module)) this.disableModule(module);
		}
	}

	/**
	 * Lists the modules.
	 */
	public List<Module> getModules()
	{
		List<Module> modules = new ArrayList<Module>();
		for (String name : this.loader.getModules().keySet())
		{
			modules.add(this.getModule(name));
		}
		return modules;
	}
}
