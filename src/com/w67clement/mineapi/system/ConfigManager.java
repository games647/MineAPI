package com.w67clement.mineapi.system;

import com.w67clement.mineapi.MineAPI;
import com.w67clement.mineapi.system.config.SymbolsConfig;

public class ConfigManager
{

	private MineAPI mineapi;
	private SymbolsConfig symbolsConfig;

	public ConfigManager(MineAPI mineapi) {
		this.mineapi = mineapi;
	}

	/**
	 * Initialize configurations...
	 */
	public void init()
	{
		if (symbolsConfig == null)
			symbolsConfig = new SymbolsConfig(this.mineapi);
	}

	/**
	 * Gets the configuration for symbols.
	 * @return SymbolsConfig Object.
	 */
	public SymbolsConfig getSymbolsConfig()
	{
		return this.symbolsConfig;
	}

}
