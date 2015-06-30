package com.aol.w67clement.mineapi;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MineAPI_AutoUpdater {

	private String latestVersion;
	private String version;
	private String latestlink;

	public MineAPI_AutoUpdater(boolean allowUpdate, MineAPI mineapi) {
		this.version = mineapi.getDescription().getVersion();
	}

	public boolean haveNewUpdate() {
		URLConnection connection;
		try {
			// Open connection
			connection = new URL("https://67clement.github.io/downloads/MineAPI_LatestVersion.txt").openConnection();
			connection.addRequestProperty("User-Agent", "MineAPI");
			// Read lines
			@SuppressWarnings("deprecation")
			FileConfiguration config = YamlConfiguration.loadConfiguration(connection.getInputStream());
			// Version found
			latestVersion = config.getString("LatestVersion", this.version);
			latestlink = config.getString("Download", "");
			// Check update;
			if (!latestVersion.equals(this.version)) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.latestVersion = version;
		return false;
	}

	public String getLatestVersion() {
		return this.latestVersion;
	}

	public String getLatestLink() {
		return latestlink;
	}
}
