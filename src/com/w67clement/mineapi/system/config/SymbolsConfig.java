package com.w67clement.mineapi.system.config;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class SymbolsConfig extends YamlConfig
{

	private HashMap<String, String> symbols;

	public SymbolsConfig(Plugin plugin) {
		super(plugin, new File(getPluginsFolder(), "General/symbols.yml"));
		this.init();
	}

	@Override
	public void init()
	{
		this.load();
	}

	@Override
	public void load()
	{
		if (!this.getFile().exists())
		{
			this.saveResource("symbols.yml",
					new File(getPluginsFolder(), "General/"), true);
		}

		this.configuration = YamlConfiguration
				.loadConfiguration(this.getFile());

		ConfigurationSection section = this.configuration
				.getConfigurationSection("symbols");

		if (this.symbols != null)
		{
			this.symbols.clear();
		}
		else
			this.symbols = new HashMap<String, String>();

		for (String keys : section.getKeys(false))
		{
			String value = section.getString(keys);
			if (!value.startsWith("\\u")) value = "\\u" + value;
			this.symbols.put(keys, value);
		}
	}

	/**
	 * Gets all symbols and the String to replace.
	 * @return HashMap with key and symbols.
	 */
	public HashMap<String, String> getSymbols()
	{
		return this.symbols;
	}

	/**
	 * Replace a String with the symbols in the configuration.
	 * @param input Text to replace with symbols.
	 * @return Text with symbols!
	 */
	public String replaceWithSymbols(String input)
	{
		return this.replaceWithSymbols(input, null);
	}

	/**
	 * Replace a String with the symbols in the configuration.
	 * @param input Text to replace with symbols.
	 * @param ignored List of the symbols ignored. Can be null.
	 * @return Text with symbols!
	 */
	public String replaceWithSymbols(String input, List<String> ignored)
	{
		String output = input;
		for (String keys : this.symbols.keySet())
		{
			String value = this.symbols.get(keys);
			if (ignored != null)
			{
				if (!ignored.contains(ignored))
					output = output.replace(keys, value);
			}
			else
				output = output.replace(keys, value);
		}
		return output;
	}

	/**
	 * Remove the symbols in a String.
	 * @param input Text to remove symbols.
	 * @return Text without symbols.
	 */
	public String removeSymbols(String input)
	{
		return this.removeSymbols(input, this.symbols.values()
				.toArray(new String[this.symbols.values().size()]));
	}

	/**
	 * Remove certain symbols in a String.
	 * @param input Text to remove Symbols.
	 * @param symbols The symbols need to remove.
	 * @return Text without certain symbols.
	 */
	public String removeSymbols(String input, String... symbols)
	{
		String output = input;
		for (String symbol : symbols)
		{
			output = output.replace(symbol, "");
		}
		return output;
	}

}
