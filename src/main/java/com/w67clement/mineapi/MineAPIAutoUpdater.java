package com.w67clement.mineapi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class MineAPIAutoUpdater
{

	private String latestVersion;
	private String version;
	private String latestlink;

	public MineAPIAutoUpdater(boolean allowUpdate, MineAPI mineapi) {
		this.version = mineapi.getDescription().getVersion();
	}

	public boolean haveNewUpdate()
	{
		URLConnection connection;
		try
		{
			// Open connection
			connection = new URL(
					"https://w67clement.github.io/downloads/MineAPI_LatestVersion.txt")
							.openConnection();
			connection.addRequestProperty("User-Agent", "MineAPI");
			connection.addRequestProperty("Connection", "close");
			// Read lines
			final BufferedReader reader = new BufferedReader(
					new InputStreamReader(connection.getInputStream()));
			String response = reader.readLine();
			reader.close();

			YamlConfiguration config = new YamlConfiguration();
			try
			{
				config.loadFromString(response);
			}
			catch (InvalidConfigurationException e)
			{
				MineAPI.sendMessageToConsole(MineAPI.PREFIX + ChatColor.RED
						+ "Error: The response isn't Yaml.");
				MineAPI.sendMessageToConsole(MineAPI.PREFIX + ChatColor.RED
						+ "Error: Response: " + response);
				return false;
			}
			// Version found
			latestVersion = config.getString("LatestVersion", this.version);
			latestlink = config.getString("Download",
					"https://w67clement.github.io/downloads/MineAPI/MineAPI-"
							+ this.latestVersion + ".jar");
			// Check update;
			if (!latestVersion.equals(this.version))
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (IOException e)
		{
			// Error, MineAPI can't retrieve latest version informations...
		}
		this.latestVersion = version;
		return false;
	}

	public String getLatestVersion()
	{
		return this.latestVersion;
	}

	public String getLatestLink()
	{
		return latestlink;
	}
}
